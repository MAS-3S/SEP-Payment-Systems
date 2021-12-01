import api from "./API";
import TokenService from "./TokenService";

class AuthService {
  async login(email, password) {
    const response = await api.post("/auth/login", {
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
    return api.post("/api/users/register", {
      email,
      fullName,
      address,
      phone,
      password,
    });
  }

  getCurrentUser() {
    return TokenService.getUser();
  }
}

export default new AuthService();
