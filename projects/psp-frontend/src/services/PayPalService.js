import { GATEWAY_URL } from "../util/Constants";
import axios from "axios";

class PayPalService {
  async getPayPalTransaction(transactionId) {
    const response = await axios.get(
      GATEWAY_URL + "paypal-service/transactions/" + transactionId
    );
    return response.data;
  }
}

export default new PayPalService();
