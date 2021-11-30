import React, { useState } from "react";
import { Redirect } from "react-router";
import Login from "../pages/Login";
import AuthService from "../services/AuthService";

export default function LoginContainer() {
  const [sholudRedirect, setSholudRedirect] = useState(false);
  const [shouldOpenSnackbar, setShouldOpenSnackbar] = useState(false);

  const handleLogin = (email, password) => {
    setShouldOpenSnackbar(false);
    AuthService.login(email, password)
      .then((response) => {
        setSholudRedirect(true);
        setShouldOpenSnackbar(true);
      })
      .catch((error) => {
        setShouldOpenSnackbar(true);
      });
  };

  return sholudRedirect ? (
    <Redirect
      to={{
        pathname: `/`,
      }}
    />
  ) : (
    <Login handleLogin={handleLogin} shouldOpenSnackbar={shouldOpenSnackbar} />
  );
}
