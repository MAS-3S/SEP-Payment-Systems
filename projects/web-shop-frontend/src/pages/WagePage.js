import { Snackbar } from "@material-ui/core";
import { Alert } from "@material-ui/lab";
import React, { useEffect, useState } from "react";
import WageCard from "../components/cards/WageCard";

export default function WagePage(props) {
  const [open, setOpen] = useState(false);
  const [severity, setSeverity] = useState("error");
  const [wageResponse, setWageResponse] = useState({
    isSuccess: props.wageResponse.isSuccess,
    message: props.wageResponse.message,
  });

  useEffect(() => {
    setWageResponse(props.wageResponse);
    if (props.wageResponse.isSuccess) {
      setSeverity("success");
    } else {
      setSeverity("error");
    }
    setOpen(true);
  }, [props.wageResponse]);

  const handleExecutePayment = (
    amount,
    currency,
    accountNumber,
    bankNumber
  ) => {
    props.executePayment(amount, currency, accountNumber, bankNumber);
  };

  const handleAlertClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }
    setOpen(false);
  };

  const snackbar = (
    <Snackbar
      open={open}
      autoHideDuration={2300}
      onClose={handleAlertClose}
      anchorOrigin={{ vertical: "top", horizontal: "center" }}
    >
      <Alert onClose={handleAlertClose} severity={severity}>
        {wageResponse.message}
      </Alert>
    </Snackbar>
  );

  return (
    <div>
      <WageCard executePayment={handleExecutePayment} />
      {snackbar}
    </div>
  );
}
