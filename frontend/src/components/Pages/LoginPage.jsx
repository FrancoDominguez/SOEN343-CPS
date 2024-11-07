import { Button, Container, TextField, Typography } from "@mui/material";
import { useState } from "react";

function LoginPage() {
  function handleLogin() {}
  

  return (
    <div className="mt-20 w-4/5 mx-80%">
      <Typography variant="h4">Login</Typography>
      <form className="flex flex-col gap-4 my-3">
        <TextField label="email"></TextField>
        <TextField label="password"></TextField>
        <Button variant="contained" onClick={handleLogin}>Login</Button>
      </form>
    </div>
  );
}

export default LoginPage;
