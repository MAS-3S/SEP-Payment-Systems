import { API_URL } from "../util/Constants";

class ProductService {
  findAllByWebShop(id) {
    return fetch(API_URL + "api/products/webshop/" + id)
      .then((response) => (response.ok ? response : Promise.reject(response)))
      .then((response) => response.json());
  }
}

export default new ProductService();
