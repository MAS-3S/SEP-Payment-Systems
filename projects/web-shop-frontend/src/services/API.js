import axios from "axios";
import TokenService from "./TokenService";
import { API_URL, LOGIN_URL } from "../util/Constants";

const instance = axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

instance.interceptors.request.use(
  (config) => {
    const token = TokenService.getLocalAccessToken();
    if (token) {
      config.headers["Authorization"] = "Bearer " + token; // for Spring Boot back-end
      // config.headers["x-access-token"] = token; // for Node.js Express back-end
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

instance.interceptors.response.use(
  (res) => {
    return res;
  },
  async (err) => {
    const originalConfig = err.config;
    if (originalConfig.url !== "auth/login" && err.response) {
      // Access Token was expired
      const token = TokenService.getLocalAccessToken();
      const tokenExpiresDate = TokenService.getTokenExpiresDate();
      const isAccessTokenExpired =
        TokenService.checkIfAccessTokenIsExpired(tokenExpiresDate);

      if (
        err.response.data === "Use refresh token for creating new access token!"
      ) {
        TokenService.removeUser();
        window.location.href = LOGIN_URL;
        return Promise.reject(err);
      }

      if (err.response.status === 401 && !originalConfig._retry && !token) {
        return Promise.reject(err);
      }

      if (
        err.response.status === 401 &&
        !originalConfig._retry &&
        token &&
        !isAccessTokenExpired
      ) {
        return Promise.reject(err);
      }

      if (
        err.response.status === 401 &&
        !originalConfig._retry &&
        token &&
        isAccessTokenExpired
      ) {
        originalConfig._retry = true;
        TokenService.updateLocalAccessToken(
          TokenService.getLocalRefreshToken()
        );
        try {
          const rs = await instance.post("auth/refreshToken", {
            refreshToken: TokenService.getLocalRefreshToken(),
          });

          TokenService.updateLocalAccessToken(
            rs.data.userTokenState.accessToken
          );

          return instance(originalConfig);
        } catch (_error) {
          return Promise.reject(_error);
        }
      }
    }

    return Promise.reject(err);
  }
);

export default instance;
