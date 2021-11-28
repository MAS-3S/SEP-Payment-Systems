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

const publicRoutes = [
  {
    key: "webshop-unauthenticated-users/:name",
    path: "/webshop-unauthenticated-users/:name",
    component: LoginContainer,
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
    key: "webshop-authenticated-users/:name",
    path: "/webshop-authenticated-users/:name",
    component: RegistrationContainer,
    exact: false,
  },
];

function App() {
  return (
    <Router>
      <Switch>
        <Redirect exact from="/" to="/webshop-unauthenticated-users/name" />
        <Route
          exact
          path={[
            "/webshop-unauthenticated-users/:name",
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

        <Route exact path={["/webshop-authenticated-users/:name"]}>
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
