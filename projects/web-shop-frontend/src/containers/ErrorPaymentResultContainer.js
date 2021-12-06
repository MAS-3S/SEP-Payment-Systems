import React, { useEffect, useState } from "react";
import PaymentResult from "../pages/PaymentResult";
import TokenService from "../services/TokenService";
import UserService from "../services/UserService";

export default function ErrorPaymentResultContainer(props) {
  const [shoppingCartItems, setShoppingCartItems] = useState([]);
  const [loggedUser, setLoggedUser] = useState({});

  useEffect(() => {
    setShoppingCartItems(JSON.parse(localStorage.getItem("shoppingCart"))); // pogoditi endpoint transakcije po id
    async function fetchData() {
      var loggedUser = await UserService.findById(TokenService.getUser().id);
      setLoggedUser(loggedUser);
    }
    fetchData();
  }, []);

  return (
    <PaymentResult
      title="Unsuccessful purchase - error"
      titleColor="#dc5050"
      color="#f6d0d0"
      shoppingCartItems={shoppingCartItems}
      loggedUser={loggedUser}
    />
  );
}
