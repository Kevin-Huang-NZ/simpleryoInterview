import React, { Component } from 'react';
import BuyRow from './BuyRow'

class BuyList extends Component {
    render() {
        const {goods, setBuyAmount} = this.props;
        const buyComponents = goods.map((good) => {
            return <BuyRow key={good.id} setBuyAmount={setBuyAmount} {...good} />
        });

        return (
            <div>
                {buyComponents}
            </div> 
        );
    }
}

export default BuyList;