import React, { useEffect, useState } from "react";
import "../assets/css/transactionCardStyle.css";
import { PayPalButton } from "react-paypal-button-v2";
import PayPalService from "../services/PayPalService";
import { Link } from "react-router-dom";
import { FRONT_URL } from "../util/Constants";

export default function PayPalPaymentMethod(props) {
  const [payPalTransaction, setPayPalTransaction] = useState(
    props.payPalTransaction
  );
  const [isPossibleSubscription, setIsPossibleSubscription] = useState(
    props.payPalTransaction.possibleSubscription
  );

  useEffect(() => {
    setPayPalTransaction(props.payPalTransaction);
    setIsPossibleSubscription(props.payPalTransaction.possibleSubscription);
  }, [props.payPalTransaction, props.payPalTransaction.possibleSubscription]);

  const navigateToMonthSubscription = (transactionId) => {
    window.location.href =
      FRONT_URL + "payment-method/pay-pal/subscription/month/" + transactionId;
  };

  const navigateToYearSubscription = (transactionId) => {
    window.location.href =
      FRONT_URL + "payment-method/pay-pal/subscription/year/" + transactionId;
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
                style={{
                  marginTop: 80,
                  paddingRight: 60,
                  paddingLeft: 60,
                }}
              >
                <PayPalButton
                  currency={payPalTransaction.currency}
                  createOrder={(data, actions) => {
                    return actions.order.create({
                      purchase_units: [
                        {
                          intent: "CAPTURE",
                          amount: {
                            currency_code: payPalTransaction.currency,
                            value: payPalTransaction.amount,
                          },
                        },
                      ],
                    });
                  }}
                  // onApprove={(data, actions) => {
                  //   // Capture the funds from the transaction
                  //   return actions.order.capture().then(function (details) {
                  //     // Show a success message to your buyer
                  //     alert(
                  //       "Transaction completed by " +
                  //         details.payer.name.given_name
                  //     );

                  //     //OPTIONAL: Call your server to save the transaction
                  //     return fetch("/paypal-transaction-complete", {
                  //       method: "post",
                  //       body: JSON.stringify({
                  //         orderID: data.orderID,
                  //       }),
                  //     });
                  //   });
                  // }}
                  onSuccess={(details, data) => {
                    alert(
                      "Transaction completed by " +
                        details.payer.name.given_name
                    );
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
                  options={{
                    clientId: payPalTransaction.clientId,
                    currency: payPalTransaction.currency,
                  }}
                />
                {isPossibleSubscription ? (
                  <div style={{ marginTop: 20 }}>
                    <Link
                      onClick={() =>
                        navigateToMonthSubscription(
                          payPalTransaction.transactionId
                        )
                      }
                      to="/"
                    >
                      Month Subscribe
                    </Link>
                    <div></div>
                    <Link
                      onClick={() =>
                        navigateToYearSubscription(
                          payPalTransaction.transactionId
                        )
                      }
                      to="/"
                    >
                      Year Subscribe
                    </Link>
                  </div>
                ) : null}
              </div>
            </div>
          </div>
        </section>
      </div>
    </div>
  );
}
