import React, { useEffect, useState } from "react";
import AccommodationsTable from "../components/tables/AccommodationsTable";
import ConferencesTable from "../components/tables/ConferencesTable";
import ProductTable from "../components/tables/ProductsTable";

export default function UserPurchaseHistory(props) {
  const [products, setProducts] = useState(props.products);
  const [accommodations, setAccommodations] = useState(props.accommodations);
  const [conferences, setConferences] = useState(props.conferences);

  useEffect(() => {
    setProducts(props.products);
  }, [props.products]);

  useEffect(() => {
    setAccommodations(props.accommodations);
  }, [props.accommodations]);

  useEffect(() => {
    setConferences(props.conferences);
  }, [props.conferences]);

  return (
    <div style={{ width: "98%" }}>
      <ProductTable products={products} />
      <AccommodationsTable accommodations={accommodations} />
      <ConferencesTable conferences={conferences} />
    </div>
  );
}
