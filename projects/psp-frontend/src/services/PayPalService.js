import { GATEWAY_URL } from "../util/Constants";
import axios from "axios";

class PayPalService {
  async getPayPalTransaction(transactionId) {
    const response = await axios.get(
      GATEWAY_URL + "paypal-service/transactions/" + transactionId
    );
    return response.data;
  }

  async changeTransactionStatusToSuccess(transactionId) {
    const response = await axios.put(
      GATEWAY_URL + "paypal-service/transactions/" + transactionId + "/success"
    );
    return response.data;
  }

  async changeTransactionStatusToCanceled(transactionId) {
    const response = await axios.put(
      GATEWAY_URL + "paypal-service/transactions/" + transactionId + "/cancel"
    );
    return response.data;
  }
}

export default new PayPalService();
