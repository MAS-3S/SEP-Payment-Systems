import React from "react";
import "../../assets/css/transactionCardStyle.css";
import * as Yup from "yup";
import { Formik } from "formik";
import { withStyles } from "@material-ui/styles";
import { TextField } from "@material-ui/core";

const MyTextField = withStyles({
  root: {
    "& label.Mui-focused": {
      color: "#424e66",
    },
    "& .MuiInput-underline:after": {
      borderBottomColor: "#424e66",
    },
    "& .MuiOutlinedInput-root": {
      "& fieldset": {
        borderColor: "#424e66",
      },
      "&:hover fieldset": {
        borderColor: "#424e66",
      },
      "&.Mui-focused fieldset": {
        borderColor: "#424e66",
      },
    },
  },
})(TextField);

const CreditCardSchema = Yup.object().shape({
  cardHolder: Yup.string().required("Required"),
  expirationDateMM: Yup.string()
    .required("Required")
    .matches(/^(0?[1-9]|1[012])$/, "Invalid"),
  expirationDateYY: Yup.string()
    .required("Required")
    .matches(/^[2-9]{1}[0-9]{1}$/, "Invalid"),
  cardNumber: Yup.string()
    .required("Required")
    .min(16, "Minimum 16 numbers")
    .max(19, "Maximum 19 numbers with - or spaces")
    .matches(
      /^([0-9]{4}[- ]?){3}[0-9]{4}$/,
      "Card number contains 16 numbers or 19 numbers with - or spaces"
    ),
  cvc: Yup.string()
    .required("Required")
    .matches(/^[0-9]{3}$/, "Invalid"),
});

export default function TransactionCard(props) {
  const handleProceed = (
    cardHolder,
    expirationDateMM,
    expirationDateYY,
    cardNumber,
    cvc
  ) => {
    console.log(
      cardHolder,
      expirationDateMM,
      expirationDateYY,
      cardNumber.replace(/ /g, "").replace(/-/g, ""),
      cvc
    );
  };

  return (
    <div className="page payment-page">
      <section className="payment-form dark">
        <Formik
          initialValues={{
            cardHolder: "",
            expirationDateMM: "",
            expirationDateYY: "",
            cardNumber: "",
            cvc: "",
          }}
          validationSchema={CreditCardSchema}
          onSubmit={() => {}}
        >
          {({
            values,
            errors,
            touched,
            isValid,
            dirty,
            handleBlur,
            handleChange,
            handleSubmit,
          }) => (
            <form onSubmit={handleSubmit}>
              <div className="card-details">
                <h3 className="title">Credit Card Details</h3>
                <div className="row">
                  <div className="form-group col-sm-7">
                    <label for="card-holder">Card Holder</label>
                    <MyTextField
                      error={Boolean(touched.cardHolder && errors.cardHolder)}
                      fullWidth
                      size="small"
                      helperText={touched.cardHolder && errors.cardHolder}
                      name="cardHolder"
                      onBlur={handleBlur}
                      onChange={handleChange}
                      value={values.cardHolder}
                      variant="outlined"
                      placeholder="Card Holder"
                    />
                  </div>
                  <div className="form-group col-sm-5">
                    <label for="">Expiration Date</label>
                    <div className="input-group expiration-date">
                      <MyTextField
                        className="form-control"
                        error={Boolean(
                          touched.expirationDateMM && errors.expirationDateMM
                        )}
                        fullWidth
                        size="small"
                        helperText={
                          touched.expirationDateMM && errors.expirationDateMM
                        }
                        name="expirationDateMM"
                        onBlur={handleBlur}
                        onChange={handleChange}
                        value={values.expirationDateMM}
                        variant="outlined"
                        placeholder="MM"
                      />
                      <span className="date-separator">/</span>
                      <MyTextField
                        className="form-control"
                        error={Boolean(
                          touched.expirationDateYY && errors.expirationDateYY
                        )}
                        fullWidth
                        size="small"
                        helperText={
                          touched.expirationDateYY && errors.expirationDateYY
                        }
                        name="expirationDateYY"
                        onBlur={handleBlur}
                        onChange={handleChange}
                        value={values.expirationDateYY}
                        variant="outlined"
                        placeholder="YY"
                      />
                    </div>
                  </div>
                  <div className="form-group col-sm-8">
                    <label for="card-number">Card Number</label>
                    <MyTextField
                      error={Boolean(touched.cardNumber && errors.cardNumber)}
                      fullWidth
                      size="small"
                      helperText={touched.cardNumber && errors.cardNumber}
                      name="cardNumber"
                      onBlur={handleBlur}
                      onChange={handleChange}
                      value={values.cardNumber}
                      variant="outlined"
                      placeholder="Card Number"
                    />
                  </div>
                  <div className="form-group col-sm-4">
                    <label for="cvc">CVC</label>
                    <MyTextField
                      error={Boolean(touched.cvc && errors.cvc)}
                      fullWidth
                      size="small"
                      helperText={touched.cvc && errors.cvc}
                      name="cvc"
                      onBlur={handleBlur}
                      onChange={handleChange}
                      value={values.cvc}
                      variant="outlined"
                      placeholder="CVC"
                    />
                  </div>
                  <div className="form-group col-sm-12">
                    <button
                      className={
                        dirty && isValid
                          ? "btn btn-primary btn-block"
                          : "btn btn-secondary btn-block"
                      }
                      type="submit"
                      disabled={!(dirty && isValid)}
                      onClick={() =>
                        handleProceed(
                          values.cardHolder,
                          values.expirationDateMM,
                          values.expirationDateYY,
                          values.cardNumber,
                          values.cvc
                        )
                      }
                    >
                      Proceed
                    </button>
                  </div>
                </div>
              </div>
            </form>
          )}
        </Formik>
      </section>
    </div>
  );
}
