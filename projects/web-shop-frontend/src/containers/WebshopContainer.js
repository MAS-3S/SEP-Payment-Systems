import React, { useEffect, useState } from "react";
import { Redirect } from "react-router";
import Webshop from "../pages/Webshop";

const webshops = [
  {
    id: 1,
    name: "Gigatron",
  },
  { id: 2, name: "Tehnomanija" },
];

export default function WebshopContainer(props) {
  const [sholudRedirect, setSholudRedirect] = useState(false);
  const [activeWebshop, setActiveWebshop] = useState({});

  useEffect(() => {
    if (
      !webshops.some(
        (webshop) =>
          webshop.name.toLowerCase() ===
          props.match.params.webshop.toLowerCase()
      )
    ) {
      setSholudRedirect(true);
    } else {
      setActiveWebshop(
        webshops[
          webshops.findIndex(
            (webshop) =>
              webshop.name.toLowerCase() ===
              props.match.params.webshop.toLowerCase()
          )
        ]
      );
    }
  }, [props.match.params.webshop]);

  return sholudRedirect ? (
    <Redirect
      to={{
        pathname: `/`,
      }}
    />
  ) : (
    <Webshop activeWebshop={activeWebshop} />
  );
}
