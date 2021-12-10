import { Snackbar } from "@material-ui/core";
import { Alert } from "@material-ui/lab";
import React, { useEffect, useState } from "react";
import "../../assets/css/productCardStyle.css";
import TokenService from "../../services/TokenService";

export default function AccommodationCard(props) {
  const [accommodation, setAccommodation] = useState(props.accommodation);
  const [open, setOpen] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertType, setAlertType] = React.useState("");
  const [alertDuration, setAlertDuration] = useState(1500);

  useEffect(() => {
    setAccommodation(props.accommodation);
  }, [props.accommodation]);

  const addToShoppingCart = () => {
    if (!TokenService.getUser()) {
      handleAlertClick(
        "You must sign in to add accommodation in shopping cart",
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
      }
    }
    for (let i = 0; i < shoppingCart.length; i++) {
      if (shoppingCart[i].id === props.accommodation.id) {
        handleAlertClick("Accommodation is already in shopping cart", "error");
        return;
      }
    }
    handleAlertClick(
      "Accommodation is successfully added in shopping cart",
      "success"
    );
    var product = props.accommodation;
    product.quantity = 1;
    product.webShopId = JSON.parse(sessionStorage.getItem("activeWebShop")).id;
    shoppingCart.push(product);
    localStorage.setItem("shoppingCart", JSON.stringify(shoppingCart));
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
        <h1>{accommodation.name}</h1>
        <p className="productCardInformation">{accommodation.description}</p>
        <div className="productCardControl">
          <button className="productCardBtn" onClick={addToShoppingCart}>
            <span className="productCardPrice">
              {accommodation.price} {accommodation.currency}
            </span>
            <span className="productCardShopping-cart">
              <i className="fa fa-shopping-cart" aria-hidden="true"></i>
            </span>
            <span className="productCardBuy">Get now</span>
          </button>
        </div>
      </div>

      <div className="product-image">
        <img src={accommodation.image} alt="Accommodation" />
        <div className="productCardInfo">
          <h2>Purchase info</h2>
          <ul>
            <li>
              <strong>Name: </strong>
              {accommodation.name}
            </li>
            <li>
              <strong>Available balance: </strong>
              {accommodation.availableBalance}
            </li>
            <li>
              <strong>Address: </strong>
              {accommodation.address}
            </li>
            <li>
              <strong>Start date: </strong>
              {accommodation.startDate}
            </li>
            <li>
              <strong>Days: </strong>
              {accommodation.days}
            </li>
            <li>
              <strong>Number of beds: </strong>
              {accommodation.numberOfBeds}
            </li>
            <li>
              <strong>Transport: </strong>
              {accommodation.transportName}
            </li>
            {/* <li>
              <strong>Transport price: </strong>
              {accommodation.transportPrice}
            </li> */}
          </ul>
        </div>
      </div>
      {snackbar}
    </div>
  );
}
