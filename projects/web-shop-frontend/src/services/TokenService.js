class TokenService {
  getLocalRefreshToken() {
    const user = JSON.parse(localStorage.getItem("user"));
    return user?.userTokenState.refreshToken;
  }

  getLocalAccessToken() {
    const user = JSON.parse(localStorage.getItem("user"));
    return user?.userTokenState.accessToken;
  }

  getTokenExpiresDate() {
    const user = JSON.parse(localStorage.getItem("user"));
    return user?.tokenExpiresDate;
  }

  updateLocalAccessToken(token) {
    let user = JSON.parse(localStorage.getItem("user"));
    user.userTokenState.accessToken = token;
    localStorage.setItem("user", JSON.stringify(user));
  }

  getUser() {
    return JSON.parse(localStorage.getItem("user"));
  }

  getUserRole() {
    const user = this.getUser();
    return user?.role;
  }

  getUserId() {
    const user = this.getUser();
    return user?.id;
  }

  setUser(user) {
    localStorage.setItem("user", JSON.stringify(user));
  }

  removeUser() {
    localStorage.removeItem("user");
  }

  checkIfAccessTokenIsExpired(expiresIn) {
    const expiresInDate = new Date(expiresIn);
    if (expiresInDate < new Date()) {
      return true;
    }
    return false;
  }
}

export default new TokenService();
