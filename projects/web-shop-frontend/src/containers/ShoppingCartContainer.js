import React from "react";
import ShoppingCart from "../pages/ShoppingCart";

export default function ShoppingCartContainer(props) {
  return <ShoppingCart webshop={props.match.params.webshop} />;
}
