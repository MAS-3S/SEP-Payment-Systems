import React, { useEffect, useState } from "react";
import "../../assets/css/paymentCardStyle.css";

export default function SubscribePaymentCard(props) {
  const [paymentsMethod, setPaymentsMethod] = useState(props.paymentsMethod);
  const [merchantId, setMerchantId] = useState("");

  useEffect(() => {
    setPaymentsMethod(props.paymentsMethod);
  }, [props.paymentsMethod]);

  useEffect(() => {
    setMerchantId(props.merchantId);
  }, [props.merchantId]);

  const handleAlertClick = (paymentsMethod) => {
    props.handleChangeMerchantSubscription(paymentsMethod, merchantId);
  };

  return (
    <div id="productCardContainer">
      <div className="productCard-details">
        <h1>{paymentsMethod.name}</h1>
        <p className="productCardInformation">{paymentsMethod.description}</p>
        <div className="productCardControl">
          <button
            className={
              paymentsMethod.subscribed
                ? "productCardBtnUnsubscribe"
                : "productCardBtnSubscribe"
            }
            onClick={() => handleAlertClick(paymentsMethod)}
          >
            {paymentsMethod.subscribed ? (
              <span className="productCardBuy">Unsubscribe</span>
            ) : (
              <span className="productCardBuy">Subscribe</span>
            )}
          </button>
        </div>
      </div>
      <div className="product-image">
        <img src={paymentsMethod.image} alt="Payments method" />
      </div>
    </div>
  );
}
