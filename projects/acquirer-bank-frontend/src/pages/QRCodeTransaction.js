import React, { useEffect, useState } from "react";
import QRCodeCard from "../components/cards/QRCodeCard";
import "../assets/css/transactionsStyle.css";

export default function QRCodeTransaction(props) {
  const [QRCodeImage, setQRCodeImage] = useState("");

  useEffect(() => {
    setQRCodeImage(props.QRCodeImage);
  }, [props.QRCodeImage]);

  const handleAcquirerClick = () => {
    props.acquirerClick();
  };

  const handleIssuerClick = () => {
    props.issuerClick();
  };

  return (
    <div>
      <h1 className="titlePaymentsMethods">Payment Method - QR Code</h1>
      <QRCodeCard
        QRCodeImage={QRCodeImage}
        acquirerClick={handleAcquirerClick}
        issuerClick={handleIssuerClick}
      />
    </div>
  );
}
