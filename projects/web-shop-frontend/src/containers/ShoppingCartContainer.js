import React, { useEffect, useState } from "react";
import ShoppingCart from "../pages/ShoppingCart";
import AuthService from "../services/AuthService";
import UserService from "../services/UserService";

export default function ShoppingCartContainer(props) {
  const [shoppingCartItems, setShoppingCartItems] = useState([]);
  const [loggedUser, setLoggedUser] = useState({});

  useEffect(() => {
    setShoppingCartItems(JSON.parse(localStorage.getItem("shoppingCart")));
    async function fetchData() {
      var loggedUser = await UserService.findById(
        AuthService.getCurrentUser().id
      );
      setLoggedUser(loggedUser);
    }
    fetchData();
  }, []);

  return (
    <ShoppingCart
      webshop={props.match.params.webshop}
      shoppingCartItems={shoppingCartItems}
      loggedUser={loggedUser}
    />
  );
}
