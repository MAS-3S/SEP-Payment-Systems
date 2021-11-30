import React, { useEffect, useState } from "react";
import AccommodationCard from "../components/cards/AccommodationCard";
import ConferenceCard from "../components/cards/ConferenceCard";
import ProductCard from "../components/cards/ProductCard";

export default function Webshop(props) {
  const [activeWebshop, setActiveWebshop] = useState(props.activeWebshop);

  useEffect(() => {
    setActiveWebshop(props.activeWebshop);
  }, [props.activeWebshop]);

  const productsList = Array.apply(null, { length: props.products.length }).map(
    (_, i) => <ProductCard key={i} product={props.products[i]} />
  );

  const conferencesList = Array.apply(null, {
    length: props.conferences.length,
  }).map((_, i) => (
    <ConferenceCard key={i} conference={props.conferences[i]} />
  ));

  const accommodationsList = Array.apply(null, {
    length: props.accommodations.length,
  }).map((_, i) => (
    <AccommodationCard key={i} accommodation={props.accommodations[i]} />
  ));

  const items = (() => {
    if (activeWebshop.type === "PRODUCT") {
      return <div>{productsList}</div>;
    } else if (activeWebshop.type === "CONFERENCE") {
      return <div>{conferencesList}</div>;
    } else if (activeWebshop.type === "ACCOMMODATION") {
      return <div>{accommodationsList}</div>;
    } else {
      return <div>WebshopUnauthenticated 2</div>;
    }
  })();

  return <div>{items}</div>;
}
