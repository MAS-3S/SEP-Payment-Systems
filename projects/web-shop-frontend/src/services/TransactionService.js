import { WEBSHOP_URL } from "../util/Constants";
import api from "./API";

class TransactionService {
  async getShoppingCartForTransaction(transactionId) {
    const response = await api.get(
      WEBSHOP_URL + "api/transactions/" + transactionId + "/shoppingCart"
    );
    return response.data;
  }
}

export default new TransactionService();
