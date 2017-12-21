import React, { Component } from 'react';

class BuyRow extends Component {
    constructor(props) {
        super();
        const {id, name} = props;
        this.state = {
            id: id,
            name: name
        }
    }

    buy = () =>{
        const {id, name} = this.state;
        const retBuyAmount = this.refs.buyAmount.value;
        this.props.setBuyAmount({
            id: id,
            name: name,
            buyAmount: retBuyAmount
        });
    }

    render() {
        const {name} = this.state;

        return (
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">{name}</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" ref="buyAmount" onBlur={this.buy} />
                </div>
            </div>
        );
    }
}

export default BuyRow;