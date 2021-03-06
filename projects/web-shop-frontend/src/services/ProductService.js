import api from "./API";
import { WEBSHOP_URL } from "../util/Constants";

class ProductService {
  async findAllByWebShop(id) {
    const response = await api.get(WEBSHOP_URL + "api/products/webshop/" + id);
    return response.data;
  }

  async findAllPayedProductsForUser(userId, webshopId) {
    const response = await api.get(
      WEBSHOP_URL + "api/products/user/" + userId + "/" + webshopId
    );
    return response.data;
  }
}

export default new ProductService();
