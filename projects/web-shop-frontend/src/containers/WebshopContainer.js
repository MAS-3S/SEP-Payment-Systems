import React, { useEffect, useState } from "react";
import { Redirect } from "react-router";
import Webshop from "../pages/Webshop";
import AccommodationService from "../services/AccommodationService";
import ConferenceService from "../services/ConferenceService";
import ProductService from "../services/ProductService";
import WebShopService from "../services/WebShopService";

export default function WebshopContainer(props) {
  const [sholudRedirect, setSholudRedirect] = useState(false);
  const [activeWebshop, setActiveWebshop] = useState({});
  const [activeCurrency, setActiveCurrency] = useState({});
  const [products, setProducts] = useState([]);
  const [conferences, setConferences] = useState([]);
  const [accommodations, setAccommodations] = useState([]);

  useEffect(() => {
    async function fetchData() {
      var webshops = await WebShopService.findAll();
      if (
        !webshops.some(
          (webshop) =>
            webshop.name.toLowerCase() ===
            props.match.params.webshop.toLowerCase()
        )
      ) {
        setSholudRedirect(true);
      } else {
        let webShop =
          webshops[
            webshops.findIndex(
              (webshop) =>
                webshop.name.toLowerCase() ===
                props.match.params.webshop.toLowerCase()
            )
          ];
        setActiveWebshop(webShop);
        sessionStorage.removeItem("activeWebShop");
        sessionStorage.setItem("activeWebShop", JSON.stringify(webShop));
        if (webShop.type === "PRODUCT") {
          setProducts(await ProductService.findAllByWebShop(webShop.id));
        } else if (webShop.type === "CONFERENCE") {
          setConferences(await ConferenceService.findAllByWebShop(webShop.id));
        } else if (webShop.type === "ACCOMMODATION") {
          setAccommodations(
            await AccommodationService.findAllByWebShop(webShop.id)
          );
        }
      }
    }
    setActiveCurrency(props.match.params.currency);
    fetchData();
  }, [props.match.params.webshop, props.match.params.currency]);

  return sholudRedirect ? (
    <Redirect
      to={{
        pathname: `/`,
      }}
    />
  ) : (
    <Webshop
      activeWebshop={activeWebshop}
      activeCurrency={activeCurrency}
      products={products}
      conferences={conferences}
      accommodations={accommodations}
    />
  );
}
