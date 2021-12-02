import { Snackbar } from "@material-ui/core";
import { Alert } from "@material-ui/lab";
import React, { useEffect, useState } from "react";
import "../../assets/css/productCardStyle.css";
import TokenService from "../../services/TokenService";

export default function ProductCard(props) {
  const [product, setProduct] = useState(props.product);
  const [open, setOpen] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertType, setAlertType] = React.useState("");
  const [alertDuration, setAlertDuration] = useState(1500);

  useEffect(() => {
    setProduct(props.product);
  }, [props.product]);

  const addToShoppingCart = () => {
    if (!TokenService.getUser()) {
      handleAlertClick(
        "You must sign in to add product in shopping cart",
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
      if (shoppingCart[i].id === props.product.id) {
        handleAlertClick("Product is already in shopping cart", "error");
        return;
      }
    }
    handleAlertClick(
      "Product is successfully added in shopping cart",
      "success"
    );
    var product = props.product;
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
      {snackbar}
      <div className="productCard-details">
        <h1>{product.name}</h1>
        <p className="productCardInformation">{product.description}</p>
        <div className="productCardControl">
          <button className="productCardBtn" onClick={addToShoppingCart}>
            <span className="productCardPrice">
              {product.price} {product.currency}
            </span>
            <span className="productCardShopping-cart">
              <i className="fa fa-shopping-cart" aria-hidden="true"></i>
            </span>
            <span className="productCardBuy">Get now</span>
          </button>
        </div>
      </div>

      <div className="product-image">
        <img src={product.image} alt="Product" />
        <div className="productCardInfo">
          <h2>Purchase info</h2>
          <ul>
            <li>
              <strong>Name: </strong>
              {product.name}
            </li>
            <li>
              <strong>Price: </strong>
              {product.price}
            </li>
            <li>
              <strong>Currency: </strong>
              {product.currency}
            </li>
            <li>
              <strong>Available balance: </strong>
              {product.availableBalance}
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
}
