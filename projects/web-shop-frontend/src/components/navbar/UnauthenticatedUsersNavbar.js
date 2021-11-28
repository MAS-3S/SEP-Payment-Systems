import React, { useState } from "react";
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
  },
  buttonMargin: {
    marginLeft: "12px",
  },
}));

export default function UnauthenticatedUsersNavbar() {
  const classes = useStyles();
  const [anchorEl, setAnchorEl] = useState(null);

  const isMenuOpen = Boolean(anchorEl);

  const handleProfileMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const navigateToHome = () => {};

  const renderMenu = (
    <Menu
      style={{ marginTop: 48 }}
      anchorEl={anchorEl}
      anchorOrigin={{ vertical: "top", horizontal: "right" }}
      keepMounted
      transformOrigin={{ vertical: "top", horizontal: "right" }}
      open={isMenuOpen}
      onClose={handleMenuClose}
    >
      <MenuItem
        style={{
          width: 150,
          justifyContent: "flex-start",
        }}
        onClick={handleMenuClose}
      >
        WinWin
      </MenuItem>
    </Menu>
  );

  return (
    <div className={classes.grow}>
      <AppBar
        position="static"
        style={{
          position: "fixed",
          background: "#6c7588",
        }}
      >
        <Toolbar>
          <IconButton
            onClick={navigateToHome}
            edge="start"
            className={classes.menuButton}
            color="inherit"
            aria-label="open drawer"
          >
            <StorefrontOutlinedIcon onClick={navigateToHome} />
          </IconButton>

          <IconButton
            onClick={handleProfileMenuOpen}
            edge="start"
            className={classes.menuButton}
            color="inherit"
            aria-label="open drawer"
            style={{ width: 150, justifyContent: "flex-start" }}
          >
            <Typography className={classes.title} variant="h6">
              Gigatron
            </Typography>
            <ExpandMoreIcon
              style={{ marginLeft: 3 }}
              onClick={handleProfileMenuOpen}
            />
          </IconButton>

          <div className={classes.grow} />
          <div className={classes.sectionDesktop}>
            <Link
              variant="contained"
              color="primary"
              to="/login"
              className="button"
            >
              Sign in
            </Link>
            <Link
              variant="contained"
              color="primary"
              to="/registration"
              className={"button"}
            >
              Sign up
            </Link>
          </div>
        </Toolbar>
      </AppBar>
      {renderMenu}
    </div>
  );
}
