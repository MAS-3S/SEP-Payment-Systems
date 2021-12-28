import { ACQUIRER_BANK_URL } from "../util/Constants";
import axios from "axios";

class TransactionService {
  async executeTransaction(
    transactionId,
    pan,
    ccv,
    expirationDate,
    cardholderName
  ) {
    const body = {
      pan: pan,
      ccv: ccv,
      expirationDate: expirationDate,
      cardholderName: cardholderName,
    };

    const response = await axios.post(
      ACQUIRER_BANK_URL + "api/transactions/execute/" + transactionId,
      body
    );
    return response.data;
  }

  async executeAcquirerBankQRCode(transactionId) {
    const body = {
      pan: "1239765115145742",
      ccv: "104",
      expirationDate: new Date("2022-12"),
      cardholderName: "Stefan Beljic",
    };

    const response = await axios.post(
      ACQUIRER_BANK_URL +
        "api/transactions/execute/" +
        transactionId +
        "/qrCode",
      body
    );
    return response.data;
  }

  async executeIssuerBankQRCode(transactionId) {
    const body = {
      pan: "4566477834965864",
      ccv: "202",
      expirationDate: new Date("2022-11"),
      cardholderName: "Stefan Savic",
    };

    const response = await axios.post(
      ACQUIRER_BANK_URL +
        "api/transactions/execute/" +
        transactionId +
        "/qrCode",
      body
    );
    return response.data;
  }

  async generateQRCode(transactionId) {
    const response = await axios.get(
      ACQUIRER_BANK_URL + "api/transactions/qrCode/" + transactionId
    );
    return response.data;
  }
}

export default new TransactionService();
