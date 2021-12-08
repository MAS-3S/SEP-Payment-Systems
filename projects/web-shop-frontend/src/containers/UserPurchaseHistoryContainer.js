import React, { useEffect, useState } from "react";
import UserPurchaseHistory from "../pages/UserPurchaseHistory";
import AccommodationService from "../services/AccommodationService";
import ConferenceService from "../services/ConferenceService";
import ProductService from "../services/ProductService";

export default function UserPurchaseHistoryContainer(props) {
  const [products, setProducts] = useState([]);
  const [accommodations, setAccommodations] = useState([]);
  const [conferences, setConferences] = useState([]);

  useEffect(() => {
    async function fetchData() {
      setProducts(
        await ProductService.findAllPayedProductsForUser(
          props.match.params.userId
        )
      );
      setAccommodations(
        await AccommodationService.findAllPayedAccommodationsForUser(
          props.match.params.userId
        )
      );
      setConferences(
        await ConferenceService.findAllPayedConferencesForUser(
          props.match.params.userId
        )
      );
    }
    fetchData();
  }, [props.match.params.userId]);

  return (
    <UserPurchaseHistory
      products={products}
      accommodations={accommodations}
      conferences={conferences}
    />
  );
}
