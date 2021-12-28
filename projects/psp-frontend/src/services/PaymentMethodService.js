import { GATEWAY_URL } from "../util/Constants";
import axios from "axios";

class PaymentMethodService {
  async getMerchantsPaymentMethodsForSubscription(merchantId) {
    const response = await axios.get(
      GATEWAY_URL + "api/psp/paymentMethods/" + merchantId
    );
    return response.data;
  }

  async getMerchantsPaymentMethodsForPayment(merchantId) {
    const response = await axios.get(
      GATEWAY_URL + "api/psp/paymentMethods/payment/" + merchantId
    );
    return response.data;
  }

  async changeMerchantSubscription(paymentId, merchantId) {
    const body = {
      paymentId: paymentId,
      merchantId: merchantId,
    };

    const response = await axios.post(
      GATEWAY_URL + "api/psp/paymentMethods/changeSubscription",
      body
    );
    return response.data;
  }

  async choosePaymentMethod(paymentMethodId, merchantId, paymentId) {
    const body = {
      paymentMethodId: paymentMethodId,
      merchantId: merchantId,
      paymentId: paymentId,
    };

    const response = await axios.post(
      GATEWAY_URL + "api/psp/paymentMethods/choosePaymentMethod",
      body
    );
    return response.data;
  }
}

export default new PaymentMethodService();
