import React from "react";
import "../assets/css/loginStyle.css";
import { Link } from "react-router-dom";
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

const SignInSchema = Yup.object().shape({
  email: Yup.string()
    .email("Invalid email")
    .max(255)
    .required("Email is required"),
  password: Yup.string()
    .required("Password is required")
    .min(8, "Password is too short - should be 8 chars minimum.")
    .matches(
      /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/,
      "Password must contain 8 characters, one uppercase, one lowercase, one number and one special case character"
    ),
});

export default function Login() {
  const handleLogin = (username, password) => {
    console.log(username, password);
  };

  return (
    <div className="loginContainer">
      <div className="form-loginContainer sign-in-loginContainer">
        <Formik
          initialValues={{
            email: "",
            password: "",
          }}
          validationSchema={SignInSchema}
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
            <form onSubmit={handleSubmit} className="myForm">
              <h1>Sign in</h1>
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
              <br />
              <button
                className={
                  dirty && isValid ? "signInButton" : "signInButtonDisabled"
                }
                type="submit"
                disabled={!(dirty && isValid)}
                onClick={() => handleLogin(values.email, values.password)}
              >
                Sign In
              </button>
            </form>
          )}
        </Formik>
      </div>
      <div className="loginOverlay-loginContainer">
        <div className="loginOverlay">
          <div className="loginOverlay-panel loginOverlay-right">
            <h1>Hello, Friend!</h1>
            <p>Enter your personal details and start journey with us</p>
            <Link
              style={{ textDecoration: "none", color: "white" }}
              to="/registration"
              className="signUp"
            >
              Sign Up
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}
