import React, { Component } from 'react';

class GoodRow extends Component {
    constructor(props) {
        super();
        const {id} = props;
        this.state = {
            id: id
        }
    }
    render() {
        const {name, amount} = this.props;
        return (
            <tr>
                <td>{name}</td>
                <td>{amount}</td>
            </tr>
        );
    }
}

export default GoodRow;