import React, { useState, useEffect } from "react";

export default function ProductTable(props) {
  const [tableRows, setTableRows] = useState([]);

  useEffect(() => {
    if (props.products.length > 0) {
      const rows = props.products.map((product, key) => {
        return (
          <tr id={key}>
            <td style={{ textAlign: "center" }}>
              <img src={product.productDto.image} alt="Product" />
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{product.productDto.name}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{product.productDto.description}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{product.productDto.price}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{product.quantity}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{product.productDto.price * product.quantity}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{product.productDto.currency}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{product.date}</label>
            </td>
          </tr>
        );
      });
      setTableRows(rows);
    } else {
      const rows = (
        <tr>
          <td
            style={{ verticalAlign: "middle", textAlign: "center" }}
            colSpan="8"
          >
            <h5>You don't have any bought products...</h5>
          </td>
        </tr>
      );
      setTableRows(rows);
    }
  }, [props.products]);

  return (
    <div style={{ marginTop: "30px", marginBottom: "30px" }}>
      <div class="row">
        <div class="col-lg-12">
          <div class="card">
            <div class="card-body">
              <div class="table-responsive project-list">
                <div class="graph-star-rating-footer text-center ">
                  <h4
                    style={{
                      textAlign: "center",
                      color: "#74767a",
                      paddingBottom: "20px",
                    }}
                  >
                    Product purchase history
                  </h4>
                </div>
                <table class="table project-table table-centered table-nowrap fixed_header">
                  <thead>
                    <tr>
                      <th scope="col" style={{ textAlign: "center" }}>
                        Image
                      </th>
                      <th scope="col" style={{ textAlign: "center" }}>
                        Name
                      </th>
                      <th scope="col" style={{ textAlign: "center" }}>
                        Description
                      </th>
                      <th scope="col" style={{ textAlign: "center" }}>
                        Price
                      </th>
                      <th scope="col" style={{ textAlign: "center" }}>
                        Quantity
                      </th>
                      <th scope="col" style={{ textAlign: "center" }}>
                        Total price
                      </th>
                      <th scope="col" style={{ textAlign: "center" }}>
                        Currency
                      </th>
                      <th scope="col" style={{ textAlign: "center" }}>
                        Purchase date
                      </th>
                    </tr>
                  </thead>
                  <tbody>{tableRows}</tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
