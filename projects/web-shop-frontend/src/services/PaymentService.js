import { WEBSHOP_URL } from "../util/Constants";
import api from "./API";

class PaymentService {
  async pay(shoppingCart) {
    const response = await api.post(WEBSHOP_URL + "api/payment", shoppingCart);
    return response.data;
  }
}

export default new PaymentService();
