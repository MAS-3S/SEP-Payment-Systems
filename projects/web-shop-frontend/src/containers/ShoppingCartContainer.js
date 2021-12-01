import React, { useEffect, useState } from "react";
import ShoppingCart from "../pages/ShoppingCart";

export default function ShoppingCartContainer(props) {
  const [shoppingCartItems, setShoppingCartItems] = useState([]);

  useEffect(() => {
    setShoppingCartItems(JSON.parse(localStorage.getItem("shoppingCart")));
  }, []);

  return (
    <ShoppingCart
      webshop={props.match.params.webshop}
      shoppingCartItems={shoppingCartItems}
    />
  );
}
