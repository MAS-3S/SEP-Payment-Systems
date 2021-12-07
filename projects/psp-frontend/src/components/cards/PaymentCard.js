import React, { useEffect, useState } from "react";
import "../../assets/css/paymentCardStyle.css";

export default function PaymentCard(props) {
  const [paymentsMethod, setPaymentsMethod] = useState(props.paymentsMethod);
  const [merchantId, setMerchantId] = useState(props.merchantId);
  const [paymentId, setPaymentId] = useState(props.paymentId);

  useEffect(() => {
    setPaymentsMethod(props.paymentsMethod);
    setMerchantId(props.merchantId);
    setPaymentId(props.paymentId);
  }, [props.paymentsMethod, props.merchantId, props.paymentId]);

  const handleAlertClick = (paymentsMethod, merchantId, paymentId) => {
    props.handleChosePaymentMethod(paymentsMethod.id, merchantId, paymentId);
  };

  return (
    <div id="productCardContainer">
      <div className="productCard-details">
        <h1>{paymentsMethod.name}</h1>
        <p className="productCardInformation">{paymentsMethod.description}</p>
        <div className="productCardControl">
          <button
            className="productCardBtnSubscribe"
            onClick={() =>
              handleAlertClick(paymentsMethod, merchantId, paymentId)
            }
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
