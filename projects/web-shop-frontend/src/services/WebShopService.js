import api from "./API";
import { WEBSHOP_URL } from "../util/Constants";

class WebShopService {
  async findAll() {
    const response = await api.get(WEBSHOP_URL + "api/webshops");
    return response.data;
  }
}

export default new WebShopService();
