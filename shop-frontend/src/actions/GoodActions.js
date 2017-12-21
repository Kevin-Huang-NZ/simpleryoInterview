import dispatcher from '../dispatcher';
import axios from 'axios';
import _ from 'lodash';

export function sellGoods(orders) {
    const params = _.map(orders, (value,index,arrs) => {
        return {
            "id": value.id,
            "buyAmount": value.buyAmount
        }
    })
    axios.post('http://localhost:8080/api/orders',params)
    .then(function (response) {
        // console.log(response);
        const {success} = response.data;
        if (success) {
            const {data} = response.data;
            dispatcher.dispatch({
                actionType: "RELOAD_GOODS",
                goods: data,
                jump: true,
                msg: ''
            })
            
            axios.get('http://localhost:8080/api/orders/latest', {
                params:{
                }
            })
            .then(function (response) {
                // console.log(response);
                const {data} = response.data;
                dispatcher.dispatch({
                    actionType: "LOAD_MYORDER",
                    order: data
                })
            })
            .catch(function (error) {
            console.log(error);
            });
        } else {
            const {message} = response.data;
            
            axios.get('http://localhost:8080/api/goods/', {
                params:{
                }
            })
            .then(function (response) {
                // console.log(response);
                const {data} = response.data;
                dispatcher.dispatch({
                    actionType: "RELOAD_GOODS",
                    goods: data,
                    jump: false,
                    msg: message
                })
            })
            .catch(function (error) {
                console.log(error);
            });
        }
    })
    .catch(function (error) {
      console.log(error);
    });

}

export function loadGoods() {
    // axios.get('testdata/goods.json', {
    axios.get('http://localhost:8080/api/goods/', {
        params:{
        }
    })
    .then(function (response) {
        // console.log(response);
        const {data} = response.data;
        dispatcher.dispatch({
            actionType: "LOAD_GOODS",
            goods: data
        })
    })
    .catch(function (error) {
      console.log(error);
    });
} 