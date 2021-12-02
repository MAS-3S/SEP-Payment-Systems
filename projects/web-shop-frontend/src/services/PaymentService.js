import api from "./API";

class PaymentService {
  async pay(shoppingCart) {
    const response = await api.post("api/payment", shoppingCart);
    return response.data;
  }
}

export default new PaymentService();
