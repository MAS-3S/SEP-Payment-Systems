import React, { useEffect, useState } from "react";
import UserPurchaseHistory from "../pages/UserPurchaseHistory";
import AccommodationService from "../services/AccommodationService";
import ConferenceService from "../services/ConferenceService";
import ProductService from "../services/ProductService";

export default function UserPurchaseHistoryContainer(props) {
  const [products, setProducts] = useState([]);
  const [accommodations, setAccommodations] = useState([]);
  const [conferences, setConferences] = useState([]);
  const [webShopName, setWebShopName] = useState(props.location.paramName);

  useEffect(() => {
    async function fetchData() {
      setWebShopName(props.location.paramName);
      if (props.location.paramName === "Products Shop") {
        setProducts(
          await ProductService.findAllPayedProductsForUser(
            props.match.params.userId,
            props.match.params.webshopId
          )
        );
      } else if (props.location.paramName === "Conferences Shop") {
        setConferences(
          await ConferenceService.findAllPayedConferencesForUser(
            props.match.params.userId,
            props.match.params.webshopId
          )
        );
      } else if (props.location.paramName === "Accommodations Shop") {
        setAccommodations(
          await AccommodationService.findAllPayedAccommodationsForUser(
            props.match.params.userId,
            props.match.params.webshopId
          )
        );
      }
    }
    fetchData();
  }, [
    props.match.params.userId,
    props.match.params.webshopId,
    props.location.paramName,
  ]);

  return (
    <UserPurchaseHistory
      webShopName={webShopName}
      products={products}
      accommodations={accommodations}
      conferences={conferences}
    />
  );
}
