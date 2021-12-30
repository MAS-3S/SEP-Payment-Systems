import React, { useEffect, useState } from "react";
import PayPalService from "../services/PayPalService";
import PayPalPaymentMethod from "../pages/PayPalPaymentMethod";
import { Redirect } from "react-router-dom";

export default function PayPalPaymentMethodContainter(props) {
  const [loading, setLoading] = useState(true);
  const [sholudRedirect, setSholudRedirect] = useState(false);
  const [payPalTransaction, setPayPalTransaction] = useState({});

  useEffect(() => {
    async function fetchData() {
      await PayPalService.getPayPalTransaction(
        props.match.params.paypalTransactionId
      ).then((response) => {
        if (response === null) {
          setSholudRedirect(true);
          return;
        }
        setPayPalTransaction(response);
        setLoading(false);
      });
    }
    fetchData();
  }, [props.match.params.paypalTransactionId]);

  return loading ? null : sholudRedirect ? (
    <Redirect
      to={{
        pathname: `/`,
      }}
    />
  ) : (
    <PayPalPaymentMethod payPalTransaction={payPalTransaction} />
  );
}
