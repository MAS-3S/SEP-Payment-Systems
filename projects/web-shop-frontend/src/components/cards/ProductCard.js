import React, { useEffect, useState } from "react";
import "../../assets/css/productCardStyle.css";

export default function ProductCard(props) {
  const [product, setProduct] = useState(props.product);
  useEffect(() => {
    setProduct(props.product);
  }, [props.product]);

  return (
    <div id="productCardContainer">
      <div className="productCard-details">
        <h1>{product.name}</h1>
        <p className="productCardInformation">{product.description}</p>
        <div className="productCardControl">
          <button className="productCardBtn">
            <span className="productCardPrice">
              {product.price} {product.currency}
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
              {product.name}
            </li>
            <li>
              <strong>Price: </strong>
              {product.price}
            </li>
            <li>
              <strong>Currency: </strong>
              {product.currency}
            </li>
            <li>
              <strong>Available balance: </strong>
              {product.availableBalance}
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
}
