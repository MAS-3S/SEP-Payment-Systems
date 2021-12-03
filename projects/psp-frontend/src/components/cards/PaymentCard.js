import React, { useEffect, useState } from "react";
import "../../assets/css/paymentCardStyle.css";

export default function PaymentCard(props) {
  const [paymentsMethod, setPaymentsMethod] = useState(props.paymentsMethod);
  // const [merchantId, setMerchantId] = useState("");

  useEffect(() => {
    setPaymentsMethod(props.paymentsMethod);
  }, [props.paymentsMethod]);

  // useEffect(() => {
  //   setMerchantId(props.merchantId);
  // }, [props.merchantId]);

  const handleAlertClick = (paymentsMethod) => {
    //props.handleChangeMerchantSubscription(paymentsMethod, merchantId);
    console.log(paymentsMethod);
  };

  return (
    <div id="productCardContainer">
      <div className="productCard-details">
        <h1>{paymentsMethod.name}</h1>
        <p className="productCardInformation">{paymentsMethod.description}</p>
        <div className="productCardControl">
          <button
            className="productCardBtnSubscribe"
            onClick={() => handleAlertClick(paymentsMethod)}
          >
            <span className="productCardBuy">Choose</span>
          </button>
        </div>
      </div>
      <div className="product-image">
        <img src={paymentsMethod.image} alt="Payments method" />
      </div>
    </div>
  );
}
