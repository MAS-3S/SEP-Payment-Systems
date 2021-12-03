import React, { useEffect, useState } from "react";
import { Redirect } from "react-router-dom";
import SubscribePaymentMethods from "../pages/SubscribePaymentMethods";
import PaymentMethodService from "../services/PaymentMethodService";
import { Snackbar } from "@material-ui/core";
import { Alert } from "@material-ui/lab";

export default function SubscribePaymentMethodsContainer(props) {
  const [sholudRedirect, setSholudRedirect] = useState(false);
  const [paymentsMethods, setPaymentsMethods] = useState([]);
  const [merchantId, setMerchantId] = useState("");
  const [open, setOpen] = React.useState(false);
  const [alertMessage, setAlertMessage] = React.useState("");
  const [alertType, setAlertType] = React.useState("");
  const [alertDuration, setAlertDuration] = useState(1500);

  useEffect(() => {
    async function fetchData() {
      // ako postoji webshopId proveri da li je okej, ako nije redirektuj na not found
      let payments = [];
      payments =
        await PaymentMethodService.getMerchantsPaymentMethodsForSubscription(
          props.match.params.webshopId
        );
      if (payments.length === 0) {
        setSholudRedirect(true);
        return;
      }
      setPaymentsMethods(payments);
      setMerchantId(props.match.params.webshopId);
    }
    fetchData();
  }, [props.match.params.webshopId]);

  const handleChangeMerchantSubscription = async (
    paymentsMethod,
    merchantId
  ) => {
    await PaymentMethodService.changeMerchantSubscription(
      paymentsMethod.id,
      merchantId
    ).then((response) => {
      async function fetchData() {
        setPaymentsMethods(
          await PaymentMethodService.getMerchantsPaymentMethodsForSubscription(
            merchantId
          )
        );
      }
      fetchData();
      setAlertType("success");
      setAlertDuration(2000);
      setOpen(true);
      if (paymentsMethod.subscribed) {
        setAlertMessage("Successfully unsubscribed");
      } else {
        setAlertMessage("Successfully subscribed");
      }
    });
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
      autoHideDuration={alertDuration}
      onClose={handleAlertClose}
      anchorOrigin={{ vertical: "top", horizontal: "center" }}
    >
      <Alert onClose={handleAlertClose} severity={alertType}>
        {alertMessage}
      </Alert>
    </Snackbar>
  );

  return sholudRedirect ? (
    <Redirect
      to={{
        pathname: `/`,
      }}
    />
  ) : (
    <div>
      {snackbar}
      <SubscribePaymentMethods
        paymentsMethods={paymentsMethods}
        merchantId={merchantId}
        handleChangeMerchantSubscription={handleChangeMerchantSubscription}
      />
    </div>
  );
}
