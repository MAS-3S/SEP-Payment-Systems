import React, { useEffect, useState } from "react";
import "../assets/css/shoppingCartStyle.css";
import ArrowBackIcon from "@material-ui/icons/ArrowBack";
import { Link } from "react-router-dom";
import PaymentResultItem from "../components/paymentResult/PaymentResultItem";

export default function PaymentResult(props) {
  const [loggedUser, setLoggedUser] = useState(props.loggedUser);
  const [shoppingCartItems, setShoppingCartItems] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0.0);
  const [color, setColor] = useState(props.color);
  const [title, setTitle] = useState(props.title);
  const [titleColor, setTitleColor] = useState(props.titleColor);

  useEffect(() => {
    setLoggedUser(props.loggedUser);
    if (JSON.parse(localStorage.getItem("shoppingCart")) !== null) {
      setShoppingCartItems(JSON.parse(localStorage.getItem("shoppingCart")));
    }
    calculateTotalPrice();
  }, [props.webshop, props.loggedUser]);

  useEffect(() => {
    setColor(props.color);
  }, [props.color]);

  useEffect(() => {
    setTitle(props.title);
  }, [props.title]);

  useEffect(() => {
    setTitleColor(props.titleColor);
  }, [props.titleColor]);

  const calculateTotalPrice = () => {
    var totalPrice = 0.0;
    var shoppingCartItems = JSON.parse(localStorage.getItem("shoppingCart"));
    for (let i = 0; i < shoppingCartItems.length; i++) {
      totalPrice += shoppingCartItems[i].price * shoppingCartItems[i].quantity;
    }
    setTotalPrice(totalPrice);
  };

  const items = Array.apply(null, {
    length: shoppingCartItems.length,
  }).map((_, i) => (
    <PaymentResultItem
      key={i}
      item={shoppingCartItems[i]}
      changedQuantity={calculateTotalPrice}
    />
  ));

  return (
    <div className="shoppingCartCard">
      <div className="row">
        <div className="col-md-8 cart">
          <div className="titleShoppingCart">
            <div className="row">
              <div className="col">
                <h4>
                  <b style={{ color: titleColor }}>{title}</b>
                </h4>
              </div>
            </div>
          </div>
          <div className="shoppingCartScroll">{items}</div>
          <div className="back-to-shop">
            <Link
              to={{
                pathname: `/`,
              }}
              style={{ textDecoration: "none", color: "black" }}
            >
              <ArrowBackIcon />
              <span className="text-muted">Back to shop</span>
            </Link>
          </div>
        </div>
        <div
          className="col-md-4 summaryShoppingCart"
          style={{ backgroundColor: color }}
        >
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
            <div className="col text-right">&euro; {totalPrice}</div>
          </div>
        </div>
      </div>
    </div>
  );
}
