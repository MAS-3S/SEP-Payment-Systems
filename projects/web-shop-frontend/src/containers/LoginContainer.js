import React, { useEffect, useState } from "react";
import { Redirect } from "react-router";
import Login from "../pages/Login";
import AuthService from "../services/AuthService";
import LocalStorageService from "../services/LocalStorageService";
import UserService from "../services/UserService";
import WebShopService from "../services/WebShopService";

export default function LoginContainer() {
  const [sholudRedirect, setSholudRedirect] = useState(false);
  const [shouldOpenSnackbar, setShouldOpenSnackbar] = useState(false);
  const [activeWebshop, setActiveWebshop] = useState({ name: "" });
  const [snackbarMessage, setSnackbarMessage] = useState("");

  useEffect(() => {
    async function fetchData() {
      var webshops = await WebShopService.findAll();
      setActiveWebshop(webshops[0]);
    }
    fetchData();
  }, []);

  const handleLogin = (email, password) => {
    setShouldOpenSnackbar(false);
    AuthService.login(email, password)
      .then((response) => {
        setSholudRedirect(true);
        setShouldOpenSnackbar(true);
        LocalStorageService.removeItem("numberOfFailedPasswordEntries");
      })
      .catch((error) => {
        if (error.response.data === "User profile is blocked") {
          setShouldOpenSnackbar(true);
          setSnackbarMessage(error.response.data);
          return;
        }

        var numberOfFailedPasswordEntries = LocalStorageService.getItem(
          "numberOfFailedPasswordEntries"
        );

        if (numberOfFailedPasswordEntries === null) {
          LocalStorageService.setItem("numberOfFailedPasswordEntries", 1);
          setSnackbarMessage("You have entered an invalid email or password");
          setShouldOpenSnackbar(true);
        } else {
          LocalStorageService.setItem(
            "numberOfFailedPasswordEntries",
            numberOfFailedPasswordEntries + 1
          );
          numberOfFailedPasswordEntries = LocalStorageService.getItem(
            "numberOfFailedPasswordEntries"
          );
          if (numberOfFailedPasswordEntries < 4) {
            setSnackbarMessage("You have entered an invalid email or password");
            setShouldOpenSnackbar(true);
          } else if (numberOfFailedPasswordEntries === 4) {
            setSnackbarMessage(
              "Your profile will be blocked if you enter wrong password next time"
            );
            setShouldOpenSnackbar(true);
          } else {
            UserService.block(email).then((response) => {
              if (response) {
                setSnackbarMessage(
                  "Your profile is blocked. Please wait for admin to unblock you."
                );
              } else {
                LocalStorageService.removeItem("numberOfFailedPasswordEntries");
                setSnackbarMessage("Invalid email");
              }
              setShouldOpenSnackbar(true);
            });
          }
        }
      });
  };

  return sholudRedirect ? (
    <Redirect
      to={{
        pathname: `/webshop/${activeWebshop.name.toLowerCase()}/EUR`,
      }}
    />
  ) : (
    <Login
      handleLogin={handleLogin}
      shouldOpenSnackbar={shouldOpenSnackbar}
      snackbarMessage={snackbarMessage}
    />
  );
}
