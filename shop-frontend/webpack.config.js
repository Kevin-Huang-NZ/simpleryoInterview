var debug = false;
var webpack = require('webpack');
var path = require('path');

module.exports = {
    devtool: debug ? "inline-sourcemap" : false,
    context: path.resolve(__dirname, './'),
    entry: {
        app: path.join(__dirname, 'src', 'index.js')
    },
    module: {
        rules: [{
            test: /\.js(x)?$/,
            exclude: /(node_modules|vendor)/,
            loader: 'babel-loader',
            query: {
                presets: [
                    'react', ['es2015', { modules: false }], 'stage-2'
                ],
                plugins: ['react-html-attrs', 'transform-decorators-legacy', 'transform-class-properties'],
            }
        }]
    },
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: "[name].js"
    },
    devServer: {
        contentBase: path.resolve(__dirname, 'dist'),
        port: 80
    },
    plugins: debug ? [] : [
        new webpack.EnvironmentPlugin({
            NODE_ENV: JSON.stringify("production")
        }),
        new webpack.optimize.CommonsChunkPlugin({
            name: 'vendor',
            minChunks: function(module) {
                return module.context && module.context.indexOf('node_modules') !== -1;
            }
        }),
        new webpack.optimize.CommonsChunkPlugin({ name: 'manifest', chunks: ['vendor'] })
    ],
};

