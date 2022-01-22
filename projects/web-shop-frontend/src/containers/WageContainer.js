import React from "react";
import { useState, useEffect } from "react";
import Wage from "../pages/Wage";
import PaymentService from "../services/PaymentService";

export default function WageContainer(props) {
  const [shouldOpenSnackbar, setShouldOpenSnackbar] = useState(false);
  const [webshopId, setWebshopId] = useState(-1);
  const [wageResponse, setWageResponse] = useState({
    success: false,
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
        setShouldOpenSnackbar(true);
        setWageResponse({
          success: response.success,
          message: response.message,
        });
      })
      .catch((error) => {
        setShouldOpenSnackbar(true);
        setWageResponse({
          success: false,
          message: "Bad request",
        });
      });
  };

  return (
    <Wage
      webshopId={webshopId}
      wageResponse={wageResponse}
      shouldOpenSnackbar={shouldOpenSnackbar}
      executePayment={handleExecutePayment}
    />
  );
}
