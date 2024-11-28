import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Box,
  Typography,
} from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

const Contracts = ({ contracts }) => {
  return (
    <Box>
      {contracts.length === 0 ? (
        <Typography>No contracts available</Typography>
      ) : (
        <Box>
          {contracts.map((contract) => (
            <Accordion key={contract.id}>
              <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                <Box marginTop={2}>
                  <Typography>
                    <strong>
                      {contract.station ? "Station Drop Off " : "Home Pick Up "}
                      Contract To{" "}
                    </strong>{" "}
                    {contract.destination.streetAddress},{" "}
                    {contract.destination.city}
                  </Typography>
                  <Typography>
                    {contract.destination.country},{" "}
                    {contract.destination.postalCode}
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
                    Dimensions: {contract.parcel.length} x{" "}
                    {contract.parcel.width} x {contract.parcel.height} in
                  </Typography>
                  <Typography>
                    Weight: {contract.parcel.weight} pounds
                  </Typography>
                  <Typography>
                    Overweight: {contract.parcel.overweight ? "Yes" : "No"}
                  </Typography>
                  <Typography>
                    Oversized: {contract.parcel.oversized ? "Yes" : "No"}
                  </Typography>
                  <Typography>
                    Fragile: {contract.parcel.fragile ? "Yes" : "No"}
                  </Typography>
                </Box>

                {/* Origin or Station */}
                <Box marginTop={2}>
                  {contract.station ? (
                    <Box>
                      <Typography variant="subtitle1">
                        <strong>Station Details:</strong>
                      </Typography>
                      <Typography>Station {contract.station.name}</Typography>
                      <Typography>
                        Address: {contract.station.streetAddress},{" "}
                        {contract.station.city}, {contract.station.country}
                      </Typography>
                    </Box>
                  ) : (
                    <Box>
                      <Typography variant="subtitle1">
                        <strong>Home Pickup Details:</strong>
                      </Typography>
                      <Typography variant="body1">
                        Origin Address: {contract.origin.streetAddress},{" "}
                        {contract.origin.city}, {contract.origin.country},{" "}
                        {contract.origin.postalCode}
                      </Typography>
                      <Typography>
                        Pickup Time:{" "}
                        {new Date(contract.pickupTime).toLocaleString()}
                      </Typography>
                      <Typography>
                        Flexible: {contract.flexible ? "Yes" : "No"}
                      </Typography>
                    </Box>
                  )}
                </Box>

                {/* Additional Information */}
                <Box marginTop={2}>
                  <Typography variant="subtitle1">
                    <strong>Additional Details:</strong>
                  </Typography>
                  <Typography>
                    Warranted Amount: ${contract.warrantedAmount}
                  </Typography>
                  <Typography>Price: ${contract.price}</Typography>
                  <Typography>ETA: {contract.eta}</Typography>
                </Box>
              </AccordionDetails>
            </Accordion>
          ))}
        </Box>
      )}
    </Box>
  );
};

export default Contracts;
