import { TextField } from "@mui/material";
import { Controller } from "react-hook-form";

const TextInput = ({ control, label, name, required, rules }) => {
  return (
    <Controller
      control={control}
      name={name}
      rules={rules}
      render={({ field, fieldState }) => (
        <TextField
          label={label}
          error={!!fieldState.error}
          helperText={fieldState?.error ? fieldState.error.message : ""}
          {...field}
        />
      )}
    />
  );
};

export default TextInput;
