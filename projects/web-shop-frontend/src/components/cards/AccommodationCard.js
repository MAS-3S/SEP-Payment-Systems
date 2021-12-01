import React, { useEffect, useState } from "react";
import "../../assets/css/productCardStyle.css";

export default function AccommodationCard(props) {
  const [accommodation, setAccommodation] = useState(props.accommodation);

  useEffect(() => {
    setAccommodation(props.accommodation);
  }, [props.accommodation]);

  return (
    <div id="productCardContainer">
      <div className="productCard-details">
        <h1>{accommodation.name}</h1>
        <p className="productCardInformation">{accommodation.description}</p>
        <div className="productCardControl">
          <button className="productCardBtn">
            <span className="productCardPrice">
              {accommodation.price} {accommodation.currency}
            </span>
            <span className="productCardShopping-cart">
              <i className="fa fa-shopping-cart" aria-hidden="true"></i>
            </span>
            <span className="productCardBuy">Get now</span>
          </button>
        </div>
      </div>

      <div className="product-image">
        <img src={accommodation.image} alt="Accommodation" />
        <div className="productCardInfo">
          <h2>Purchase info</h2>
          <ul>
            <li>
              <strong>Name: </strong>
              {accommodation.name}
            </li>
            <li>
              <strong>Available balance: </strong>
              {accommodation.availableBalance}
            </li>
            <li>
              <strong>Address: </strong>
              {accommodation.address}
            </li>
            <li>
              <strong>Start date: </strong>
              {accommodation.startDate}
            </li>
            <li>
              <strong>Days: </strong>
              {accommodation.days}
            </li>
            <li>
              <strong>Number of beds: </strong>
              {accommodation.numberOfBeds}
            </li>
            <li>
              <strong>Transport: </strong>
              {accommodation.transportName}
            </li>
            <li>
              <strong>Transport price: </strong>
              {accommodation.transportPrice}
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
}
