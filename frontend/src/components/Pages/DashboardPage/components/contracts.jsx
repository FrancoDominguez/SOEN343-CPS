import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Box,
  Button,
  Modal,
  Typography,
} from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { useState } from "react";
import PaymentPage from "../../PaymentPage";
import axios from "axios";
import { toast } from "react-toastify";

const Contracts = ({ contracts, setContracts }) => {
  const [openPayment, setOpenPayment] = useState(false);
  const [payedContractId, setPayedContractId] = useState();

  const handlePay = (contractId) => {
    setOpenPayment(true);
    setPayedContractId(contractId);
  };

  const onPaymentSuccess = async () => {
    try {
      await axios.post("http://localhost:8080/delivery", {
        contractId: payedContractId,
      });
      setContracts((prevContracts) =>
        prevContracts.filter((contract) => contract.id !== payedContractId)
      );
    } catch (error) {
      toast.error(error);
    }
  };

  const handleDelete = async (contractId) => {
    try {
      await axios.delete(
        `http://localhost:8080/contract?contractId=${contractId}`
      );
      toast.success("Successfully deleted contract");

      setContracts((prevContracts) =>
        prevContracts.filter((contract) => contract.id !== contractId)
      );
    } catch (error) {
      toast.error(error);
    }
  };

  return (
    <Box>
      <Modal open={openPayment} onClose={() => setOpenPayment(false)}>
        <Box>
          <PaymentPage onPaymentSuccess={onPaymentSuccess} />
        </Box>
      </Modal>
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

                <Box marginTop={2}>
                  <Button
                    onClick={() => handlePay(contract.id)}
                    sx={{
                      width: "100px",
                      marginRight: "12px",
                    }}
                    variant="contained"
                  >
                    Pay
                  </Button>
                  <Button
                    onClick={() => handleDelete(contract.id)}
                    sx={{
                      width: "100px",
                    }}
                    variant="outlined"
                  >
                    Delete
                  </Button>
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
