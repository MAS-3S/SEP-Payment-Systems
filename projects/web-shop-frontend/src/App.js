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
import HomePageContainer from "./containers/HomePageContainer";

const publicRoutes = [
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
    key: "home-page",
    path: "/home-page",
    component: HomePageContainer,
    exact: false,
  },
];

function App() {
  return (
    <Router>
      <Switch>
        <Redirect exact from="/" to="/login" />
        <Route exact path={["/home-page"]}>
          <LayoutAuthenticated>
            <Switch>
              {privateRoutes.map((privateRouteProps) => (
                <PrivateRoute {...privateRouteProps} />
              ))}
            </Switch>
          </LayoutAuthenticated>
        </Route>

        <Route exact path={["/login", "/registration"]}>
          <LayoutAnonymous>
            <Switch>
              {publicRoutes.map((publicRouteProps) => (
                <PublicRoute {...publicRouteProps} />
              ))}
            </Switch>
          </LayoutAnonymous>
        </Route>

        <Route path="*">
          <LayoutAnonymous>
            <Switch>
              <Route component={NotFound} />
            </Switch>
          </LayoutAnonymous>
        </Route>
      </Switch>
    </Router>
  );
}

export default App;
