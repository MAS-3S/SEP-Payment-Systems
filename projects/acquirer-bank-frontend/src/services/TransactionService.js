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
}

export default new TransactionService();
