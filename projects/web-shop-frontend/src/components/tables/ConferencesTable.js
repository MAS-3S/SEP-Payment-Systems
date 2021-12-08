import React, { useState, useEffect } from "react";

export default function ConferencesTable(props) {
  const [tableRows, setTableRows] = useState([]);

  useEffect(() => {
    if (props.conferences.length > 0) {
      const rows = props.conferences.map((conference, key) => {
        return (
          <tr id={key}>
            <td style={{ textAlign: "center" }}>
              <img src={conference.conferenceDto.image} alt="Product" />
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{conference.conferenceDto.name}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{conference.conferenceDto.price}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{conference.conferenceDto.currency}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{conference.conferenceDto.address}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{conference.conferenceDto.startTime}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{conference.conferenceDto.endTime}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{conference.quantity}</label>
            </td>
            <td style={{ verticalAlign: "middle", textAlign: "center" }}>
              <label>{conference.date}</label>
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
            colSpan="9"
          >
            <h5>You don't have any bought conferences...</h5>
          </td>
        </tr>
      );
      setTableRows(rows);
    }
  }, [props.conferences]);

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
                    Conference purchase history
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
                        Currency
                      </th>
                      <th
                        scope="col"
                        style={{ minWidth: "130px", textAlign: "center" }}
                      >
                        Address
                      </th>
                      <th scope="col" style={{ textAlign: "center" }}>
                        Start time
                      </th>
                      <th scope="col" style={{ textAlign: "center" }}>
                        End time
                      </th>

                      <th scope="col" style={{ textAlign: "center" }}>
                        Quantity
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
