import React from "react";
import "../assets/css/transactionCardStyle.css";
import { PayPalButton } from "react-paypal-button-v2";

export default function PayPalPaymentMethod(props) {
  const [payPalTransaction, setPayPalTransaction] = useState(
    props.payPalTransaction
  );

  useEffect(() => {
    console.log(props.payPalTransaction);
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
                  //currency="EUR"
                  // shippingPreference="NO_SHIPPING" // default is "GET_FROM_FILE"
                  createOrder={(data, actions) => {
                    return actions.order.create({
                      purchase_units: [
                        {
                          intent: "CAPTURE",
                          amount: {
                            currency_code: "EUR",
                            value: "0.01",
                          },
                        },
                      ],
                      // application_context: {
                      //   shipping_preference: "NO_SHIPPING" // default is "GET_FROM_FILE"
                      // }
                    });
                  }}
                  onApprove={(data, actions) => {
                    // Capture the funds from the transaction
                    return actions.order.capture().then(function (details) {
                      // Show a success message to your buyer
                      alert(
                        "Transaction completed by " +
                          details.payer.name.given_name
                      );

                      // OPTIONAL: Call your server to save the transaction
                      return fetch("/paypal-transaction-complete", {
                        method: "post",
                        body: JSON.stringify({
                          orderID: data.orderID,
                        }),
                      });
                    });
                  }}
                  // onSuccess={(details, data) => {
                  //   alert(
                  //     "Transaction completed by " +
                  //       details.payer.name.given_name
                  //   );

                  //   // OPTIONAL: Call your server to save the transaction
                  //   return fetch("/paypal-transaction-complete", {
                  //     method: "post",
                  //     body: JSON.stringify({
                  //       orderId: data.orderID,
                  //     }),
                  //   });
                  // }}
                  // onCancel={(data) => {
                  //   alert("Cancel", data);
                  // }}
                  onError={(err) => {
                    alert("Error", err);
                  }}
                  options={{
                    clientId:
                      "ARoSLly2260BRX4ew1qRJYYSKKO35em5ifi-ObiUOQDTTMsbp5peEh0Pxlhp4ILKQBHXDTdHrRug7uF1",
                    currency: "EUR",
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
