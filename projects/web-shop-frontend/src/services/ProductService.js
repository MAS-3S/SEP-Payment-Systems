import api from "./API";

class ProductService {
  async findAllByWebShop(id) {
    const response = await api.get("api/products/webshop/" + id);
    return response.data;
  }
}

export default new ProductService();
