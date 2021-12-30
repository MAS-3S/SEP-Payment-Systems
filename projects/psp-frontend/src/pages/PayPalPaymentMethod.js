import React, { useEffect, useState } from "react";
import "../assets/css/transactionCardStyle.css";
import { PayPalButton } from "react-paypal-button-v2";

export default function PayPalPaymentMethod(props) {
  const [payPalTransaction, setPayPalTransaction] = useState(
    props.payPalTransaction
  );

  useEffect(() => {
    setPayPalTransaction(props.payPalTransaction);
  }, [props.payPalTransaction]);

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
                <PayPalButton
                  //amount="0.01"
                  currency={payPalTransaction.currency}
                  // shippingPreference="NO_SHIPPING" // default is "GET_FROM_FILE"
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
                      // application_context: {
                      //   shipping_preference: "NO_SHIPPING" // default is "GET_FROM_FILE"
                      // }
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
                    // OPTIONAL: Call your server to save the transaction
                    // return fetch("/paypal-transaction-complete", {
                    //   method: "post",
                    //   body: JSON.stringify({
                    //     orderId: data.orderID,
                    //   }),
                    // });
                  }}
                  onCancel={(data) => {
                    alert("Cancel", data);
                    window.location.href = payPalTransaction.failedUrl;
                  }}
                  onError={(err) => {
                    alert("Error", err);
                    window.location.href = payPalTransaction.failedUrl;
                  }}
                  options={{
                    clientId: payPalTransaction.clientId,
                    // ? payPalTransaction.clientId
                    // : "AbyipjcjSVGHvV-CflLej8uUdlgw-Xo9XvRd4Xzt6P9S2LR7GE1Y4hpaDPbM_H26erKl8xhOJFkSvW6G",
                    currency: payPalTransaction.currency,
                    // ? payPalTransaction.currency
                    // : "EUR",
                  }}
                />
              </div>
            </div>
          </div>
        </section>
      </div>
    </div>
  );
}
