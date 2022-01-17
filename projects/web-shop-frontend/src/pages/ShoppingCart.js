import React, { useEffect, useState } from "react";
import "../assets/css/shoppingCartStyle.css";
import ArrowBackIcon from "@material-ui/icons/ArrowBack";
import { Link } from "react-router-dom";
import Item from "../components/shoppingCart/Item";
import { Snackbar } from "@material-ui/core";
import { Alert } from "@material-ui/lab";
import TokenService from "../services/TokenService";

export default function ShoppingCart(props) {
  const [open, setOpen] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertType, setAlertType] = React.useState("");
  const [alertDuration, setAlertDuration] = useState(2000);
  const [loggedUser, setLoggedUser] = useState(props.loggedUser);
  const [activeWebshop, setActiveWebshop] = useState(props.webshop);
  const [shoppingCartItems, setShoppingCartItems] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0.0);
  const [activeCurrency, setActiveCurrency] = useState("");

  useEffect(() => {
    setLoggedUser(props.loggedUser);
    setActiveWebshop(props.webshop);
    if (JSON.parse(localStorage.getItem("shoppingCart")) !== null) {
      setShoppingCartItems(JSON.parse(localStorage.getItem("shoppingCart")));
    }
    checkActiveCurrency();
    calculateTotalPrice();
  }, [props.webshop, props.loggedUser]);

  const calculateTotalPrice = () => {
    var totalPrice = 0.0;
    var shoppingCartItems = JSON.parse(localStorage.getItem("shoppingCart"));
    for (let i = 0; i < shoppingCartItems.length; i++) {
      totalPrice += shoppingCartItems[i].price * shoppingCartItems[i].quantity;
    }
    setTotalPrice(totalPrice);
  };

  const checkActiveCurrency = () => {
    var shoppingCartItems = JSON.parse(localStorage.getItem("shoppingCart"));
    if (shoppingCartItems.length > 0) {
      setActiveCurrency(shoppingCartItems[0].currency);
    }
  };

  const handleRemovedItem = (removed) => {
    if (removed) {
      handleAlertClick(
        "Item is successfully removed from shopping cart",
        "success"
      );
      setShoppingCartItems(JSON.parse(localStorage.getItem("shoppingCart")));
      calculateTotalPrice();
    }
  };

  const handleCheckoutClick = () => {
    var itemsToPurchase = [];
    var shoppingCartItems = JSON.parse(localStorage.getItem("shoppingCart"));
    if (shoppingCartItems.length === 0) {
      handleAlertClick("Shopping cart is empty!", "error");
      return;
    }
    for (let i = 0; i < shoppingCartItems.length; i++) {
      var itemToPurchase = {};
      itemToPurchase.productId = shoppingCartItems[i].id;
      itemToPurchase.quantity = shoppingCartItems[i].quantity;
      itemToPurchase.isPossibleSubscription = shoppingCartItems[i].isCourse;
      itemsToPurchase.push(itemToPurchase);
    }
    var shoppingCart = {
      userId: TokenService.getUser().id,
      webShopId: JSON.parse(sessionStorage.getItem("activeWebShop")).id,
      totalPrice: totalPrice,
      currency: activeCurrency,
      itemsToPurchase: itemsToPurchase,
    };
    props.saveShoppingCart(shoppingCart);
  };

  const items = Array.apply(null, {
    length: shoppingCartItems.length,
  }).map((_, i) => (
    <Item
      key={i}
      item={shoppingCartItems[i]}
      removed={handleRemovedItem}
      changedQuantity={calculateTotalPrice}
    />
  ));

  const handleAlertClick = (message, type) => {
    setAlertMessage(message);
    setAlertType(type);
    setAlertDuration(2500);
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
    <div className="shoppingCartCard">
      <div className="row">
        <div className="col-md-8 cart">
          <div className="titleShoppingCart">
            <div className="row">
              <div className="col">
                <h4>
                  <b>Shopping Cart</b>
                </h4>
              </div>
              <div className="col align-self-center text-right text-muted">
                {shoppingCartItems.length} items
              </div>
            </div>
          </div>
          <div className="shoppingCartScroll">{items}</div>
          <div className="back-to-shop">
            <Link
              to={{
                pathname: `/webshop/${activeWebshop}/${activeCurrency}`,
              }}
              style={{ textDecoration: "none", color: "black" }}
            >
              <ArrowBackIcon />
              <span className="text-muted"> Back to shop</span>
            </Link>
          </div>
        </div>
        <div className="col-md-4 summaryShoppingCart">
          <div>
            <h5 className="h5ShoppingCart">
              <b>Delivery info</b>
            </h5>
          </div>
          <hr className="hrShoppingCart" />
          <div className="row">
            <div className="col" style={{ paddingLeft: 0 }}>
              NAME
            </div>
            <div className="col text-right">{loggedUser.fullName}</div>
          </div>
          <hr className="hrShoppingCart" />
          <div className="row">
            <div className="col" style={{ paddingLeft: 0 }}>
              ADDRESS
            </div>
            <div className="row text-right">{loggedUser.address}</div>
          </div>
          <hr className="hrShoppingCart" />
          <div className="row">
            <div className="col" style={{ paddingLeft: 0 }}>
              PHONE
            </div>
            <div className="col text-right">{loggedUser.phone}</div>
          </div>
          <hr className="hrShoppingCart" />
          <div>
            <h5 className="h5ShoppingCart">
              <b>Summary</b>
            </h5>
          </div>
          <hr className="hrShoppingCart" />
          <div className="row">
            <div className="col" style={{ paddingLeft: 0 }}>
              ITEMS
            </div>
            <div className="col text-right">{shoppingCartItems.length}</div>
          </div>
          <hr className="hrShoppingCart" />
          <div className="row">
            <div className="col" style={{ paddingLeft: 0 }}>
              TOTAL PRICE
            </div>
            <div className="col text-right">
              {totalPrice} {activeCurrency}
            </div>
          </div>
          <Link
            type="button"
            variant="contained"
            color="primary"
            to={{
              pathname: `/shopping-cart/${activeWebshop}/${activeCurrency}`,
            }}
            className="checkoutButton"
            style={{ textDecoration: "none", color: "white" }}
            onClick={handleCheckoutClick}
          >
            CHECKOUT
          </Link>
        </div>
      </div>
      {snackbar}
    </div>
  );
}
