import "./App.css";
import "./assets/css/commonStyle.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Layout from "./components/routing/Layout";
import { PrivateRoute } from "./components/routing/PrivateRoute";
import PaymentMethodsContainer from "./containers/PaymentMethodsContainer";
import SubscribePaymentMethodsContainer from "./containers/SubscribePaymentMethodsContainer";
import NotFound from "./pages/NotFound";
import LayoutPageNotFound from "./components/routing/LayoutPageNotFound";
import PayPalPaymentMethodContainter from "./containers/PayPalPaymentMethodContainter";
import PayPalMonthSubscriptionContainer from "./containers/PayPalMonthSubscriptionContainer";
import PayPalYearSubscriptionContainer from "./containers/PayPalYearSubscriptionContainer";

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
    key: "payment-method/pay-pal/subscription/month/:paypalTransactionId",
    path: "/payment-method/pay-pal/subscription/month/:paypalTransactionId",
    component: PayPalMonthSubscriptionContainer,
    exact: false,
  },
  {
    key: "payment-method/pay-pal/subscription/year/:paypalTransactionId",
    path: "/payment-method/pay-pal/subscription/year/:paypalTransactionId",
    component: PayPalYearSubscriptionContainer,
    exact: false,
  },
  {
    key: "payment-method/pay-pal/:paypalTransactionId",
    path: "/payment-method/pay-pal/:paypalTransactionId",
    component: PayPalPaymentMethodContainter,
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
            "/payment-method/pay-pal/:paypalTransactionId",
            "/payment-method/pay-pal/subscription/month/:paypalTransactionId",
            "/payment-method/pay-pal/subscription/year/:paypalTransactionId",
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
