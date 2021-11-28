import React from "react";

const MainLayoutRoot = {
  position: "fixed",
  height: "100%",
  width: "100%",
  overflow: "hidden",
};

const MainLayoutContent = {
  display: "flex",
  flex: "auto",
  height: "100%",
  overflow: "auto",
  marginTop: 25,
  justifyContent: "center",
  alignItems: "center",
};

export default function LayoutAuthenticated(props) {
  return (
    <div style={MainLayoutRoot}>
      {/* <AuthenticatedUsersNavbar /> */}
      <div style={MainLayoutContent}>{props.children}</div>
    </div>
  );
}
