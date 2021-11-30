import React, { useEffect, useState } from "react";
import "../../assets/css/productCardStyle.css";

export default function ConferenceCard(props) {
  const [conference, setConference] = useState(props.conference);

  useEffect(() => {
    setConference(props.conference);
  }, [props.conference]);

  return (
    <div id="productCardContainer">
      <div className="productCard-details">
        <h1>{conference.name}</h1>
        <p className="productCardInformation">{conference.description}</p>
        <div className="productCardControl">
          <button className="productCardBtn">
            <span className="productCardPrice">
              {conference.price} {conference.currency}
            </span>
            <span className="productCardShopping-cart">
              <i className="fa fa-shopping-cart" aria-hidden="true"></i>
            </span>
            <span className="productCardBuy">Get now</span>
          </button>
        </div>
      </div>

      <div className="product-image">
        <img
          src="https://images.unsplash.com/photo-1606830733744-0ad778449672?ixid=MXwxMjA3fDB8MHxzZWFyY2h8Mzl8fGNocmlzdG1hcyUyMHRyZWV8ZW58MHx8MHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"
          alt=""
        />

        <div className="productCardInfo">
          <h2>Purchase info</h2>
          <ul>
            <li>
              <strong>Name: </strong>
              {conference.name}
            </li>
            <li>
              <strong>Available balance: </strong>
              {conference.availableBalance}
            </li>
            <li>
              <strong>Address: </strong>
              {conference.address}
            </li>
            <li>
              <strong>Start time: </strong>
              {conference.startTime}
            </li>
            <li>
              <strong>End time: </strong>
              {conference.endTime}
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
}
