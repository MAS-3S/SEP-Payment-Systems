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
import UserPurchaseHistoryContainer from "./containers/UserPurchaseHistoryContainer";
import WageContainer from "./containers/WageContainer";

const publicRoutes = [
  {
    key: "webshop/:webshop/:currency",
    path: "/webshop/:webshop/:currency",
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
    key: "shopping-cart/:webshop/:currency",
    path: "/shopping-cart/:webshop/:currency",
    component: ShoppingCartContainer,
    exact: false,
  },
  {
    key: "user-purchase-history/:userId/:webshopId",
    path: "/user-purchase-history/:userId/:webshopId",
    component: UserPurchaseHistoryContainer,
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
  {
    key: "wage/:webshopId",
    path: "/wage/:webshopId",
    component: WageContainer,
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
          from="/webshop//EUR"
          to={{
            pathname: `/webshop/${activeWebshop.name.toLowerCase()}/EUR`,
          }}
        />
        <Redirect
          exact
          from="/webshop/"
          to={{
            pathname: `/webshop/${activeWebshop.name.toLowerCase()}/EUR`,
          }}
        />
        <Redirect
          exact
          from="/"
          to={{
            pathname: `/webshop/${activeWebshop.name.toLowerCase()}/EUR`,
          }}
        />
        <Route
          exact
          path={["/webshop/:webshop/:currency", "/login", "/registration"]}
        >
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
            "/shopping-cart/:webshop/:currency",
            "/user-purchase-history/:userId/:webshopId",
            "/success-transaction/:orderId",
            "/error-transaction/:orderId",
            "/failed-transaction/:orderId",
            "/wage/:webshopId",
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
