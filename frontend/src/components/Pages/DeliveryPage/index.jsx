import * as React from "react";
import { useState } from "react";
import {
  Stepper,
  Step,
  StepLabel,
  Button,
  Typography,
  FormGroup,
} from "@mui/material";
import { useForm } from "react-hook-form";

import TextInput from "../../TextInput";
import formDataJSON from "../../../assets/DeliveryFormInputFields.json";
import CheckBoxInput from "../../CheckBoxInput";
import { contractDefaultValues, MockStations, transformData } from "./settings";
import DeliveryTypeForm from "./components/DeliveryTypeForm";
import { useAuth } from "../../../../hooks/useAuth";

const steps = Object.values(formDataJSON).map((step) => step.title);

function DeliverPage() {
  const [activeStep, setActiveStep] = useState(0);
  const [deliveryType, setDeliveryType] = useState("pickup");
  const [station, setStation] = useState(MockStations[0].id);
  const {
    control,
    handleSubmit,
    formState: { isValid },
    getValues,
    reset,
  } = useForm({ mode: "onChange", defaultValues: contractDefaultValues });
  const { user } = useAuth();

  const handleTypeChange = () => {
    deliveryType === "pickup"
      ? setDeliveryType("dropoff")
      : setDeliveryType("pickup");
  };

  const isStepOptional = (step) => {
    return step === 3;
  };
  const handleNext = (values) => {
    if (isValid) {
      console.log(station);
      if (activeStep === 4) {
        const structuredJson = transformData(
          values,
          user,
          deliveryType,
          station
        );
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

  return (
    <div className="mt-20 w-4/5 mx-80%">
      <Typography variant="h4">Deliver with CPS</Typography>
      <Stepper activeStep={activeStep}>
        {steps.map((label, index) => {
          const stepProps = {};
          const labelProps = {};
          if (isStepOptional(index)) {
            labelProps.optional = (
              <Typography variant="caption">Optional</Typography>
            );
          }
          return (
            <Step key={label} {...stepProps}>
              <StepLabel {...labelProps}>{label}</StepLabel>
            </Step>
          );
        })}
      </Stepper>
      {activeStep === steps.length ? (
        <React.Fragment>
          <Typography className="mt-2 mb-1">
            All steps completed - your delivery is in progress
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
                {formDataJSON.addOns.fields.map((field) => (
                  <CheckBoxInput
                    key={field.id}
                    control={control}
                    name={field.id}
                    label={field.label}
                  />
                ))}
              </FormGroup>
              <TextInput
                control={control}
                name={formDataJSON.addOns.warranty.id}
                label={formDataJSON.addOns.warranty.label}
                type="number"
              />
            </form>
          )}
          {activeStep === 4 && <></>}
        </React.Fragment>
      )}
    </div>
  );
}

export default DeliverPage;
