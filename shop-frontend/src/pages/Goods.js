import React from "react";
import {
  Redirect
} from "react-router-dom";

import _ from 'lodash';

import GoodList from '../components/good/GoodList'
import BuyList from '../components/buy/BuyList'

import * as GoodActions from '../actions/GoodActions';
import GoodStore from "../stores/GoodStore";

export default class Goods extends React.Component {
  constructor() {
    super();
    const {goods, orders} = this.getGoods();
    this.state = {
      goods: goods,
      orders: orders,
      jump: false,
      showMsg: false,
      msgs: []
    };
  }

  componentWillMount() {
    GoodStore.on("change", this.refresh);
  }

  componentWillUnmount() {
    GoodStore.removeListener("change", this.refresh);
  }

  refresh = () => {
    this.setState(
      this.getGoods()
    );
  }

  getGoods = () => {
    const {goods, jump, msg} = GoodStore.getAll();
    const orders = _.map(goods, (value, index, arrs) => {
      return {
        id: value.id,
        name:value.name,
        buyAmount: 0
      };
    })
    let msgs = new Array(),
      showMsg = false;
    if (msg != '') {
      showMsg = true;
      msgs.push(msg);
    }
    return {
      goods: goods,
      orders: orders,
      jump: jump,
      showMsg: showMsg,
      msgs: msgs
    };
  }
  
  buy = () => {
    const {orders} = this.state;
    let canBuy = true;
    let msgs = new Array();
    _.each(orders, (value, index, arrs) => {
      const checkResult = this.checkOrder(value);
      if (checkResult.isOk) {
        value.buyAmount = checkResult.buyAmount;
      } else {
        msgs.push(checkResult.msg);
        canBuy = false;
      }
    })

    if (canBuy) {
      let buyAnything = false;
      _.each(orders, (value, index, arrs) => {
        if (parseInt(value.buyAmount) > 0) {
          buyAnything = true;
        }
      })

      if (!buyAnything) {
        msgs.push("请至少选购一件商品购买。");
        canBuy = false;
      }
    }

    if (canBuy) {
      GoodActions.sellGoods(this.state.orders);
    } else {
      this.setState({
        showMsg: true,
        msgs:msgs
      })
    }
  }

  
  checkOrder(order) {
    const {id, name, buyAmount} = order;
    
    let {goods} = this.state;
    const matchGood = _.find(goods, {id: id});
    
    let checkResult = {
      isOk: true,
      msg: ''
    };
    if (buyAmount != '') {
      if (/^(([1-9][0-9]*)|0)$/.test(buyAmount)) {

        if (parseInt(buyAmount) > parseInt(matchGood.amount)) {
          checkResult = {
            isOk: false,
            msg: name + "库存不足。"
          };
        }
      } else {
        checkResult = {
          isOk: false,
          msg: name + "的购买数量不是合法数字。"
        };
      }
      checkResult.buyAmount = buyAmount;
    } else {
      checkResult.buyAmount = '0';
    }
    return checkResult;
  }

  setBuyAmount(order) {
    let {orders} = this.state;
    const match = _.find(orders, {id: order.id});
    if(match){
      var index = _.indexOf(orders, _.find(orders, {id: order.id}));
      orders.splice(index, 1, order);
    } else {
      orders.push(order);
    }
  }

  render() {
    const {jump, showMsg, msgs} = this.state;

    if (jump) {
      const jumpTo = { pathname: '/myorder' };
      return (
        <Redirect to={jumpTo}/>
      );
    }

    const goodsStyle = {
      minHeight: "300px"
    }
    const buysStyle = {
      minHeight: "300px",
      marginTop: "30px"
    }
    
    const isShowMsg = showMsg ? {display: "block"} : {display: "none"};
    
    const msgComponents = _.map(msgs, (value, index, arrs) => {
      return (
        <p key={index}>{value}</p>
      );
    })

    return (
      <div>
        <div style={goodsStyle}>
          <GoodList goods={this.state.goods} />
        </div>
        <div style={buysStyle}>
          <BuyList goods={this.state.goods} setBuyAmount={(order) => {this.setBuyAmount(order)}} />
          <button type="button" onClick={this.buy} class="btn btn-primary">Buy</button>
          
          <div class="row alert alert-primary" style={isShowMsg} >
            {msgComponents}
          </div>
        </div>
      </div>
    );
  }
}
