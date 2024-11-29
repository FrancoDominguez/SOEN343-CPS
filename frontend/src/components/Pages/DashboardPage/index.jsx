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

function DashboardPage() {
  const [contracts, setContracts] = useState([]);
  const { user } = useAuth();

  useEffect(() => {
    getContracts();
  }, [user]);

  const getContracts = async () => {
    try {
      const contracts = (
        await axios.get(`http://localhost:8080/contract?userId=${user.userId}`)
      ).data;

      setContracts(contracts);
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
          <AccordionDetails></AccordionDetails>
        </Accordion>
      </Box>
    </div>
  );
}

export default DashboardPage;
