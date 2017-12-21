import React, { Component } from 'react';
import GoodRow from './GoodRow'

class GoodList extends Component {
    render() {
        // console.log(this.props);
        const {goods} = this.props;
        const goodComponents = goods.map((good) => {
            return <GoodRow key={good.id} {...good} />
        });

        return (
            <table class="table">
                <thead>
                    <tr>
                        <th>商品</th>
                        <th>库存</th>
                    </tr>
                </thead>
                <tbody>
                    {goodComponents}
                </tbody>
            </table>
        );
    }
}

export default GoodList;