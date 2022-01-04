import React, { useEffect, useState } from "react";
import AccommodationCard from "../components/cards/AccommodationCard";
import ConferenceCard from "../components/cards/ConferenceCard";
import ProductCard from "../components/cards/ProductCard";

export default function Webshop(props) {
  const [activeWebshop, setActiveWebshop] = useState(props.activeWebshop);
  const [productsList, setProductsList] = useState([]);
  const [conferencesList, setConferencesList] = useState([]);
  const [accommodationsList, setAccommodationsList] = useState([]);

  useEffect(() => {
    setActiveWebshop(props.activeWebshop);
    if (!localStorage.getItem("shoppingCart")) {
      localStorage.setItem("shoppingCart", JSON.stringify([]));
    }
  }, [props.activeWebshop]);

  useEffect(() => {
    const products = Array.apply(null, { length: props.products.length }).map(
      (_, i) => (
        <ProductCard
          key={i}
          product={props.products[i]}
          activeCurrency={props.activeCurrency}
        />
      )
    );
    setProductsList(products);
  }, [props.products, props.activeCurrency]);

  useEffect(() => {
    const conferences = Array.apply(null, {
      length: props.conferences.length,
    }).map((_, i) => (
      <ConferenceCard
        key={i}
        conference={props.conferences[i]}
        activeCurrency={props.activeCurrency}
      />
    ));
    setConferencesList(conferences);
  }, [props.conferences, props.activeCurrency]);

  useEffect(() => {
    const accommodations = Array.apply(null, {
      length: props.accommodations.length,
    }).map((_, i) => (
      <AccommodationCard
        key={i}
        accommodation={props.accommodations[i]}
        activeCurrency={props.activeCurrency}
      />
    ));
    setAccommodationsList(accommodations);
  }, [props.accommodations, props.activeCurrency]);

  const items = (() => {
    if (activeWebshop.type === "PRODUCT") {
      return <div>{productsList}</div>;
    } else if (activeWebshop.type === "CONFERENCE") {
      return <div>{conferencesList}</div>;
    } else if (activeWebshop.type === "ACCOMMODATION") {
      return <div>{accommodationsList}</div>;
    } else {
      return <div></div>;
    }
  })();

  return <div>{items}</div>;
}
