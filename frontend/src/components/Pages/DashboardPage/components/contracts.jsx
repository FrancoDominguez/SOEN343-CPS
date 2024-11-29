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
  const [paymentDetails, setPaymentDetails] = useState(); // added

  // Modified
  const handlePay = (contract) => {
    setPayedContractId(contract.id); // Set the ID for the selected contract
    setPaymentDetails({
      contractId: contract.id,
      amount: contract.price,
    });
    setOpenPayment(true);
  };

  const onPaymentSuccess = async () => {
    try {
      await axios.post("http://localhost:8080/delivery", {
        contractId: payedContractId,
      });
      setContracts((prevContracts) =>
        prevContracts.filter((contract) => contract.id !== payedContractId)
      );
      toast.success("Payment successful and contract removed.");
      setOpenPayment(false); // Close the payment modal
    } catch (error) {
      toast.error(error.message || "Failed to update contract status.");
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
      toast.error(error.message || "Failed to delete contract.");
    }
  };

  return (
    <Box>
      <Modal
        open={openPayment}
        onClose={() => setOpenPayment(false)}
        sx={{
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          backdropFilter: "blur(3px)",
        }}
      >
        <Box
          sx={{
            bgcolor: "background.paper",
            border: "2px solid #000",
            boxShadow: 24,
            p: 4,
          }}
        >
          {paymentDetails && (
            <PaymentPage
              amount={paymentDetails.amount}
              contractId={paymentDetails.contractId}
              onPaymentSuccess={onPaymentSuccess}
            />
          )}
        </Box>
      </Modal>
      {!contracts || contracts.length === 0 ? (
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
                      <Typography>
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
                    onClick={() => handlePay(contract)}
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
