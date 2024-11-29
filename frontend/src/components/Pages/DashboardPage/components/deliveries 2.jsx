import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Box,
  Typography,
} from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

const Deliveries = ({ deliveries }) => {
  return (
    <>
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
              </AccordionDetails>
            </Accordion>
          ))}
        </Box>
      )}
    </>
  );
};

export default Deliveries;
