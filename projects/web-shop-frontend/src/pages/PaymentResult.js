import React, { useEffect, useState } from "react";
import "../assets/css/shoppingCartStyle.css";
import ArrowBackIcon from "@material-ui/icons/ArrowBack";
import { Link } from "react-router-dom";
import PaymentResultItem from "../components/paymentResult/PaymentResultItem";

export default function PaymentResult(props) {
  const [transaction, setTransaction] = useState(props.transaction);
  const [color, setColor] = useState(props.color);
  const [title, setTitle] = useState(props.title);
  const [titleColor, setTitleColor] = useState(props.titleColor);

  useEffect(() => {
    setTransaction(props.transaction);
  }, [props.transaction]);

  useEffect(() => {
    setColor(props.color);
  }, [props.color]);

  useEffect(() => {
    setTitle(props.title);
  }, [props.title]);

  useEffect(() => {
    setTitleColor(props.titleColor);
  }, [props.titleColor]);

  const items = Array.apply(null, {
    length: transaction.itemsToPurchase.length,
  }).map((_, i) => (
    <PaymentResultItem
      key={i}
      item={transaction.itemsToPurchase[i]}
      currency={transaction.currency}
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
                pathname: `/webshop/${transaction.webShop.name}/EUR`,
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
            <div className="col text-right">{transaction.user.fullName}</div>
          </div>
          <hr className="hrShoppingCart" />
          <div className="row">
            <div className="col" style={{ paddingLeft: 0 }}>
              ADDRESS
            </div>
            <div className="row text-right">{transaction.user.address}</div>
          </div>
          <hr className="hrShoppingCart" />
          <div className="row">
            <div className="col" style={{ paddingLeft: 0 }}>
              PHONE
            </div>
            <div className="col text-right">{transaction.user.phone}</div>
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
            <div className="col text-right">
              {transaction.itemsToPurchase.length}
            </div>
          </div>
          <hr className="hrShoppingCart" />
          <div className="row">
            <div className="col" style={{ paddingLeft: 0 }}>
              TOTAL PRICE
            </div>
            <div className="col text-right">
              {transaction.totalPrice} {transaction.currency}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
