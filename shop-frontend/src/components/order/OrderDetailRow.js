import React, { Component } from 'react';

class OrderDetailRow extends Component {
    constructor(props) {
        super();
        const {id} = props;
        this.state = {
            id: id
        }
    }
    render() {
        const {name, buyAmount} = this.props;
        return (
            <tr>
                <td>{name}</td>
                <td>{buyAmount}</td>
            </tr>
        );
    }
}

export default OrderDetailRow;