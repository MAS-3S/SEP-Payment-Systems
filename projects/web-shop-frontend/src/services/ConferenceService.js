import api from "./API";

class ConferenceService {
  async findAllByWebShop(id) {
    const response = await api.get("api/conferences/webshop/" + id);
    return response.data;
  }
}

export default new ConferenceService();
