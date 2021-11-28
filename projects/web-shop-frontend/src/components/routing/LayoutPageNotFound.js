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
  justifyContent: "center",
  alignItems: "center",
};

export default function LayoutPageNotFound(props) {
  return (
    <div style={MainLayoutRoot}>
      <div style={MainLayoutContent}>{props.children}</div>
    </div>
  );
}
