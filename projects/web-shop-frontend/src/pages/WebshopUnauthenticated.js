import React, { useEffect, useState } from "react";
import ProductCard from "../components/cards/ProductCard";

const products = [
  {
    id: 1,
    name: "Gaming mouse 1",
    description:
      "For the majority of games and gamers, the DeathAdder V2 is a fantastic mouse. Its design is simple, with two perfectly placed, generously sized thumb buttons. It also has an excellent optical mouse sensor that will work on both hard and cloth pads, and it has the ultimate body shape for a claw or hybrid claw/palm grip. ",
    price: 100,
    availableBalance: 10,
    image: "",
    currency: "€",
  },
  {
    id: 2,
    name: "Gaming mouse 2",
    description:
      "For the majority of games and gamers, the DeathAdder V2 is a fantastic mouse. Its design is simple, with two perfectly placed, generously sized thumb buttons. It also has an excellent optical mouse sensor that will work on both hard and cloth pads, and it has the ultimate body shape for a claw or hybrid claw/palm grip. ",
    price: 111,
    availableBalance: 20,
    image: "",
    currency: "€",
  },
  {
    id: 3,
    name: "Gaming mouse 3",
    description:
      "For the majority of games and gamers, the DeathAdder V2 is a fantastic mouse. Its design is simple, with two perfectly placed, generously sized thumb buttons. It also has an excellent optical mouse sensor that will work on both hard and cloth pads, and it has the ultimate body shape for a claw or hybrid claw/palm grip. ",
    price: 99,
    availableBalance: 15,
    image: "",
    currency: "€",
  },
];

export default function WebshopUnauthenticated(props) {
  const [activeWebshop, setActiveWebshop] = useState(props.activeWebshop);

  useEffect(() => {
    setActiveWebshop(props.activeWebshop);
  }, [props.activeWebshop]);

  const productsList = Array.apply(null, { length: products.length }).map(
    (_, i) => <ProductCard key={i} product={products[i]} />
  );

  return activeWebshop.id === 1 ? (
    <div>{productsList}</div>
  ) : (
    <div>WebshopUnauthenticated 2</div>
  );
}
