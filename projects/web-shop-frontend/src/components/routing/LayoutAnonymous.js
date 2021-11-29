import React from "react";
import UnauthenticatedUsersNavbar from "../navbar/UnauthenticatedUsersNavbar";

const MainLayoutRoot = {
  height: "100%",
  width: "100%",
  overflow: "hidden",
};

const MainLayoutContent = {
  display: "flex",
  flex: "auto",
  height: "100%",
  width: "100%",
  overflow: "auto",
  marginTop: 60,
  justifyContent: "center",
  alignItems: "center",
};

export default function LayoutAnonymous(props) {
  return (
    <div style={MainLayoutRoot}>
      <UnauthenticatedUsersNavbar />
      <div style={MainLayoutContent}>{props.children}</div>
    </div>
  );
}
