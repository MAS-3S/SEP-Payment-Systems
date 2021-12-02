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

export default function Navbar(props) {
  const classes = useStyles();
  const [anchorEl, setAnchorEl] = useState(null);
  const [webshops, setWebshops] = useState([]);
  const [activeWebshop, setActiveWebshop] = useState({ name: "" });
  const [numberOfProductsInShoppingCart, setNumberOfProductsInShoppingCart] =
    useState(0);

  const isMenuOpen = Boolean(anchorEl);

  useEffect(() => {
    async function fetchData() {
      var webshops = await WebShopService.findAll();
      setWebshops(webshops);
      setActiveWebshop(webshops[0]);
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

  const changeWebshop = (webshop) => {
    setAnchorEl(null);
    // var shoppingCartItems = JSON.parse(localStorage.getItem("shoppingCart"));
    // if (shoppingCartItems.length !== 0) {
    //   console.log("NE");
    // } else {
    //   setActiveWebshop(webshop);
    // }
    setActiveWebshop(webshop);
  };

  const navigateToHome = () => {};

  const handleLogOut = () => {
    AuthService.logout();
  };

  const webShopItems =
    webshops.length > 1 ? (
      webshops.map((webshop) => {
        if (webshop.name !== activeWebshop.name) {
          return (
            <Link
              to={{
                pathname: `/webshop/${webshop.name.toLowerCase()}`,
              }}
              style={{ textDecoration: "none", color: "black" }}
            >
              <MenuItem
                style={{
                  width: 260,
                  height: 40,
                  justifyContent: "flex-start",
                }}
                onClick={() => changeWebshop(webshop)}
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

  const navbarLinks = (() => {
    if (!AuthService.getCurrentUser()) {
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
    } else {
      return (
        <div className={classes.sectionDesktop}>
          <Link
            variant="contained"
            color="primary"
            to={{
              pathname: `/shopping-cart/${activeWebshop.name.toLowerCase()}`,
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
              pathname: `/webshop/${activeWebshop.name.toLowerCase()}`,
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
    </div>
  );
}
