import React from "react";
import { useState, useEffect } from "react";
import WagePage from "../pages/WagePage";

export default function WageContainer(props) {
  const [webshopId, setWebshopId] = useState(-1);

  useEffect(() => {
    setWebshopId(props.match.params.webshopId);
  }, [props.match.params.webshopId]);

  const handleExecutePayment = (
    amount,
    currency,
    accountNumber,
    bankNumber
  ) => {
    console.log(amount, currency, accountNumber, bankNumber, webshopId);
  };

  return (
    <WagePage webshopId={webshopId} executePayment={handleExecutePayment} />
  );
}
