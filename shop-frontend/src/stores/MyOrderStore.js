import {EventEmitter} from 'events';
import _ from 'lodash';
import dispatcher from '../dispatcher';
import * as MyOrderActions from '../actions/MyOrderActions';

class MyOrderStore extends EventEmitter {
    constructor() {
        super();
        this.id = '';
        this.buyDate = '';
        this.details = [];
    }

    getAll() {
        return {
            id: this.id,
            buyDate: this.buyDate,
            details: this.details
        };
    }

    handleActions(action) {
        switch(action.actionType) {
            case "LOAD_MYORDER": {
                this.id = action.order.id;
                this.buyDate = action.order.buyDate;
                this.details = action.order.details;
                this.emit("change");
                break;
            }
        }
    }
}

const myOrderStore = new MyOrderStore();

dispatcher.register(myOrderStore.handleActions.bind(myOrderStore));

MyOrderActions.loadMyOrder();
// window.dispatcher = dispatcher;

export default myOrderStore;