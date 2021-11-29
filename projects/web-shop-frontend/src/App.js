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

const webshops = [
  {
    id: 1,
    name: "Gigatron",
  },
  { id: 2, name: "Tehnomanija" },
];

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
    key: "webshop/:webshop",
    path: "/webshop/:webshop",
    component: WebshopContainer,
    exact: false,
  },
  {
    key: "shopping-cart/:webshop",
    path: "/shopping-cart/:webshop",
    component: ShoppingCartContainer,
    exact: false,
  },
];

function App() {
  return (
    <Router>
      <Switch>
        <Redirect
          exact
          from="/"
          to={{
            pathname: `/webshop/${webshops[0].name.toLowerCase()}`,
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

        <Route exact path={["/webshop/:webshop", "/shopping-cart/:webshop"]}>
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
