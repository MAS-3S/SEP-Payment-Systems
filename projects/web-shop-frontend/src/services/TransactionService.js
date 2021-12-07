import { WEBSHOP_URL } from "../util/Constants";
import api from "./API";

class TransactionService {
  async getShoppingCartForTransaction(transactionId) {
    const response = await api.get(
      WEBSHOP_URL + "api/transactions/" + transactionId + "/shoppingCart"
    );
    return response.data;
  }

  async changeTransactionStatus(transactionId, status) {
    const body = {
      id: transactionId,
      status: status,
    };

    const response = await api.put(
      WEBSHOP_URL + "api/transactions/" + transactionId,
      body
    );
    return response.data;
  }
}

export default new TransactionService();
