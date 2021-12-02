import { WEBSHOP_URL } from "../util/Constants";
import api from "./API";

class UserService {
  async findById(id) {
    const response = await api.get(WEBSHOP_URL + "api/users/" + id);
    return response.data;
  }
}

export default new UserService();
