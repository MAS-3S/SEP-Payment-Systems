import React, { useEffect, useState } from "react";
import "../assets/css/shoppingCartStyle.css";
import ArrowBackIcon from "@material-ui/icons/ArrowBack";
import CloseIcon from "@material-ui/icons/Close";
import { Link } from "react-router-dom";

export default function ShoppingCart(props) {
  const [activeWebshop, setActiveWebshop] = useState(props.webshop);

  useEffect(() => {
    setActiveWebshop(props.webshop);
  }, [props.webshop]);

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
                3 items
              </div>
            </div>
          </div>
          <div className="shoppingCartScroll">
            <div className="row border-top">
              <div className="row mainShoppingCart align-items-center">
                <div className="col-2">
                  <img
                    alt=""
                    className="img-fluid"
                    src="https://i.imgur.com/ba3tvGm.jpg"
                  />
                </div>
                <div className="col">
                  <div className="row text-muted">Shirt</div>
                  <div className="row">Cotton T-shirt</div>
                </div>
                <div className="col">
                  <div class="input-group" style={{ marginTop: -8 }}>
                    <span
                      class="input-group-prepend"
                      style={{ height: 38, width: 38, marginTop: 8 }}
                    >
                      <button
                        type="button"
                        class="btn btn-outline-secondary btn-number"
                        disabled="disabled"
                        data-type="minus"
                        data-field="quant[1]"
                      >
                        <span class="fa fa-minus"></span>
                      </button>
                    </span>
                    <input
                      type="text"
                      name="quant[1]"
                      class="form-control input-number"
                      value="1"
                      min="1"
                      max="100"
                      style={{
                        textAlign: "center",
                      }}
                    />
                    <span
                      class="input-group-append"
                      style={{ height: 38, width: 38, marginTop: 8 }}
                    >
                      <button
                        type="button"
                        class="btn btn-outline-secondary btn-number"
                        data-type="plus"
                        data-field="quant[1]"
                      >
                        <span class="fa fa-plus"></span>
                      </button>
                    </span>
                  </div>
                </div>
                <div className="col" style={{ marginLeft: 20 }}>
                  &euro; 44.00{" "}
                  <span className="close">
                    <CloseIcon
                      style={{
                        widht: 15,
                        height: 15,
                        marginTop: -3,
                      }}
                    />
                  </span>
                </div>
              </div>
            </div>
            <div className="row border-top border-bottom">
              <div className="row mainShoppingCart align-items-center">
                <div className="col-2">
                  <img
                    alt=""
                    className="img-fluid"
                    src="https://i.imgur.com/pHQ3xT3.jpg"
                  />
                </div>
                <div className="col">
                  <div className="row text-muted">Shirt</div>
                  <div className="row">Cotton T-shirt</div>
                </div>
                <div className="col">
                  <div class="input-group" style={{ marginTop: -8 }}>
                    <span
                      class="input-group-prepend"
                      style={{ height: 38, width: 38, marginTop: 8 }}
                    >
                      <button
                        type="button"
                        class="btn btn-outline-secondary btn-number"
                        disabled="disabled"
                        data-type="minus"
                        data-field="quant[1]"
                      >
                        <span class="fa fa-minus"></span>
                      </button>
                    </span>
                    <input
                      type="text"
                      name="quant[1]"
                      class="form-control input-number"
                      value="1"
                      min="1"
                      max="100"
                      style={{
                        textAlign: "center",
                      }}
                    />
                    <span
                      class="input-group-append"
                      style={{ height: 38, width: 38, marginTop: 8 }}
                    >
                      <button
                        type="button"
                        class="btn btn-outline-secondary btn-number"
                        data-type="plus"
                        data-field="quant[1]"
                      >
                        <span class="fa fa-plus"></span>
                      </button>
                    </span>
                  </div>
                </div>
                <div className="col" style={{ marginLeft: 20 }}>
                  &euro; 44.00{" "}
                  <span className="close">
                    <CloseIcon
                      style={{
                        widht: 15,
                        height: 15,
                        marginTop: -3,
                      }}
                    />
                  </span>
                </div>
              </div>
            </div>
          </div>
          <div className="back-to-shop">
            <Link
              to={{
                pathname: `/webshop/${activeWebshop}`,
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
              <b>Summary</b>
            </h5>
          </div>
          <hr className="hrShoppingCart" />
          <div className="row">
            <div className="col" style={{ paddingLeft: 0 }}>
              ITEMS
            </div>
            <div className="col text-right">3</div>
          </div>
          <hr className="hrShoppingCart" />
          <div className="row">
            <div className="col" style={{ paddingLeft: 0 }}>
              TOTAL PRICE
            </div>
            <div className="col text-right">&euro; 137.00</div>
          </div>
          <Link
            type="button"
            variant="contained"
            color="primary"
            to={{
              pathname: `/webshop/${activeWebshop}`,
            }}
            className="checkoutButton"
            style={{ textDecoration: "none", color: "white" }}
          >
            CHECKOUT
          </Link>
        </div>
      </div>
    </div>
  );
}
