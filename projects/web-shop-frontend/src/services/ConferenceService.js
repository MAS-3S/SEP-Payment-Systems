import api from "./API";
import { WEBSHOP_URL } from "../util/Constants";

class ConferenceService {
  async findAllByWebShop(id) {
    const response = await api.get(
      WEBSHOP_URL + "api/conferences/webshop/" + id
    );
    return response.data;
  }

  async findAllPayedConferencesForUser(userId, webshopId) {
    const response = await api.get(
      WEBSHOP_URL + "api/conferences/user/" + userId + "/" + webshopId
    );
    return response.data;
  }
}

export default new ConferenceService();
