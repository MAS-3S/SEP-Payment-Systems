import React, { useEffect, useState } from "react";
import "../assets/css/paymentMethodsStyle.css";
import SubscribePaymentCard from "../components/cards/SubscribePaymentCard";

export default function SubscribePaymentMethods(props) {
  const [paymentsMethods, setPaymentsMethods] = useState(props.paymentsMethods);
  const [merchantId, setMerchantId] = useState(props.merchantId);

  useEffect(() => {
    setPaymentsMethods(props.paymentsMethods);
  }, [props.paymentsMethods]);

  useEffect(() => {
    setMerchantId(props.merchantId);
  }, [props.merchantId]);

  const handleChangeMerchantSubscription = (paymentsMethod, merchantId) => {
    props.handleChangeMerchantSubscription(paymentsMethod, merchantId);
  };

  return (
    <div>
      <h1 className="titlePaymentsMethods">Payment Methods</h1>
      {Array.apply(null, {
        length: paymentsMethods.length,
      }).map((_, i) => (
        <SubscribePaymentCard
          key={i}
          paymentsMethod={paymentsMethods[i]}
          merchantId={merchantId}
          handleChangeMerchantSubscription={handleChangeMerchantSubscription}
        />
      ))}
    </div>
  );
}
