import { WEBSHOP_URL } from "../util/Constants";
import api from "./API";

class UserService {
  async findById(id) {
    const response = await api.get(WEBSHOP_URL + "api/users/" + id);
    return response.data;
  }

  async block(email) {
    const body = {
      email: email,
    };

    const response = await api.put(WEBSHOP_URL + "api/users/block", body);
    return response.data;
  }
}

export default new UserService();
