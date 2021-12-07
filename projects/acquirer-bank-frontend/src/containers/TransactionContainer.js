import React, { useEffect, useState } from "react";
import Transaction from "../pages/Transaction";
import TransactionService from "../services/TransactionService";

export default function TransactionContainer(props) {
  const [transactionId, setTransactionId] = useState(
    props.match.params.transactionId
  );

  useEffect(() => {
    setTransactionId(props.match.params.transactionId);
  }, [props.match.params.transactionId]);

  const handleExecuteTransaction = (pan, cvc, expirationDate, cardHolder) => {
    TransactionService.executeTransaction(
      transactionId,
      pan,
      cvc,
      expirationDate,
      cardHolder
    ).then((response) => {
      if (response !== null) {
        window.location.href = response.paymentUrl;
      }
    });
  };
  return <Transaction handleExecuteTransaction={handleExecuteTransaction} />;
}
