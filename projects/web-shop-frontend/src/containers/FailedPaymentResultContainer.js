import React, { useEffect, useState } from "react";
import PaymentResult from "../pages/PaymentResult";
import TokenService from "../services/TokenService";
import UserService from "../services/UserService";

export default function FailedPaymentResultContainer(props) {
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
      title="Failed purchase"
      titleColor="#ffae19"
      color="#ffe6ba"
      shoppingCartItems={shoppingCartItems}
      loggedUser={loggedUser}
    />
  );
}
