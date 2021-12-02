import api from "./API";
import { GATEWAY_URL } from "../util/Constants";

class SubscribeService {
  async findSubscribeUrl(merchantId) {
    const response = await api.post(
      GATEWAY_URL + "api/psp/paymentMethods/subscribeUrl",
      {
        merchantId: merchantId,
      }
    );
    return response.data;
  }
}

export default new SubscribeService();
