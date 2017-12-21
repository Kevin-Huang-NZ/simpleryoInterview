import React, { Component } from 'react';
import OrderDetailRow from './OrderDetailRow'

class OrderDetailList extends Component {
    render() {
        const {details} = this.props;
        const detailComponents = details.map((detail) => {
            return <OrderDetailRow key={detail.id} {...detail} />
        });

        return (
            <table class="table">
                <thead>
                    <tr>
                        <th>商品</th>
                        <th>购买数量</th>
                    </tr>
                </thead>
                <tbody>
                    {detailComponents}
                </tbody>
            </table>
        );
    }
}

export default OrderDetailList;