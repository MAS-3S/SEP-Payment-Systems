import React from "react";
import { Link } from "react-router-dom";

export default function NotFound() {
  return (
    <div>
      <h1>404 Page Not Found</h1>
      <Link to="/login" className="btn orange">
        Go to login
      </Link>
    </div>
  );
}
