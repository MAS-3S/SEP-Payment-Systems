import api from "./API";
import { WEBSHOP_URL } from "../util/Constants";

class ConferenceService {
  async findAllByWebShop(id) {
    const response = await api.get(
      WEBSHOP_URL + "api/conferences/webshop/" + id
    );
    return response.data;
  }
}

export default new ConferenceService();
