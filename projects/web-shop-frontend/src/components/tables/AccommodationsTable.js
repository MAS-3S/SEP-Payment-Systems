import React, { useState, useEffect } from "react";

export default function AccommodationsTable(props) {
  const [tableRows, setTableRows] = useState([]);

  useEffect(() => {
    if (props.accommodations.length > 0) {
      const rows = props.accommodations.map((accommodation, key) => {
        return (
          <tr id={key}>
            <td style={{ textAlign: "center" }}>
              <img
                src={accommodation.accommodationDto.image}
                alt="Accommodation"
              />
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{accommodation.accommodationDto.name}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{accommodation.accommodationDto.price}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{accommodation.quantity}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>
                {accommodation.accommodationDto.price * accommodation.quantity}
              </label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{accommodation.accommodationDto.currency}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{accommodation.accommodationDto.address}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{accommodation.accommodationDto.startDate}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{accommodation.accommodationDto.days}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{accommodation.accommodationDto.numberOfBeds}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{accommodation.accommodationDto.transportName}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{accommodation.date}</label>
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
            colSpan="12"
          >
            <h5>You don't have any bought accommodations...</h5>
          </td>
        </tr>
      );
      setTableRows(rows);
    }
  }, [props.accommodations]);

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
                    Accommodation purchase history
                  </h4>
                </div>
                <table class="table project-table table-centered table-nowrap fixed_header">
                  <thead>
                    <tr>
                      <th scope="col" style={{ textAlign: "center" }}>
                        Image
                      </th>
                      <th
                        scope="col"
                        style={{ minWidth: "130px", textAlign: "center" }}
                      >
                        Name
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
                      <th
                        scope="col"
                        style={{ minWidth: "130px", textAlign: "center" }}
                      >
                        Address
                      </th>
                      <th scope="col" style={{ textAlign: "center" }}>
                        Start date
                      </th>
                      <th scope="col" style={{ textAlign: "center" }}>
                        Days
                      </th>
                      <th scope="col" style={{ textAlign: "center" }}>
                        Number of beds
                      </th>
                      <th
                        scope="col"
                        style={{ minWidth: "130px", textAlign: "center" }}
                      >
                        Transport name
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
