import React, { useEffect, useState } from "react";
import PaymentResult from "../pages/PaymentResult";
import TransactionService from "../services/TransactionService";

export default function FailedPaymentResultContainer(props) {
  const [transaction, setTransaction] = useState({
    itemsToPurchase: [],
    totalPrice: 0,
    user: {},
    webShop: {},
  });

  useEffect(() => {
    async function fetchData() {
      const result = await TransactionService.getShoppingCartForTransaction(
        props.match.params.orderId
      );
      setTransaction(result);
    }
    fetchData();
  }, [props.match.params.orderId]);

  return (
    <PaymentResult
      transaction={transaction}
      title="Purchase failed"
      titleColor="#ffae19"
      color="#ffe6ba"
    />
  );
}
