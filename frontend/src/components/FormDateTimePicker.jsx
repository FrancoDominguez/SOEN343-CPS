import { DateTimePicker } from "@mui/x-date-pickers/DateTimePicker";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider/LocalizationProvider";
import { TextField } from "@mui/material";
import { Controller } from "react-hook-form";

const FormDateTimePicker = ({ control, name, label }) => {
  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <Controller
        control={control}
        name={name}
        rules={{
          required: { value: true, message: "Please enter a date and time" },
        }}
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
