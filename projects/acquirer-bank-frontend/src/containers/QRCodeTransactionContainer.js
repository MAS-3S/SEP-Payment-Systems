import React, { useEffect, useState } from "react";
import QRCodeTransaction from "../pages/QRCodeTransaction";
import TransactionService from "../services/TransactionService";

export default function QRCodeTransactionContainer(props) {
  const [transactionId, setTransactionId] = useState(
    props.match.params.transactionId
  );
  const [QRCodeImage, setQRCodeImage] = useState("");

  useEffect(() => {
    setTransactionId(props.match.params.transactionId);
    TransactionService.generateQRCode(props.match.params.transactionId).then(
      (response) => {
        if (response !== null) {
          setQRCodeImage(response);
        }
      }
    );
  }, [props.match.params.transactionId]);

  const handleAcquirerClick = () => {
    TransactionService.executeAcquirerBankQRCode(transactionId).then(
      (response) => {
        if (response !== null) {
          if (response.success === false) {
            alert(response.message);
            window.location.href = response.paymentUrl;
          } else {
            window.location.href = response.paymentUrl;
          }
        }
      }
    );
  };

  const handleIssuerClick = () => {
    TransactionService.executeIssuerBankQRCode(transactionId).then(
      (response) => {
        if (response !== null) {
          if (response.success === false) {
            alert(response.message);
            window.location.href = response.paymentUrl;
          } else {
            window.location.href = response.paymentUrl;
          }
        }
      }
    );
  };

  return (
    <QRCodeTransaction
      QRCodeImage={QRCodeImage}
      acquirerClick={handleAcquirerClick}
      issuerClick={handleIssuerClick}
    />
  );
}
