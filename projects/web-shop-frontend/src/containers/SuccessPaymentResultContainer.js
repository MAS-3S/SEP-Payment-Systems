import React, { useEffect, useState } from "react";
import { Redirect } from "react-router";
import PaymentResult from "../pages/PaymentResult";
import TransactionService from "../services/TransactionService";

export default function SuccessPaymentResultContainer(props) {
  const [shouldRedirect, setShouldRedirect] = useState(false);
  const [transaction, setTransaction] = useState({
    itemsToPurchase: [],
    totalPrice: 0,
    user: {},
    webShop: {},
  });

  useEffect(() => {
    async function fetchData() {
      await TransactionService.getShoppingCartForTransaction(
        props.match.params.orderId
      )
        .then((response) => {
          setTransaction(response);
        })
        .catch((error) => {
          if (error.response.status === 404) {
            setShouldRedirect(true);
          }
        });

      await TransactionService.changeTransactionStatus(
        props.match.params.orderId,
        1
      );
    }
    fetchData();
  }, [props.match.params.orderId]);

  return shouldRedirect ? (
    <Redirect to="/" />
  ) : (
    <PaymentResult
      transaction={transaction}
      title="Successful purchase"
      titleColor="#52db52"
      color="#d0f5d0"
    />
  );
}
