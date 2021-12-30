import React from "react";
import PayPalService from "../services/PayPalService";
import PayPalPaymentMethod from "../pages/PayPalPaymentMethod";

export default function PayPalPaymentMethodContainter(props) {
  const [sholudRedirect, setSholudRedirect] = useState(false);
  const [payPalTransaction, setPayPalTransaction] = useState({});

  useEffect(() => {
    async function fetchData() {
      let payPalTransaction = null;
      payPalTransaction = await PayPalService.getPayPalTransaction(
        props.match.params.paypalTransactionId
      );
      if (payPalTransaction === null) {
        setSholudRedirect(true);
        return;
      }
      setPayPalTransaction(payPalTransaction);
    }
    fetchData();
  }, [props.match.params.paypalTransactionId]);

  return sholudRedirect ? (
    <Redirect
      to={{
        pathname: `/`,
      }}
    />
  ) : (
    <div>
      <PayPalPaymentMethod payPalTransaction={payPalTransaction} />
    </div>
  );
}
