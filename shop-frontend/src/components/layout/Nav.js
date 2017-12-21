import React from "react";
import { Link } from "react-router-dom";

export default class Nav extends React.Component {
  constructor() {
    super();
    this.state = {
      collapsed: true,
    };
  }

  toggleCollapse = () => {
    const collapsed = !this.state.collapsed;
    this.setState({collapsed});
  }

  render() {
    const { location } = this.props;
    const { collapsed } = this.state;
    const homepageClass = location.pathname === "/" ? "active" : "";
    const navClass = collapsed ? "" : "show";

    return (
      <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
        <Link to="/" onClick={this.toggleCollapse} class="navbar-brand">Simpleryo</Link>
        <button type="button" onClick={this.toggleCollapse} class="navbar-toggler" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
      
        <div id="navbarSupportedContent" class={"collapse navbar-collapse " + navClass}>
          <ul class="navbar-nav mr-auto">
            <li class={"nav-item " + homepageClass}>
              <Link to="/" onClick={this.toggleCollapse} class="nav-link">Goods</Link>
            </li>
            <li class={"nav-item " + homepageClass}>
              <Link to="/myorder" onClick={this.toggleCollapse} class="nav-link">MyOrder</Link>
            </li>
          </ul>
        </div>
      </nav>
    );
  }
}
