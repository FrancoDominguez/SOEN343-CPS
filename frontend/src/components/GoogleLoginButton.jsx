import { Button } from "@mui/material";
import { useNavigate } from "react-router";
function GoogleLoginButton() {
  const navigate = useNavigate();
  function handleClick() {
    navigate("/login");
  }

  return (
    <Button variant="outlined" onClick={handleClick} sx={{ color: "white" }}>
      Login
    </Button>
  );
}

export default GoogleLoginButton;