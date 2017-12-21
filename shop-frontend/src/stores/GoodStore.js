import {EventEmitter} from 'events';
import _ from 'lodash';
import dispatcher from '../dispatcher';
import * as GoodActions from '../actions/GoodActions';

class GoodStore extends EventEmitter {
    constructor() {
        super();
        this.goods = [];
        this.jump = false;
        this.msg = '';
    }

    getAll() {
        return {
            goods: this.goods,
            jump: this.jump,
            msg: this.msg
        };
    }


    handleActions(action) {
        // console.log(action);
        switch(action.actionType) {
            case "LOAD_GOODS": {
                this.goods = action.goods;
                this.jump = false;
                this.msg = '';
                this.emit("change");
                break;
            }
            case "RELOAD_GOODS": {
                this.goods = action.goods;
                this.jump = action.jump;
                this.msg = action.msg;
                this.emit("change");
                break;
            }
        }
    }
}

const goodStore = new GoodStore();

dispatcher.register(goodStore.handleActions.bind(goodStore));

GoodActions.loadGoods();
// window.dispatcher = dispatcher;

export default goodStore;