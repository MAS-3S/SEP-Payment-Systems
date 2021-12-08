import api from "./API";
import { WEBSHOP_URL } from "../util/Constants";

class AccommodationService {
  async findAllByWebShop(id) {
    const response = await api.get(
      WEBSHOP_URL + "api/accommodations/webshop/" + id
    );
    return response.data;
  }

  async findAllPayedAccommodationsForUser(userId, webshopId) {
    const response = await api.get(
      WEBSHOP_URL + "api/accommodations/user/" + userId + "/" + webshopId
    );
    return response.data;
  }
}

export default new AccommodationService();
