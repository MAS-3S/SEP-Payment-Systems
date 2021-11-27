import React, { Component } from "react";

export default class LayoutAuthenticated extends Component {
  render() {
    return (
      <div className="container">
        <div className="wrapper">{this.props.children}</div>
      </div>
    );
  }
}
