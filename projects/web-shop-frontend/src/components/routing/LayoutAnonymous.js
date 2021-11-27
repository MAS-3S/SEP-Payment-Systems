import React, { Component } from "react";

export default class LayoutAnonymous extends Component {
  render() {
    return <div>{this.props.children}</div>;
  }
}
