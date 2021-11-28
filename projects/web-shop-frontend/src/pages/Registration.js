import React from "react";
import { Link } from "react-router-dom";
import "../assets/css/registrationStyle.css";

export default function Registration() {
  return (
    <div className="registrationContainer">
      <div className="form-registrationContainer sign-up-registrationContainer">
        <form action="#">
          <h1>Create Account</h1>
          <input type="text" placeholder="Username" />
          <input type="password" placeholder="Password" />
          <input type="password" placeholder="Repeat password" />
          <input type="text" placeholder="Full name" />
          <input type="email" placeholder="Email" />
          <input type="num" placeholder="Phone" />
          <input type="text" placeholder="Address" />
          <br />
          <button className="signUpButton">Sign Up</button>
        </form>
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
