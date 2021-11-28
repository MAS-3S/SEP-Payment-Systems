import React from "react";
import { Link } from "react-router-dom";
import "../assets/css/registrationStyle.css";
import * as Yup from "yup";
import { Formik } from "formik";
import { TextField } from "@material-ui/core";
import { withStyles } from "@material-ui/styles";

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

const SignUpSchema = Yup.object({
  username: Yup.string().required("Username is required"),
  email: Yup.string()
    .email("Invalid email")
    .max(255)
    .required("Email is required"),
  fullName: Yup.string().required("Full name is required"),
  phone: Yup.string()
    .required("Phone is required")
    .min(6, "Phone is too short - should be 5 chars minimum.")
    .matches(
      /^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s./0-9]*$/,
      "Phone can contain only +, / and numbers."
    ),
  address: Yup.string().required("Address is required"),
  password: Yup.string()
    .required("Password is required")
    .min(8, "Password is too short - should be 8 chars minimum.")
    .matches(
      /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/,
      "Password must contain 8 characters, one uppercase, one lowercase, one number and one special case character"
    ),
  passwordConfirmation: Yup.string().test(
    "passwords-match",
    "Passwords must match",
    function (value) {
      return this.parent.password === value;
    }
  ),
});

export default function Registration() {
  const handleRegistration = (
    username,
    email,
    fullName,
    phone,
    address,
    password
  ) => {
    console.log(username, email, fullName, phone, address, password);
  };

  return (
    <div className="registrationContainer">
      <div className="form-registrationContainer sign-up-registrationContainer">
        <Formik
          initialValues={{
            username: "",
            email: "",
            fullName: "",
            phone: "",
            address: "",
            password: "",
            passwordConfirmation: "",
          }}
          validationSchema={SignUpSchema}
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
              <h1>Create Account</h1>
              <MyTextField
                error={Boolean(touched.username && errors.username)}
                fullWidth
                size="small"
                helperText={touched.username && errors.username}
                label="Username"
                margin="normal"
                name="username"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.username}
                variant="outlined"
              />
              <MyTextField
                error={Boolean(touched.email && errors.email)}
                fullWidth
                size="small"
                helperText={touched.email && errors.email}
                label="Email"
                margin="normal"
                name="email"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.email}
                variant="outlined"
              />
              <MyTextField
                error={Boolean(touched.fullName && errors.fullName)}
                fullWidth
                size="small"
                helperText={touched.fullName && errors.fullName}
                label="Full name"
                margin="normal"
                name="fullName"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.fullName}
                variant="outlined"
              />

              <MyTextField
                error={Boolean(touched.phone && errors.phone)}
                fullWidth
                size="small"
                helperText={touched.phone && errors.phone}
                label="Phone"
                margin="normal"
                name="phone"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.phone}
                variant="outlined"
              />
              <MyTextField
                error={Boolean(touched.address && errors.address)}
                fullWidth
                size="small"
                helperText={touched.address && errors.address}
                label="Address"
                margin="normal"
                name="address"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.address}
                variant="outlined"
              />
              <MyTextField
                error={Boolean(touched.password && errors.password)}
                fullWidth
                size="small"
                helperText={touched.password && errors.password}
                label="Password"
                margin="normal"
                name="password"
                type="password"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.password}
                variant="outlined"
              />
              <MyTextField
                error={Boolean(
                  touched.passwordConfirmation && errors.passwordConfirmation
                )}
                fullWidth
                size="small"
                helperText={
                  touched.passwordConfirmation && errors.passwordConfirmation
                }
                label="Confirm password"
                margin="normal"
                name="passwordConfirmation"
                type="password"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.passwordConfirmation}
                variant="outlined"
              />
              <br />
              <button
                className={
                  dirty && isValid ? "signUpButton" : "signUpButtonDisabled"
                }
                type="submit"
                disabled={!(dirty && isValid)}
                onClick={() =>
                  handleRegistration(
                    values.username,
                    values.password,
                    values.fullName,
                    values.email,
                    values.phone,
                    values.address
                  )
                }
              >
                Sign Up
              </button>
            </form>
          )}
        </Formik>
      </div>
      <div className="registrationOverlay-registrationContainer">
        <div className="registrationOverlay">
          <div className="registrationOverlay-panel registrationOverlay-left">
            <h1>Welcome Back!</h1>
            <p>
              To keep connected with us please login with your personal info
            </p>
            <Link to="/login" className="signIn">
              Sign In
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}
