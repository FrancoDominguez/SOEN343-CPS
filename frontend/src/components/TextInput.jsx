import { TextField } from "@mui/material";
import { Controller } from "react-hook-form";

const TextInput = ({ control, label, name, rules, type }) => {
  return (
    <Controller
      control={control}
      name={name}
      rules={rules}
      render={({ field, fieldState }) => (
        <TextField
          label={label}
          error={!!fieldState.error}
          type={type}
          helperText={fieldState?.error ? fieldState.error.message : ""}
          {...field}
        />
      )}
    />
  );
};

export default TextInput;
