import React from "react";
import "../../assets/css/wageCardStyle.css";
import * as Yup from "yup";
import { Formik } from "formik";
import { withStyles } from "@material-ui/styles";
import { MenuItem, Select, TextField } from "@material-ui/core";

const MyTextField = withStyles({
  root: {
    "& label.Mui-focused": {
      color: "#BBBBBB",
    },
    "& .MuiInput-underline:after": {
      borderBottomColor: "#BBBBBB",
    },
    "& .MuiOutlinedInput-root": {
      "& fieldset": {
        borderColor: "#BBBBBB",
      },
      "&:hover fieldset": {
        borderColor: "#BBBBBB",
      },
      "&.Mui-focused fieldset": {
        borderColor: "#BBBBBB",
      },
    },
  },
})(TextField);

const WageSchema = Yup.object().shape({
  amount: Yup.string()
    .required("Required")
    .matches(/^[1-9][0-9]*$/, "Invalid"),
  currency: Yup.string().required("Required"),
  accountNumber: Yup.string()
    .required("Required")
    .matches(/^[0-9]{18}$/, "Account number contains 18 numbers"),
  bank: Yup.string().required("Required"),
});

export default function WageCard(props) {
  const onSubmit = async (
    values,
    { setSubmitting, setErrors, setStatus, resetForm }
  ) => {
    try {
      props.executePayment(
        values.amount,
        values.currency,
        values.accountNumber,
        values.bank
      );
      resetForm({});
      setStatus({ success: true });
    } catch (error) {
      setStatus({ success: false });
      setSubmitting(false);
      setErrors({ submit: error.message });
    }
  };

  return (
    <div className="page payment-page">
      <section className="payment-form dark">
        <Formik
          initialValues={{
            amount: "",
            currency: "",
            accountNumber: "",
            bank: "",
          }}
          validationSchema={WageSchema}
          onSubmit={onSubmit}
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
                <h3 className="title">Wage payment</h3>
                <div className="row">
                  <div className="form-group col-sm-7">
                    <label for="amount">Amount</label>
                    <MyTextField
                      error={Boolean(touched.amount && errors.amount)}
                      fullWidth
                      size="small"
                      helperText={touched.amount && errors.amount}
                      name="amount"
                      onBlur={handleBlur}
                      onChange={handleChange}
                      value={values.amount}
                      variant="outlined"
                      placeholder="Amount"
                    />
                  </div>
                  <div className="form-group col-sm-5">
                    <label for="currency">Currency</label>
                    <Select
                      error={Boolean(touched.currency && errors.currency)}
                      fullWidth
                      name="currency"
                      onBlur={handleBlur}
                      onChange={handleChange}
                      value={values.currency}
                      variant="outlined"
                      style={{ height: "40px" }}
                    >
                      <MenuItem value="EUR">EUR</MenuItem>
                      <MenuItem value="USD">USD</MenuItem>
                    </Select>
                  </div>
                  <div className="form-group col-sm-7">
                    <label for="accountNumber">Account number</label>
                    <MyTextField
                      error={Boolean(
                        touched.accountNumber && errors.accountNumber
                      )}
                      fullWidth
                      size="small"
                      helperText={touched.accountNumber && errors.accountNumber}
                      name="accountNumber"
                      onBlur={handleBlur}
                      onChange={handleChange}
                      value={values.accountNumber}
                      variant="outlined"
                      placeholder="Account number"
                    />
                  </div>
                  <div className="form-group col-sm-5">
                    <label for="bank">Bank</label>
                    <Select
                      error={Boolean(touched.bank && errors.bank)}
                      fullWidth
                      size="small"
                      name="bank"
                      onBlur={handleBlur}
                      onChange={handleChange}
                      value={values.bank}
                      variant="outlined"
                      style={{ height: "40px" }}
                    >
                      <MenuItem value="123">Acquirer</MenuItem>
                      <MenuItem value="456">Issuer</MenuItem>
                    </Select>
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
                    >
                      Pay wage
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
