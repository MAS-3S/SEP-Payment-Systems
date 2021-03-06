import React from "react";
import { Route } from "react-router-dom";

export const PublicRoute = ({ component: Component, roles, ...rest }) => {
  if (!Component) return null;
  return (
    <Route
      {...rest}
      render={(props) => {
        return <Component {...props} />;
      }}
    />
  );
};
