import api from "./API";
import TokenService from "./TokenService";
import { WEBSHOP_URL } from "../util/Constants";

class AuthService {
  async login(email, password) {
    const response = await api.post(WEBSHOP_URL + "auth/login", {
      email,
      password,
    });
    if (response.data.userTokenState) {
      TokenService.setUser(response.data);
    }
    return response.data;
  }

  logout() {
    TokenService.removeUser();
  }

  register(email, fullName, address, phone, password) {
    return api.post(WEBSHOP_URL + "api/users/register", {
      email,
      fullName,
      address,
      phone,
      password,
    });
  }
}

export default new AuthService();
