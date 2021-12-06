import React, { useEffect, useState } from "react";
import PaymentResult from "../pages/PaymentResult";
import TransactionService from "../services/TransactionService";

export default function SuccessPaymentResultContainer(props) {
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
      title="Successful purchase"
      titleColor="#52db52"
      color="#d0f5d0"
    />
  );
}
