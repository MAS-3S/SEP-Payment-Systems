import React, { useEffect, useState } from "react";
import { Redirect } from "react-router";
import Login from "../pages/Login";
import AuthService from "../services/AuthService";
import WebShopService from "../services/WebShopService";

export default function LoginContainer() {
  const [sholudRedirect, setSholudRedirect] = useState(false);
  const [shouldOpenSnackbar, setShouldOpenSnackbar] = useState(false);
  const [activeWebshop, setActiveWebshop] = useState({ name: "" });

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
      })
      .catch((error) => {
        setShouldOpenSnackbar(true);
      });
  };

  return sholudRedirect ? (
    <Redirect
      to={{
        pathname: `/webshop/${activeWebshop.name.toLowerCase()}/EUR`,
      }}
    />
  ) : (
    <Login handleLogin={handleLogin} shouldOpenSnackbar={shouldOpenSnackbar} />
  );
}
