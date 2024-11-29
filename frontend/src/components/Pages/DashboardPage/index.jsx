import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Typography,
  Box,
} from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import Contracts from "./components/contracts";
import { useEffect, useState } from "react";
import axios from "axios";
import { toast } from "react-toastify";

import { useAuth } from "../../../../hooks/useAuth";
import Deliveries from "./components/deliveries";

function DashboardPage() {
  const [contracts, setContracts] = useState();
  const [deliveries, setDeliveries] = useState([]);
  const { user } = useAuth();

  useEffect(() => {
    getContracts();
  }, [user]);

  useEffect(() => {
    contracts && getDeliveries();
  }, [contracts]);

  const getContracts = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/contract?userId=${user.userId}`
      );

      setContracts(response.data);
    } catch (error) {
      toast.error(error);
    }
  };

  const getDeliveries = async () => {
    try {
      const deliveries = (
        await axios.get(`http://localhost:8080/delivery?userId=${user.userId}`)
      ).data;

      setDeliveries(deliveries);
    } catch (error) {
      toast.error(error);
    }
  };

  return (
    <div className="mt-20 w-4/5 mx-80%">
      <Typography variant="h4">Dashboard</Typography>
      <Box marginY={2}>
        <Accordion>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            Contracts
          </AccordionSummary>
          <AccordionDetails>
            <Contracts contracts={contracts} setContracts={setContracts} />
          </AccordionDetails>
        </Accordion>
        <Accordion>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            Deliveries
          </AccordionSummary>
          <AccordionDetails>
            <Deliveries deliveries={deliveries} />
          </AccordionDetails>
        </Accordion>
      </Box>
    </div>
  );
}

export default DashboardPage;
