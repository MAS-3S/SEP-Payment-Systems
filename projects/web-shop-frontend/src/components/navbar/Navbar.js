import React, { useEffect, useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import Typography from "@material-ui/core/Typography";
import MenuItem from "@material-ui/core/MenuItem";
import Menu from "@material-ui/core/Menu";
import StorefrontOutlinedIcon from "@material-ui/icons/StorefrontOutlined";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import { Link } from "react-router-dom";
import WebShopService from "../../services/WebShopService";
import AuthService from "../../services/AuthService";
import { Badge } from "@material-ui/core";
import TokenService from "../../services/TokenService";
import SubscribeService from "../../services/SubscribeService";

const useStyles = makeStyles((theme) => ({
  grow: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(1.3),
  },
  title: {
    display: "none",
    [theme.breakpoints.up("sm")]: {
      display: "block",
    },
  },
  sectionDesktop: {
    display: "none",
    [theme.breakpoints.up("md")]: {
      display: "flex",
    },
    marginRight: -20,
  },
  buttonMargin: {
    marginLeft: "12px",
  },
  toolbar: {
    height: "60px",
  },
}));

const availableCurrencies = [{ name: "EUR" }, { name: "USD" }];

export default function Navbar(props) {
  const classes = useStyles();
  const [anchorEl, setAnchorEl] = useState(null);
  const [anchorCurrencyEl, setAnchorCurrencyEl] = useState(null);
  const [webshops, setWebshops] = useState([]);
  const [activeWebshop, setActiveWebshop] = useState({ name: "" });
  const [activeCurrency, setActiveCurrency] = useState({ name: "" });
  const [numberOfProductsInShoppingCart, setNumberOfProductsInShoppingCart] =
    useState(0);

  const isMenuOpen = Boolean(anchorEl);
  const isCurrencyMenuOpen = Boolean(anchorCurrencyEl);

  useEffect(() => {
    async function fetchData() {
      var webshops = await WebShopService.findAll();
      setWebshops(webshops);
      setActiveWebshop(webshops[0]);
      setActiveCurrency(availableCurrencies[0]);
      setNumberOfProductsInShoppingCart(0);
      // setNumberOfProductsInShoppingCart(
      //   JSON.parse(localStorage.getItem("shoppingCart")).length
      // );
    }
    fetchData();
  }, []);

  const handleProfileMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const handleCurrencyMenuOpen = (event) => {
    setAnchorCurrencyEl(event.currentTarget);
  };

  const handleCurrencyMenuClose = () => {
    setAnchorCurrencyEl(null);
  };

  const changeWebshop = (webshop) => {
    setAnchorEl(null);
    setActiveWebshop(webshop);
  };

  const changeCurrency = (currency) => {
    setAnchorCurrencyEl(null);
    setActiveCurrency(currency);
  };

  const navigateToHome = () => {};

  const handleLogOut = () => {
    AuthService.logout();
  };

  const handleSubscribe = () => {
    SubscribeService.findSubscribeUrl(activeWebshop.id).then((response) => {
      window.location.href = response;
    });
  };

  const webShopItems =
    webshops.length > 1 ? (
      webshops.map((webshop) => {
        if (webshop.name !== activeWebshop.name) {
          return (
            <Link
              to={{
                pathname: `/webshop/${webshop.name.toLowerCase()}/${
                  activeCurrency.name
                }`,
              }}
              style={{ textDecoration: "none", color: "black" }}
              onClick={() => changeWebshop(webshop)}
            >
              <MenuItem
                style={{
                  width: 260,
                  height: 40,
                  justifyContent: "flex-start",
                }}
              >
                {webshop.name}
              </MenuItem>
            </Link>
          );
        }
        return null;
      })
    ) : (
      <MenuItem
        style={{
          width: 165,
          height: 40,
          justifyContent: "flex-start",
        }}
        disabled={true}
      >
        There is no more
      </MenuItem>
    );

  const currencyItems = availableCurrencies.map((currency) => {
    return (
      <Link
        to={{
          pathname: `/webshop/${activeWebshop.name.toLowerCase()}/${
            currency.name
          }`,
        }}
        style={{ textDecoration: "none", color: "black" }}
        onClick={() => changeCurrency(currency)}
      >
        <MenuItem
          style={{
            width: 100,
            height: 40,
            justifyContent: "flex-start",
          }}
        >
          {currency.name}
        </MenuItem>
      </Link>
    );
  });

  const renderMenu = (
    <Menu
      style={{ marginTop: 42 }}
      anchorEl={anchorEl}
      anchorOrigin={{ vertical: "top", horizontal: "right" }}
      keepMounted
      transformOrigin={{ vertical: "top", horizontal: "right" }}
      open={isMenuOpen}
      onClose={handleMenuClose}
    >
      {webShopItems}
    </Menu>
  );

  const renderCurrencyMenu = (
    <Menu
      style={{ marginTop: 42 }}
      anchorEl={anchorCurrencyEl}
      anchorOrigin={{ vertical: "top", horizontal: "right" }}
      keepMounted
      transformOrigin={{ vertical: "top", horizontal: "right" }}
      open={isCurrencyMenuOpen}
      onClose={handleCurrencyMenuClose}
    >
      {currencyItems}
    </Menu>
  );

  const navbarLinks = (() => {
    if (!TokenService.getUser()) {
      return (
        <div className={classes.sectionDesktop}>
          <Link
            variant="contained"
            color="primary"
            to="/login"
            className="myButton"
            style={{ textDecoration: "none", color: "white" }}
          >
            Sign in
          </Link>
          <Link
            variant="contained"
            color="primary"
            to="/registration"
            className="myButton"
            style={{ textDecoration: "none", color: "white" }}
          >
            Sign up
          </Link>
        </div>
      );
    } else if (TokenService.getUserRole() === "CUSTOMER") {
      return (
        <div className={classes.sectionDesktop}>
          <IconButton
            onClick={handleCurrencyMenuOpen}
            edge="end"
            className={classes.menuButton}
            color="inherit"
            aria-label="open drawer"
            style={{ width: 100, justifyContent: "felx-end" }}
          >
            <Typography className={classes.title} variant="h6">
              {activeCurrency.name}
            </Typography>
            <ExpandMoreIcon
              style={{ marginLeft: 3 }}
              onClick={handleCurrencyMenuOpen}
            />
          </IconButton>
          <Link
            variant="contained"
            color="primary"
            to={{
              pathname: `/user-purchase-history/${TokenService.getUserId()}/${
                activeWebshop.id
              }`,
              paramName: `${activeWebshop.name}`,
            }}
            className="myButton"
            style={{ textDecoration: "none", color: "white" }}
          >
            Purchase history
          </Link>
          <Link
            variant="contained"
            color="primary"
            to={{
              pathname: `/shopping-cart/${activeWebshop.name.toLowerCase()}/${
                activeCurrency.name
              }`,
            }}
            className="myButton"
            style={{ textDecoration: "none", color: "white" }}
          >
            Shopping cart
            <Badge
              badgeContent={numberOfProductsInShoppingCart}
              color="secondary"
              style={{ float: "right", marginTop: -12 }}
            ></Badge>
          </Link>
          <Link
            variant="contained"
            color="primary"
            to="/login"
            className="myButton"
            onClick={handleLogOut}
            style={{ textDecoration: "none", color: "white" }}
          >
            Log out
          </Link>
        </div>
      );
    } else {
      if (activeWebshop.name.toLowerCase() === "conferences shop") {
        return (
          <div className={classes.sectionDesktop}>
            <Link
              variant="contained"
              color="primary"
              to={{
                pathname: `/wage/${activeWebshop.id}`,
              }}
              className="myButton"
              style={{ textDecoration: "none", color: "white" }}
            >
              Wage
            </Link>
            <button
              variant="contained"
              color="primary"
              className="myButton"
              style={{ textDecoration: "none", color: "white", marginTop: 15 }}
              onClick={handleSubscribe}
            >
              Payments
            </button>
            <Link
              variant="contained"
              color="primary"
              to="/login"
              className="myButton"
              onClick={handleLogOut}
              style={{ textDecoration: "none", color: "white" }}
            >
              Log out
            </Link>
          </div>
        );
      } else {
        return (
          <div className={classes.sectionDesktop}>
            <button
              variant="contained"
              color="primary"
              className="myButton"
              style={{ textDecoration: "none", color: "white", marginTop: 15 }}
              onClick={handleSubscribe}
            >
              Payments
            </button>
            <Link
              variant="contained"
              color="primary"
              to="/login"
              className="myButton"
              onClick={handleLogOut}
              style={{ textDecoration: "none", color: "white" }}
            >
              Log out
            </Link>
          </div>
        );
      }
    }
  })();

  return (
    <div className={classes.grow}>
      <AppBar
        position="static"
        style={{
          position: "fixed",
          background: "#6c7588",
        }}
      >
        <Toolbar className={classes.toolbar}>
          <Link
            to={{
              pathname: `/webshop/${activeWebshop.name.toLowerCase()}/${
                activeCurrency.name
              }`,
            }}
          >
            <IconButton
              onClick={navigateToHome}
              edge="start"
              className={classes.menuButton}
              aria-label="open drawer"
            >
              <StorefrontOutlinedIcon
                onClick={navigateToHome}
                style={{ color: "white" }}
              />
            </IconButton>
          </Link>
          <IconButton
            onClick={handleProfileMenuOpen}
            edge="start"
            className={classes.menuButton}
            color="inherit"
            aria-label="open drawer"
            style={{ width: 260, justifyContent: "flex-start" }}
          >
            <Typography className={classes.title} variant="h6">
              {activeWebshop.name}
            </Typography>
            <ExpandMoreIcon
              style={{ marginLeft: 3 }}
              onClick={handleProfileMenuOpen}
            />
          </IconButton>

          <div className={classes.grow} />
          {navbarLinks}
        </Toolbar>
      </AppBar>
      {renderMenu}
      {renderCurrencyMenu}
    </div>
  );
}
