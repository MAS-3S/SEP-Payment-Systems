import api from "./API";

class AccommodationService {
  async findAllByWebShop(id) {
    const response = await api.get("api/accommodations/webshop/" + id);
    return response.data;
  }
}

export default new AccommodationService();
