import React from "react";
import {
  Route,
  Switch
} from "react-router-dom";

import Footer from "../components/layout/Footer";
import Nav from "../components/layout/Nav";

import Goods from "../pages/Goods";
import MyOrder from "../pages/MyOrder";

export default class Layout extends React.Component {
  render() {
    const { location } = this.props;
    return (
      <div>
        <Nav location={location} />

        <main role="main" class="container main">
          <div class="row">
            <div class="col-lg-12">
              <Switch>
                <Route path="/" exact component={Goods}></Route>
                <Route path="/myorder" exact component={MyOrder}></Route>
              </Switch>

            </div>
          </div>
        </main>

        <Footer/>
      </div>

    );
  }
}
