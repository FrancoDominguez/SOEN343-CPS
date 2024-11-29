import * as React from "react";
import { useState, useEffect } from "react";
import {
  Stepper,
  Step,
  StepLabel,
  Button,
  Typography,
  FormGroup,
} from "@mui/material";
import { useForm } from "react-hook-form";
import axios from "axios";
import { toast } from "react-toastify";

import TextInput from "../../TextInput";
import formDataJSON from "../../../assets/DeliveryFormInputFields.json";
import CheckBoxInput from "../../CheckBoxInput";
import { contractDefaultValues, transformData } from "./settings";
import DeliveryTypeForm from "./components/DeliveryTypeForm";
import { useAuth } from "../../../../hooks/useAuth";

const steps = Object.values(formDataJSON).map((step) => step.title);

function DeliverPage() {
  const [activeStep, setActiveStep] = useState(0);
  const [deliveryType, setDeliveryType] = useState("pickup");
  const [station, setStation] = useState(1);
  const [stationList, setStationList] = useState();
  const { control, handleSubmit, trigger, reset } = useForm({
    mode: "onChange",
    defaultValues: contractDefaultValues,
  });
  const { user } = useAuth();

  const handleTypeChange = () => {
    deliveryType === "pickup"
      ? setDeliveryType("dropoff")
      : setDeliveryType("pickup");
  };

  const isStepOptional = (step) => {
    return step === 3;
  };

  useEffect(() => {
    getStationList();
  }, []);

  const getStationList = async () => {
    try {
      const stations = (await axios.get("http://localhost:8080/stations")).data;
      setStationList(stations);
    } catch (error) {
      toast.error(error);
    }
  };

  const handleNext = async (values) => {
    const isValid = trigger();
    if (isValid) {
      if (activeStep === 3) {
        const structuredJson = transformData(
          values,
          user,
          deliveryType,
          station
        );
        console.log(structuredJson);
        try {
          await createContract(structuredJson);
        } catch (error) {
          toast.error("Failed to create contract: please try again.");
        }
      }
      setActiveStep((prevActiveStep) => prevActiveStep + 1);
    }
  };

  const handleBack = () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  };
  const handleReset = () => {
    reset();
    setActiveStep(0);
  };

  const createContract = async (values) => {
    try {
      const response = await axios.post(
        "http://localhost:8080/contract",
        values
      );
      return response.data;
    } catch (error) {
      throw error;
    }
  };

  return (
    <div className="mt-20 w-4/5 mx-80%">
      <Typography variant="h4">Deliver with CPS</Typography>
      <Stepper activeStep={activeStep}>
        {steps.map((label, index) => (
          <Step key={label}>
            <StepLabel
              optional={
                isStepOptional(index) && (
                  <Typography variant="caption">Optional</Typography>
                )
              }
            >
              {label}
            </StepLabel>
          </Step>
        ))}
      </Stepper>
      {activeStep === steps.length ? (
        <React.Fragment>
          <Typography className="mt-2 mb-1">
            All steps completed - Find your new contract in the Dashboard
          </Typography>
          <div className="flex flex-row pt-2">
            <div className="flex-1" />
            <Button onClick={handleReset}>Reset</Button>
          </div>
        </React.Fragment>
      ) : (
        <React.Fragment>
          <Typography className="mt-2 mb-1">
            Step {activeStep + 1} - {steps[activeStep]}
          </Typography>
          <div className="flex flex-row pt-2">
            <Button
              color="inherit"
              variant="contained"
              disabled={activeStep === 0}
              onClick={handleBack}
            >
              Back
            </Button>
            <div className="flex-1" />
            <Button onClick={handleSubmit(handleNext)} variant="contained">
              {activeStep === steps.length - 1 ? "Finish" : "Next"}
            </Button>
          </div>
          {activeStep === 0 && (
            <DeliveryTypeForm
              deliveryType={deliveryType}
              handleTypeChange={handleTypeChange}
              station={station}
              stationList={stationList}
              setStation={setStation}
              control={control}
            />
          )}
          {activeStep === 1 && (
            <form name="destinationInfo">
              <div className="flex flex-col gap-3 w-[100%] py-3">
                {formDataJSON.destinationAddress.textFields.map((field) => (
                  <TextInput
                    control={control}
                    key={field.id}
                    id={field.id}
                    label={field.label}
                    name={field.name}
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    rules={{
                      required: {
                        value: true,
                        message: `Please enter a ${field.label}`,
                      },
                    }}
                  />
                ))}
              </div>
            </form>
          )}
          {activeStep === 2 && (
            <form name="packageInfo">
              <div className="flex flex-col gap-3 w-[100%] py-3">
                {formDataJSON.packageInfo.textFields.map((field) => (
                  <TextInput
                    control={control}
                    key={field.id}
                    id={field.id}
                    label={field.label}
                    name={field.name}
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    rules={{
                      required: {
                        value: true,
                        message: `Please enter a ${field.label}`,
                      },
                      pattern: {
                        value: /^\d+$/,
                        message: "Please enter a number",
                      },
                    }}
                  />
                ))}
              </div>
            </form>
          )}
          {activeStep === 3 && (
            <form>
              <FormGroup>
                {formDataJSON.addOns.fields.map((field) => {
                  return field.id === "isFlexible" ? (
                    deliveryType === "pickup" && (
                      <CheckBoxInput
                        key={field.id}
                        control={control}
                        name={field.id}
                        label={field.label}
                      />
                    )
                  ) : (
                    <CheckBoxInput
                      key={field.id}
                      control={control}
                      name={field.id}
                      label={field.label}
                    />
                  );
                })}
              </FormGroup>
              <TextInput
                control={control}
                name={formDataJSON.addOns.warranty.id}
                label={formDataJSON.addOns.warranty.label}
                type="number"
              />
            </form>
          )}
        </React.Fragment>
      )}
    </div>
  );
}

export default DeliverPage;
