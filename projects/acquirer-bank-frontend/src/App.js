import "./App.css";
import "./assets/css/commonStyle.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Layout from "./components/routing/Layout";
import { PrivateRoute } from "./components/routing/PrivateRoute";
import NotFound from "./pages/NotFound";
import LayoutPageNotFound from "./components/routing/LayoutPageNotFound";
import TransactionContainer from "./containers/TransactionContainer";
import QRCodeTransactionContainer from "./containers/QRCodeTransactionContainer";

const privateRoutes = [
  {
    key: "acquirer-bank/transaction/:transactionId",
    path: "/acquirer-bank/transaction/:transactionId",
    component: TransactionContainer,
    exact: false,
  },
  {
    key: "acquirer-bank/qr-code/:transactionId",
    path: "/acquirer-bank/qr-code/:transactionId",
    component: QRCodeTransactionContainer,
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
            "/acquirer-bank/transaction/:transactionId",
            "/acquirer-bank/qr-code/:transactionId",
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
