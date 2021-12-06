import React, { useEffect, useState } from "react";

export default function PaymentResultItem(props) {
  const [item, setItem] = useState(props.item);
  const [quantity, setQuantity] = useState(props.item.quantity);

  useEffect(() => {
    setItem(props.item);
    setQuantity(props.item.quantity);
  }, [props.item, props.item.quantity]);

  return (
    <div className="row border-top">
      <div className="row mainShoppingCart align-items-center">
        <div className="col-2">
          <img alt="" className="img-fluid" src={item.image} />
        </div>
        <div className="col">
          <div className="row text-muted">{item.name}</div>
        </div>
        <div className="col">
          <div class="input-group" style={{ marginTop: -8, marginLeft: 40 }}>
            <input
              disabled={true}
              type="text"
              name="quant[1]"
              class="form-control input-number"
              value={quantity}
              style={{
                textAlign: "center",
                width: 40,
                height: 50,
                backgroundColor: "white",
                border: "none",
              }}
            />
          </div>
        </div>
        <div className="col text-right" style={{ marginLeft: 20 }}>
          &euro; {item.price}
        </div>
      </div>
    </div>
  );
}
