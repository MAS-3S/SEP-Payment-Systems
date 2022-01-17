import { Snackbar } from "@material-ui/core";
import { Alert } from "@material-ui/lab";
import React, { useEffect, useState } from "react";
import "../../assets/css/productCardStyle.css";
import TokenService from "../../services/TokenService";

export default function ConferenceCard(props) {
  const [conference, setConference] = useState(props.conference);
  const [activeCurrency, setActiveCurrency] = useState(props.activeCurrency);
  const [open, setOpen] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertType, setAlertType] = React.useState("");
  const [alertDuration, setAlertDuration] = useState(1500);

  useEffect(() => {
    setConference(props.conference);
    setActiveCurrency(props.activeCurrency);
  }, [props.conference, props.activeCurrency]);

  const addToShoppingCart = () => {
    if (!TokenService.getUser()) {
      handleAlertClick(
        "You must sign in to add conference in shopping cart",
        "error"
      );
      return;
    }
    var shoppingCart = [];
    shoppingCart = JSON.parse(localStorage.getItem("shoppingCart"));
    if (shoppingCart.length > 0) {
      if (
        shoppingCart[0].webShopId !==
        JSON.parse(sessionStorage.getItem("activeWebShop")).id
      ) {
        handleAlertClick(
          "You already have products from another web shop in your shopping card!",
          "error"
        );
        return;
      } else if (shoppingCart[0].currency !== activeCurrency) {
        handleAlertClick(
          "You can't add product with another currency in your shopping card!",
          "error"
        );
        return;
      }
    }
    for (let i = 0; i < shoppingCart.length; i++) {
      if (shoppingCart[i].id === props.conference.id) {
        if (shoppingCart[i].isConference === true) {
          handleAlertClick("Conference is already in shopping cart", "error");
          return;
        } else if (shoppingCart[i].isCourse === true) {
          handleAlertClick("Course is already in shopping cart", "error");
          return;
        }
      } else if (shoppingCart[i].isConference === true) {
        handleAlertClick(
          "You can not add course if you have have conference in shopping cart",
          "error"
        );
        return;
      } else if (shoppingCart[i].isCourse === true) {
        handleAlertClick(
          "You can not add conference if you have have course in shopping cart",
          "error"
        );
        return;
      }
    }
    var product = props.conference;
    if (product.name.includes("course")) {
      product.isCourse = true;
      handleAlertClick(
        "Course is successfully added in shopping cart",
        "success"
      );
    } else if (product.name.includes("conference")) {
      product.isConference = true;
      handleAlertClick(
        "Conference is successfully added in shopping cart",
        "success"
      );
    }
    product.webShopId = JSON.parse(sessionStorage.getItem("activeWebShop")).id;
    product.quantity = 1;
    product.currency = activeCurrency;
    product.price = convertCurrency(product.price);
    shoppingCart.push(product);
    localStorage.setItem("shoppingCart", JSON.stringify(shoppingCart));
  };

  const convertCurrency = (amount) => {
    if (activeCurrency === "EUR") {
      return amount;
    } else if (activeCurrency === "USD") {
      return Math.round(amount * 1.13 * 100) / 100;
    }
  };

  const handleAlertClick = (message, type) => {
    setAlertMessage(message);
    setAlertType(type);
    setAlertDuration(2300);
    setOpen(true);
  };

  const handleAlertClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }
    setOpen(false);
  };

  const snackbar = (
    <Snackbar
      open={open}
      autoHideDuration={alertDuration}
      onClose={handleAlertClose}
      anchorOrigin={{ vertical: "top", horizontal: "center" }}
    >
      <Alert onClose={handleAlertClose} severity={alertType}>
        {alertMessage}
      </Alert>
    </Snackbar>
  );

  return (
    <div id="productCardContainer">
      <div className="productCard-details">
        <h1>{conference.name}</h1>
        <p className="productCardInformation">{conference.description}</p>
        <div className="productCardControl">
          <button className="productCardBtn" onClick={addToShoppingCart}>
            <span className="productCardPrice">
              {convertCurrency(conference.price)} {activeCurrency}
            </span>
            <span className="productCardShopping-cart">
              <i className="fa fa-shopping-cart" aria-hidden="true"></i>
            </span>
            <span className="productCardBuy">Get now</span>
          </button>
        </div>
      </div>

      <div className="product-image">
        <img src={conference.image} alt="Conference" />
        <div className="productCardInfo">
          <h2>Purchase info</h2>
          <ul>
            <li>
              <strong>Name: </strong>
              {conference.name}
            </li>
            <li>
              <strong>Available balance: </strong>
              {conference.availableBalance}
            </li>
            <li>
              <strong>Address: </strong>
              {conference.address}
            </li>
            <li>
              <strong>Start time: </strong>
              {conference.startTime}
            </li>
            <li>
              <strong>End time: </strong>
              {conference.endTime}
            </li>
          </ul>
        </div>
      </div>
      {snackbar}
    </div>
  );
}
