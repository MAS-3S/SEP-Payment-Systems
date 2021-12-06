import "./App.css";
import "./assets/css/commonStyle.css";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from "react-router-dom";
import Layout from "./components/routing/Layout";
import { PrivateRoute } from "./components/routing/PrivateRoute";
import { PublicRoute } from "./components/routing/PublicRoute";
import NotFound from "./pages/NotFound";
import LoginContainer from "./containers/LoginContainer";
import RegistrationContainer from "./containers/RegistrationContainer";
import LayoutPageNotFound from "./components/routing/LayoutPageNotFound";
import WebshopContainer from "./containers/WebshopContainer";
import ShoppingCartContainer from "./containers/ShoppingCartContainer";
import { useEffect, useState } from "react";
import WebShopService from "./services/WebShopService";
import SuccessPaymentResultContainer from "./containers/SuccessPaymentResultContainer";
import ErrorPaymentResultContainer from "./containers/ErrorPaymentResultContainer";
import FailedPaymentResultContainer from "./containers/FailedPaymentResultContainer";

const publicRoutes = [
  {
    key: "webshop/:webshop",
    path: "/webshop/:webshop",
    component: WebshopContainer,
    exact: true,
  },
  {
    key: "login",
    path: "/login",
    component: LoginContainer,
    exact: false,
  },
  {
    key: "registration",
    path: "/registration",
    component: RegistrationContainer,
    exact: false,
  },
];

const privateRoutes = [
  {
    key: "shopping-cart/:webshop",
    path: "/shopping-cart/:webshop",
    component: ShoppingCartContainer,
    exact: false,
  },
  {
    key: "success-transaction/:orderId",
    path: "/success-transaction/:orderId",
    component: SuccessPaymentResultContainer,
    exact: false,
  },
  {
    key: "error-transaction/:orderId",
    path: "/error-transaction/:orderId",
    component: ErrorPaymentResultContainer,
    exact: false,
  },
  {
    key: "failed-transaction/:orderId",
    path: "/failed-transaction/:orderId",
    component: FailedPaymentResultContainer,
    exact: false,
  },
];

function App() {
  const [activeWebshop, setActiveWebshop] = useState({ name: "" });

  useEffect(() => {
    async function fetchData() {
      var webshops = await WebShopService.findAll();
      setActiveWebshop(webshops[0]);
    }
    fetchData();
  }, []);

  return (
    <Router>
      <Switch>
        <Redirect
          exact
          from="/webshop/"
          to={{
            pathname: `/webshop/${activeWebshop.name.toLowerCase()}`,
          }}
        />
        <Redirect
          exact
          from="/"
          to={{
            pathname: `/webshop/${activeWebshop.name.toLowerCase()}`,
          }}
        />
        <Route exact path={["/webshop/:webshop", "/login", "/registration"]}>
          <Layout>
            <Switch>
              {publicRoutes.map((publicRouteProps) => (
                <PublicRoute {...publicRouteProps} />
              ))}
            </Switch>
          </Layout>
        </Route>

        <Route
          exact
          path={[
            "/shopping-cart/:webshop",
            "/success-transaction/:orderId",
            "/error-transaction/:orderId",
            "/failed-transaction/:orderId",
          ]}
        >
          <Layout>
            <Switch>
              {privateRoutes.map((privateRouteProps) => (
                <PrivateRoute {...privateRouteProps} />
              ))}
            </Switch>
          </Layout>
        </Route>

        <Route path="*">
          <LayoutPageNotFound>
            <Switch>
              <Route component={NotFound} />
            </Switch>
          </LayoutPageNotFound>
        </Route>
      </Switch>
    </Router>
  );
}

export default App;
