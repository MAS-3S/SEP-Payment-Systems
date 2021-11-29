import { API_URL } from "../util/Constants";

class ConferenceService {
  findAllByWebShop(id) {
    return fetch(API_URL + "api/conferences/webshop/" + id)
      .then((response) => (response.ok ? response : Promise.reject(response)))
      .then((response) => response.json());
  }
}

export default new ConferenceService();
