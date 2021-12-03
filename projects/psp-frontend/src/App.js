import "./App.css";
import "./assets/css/commonStyle.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Layout from "./components/routing/Layout";
import { PrivateRoute } from "./components/routing/PrivateRoute";
import PaymentMethodsContainer from "./containers/PaymentMethodsContainer";
import SubscribePaymentMethodsContainer from "./containers/SubscribePaymentMethodsContainer";
import NotFound from "./pages/NotFound";
import LayoutPageNotFound from "./components/routing/LayoutPageNotFound";

const privateRoutes = [
  {
    key: "payment-methods/payment/webshop-id/:webshopId",
    path: "/payment-methods/payment/webshop-id/:webshopId",
    component: PaymentMethodsContainer,
    exact: false,
  },
  {
    key: "payment-methods/webshop-id/:webshopId",
    path: "/payment-methods/webshop-id/:webshopId",
    component: SubscribePaymentMethodsContainer,
    exact: false,
  },
];

function App() {
  return (
    <Router>
      <Switch>
        <Route
          exact
          path={[
            "/payment-methods/payment/webshop-id/:webshopId",
            "/payment-methods/webshop-id/:webshopId",
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
