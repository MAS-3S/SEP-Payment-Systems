import { WEBSHOP_URL } from "../util/Constants";
import api from "./API";

class PaymentService {
  async pay(shoppingCart) {
    const response = await api.post(WEBSHOP_URL + "api/payment", shoppingCart);
    return response.data;
  }

  async payWage(amount, currency, accountNumber, bankNumber, webShopId) {
    const body = {
      webShopId: webShopId,
      accountNumber: accountNumber,
      bankNumber: bankNumber,
      amount: amount,
      currency: currency,
    };

    const response = await api.post(WEBSHOP_URL + "api/payment/wage", body);
    return response.data;
  }
}

export default new PaymentService();
