import {
  ToggleButton,
  ToggleButtonGroup,
  Box,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";

import formDataJSON from "../../../../assets/DeliveryFormInputFields.json";
import TextInput from "../../../TextInput";
import FormDateTimePicker from "../../../FormDateTimePicker";

const DeliveryTypeForm = ({
  deliveryType,
  handleTypeChange,
  station,
  setStation,
  control,
  stationList,
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
                  required:
                    deliveryType === "pickup"
                      ? "This field is required"
                      : false,
                }}
              />
              <TextInput
                control={control}
                name={formDataJSON.deliveryType.pickup.textFields[1].id}
                label={formDataJSON.deliveryType.pickup.textFields[1].label}
                rules={{
                  required:
                    deliveryType === "pickup"
                      ? "This field is required"
                      : false,
                }}
              />
            </div>

            <div className="flex flex-row gap-3 w-[100%] ">
              <TextInput
                control={control}
                name={formDataJSON.deliveryType.pickup.textFields[2].id}
                label={formDataJSON.deliveryType.pickup.textFields[2].label}
                rules={{
                  required:
                    deliveryType === "pickup"
                      ? "This field is required"
                      : false,
                }}
              />
              <TextInput
                control={control}
                name={formDataJSON.deliveryType.pickup.textFields[3].id}
                label={formDataJSON.deliveryType.pickup.textFields[3].label}
                rules={{
                  required:
                    deliveryType === "pickup"
                      ? "This field is required"
                      : false,
                }}
              />
            </div>
            <TextInput
              control={control}
              name={formDataJSON.deliveryType.pickup.textFields[4].id}
              label={formDataJSON.deliveryType.pickup.textFields[4].label}
              rules={{
                required:
                  deliveryType === "pickup" ? "This field is required" : false,
              }}
            />
            <FormDateTimePicker
              control={control}
              name={formDataJSON.deliveryType.pickup.dateSelector.id}
              label={formDataJSON.deliveryType.pickup.dateSelector.label}
              rules={{
                required:
                  deliveryType === "pickup" ? "This field is required" : false,
              }}
            />
          </div>
        ) : (
          <FormControl>
            <InputLabel>Station</InputLabel>
            <Select
              value={station}
              label="Station"
              onChange={handleStationChange}
            >
              {stationList.map((station) => (
                <MenuItem key={station.id} value={station.id}>
                  {station.name + " -- " + station.streetAddress}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        )}
      </form>
    </Box>
  );
};

export default DeliveryTypeForm;
