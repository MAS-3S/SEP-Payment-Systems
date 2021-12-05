import React from "react";
import TransactionCard from "../components/cards/TransactionCard";
import "../assets/css/transactionsStyle.css";

export default function Transaction() {
  return (
    <div>
      <h1 className="titlePaymentsMethods">Payment Method - Credit Card</h1>
      <TransactionCard />
    </div>
  );
}
