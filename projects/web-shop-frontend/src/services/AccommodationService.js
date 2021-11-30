import { API_URL } from "../util/Constants";

class AccommodationService {
  findAllByWebShop(id) {
    return fetch(API_URL + "api/accommodations/webshop/" + id)
      .then((response) => (response.ok ? response : Promise.reject(response)))
      .then((response) => response.json());
  }
}

export default new AccommodationService();
