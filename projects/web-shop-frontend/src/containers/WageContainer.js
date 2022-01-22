import React from "react";
import { useState, useEffect } from "react";
import WagePage from "../pages/WagePage";
import PaymentService from "../services/PaymentService";

export default function WageContainer(props) {
  const [webshopId, setWebshopId] = useState(-1);
  const [wageResponse, setWageResponse] = useState({
    isSuccess: false,
    message: "",
  });

  useEffect(() => {
    setWebshopId(props.match.params.webshopId);
  }, [props.match.params.webshopId]);

  const handleExecutePayment = async (
    amount,
    currency,
    accountNumber,
    bankNumber
  ) => {
    await PaymentService.payWage(
      amount,
      currency,
      accountNumber,
      bankNumber,
      webshopId
    )
      .then((response) => {
        setWageResponse({
          isSuccess: response.isSuccess,
          message: response.message,
        });
      })
      .catch((error) => {
        setWageResponse({
          isSuccess: false,
          message: "Bad request",
        });
      });
  };

  return (
    <WagePage
      webshopId={webshopId}
      wageResponse={wageResponse}
      executePayment={handleExecutePayment}
    />
  );
}
