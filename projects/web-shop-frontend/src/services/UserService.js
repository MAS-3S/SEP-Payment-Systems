import api from "./API";

class UserService {
  async findById(id) {
    const response = await api.get("api/users/" + id);
    return response.data;
  }
}

export default new UserService();
