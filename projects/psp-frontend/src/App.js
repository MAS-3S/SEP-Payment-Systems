import "./App.css";
import "./assets/css/commonStyle.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Layout from "./components/routing/Layout";
import { PrivateRoute } from "./components/routing/PrivateRoute";
import PaymentMethodsContainer from "./containers/PaymentMethodsContainer";
import SubscribePaymentMethodsContainer from "./containers/SubscribePaymentMethodsContainer";
import NotFound from "./pages/NotFound";
import LayoutPageNotFound from "./components/routing/LayoutPageNotFound";
import PayPalPaymentMethod from "./pages/PayPalPaymentMethod";

const privateRoutes = [
  {
    key: "payment-methods/payment/webshop-id/:webshopId/:paymentId",
    path: "/payment-methods/payment/webshop-id/:webshopId/:paymentId",
    component: PaymentMethodsContainer,
    exact: false,
  },
  {
    key: "payment-methods/webshop-id/:webshopId",
    path: "/payment-methods/webshop-id/:webshopId",
    component: SubscribePaymentMethodsContainer,
    exact: false,
  },
  {
    key: "payment-method/pay-pal",
    path: "/payment-method/pay-pal",
    component: PayPalPaymentMethod,
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
            "/payment-methods/payment/webshop-id/:webshopId/:paymentId",
            "/payment-methods/webshop-id/:webshopId",
            "/payment-method/pay-pal",
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
