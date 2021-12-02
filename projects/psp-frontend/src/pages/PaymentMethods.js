import React, { useEffect, useState } from "react";
import PaymentCard from "../components/cards/PaymentCard";
import "../assets/css/paymentMethodsStyle.css";

export default function PaymentMethods(props) {
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
        <PaymentCard
          key={i}
          paymentsMethod={paymentsMethods[i]}
          merchantId={merchantId}
          handleChangeMerchantSubscription={handleChangeMerchantSubscription}
        />
      ))}
    </div>
  );
}
