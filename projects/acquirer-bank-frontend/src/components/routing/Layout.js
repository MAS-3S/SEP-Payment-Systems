import React from "react";

const MainLayoutRoot = {
  height: "100%",
  width: "100%",
  overflow: "hidden",
  backgroundColor: "#f6f5f7",
};

const MainLayoutContent = {
  display: "flex",
  flex: "auto",
  height: "100%",
  width: "100%",
  overflow: "auto",
  justifyContent: "center",
  alignItems: "center",
};

export default function Layout(props) {
  return (
    <div style={MainLayoutRoot}>
      <div style={MainLayoutContent}>{props.children}</div>
    </div>
  );
}
