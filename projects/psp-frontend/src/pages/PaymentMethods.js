import React, { useEffect, useState } from "react";
import PaymentCard from "../components/cards/PaymentCard";
import "../assets/css/paymentMethodsStyle.css";

export default function PaymentMethods(props) {
  const [paymentsMethods, setPaymentsMethods] = useState(props.paymentsMethods);
  const [merchantId, setMerchantId] = useState(props.merchantId);
  const [paymentId, setPaymentId] = useState(props.paymentId);

  useEffect(() => {
    setPaymentsMethods(props.paymentsMethods);
  }, [props.paymentsMethods]);

  useEffect(() => {
    setMerchantId(props.merchantId);
    setPaymentId(props.paymentId);
  }, [props.merchantId, props.paymentId]);

  const handleChangeMerchantSubscription = (paymentsMethod, merchantId) => {
    props.handleChangeMerchantSubscription(paymentsMethod, merchantId);
  };

  const handleChoosePaymentMethod = (
    paymentsMethodId,
    merchantId,
    paymentId
  ) => {
    props.handleChoosePaymentMethod(paymentsMethodId, merchantId, paymentId);
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
          paymentId={paymentId}
          handleChangeMerchantSubscription={handleChangeMerchantSubscription}
          handleChoosePaymentMethod={handleChoosePaymentMethod}
        />
      ))}
    </div>
  );
}
