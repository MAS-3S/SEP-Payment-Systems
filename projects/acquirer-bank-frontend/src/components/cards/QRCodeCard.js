import React, { useEffect, useState } from "react";
import "../../assets/css/transactionCardStyle.css";

export default function QRCodeCard(props) {
  const [QRCodeImage, setQRCodeImage] = useState("");

  useEffect(() => {
    setQRCodeImage(props.QRCodeImage);
  }, [props.QRCodeImage]);

  const acquirerClick = () => {
    props.acquirerClick();
  };

  const issuerClick = () => {
    props.issuerClick();
  };

  return (
    <div className="page payment-page">
      <section className="payment-form dark">
        <div className="card-details">
          <h3 className="title">QR Code</h3>
          <div className="row">
            <div className="col-sm-12">
              <img src={QRCodeImage} alt="QRCode" width="300" height="300" />
            </div>
            <div className="col-sm-6" style={{ marginTop: -10 }}>
              <button
                className="btn btn-primary btn-block"
                onClick={acquirerClick}
              >
                Acquirer bank
              </button>
            </div>
            <div className="col-sm-6" style={{ marginTop: -10 }}>
              <button
                className="btn btn-primary btn-block"
                onClick={issuerClick}
              >
                Issuer bank
              </button>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}
