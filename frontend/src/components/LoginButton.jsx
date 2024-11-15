import { Avatar, Button, Popover } from "@mui/material";
import { useNavigate } from "react-router";

import { useUser } from "../../hooks/useUser";
import { useState } from "react";
import { useAuth } from "../../hooks/useAuth";

function LoginButton() {
  const navigate = useNavigate();
  const [anchorEl, setAnchorEl] = useState(null);
  const { user } = useUser();
  const { logout } = useAuth();

  function handleLogin() {
    navigate("/login");
  }

  const handleAvaterClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    logout();
    handleClose();
  };

  const open = Boolean(anchorEl);

  return (
    <>
      {!user ? (
        <Button
          variant="outlined"
          onClick={handleLogin}
          sx={{ color: "white" }}
        >
          Login
        </Button>
      ) : (
        <>
          <Avatar onClick={handleAvaterClick}>
            {user.firstname[0] + user.lastname[0]}
          </Avatar>
          <Popover
            open={open}
            anchorEl={anchorEl}
            onClose={handleClose}
            anchorOrigin={{
              vertical: "bottom",
              horizontal: "left",
            }}
          >
            <Button variant="outlined" onClick={handleLogout}>
              Logout
            </Button>
          </Popover>
        </>
      )}
    </>
  );
}

export default LoginButton;
