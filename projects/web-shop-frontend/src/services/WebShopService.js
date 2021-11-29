import { API_URL } from "../util/Constants";

class WebShopService {
  findAll() {
    return fetch(API_URL + "api/webshops")
      .then((response) => (response.ok ? response : Promise.reject(response)))
      .then((response) => response.json());
  }
}

export default new WebShopService();
