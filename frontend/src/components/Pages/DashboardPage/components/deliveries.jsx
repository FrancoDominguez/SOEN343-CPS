import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Box,
  Button,
  Dialog,
  DialogContent,
  DialogTitle,
  Typography,
} from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { useState } from "react";
import FormDateTimePicker from "../../../FormDateTimePicker";
import { useForm } from "react-hook-form";
import axios from "axios";
import dayjs from "dayjs";

const Deliveries = ({ deliveries }) => {
  const [changingTime, setChangingTime] = useState(false);
  const [deliveryId, setDeliveryId] = useState();
  const { control, handleSubmit } = useForm();

  const handlePickupTimeChange = (deliveryId) => {
    setChangingTime(true);
    setDeliveryId(deliveryId);
  };

  const handleConfirm = async (values) => {
    try {
      const formattedDate = dayjs(values.newTime).format("YYYY-MM-DD HH:mm:ss");
      console.log(formattedDate);

      await axios.put(
        `http://localhost:8080/delivery?deliveryId=${deliveryId}&newTime=${formattedDate}`
      );

      setChangingTime(false);
    } catch (error) {
      toast.error(error);
    }
  };

  return (
    <>
      <Dialog open={changingTime} onClose={() => setChangingTime(false)}>
        <DialogTitle>Change Pickup Time</DialogTitle>
        <DialogContent
          sx={{
            display: "flex",
            flexDirection: "column",
            gap: "12px",
            overflow: "visible",
          }}
        >
          <FormDateTimePicker
            control={control}
            name="newTime"
            label="New Pickup Time"
          />
          <Button onClick={handleSubmit(handleConfirm)}>Confirm</Button>
        </DialogContent>
      </Dialog>
      {deliveries.length === 0 ? (
        <Typography>No deliveries available</Typography>
      ) : (
        <Box>
          {deliveries.map((delivery) => (
            <Accordion key={delivery.id}>
              <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                <Box marginTop={2}>
                  <Typography>
                    <strong>Delivery To </strong>{" "}
                    {delivery.destination.streetAddress},{" "}
                    {delivery.destination.city}
                  </Typography>
                  <Typography>
                    {delivery.destination.country},{" "}
                    {delivery.destination.postalCode}
                  </Typography>
                </Box>
              </AccordionSummary>
              <AccordionDetails>
                {/* Parcel Information */}
                <Box>
                  <Typography>
                    <strong>Parcel Details:</strong>
                  </Typography>
                  <Typography>
                    Dimensions: {delivery.parcel.length} x{" "}
                    {delivery.parcel.width} x {delivery.parcel.height} in
                  </Typography>
                  <Typography>
                    Weight: {delivery.parcel.weight} pounds
                  </Typography>
                  <Typography>
                    Overweight: {delivery.parcel.overweight ? "Yes" : "No"}
                  </Typography>
                  <Typography>
                    Oversized: {delivery.parcel.oversized ? "Yes" : "No"}
                  </Typography>
                  <Typography>
                    Fragile: {delivery.parcel.fragile ? "Yes" : "No"}
                  </Typography>
                </Box>

                {/* Origin or Station */}
                <Box marginTop={2}>
                  {delivery.pickupLocation && (
                    <Box>
                      <Typography variant="subtitle1">
                        <strong>Home Pickup Details:</strong>
                      </Typography>
                      <Typography variant="body1">
                        Origin Address: {delivery.pickupLocation.streetAddress},{" "}
                        {delivery.pickupLocation.city},{" "}
                        {delivery.pickupLocation.country},{" "}
                        {delivery.pickupLocation.postalCode}
                      </Typography>
                      <Typography>
                        Pickup Time:{" "}
                        {new Date(delivery.pickupTime).toLocaleString()}
                      </Typography>
                      <Typography>
                        Flexible: {delivery.flexible ? "Yes" : "No"}
                      </Typography>
                    </Box>
                  )}
                </Box>

                <Box marginTop={2}>
                  <Typography variant="subtitle1">
                    <strong>Shipping Status:</strong>
                  </Typography>
                  <Typography>
                    Tracking ID: {delivery.status.trackingId}
                  </Typography>
                  <Typography>Status: {delivery.status.status}</Typography>
                  <Typography>ETA: {delivery.status.eta}</Typography>
                </Box>

                {delivery.flexible && (
                  <Box marginTop={2}>
                    <Button
                      onClick={() => handlePickupTimeChange(delivery.id)}
                      variant="contained"
                    >
                      Change Pickup Time
                    </Button>
                  </Box>
                )}
              </AccordionDetails>
            </Accordion>
          ))}
        </Box>
      )}
    </>
  );
};

export default Deliveries;
