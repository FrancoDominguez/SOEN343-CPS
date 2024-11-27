import {
  ToggleButton,
  ToggleButtonGroup,
  Box,
  FormControl,
  RadioGroup,
  FormControlLabel,
  Radio,
} from "@mui/material";

import formDataJSON from "../../../../assets/DeliveryFormInputFields.json";
import TextInput from "../../../TextInput";
import { MockStations } from "../settings";
import FormDateTimePicker from "../../../FormDateTimePicker";

const DeliveryTypeForm = ({
  deliveryType,
  handleTypeChange,
  station,
  setStation,
  control,
}) => {
  const handleStationChange = (event) => {
    setStation(event.target.value);
  };

  return (
    <Box className="flex flex-col gap-3 w-[100%] py-3 items-center">
      <ToggleButtonGroup
        value={deliveryType}
        exclusive
        onChange={handleTypeChange}
      >
        <ToggleButton value="pickup">Schedule a Pickup</ToggleButton>
        <ToggleButton value="dropoff">Station DropOff</ToggleButton>
      </ToggleButtonGroup>
      <form>
        {deliveryType === "pickup" ? (
          <div className="flex flex-col gap-3 w-[100%] py-3">
            <div className="flex flex-row gap-3 w-[100%] ">
              <TextInput
                control={control}
                name={formDataJSON.deliveryType.pickup.textFields[0].id}
                label={formDataJSON.deliveryType.pickup.textFields[0].label}
                rules={{
                  required: {
                    value: true,
                    message: "This field is required",
                  },
                }}
              />
              <TextInput
                control={control}
                name={formDataJSON.deliveryType.pickup.textFields[1].id}
                label={formDataJSON.deliveryType.pickup.textFields[1].label}
                rules={{
                  required: {
                    value: true,
                    message: "This field is required",
                  },
                }}
              />
            </div>

            <div className="flex flex-row gap-3 w-[100%] ">
              <TextInput
                control={control}
                name={formDataJSON.deliveryType.pickup.textFields[2].id}
                label={formDataJSON.deliveryType.pickup.textFields[2].label}
                rules={{
                  required: {
                    value: true,
                    message: "This field is required",
                  },
                }}
              />
              <TextInput
                control={control}
                name={formDataJSON.deliveryType.pickup.textFields[3].id}
                label={formDataJSON.deliveryType.pickup.textFields[3].label}
                rules={{
                  required: {
                    value: true,
                    message: "This field is required",
                  },
                }}
              />
            </div>
            <TextInput
              control={control}
              name={formDataJSON.deliveryType.pickup.textFields[4].id}
              label={formDataJSON.deliveryType.pickup.textFields[4].label}
              rules={{
                required: {
                  value: true,
                  message: "This field is required",
                },
              }}
            />
            <FormDateTimePicker
              control={control}
              name={formDataJSON.deliveryType.pickup.dateSelector.id}
              label={formDataJSON.deliveryType.pickup.dateSelector.label}
            />
          </div>
        ) : (
          <FormControl>
            <RadioGroup value={station} onChange={handleStationChange}>
              {MockStations.map((station) => (
                <FormControlLabel
                  key={station.id}
                  value={station.id}
                  control={<Radio />}
                  label={station.name + " -- " + station.address}
                />
              ))}
            </RadioGroup>
          </FormControl>
        )}
      </form>
    </Box>
  );
};

export default DeliveryTypeForm;
