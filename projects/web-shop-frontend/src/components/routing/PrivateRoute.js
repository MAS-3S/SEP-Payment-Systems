import React from "react";
import { Route, Redirect } from "react-router-dom";
import TokenService from "../../services/TokenService";

export const PrivateRoute = ({ component: Component, roles, ...rest }) => {
  if (!Component) return null;
  return (
    <Route
      {...rest}
      render={(props) => {
        const user = TokenService.getUser();
        if (user == null) {
          return (
            <Redirect
              to={{ pathname: "/login", state: { from: props.location } }}
            />
          );
        }
        return <Component {...props} />;
      }}
    />
  );
};
