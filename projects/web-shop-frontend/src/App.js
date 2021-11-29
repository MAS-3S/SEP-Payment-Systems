import "./App.css";
import "./assets/css/commonStyle.css";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from "react-router-dom";
import LayoutAuthenticated from "./components/routing/LayoutAuthenticated";
import LayoutAnonymous from "./components/routing/LayoutAnonymous";
import { PrivateRoute } from "./components/routing/PrivateRoute";
import { PublicRoute } from "./components/routing/PublicRoute";
import NotFound from "./pages/NotFound";
import LoginContainer from "./containers/LoginContainer";
import RegistrationContainer from "./containers/RegistrationContainer";
import LayoutPageNotFound from "./components/routing/LayoutPageNotFound";
import WebshopUnauthenticatedContainer from "./containers/WebshopUnauthenticatedContainer";

const webshops = [
  {
    id: 1,
    name: "Gigatron",
  },
  { id: 2, name: "Tehnomanija" },
];

const publicRoutes = [
  {
    key: "webshop-unauthenticated/:webshop",
    path: "/webshop-unauthenticated/:webshop",
    component: WebshopUnauthenticatedContainer,
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
    key: "webshop-authenticated/:webshop",
    path: "/webshop-authenticated/:webshop",
    component: RegistrationContainer,
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
            pathname: `/webshop-unauthenticated/${webshops[0].name.toLowerCase()}`,
          }}
        />
        <Route
          exact
          path={[
            "/webshop-unauthenticated/:webshop",
            "/login",
            "/registration",
          ]}
        >
          <LayoutAnonymous>
            <Switch>
              {publicRoutes.map((publicRouteProps) => (
                <PublicRoute {...publicRouteProps} />
              ))}
            </Switch>
          </LayoutAnonymous>
        </Route>

        <Route exact path={["/webshop-authenticated/:webshop"]}>
          <LayoutAuthenticated>
            <Switch>
              {privateRoutes.map((privateRouteProps) => (
                <PrivateRoute {...privateRouteProps} />
              ))}
            </Switch>
          </LayoutAuthenticated>
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
