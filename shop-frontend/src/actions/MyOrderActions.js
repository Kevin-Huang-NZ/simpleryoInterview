import dispatcher from '../dispatcher';
import axios from 'axios';

export function loadMyOrder() {
    // axios.get('testdata/myorder.json', {
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
} 