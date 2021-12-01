import api from "./API";

class WebShopService {
  async findAll() {
    const response = await api.get("api/webshops");
    return response.data;
  }
}

export default new WebShopService();
