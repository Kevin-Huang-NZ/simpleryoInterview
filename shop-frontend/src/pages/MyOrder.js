import React, { Component } from 'react';
import {
    Link
} from "react-router-dom";

import _ from 'lodash';

import OrderDetailList from '../components/order/OrderDetailList'

import * as MyOrderActions from '../actions/MyOrderActions';
import MyOrderStore from "../stores/MyOrderStore";

class MyOrder extends Component {
    constructor() {
      super();
      this.state = this.getOrder();
    }  

    componentWillMount() {
        MyOrderStore.on("change", this.refresh);
    }

    componentWillUnmount() {
        MyOrderStore.removeListener("change", this.refresh);
    }
    
    refresh = () => {
        this.setState(
            this.getOrder()
        );
    }

    getOrder = () => {
        const order = MyOrderStore.getAll();
        return {
            order: order
        }
    }

    render() {
        const {id, buyDate, details} = this.state.order;
        return (
            <div>
                <div class="row alert alert-primary" >
                    <h2>感谢您的购物。</h2>
                </div>
                <hr/>
                <p>购买时间：{buyDate}</p>
                <div class="container">
                    <OrderDetailList details={details} />
                </div>
                <Link to="/" class="btn btn-default">继续购买</Link>
            </div>
        );
    }
}

export default MyOrder;