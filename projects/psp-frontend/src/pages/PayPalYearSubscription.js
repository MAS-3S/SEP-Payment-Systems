import React, { useEffect, useState } from "react";
import "../assets/css/transactionCardStyle.css";
import { PayPalButton } from "react-paypal-button-v2";
import PayPalService from "../services/PayPalService";
import { Link } from "react-router-dom";
import { FRONT_URL } from "../util/Constants";

export default function PayPalYearSubscription(props) {
  const [payPalTransaction, setPayPalTransaction] = useState(
    props.payPalTransaction
  );

  useEffect(() => {
    setPayPalTransaction(props.payPalTransaction);
  }, [props.payPalTransaction]);

  const navigateToPayPal = (transactionId) => {
    window.location.href =
      FRONT_URL + "payment-method/pay-pal/" + transactionId;
  };

  return (
    <div>
      <h1 className="titlePaymentsMethods">Payment Method - PayPal</h1>
      <div className="page payment-page">
        <section className="payment-form dark">
          <div className="card-details">
            <h3 className="title">PayPal</h3>
            <div className="row">
              <div
                className="col-sm-6"
                style={{ marginTop: 80, paddingRight: 60, paddingLeft: 60 }}
              >
                <p className="title">Year</p>
                <div>
                  <PayPalButton
                    style={{
                      chape: "pill",
                      color: "blue",
                      layout: "horizontal",
                      label: "subscribe",
                    }}
                    options={{
                      vault: true,
                      clientId: payPalTransaction.clientId,
                    }}
                    createSubscription={(data, actions) => {
                      return actions.subscription.create({
                        plan_id: "P-3EA44907VN6687836MHSFMHI",
                      });
                    }}
                    onApprove={(data, actions) => {
                      window.location.href = payPalTransaction.successUrl;
                      PayPalService.changeTransactionStatusToSuccess(
                        payPalTransaction.transactionId
                      );
                    }}
                    onCancel={(data) => {
                      window.location.href = payPalTransaction.cancelUrl;
                      PayPalService.changeTransactionStatusToCanceled(
                        payPalTransaction.transactionId
                      );
                    }}
                    onError={(err) => {
                      window.location.href = payPalTransaction.cancelUrl;
                      PayPalService.changeTransactionStatusToCanceled(
                        payPalTransaction.transactionId
                      );
                    }}
                  />
                  <div style={{ marginTop: 50 }}>
                    <Link
                      onClick={() =>
                        navigateToPayPal(payPalTransaction.transactionId)
                      }
                      to="/"
                    >
                      Go back
                    </Link>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>
    </div>
  );
}
