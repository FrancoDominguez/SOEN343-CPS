import { Box, Button, Card, Container, Typography } from "@mui/material";
import { useForm } from "react-hook-form";
import axios from "axios";
import { useEffect, useState } from "react";

import TextInput from "../TextInput";

function LoginPage() {
  const [isLogin, setIsLogin] = useState(true);
  const { handleSubmit, control, reset, getValues } = useForm({
    defaultValues: {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      rePassword: "",
    },
  });

  const handleLogin = (values) => {
    console.log(values);
  };

  const handleSignup = (values) => {
    console.log(values);
  };

  const handleSwitch = () => setIsLogin(!isLogin);

  useEffect(() => {
    reset(getValues());
  }, [isLogin]);

  return (
    <Container className="h-[100%] w-[100%] flex items-center justify-center">
      <Card className="flex flex-row gap-[10px] w-[75%] h-[75%] relative">
        {/* Login Form */}
        <Box className="w-[100%] mx-auto p-[15px] flex flex-col justify-center items-center">
          <Typography variant="h4">Login</Typography>
          <form
            className="flex flex-col gap-4 my-3"
            onSubmit={handleSubmit(handleLogin)}
          >
            <TextInput
              control={control}
              label="Email"
              name="email"
              required
              rules={{
                required: {
                  value: true,
                  message: "Please enter an email address.",
                },
                pattern: {
                  value: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
                  message: "Please enter a valid email address.",
                },
              }}
            />
            <TextInput
              control={control}
              label="Password"
              name="password"
              type="password"
              rules={{
                required: { value: true, message: "Please enter a password." },
              }}
            />
            <Button variant="contained" type="submit">
              Login
            </Button>
          </form>
        </Box>

        {/* Gradient Box */}
        <Box
          className={`w-[50%] h-[100%] absolute top-0 right-0 p-[15px] flex flex-col justify-center items-center gap-[12px] transition-all duration-500 ease-in-out transform ${
            isLogin ? "translate-x-0" : "translate-x-[-100%]"
          }`}
          sx={{
            background: "#525252",
            background: "-webkit-linear-gradient(to bottom, #3d72b4, #525252)",
            background: "linear-gradient(to bottom, #3d72b4, #525252)",
            zIndex: 10000,
          }}
        >
          <Typography variant="h4" color="white">
            {isLogin ? "Welcome Back!" : "Welcome!"}
          </Typography>
          <Typography variant="subtitle" color="white" align="center">
            {isLogin ? "Don't have an account?" : "Already have an account?"}
          </Typography>
          <Button
            variant="outlined"
            sx={{
              borderColor: "white",
              color: "white",
              "&:hover": {
                borderColor: "#ccc",
                backgroundColor: "#364d70",
              },
            }}
            onClick={handleSwitch}
          >
            {isLogin ? "Sign Up" : "Log In"}
          </Button>
        </Box>

        {/* Sign Up Form */}
        <Box className="w-[100%] mx-auto p-[15px] flex flex-col justify-center items-center">
          <form
            className="flex flex-col gap-4 my-3"
            onSubmit={handleSubmit(handleSignup)}
          >
            <Typography variant="h4">Sign Up</Typography>
            <Box className="flex flex-row gap-[12px]">
              <TextInput
                control={control}
                label="First Name"
                name="firstName"
                required
                rules={{
                  required: {
                    value: true,
                    message: "Please enter a first name.",
                  },
                }}
              />
              <TextInput
                control={control}
                label="Last Name"
                name="lastName"
                required
                rules={{
                  required: {
                    value: true,
                    message: "Please enter a last name.",
                  },
                }}
              />
            </Box>
            <TextInput
              control={control}
              label="Email"
              name="email"
              required
              rules={{
                required: {
                  value: true,
                  message: "Please enter an email address.",
                },
                pattern: {
                  value: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
                  message: "Please enter a valid email address.",
                },
              }}
            />
            <Box className="flex flex-row gap-[12px]">
              <TextInput
                control={control}
                label="Password"
                name="password"
                type="password"
                rules={{
                  required: {
                    value: true,
                    message: "Please enter a password.",
                  },
                }}
              />
              <TextInput
                control={control}
                label="Repeat Password"
                name="rePassword"
                type="password"
                rules={{
                  validate: {
                    value: (value) =>
                      value === getValues("password") || "Passwords must match",
                  },
                }}
              />
            </Box>
            <Button variant="contained" type="submit">
              Sign Up
            </Button>
          </form>
        </Box>
      </Card>
    </Container>
  );
}

export default LoginPage;
