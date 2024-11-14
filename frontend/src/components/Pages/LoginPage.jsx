import { Button, Typography } from "@mui/material";
import { useForm } from "react-hook-form";
import axios from "axios";

import TextInput from "../TextInput";

function LoginPage() {
  const { handleSubmit, control } = useForm({
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const handleLogin = (values) => {
    console.log(values);

    axios
      .post("http://localhost:8080", {
        email: values.email,
        password: values.password,
      })
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.error("Error submitting data:", error);
      });
  };

  return (
    <div className="mt-20 w-[80%] mx-auto%">
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
          rules={{
            required: {
              value: true,
              message: "Please enter a password.",
            },
          }}
        />
        <Button variant="contained" type="submit">
          Login
        </Button>
      </form>
    </div>
  );
}

export default LoginPage;
