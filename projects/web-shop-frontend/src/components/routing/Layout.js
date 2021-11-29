import React from "react";
import Navbar from "../navbar/Navbar";

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
  marginTop: 65,
  justifyContent: "center",
  alignItems: "center",
};

export default function Layout(props) {
  return (
    <div style={MainLayoutRoot}>
      <Navbar />
      <div style={MainLayoutContent}>{props.children}</div>
    </div>
  );
}
