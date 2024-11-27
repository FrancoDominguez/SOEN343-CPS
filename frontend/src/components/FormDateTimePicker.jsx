import { DateTimePicker } from "@mui/x-date-pickers/DateTimePicker";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider/LocalizationProvider";
import { TextField } from "@mui/material";
import { Controller } from "react-hook-form";

const FormDateTimePicker = ({ control, name, label, rules }) => {
  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <Controller
        control={control}
        name={name}
        rules={rules}
        render={({ field, fieldState }) => (
          <DateTimePicker
            label={label}
            value={field.value}
            onChange={(date) => field.onChange(date)}
            renderInput={(params) => (
              <TextField
                {...params}
                error={!!fieldState.error}
                helperText={fieldState?.error ? fieldState.error.message : ""}
              />
            )}
          />
        )}
      />
    </LocalizationProvider>
  );
};

export default FormDateTimePicker;
