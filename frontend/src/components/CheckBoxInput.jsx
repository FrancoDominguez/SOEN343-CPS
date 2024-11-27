import { FormControlLabel, Checkbox } from "@mui/material";
import { Controller } from "react-hook-form";

const CheckBoxInput = ({ control, name, label }) => {
  return (
    <Controller
      control={control}
      name={name}
      render={({ field }) => (
        <FormControlLabel control={<Checkbox />} label={label} {...field} />
      )}
    />
  );
};

export default CheckBoxInput;
