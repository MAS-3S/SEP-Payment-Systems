import React from "react";
import WageCard from "../components/cards/WageCard";

export default function WagePage(props) {
  const handleExecutePayment = (
    amount,
    currency,
    accountNumber,
    bankNumber
  ) => {
    props.executePayment(amount, currency, accountNumber, bankNumber);
  };

  return (
    <div>
      <WageCard executePayment={handleExecutePayment} />
    </div>
  );
}
