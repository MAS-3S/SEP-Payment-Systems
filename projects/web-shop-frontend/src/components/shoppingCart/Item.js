import React, { useEffect, useState } from "react";
import CloseIcon from "@material-ui/icons/Close";

export default function Item(props) {
  const [item, setItem] = useState(props.item);
  const [minusDisabled, setMinusDisabled] = useState("disabled");
  const [plusDisabled, setPlusDisabled] = useState("");
  const [quantity, setQuantity] = useState(1);

  useEffect(() => {
    setItem(props.item);
    props.item.availableBalance === 1
      ? setPlusDisabled("disabled")
      : setPlusDisabled("");
  }, [props.item]);

  const handleRemoveItem = () => {
    var shoppingCart = [];
    shoppingCart = JSON.parse(localStorage.getItem("shoppingCart"));
    for (let i = 0; i < shoppingCart.length; i++) {
      if (shoppingCart[i].id === item.id) {
        const index = shoppingCart.indexOf(shoppingCart[i]);
        shoppingCart.splice(index, 1);
        localStorage.setItem("shoppingCart", JSON.stringify(shoppingCart));
        props.removed(true);
        return;
      }
    }
  };

  const handleMinusQuantity = () => {
    if (quantity - 1 <= 1) {
      setMinusDisabled("disabled");
    } else {
      setMinusDisabled("");
    }
    if (minusDisabled !== "disabled") {
      setQuantity(quantity - 1);
      changeItemQuantity(quantity - 1);
      props.changedQuantity(true);
    }
  };

  const handlePlusQuantity = () => {
    setMinusDisabled("");
    quantity + 1 >= props.item.availableBalance
      ? setPlusDisabled("disabled")
      : setPlusDisabled("");
    if (plusDisabled !== "disabled") {
      setQuantity(quantity + 1);
      changeItemQuantity(quantity + 1);
      props.changedQuantity(true);
    }
  };

  const changeItemQuantity = (newQuantity) => {
    var shoppingCartItems = JSON.parse(localStorage.getItem("shoppingCart"));
    for (let i = 0; i < shoppingCartItems.length; i++) {
      if (shoppingCartItems[i].id === item.id) {
        shoppingCartItems[i].quantity = newQuantity;
        break;
      }
    }
    localStorage.setItem("shoppingCart", JSON.stringify(shoppingCartItems));
  };

  return (
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
          <div className="row text-muted">{item.name}</div>
          {/* <div className="row">{item.description}</div> */}
        </div>
        <div className="col">
          <div class="input-group" style={{ marginTop: -8 }}>
            <span
              class="input-group-prepend"
              style={{ height: 38, width: 38, marginTop: 8 }}
              onClick={handleMinusQuantity}
            >
              <button
                type="button"
                class="btn btn-outline-danger btn-number"
                disabled={minusDisabled}
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
              value={quantity}
              min="1"
              max="100"
              style={{
                textAlign: "center",
              }}
            />
            <span
              class="input-group-append"
              style={{ height: 38, width: 38, marginTop: 8 }}
              onClick={handlePlusQuantity}
            >
              <button
                type="button"
                class="btn btn-outline-success btn-number"
                data-type="plus"
                disabled={plusDisabled}
                data-field="quant[1]"
              >
                <span class="fa fa-plus"></span>
              </button>
            </span>
          </div>
        </div>
        <div className="col" style={{ marginLeft: 20 }}>
          &euro; {item.price}{" "}
          <span className="close" onClick={handleRemoveItem}>
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
  );
}
