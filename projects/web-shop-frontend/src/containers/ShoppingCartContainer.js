import React, { useEffect, useState } from "react";
import ShoppingCart from "../pages/ShoppingCart";
import PaymentService from "../services/PaymentService";
import TokenService from "../services/TokenService";
import UserService from "../services/UserService";

export default function ShoppingCartContainer(props) {
  const [shoppingCartItems, setShoppingCartItems] = useState([]);
  const [loggedUser, setLoggedUser] = useState({});

  useEffect(() => {
    setShoppingCartItems(JSON.parse(localStorage.getItem("shoppingCart")));
    async function fetchData() {
      var loggedUser = await UserService.findById(TokenService.getUser().id);
      setLoggedUser(loggedUser);
    }
    fetchData();
  }, []);

  const handleSaveShoppingCart = async (shoppingCart) => {
    try {
      await PaymentService.pay(shoppingCart)
        .then((response) => {
          localStorage.removeItem("shoppingCart");
          localStorage.setItem("shoppingCart", JSON.stringify([]));
          window.location.href = response;
        })
        .catch((error) => {
          alert(error);
        });
    } catch (error) {
      alert(error);
    }
  };

  return (
    <ShoppingCart
      webshop={props.match.params.webshop}
      shoppingCartItems={shoppingCartItems}
      loggedUser={loggedUser}
      saveShoppingCart={handleSaveShoppingCart}
    />
  );
}
