import * as React from "react";
import {
  Stepper,
  Step,
  StepLabel,
  Button,
  Typography,
  TextField,
  FormGroup,
  FormControlLabel,
  Checkbox,
} from "@mui/material";

const steps = [
  "Destination Address",
  "Package Information",
  "add ons",
  "Complete Purchase",
];

function DeliverPage() {
  const [activeStep, setActiveStep] = React.useState(0);
  const [skipped, setSkipped] = React.useState(new Set());

  const isStepOptional = (step) => {
    return step === 2;
  };

  const isStepSkipped = (step) => {
    return skipped.has(step);
  };

  const handleNext = () => {
    let newSkipped = skipped;
    if (isStepSkipped(activeStep)) {
      newSkipped = new Set(newSkipped.values());
      newSkipped.delete(activeStep);
    }

    setActiveStep((prevActiveStep) => prevActiveStep + 1);
    setSkipped(newSkipped);
  };

  const handleBack = () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  };

  const handleSkip = () => {
    if (!isStepOptional(activeStep)) {
      throw new Error("You can't skip a step that isn't optional.");
    }

    setActiveStep((prevActiveStep) => prevActiveStep + 1);
    setSkipped((prevSkipped) => {
      const newSkipped = new Set(prevSkipped.values());
      newSkipped.add(activeStep);
      return newSkipped;
    });
  };

  const handleReset = () => {
    setActiveStep(0);
  };

  return (
    <div className="mt-40 w-4/5 mx-80%">
      <Typography variant="h4">Send with CPS</Typography>
      <Stepper activeStep={activeStep}>
        {steps.map((label, index) => {
          const stepProps = {};
          const labelProps = {};
          if (isStepOptional(index)) {
            labelProps.optional = (
              <Typography variant="caption">Optional</Typography>
            );
          }
          if (isStepSkipped(index)) {
            stepProps.completed = false;
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
            All steps completed - you&apos;re finished
          </Typography>
          <div className="flex flex-row pt-2">
            <div className="flex-1" />
            <Button onClick={handleReset}>Reset</Button>
          </div>
        </React.Fragment>
      ) : (
        <React.Fragment>
          <Typography className="mt-2 mb-1">Step {activeStep + 1}</Typography>
          <div className="flex flex-row pt-2">
            <Button
              color="inherit"
              variant="contained"
              disabled={activeStep === 0}
              onClick={handleBack}
              className="mr-1"
            >
              Back
            </Button>
            <div className="flex-1" />
            <div className="mr-5">
              {isStepOptional(activeStep) && (
                <Button
                  color="inherit"
                  onClick={handleSkip}
                  className="mr-2"
                  variant="contained"
                >
                  Skip
                </Button>
              )}
            </div>
            <Button onClick={handleNext} variant="contained">
              {activeStep === steps.length - 1 ? "Finish" : "Next"}
            </Button>
          </div>
          {activeStep === 0 && (
            <form name="packageinfo">
              <TextField
                id="country"
                label="Country"
                variant="outlined"
                fullWidth
                margin="normal"
              />
              <TextField
                id="province"
                label="Province"
                variant="outlined"
                fullWidth
                margin="normal"
              />
              <TextField
                id="city"
                label="City"
                variant="outlined"
                fullWidth
                margin="normal"
              />
              <TextField
                id="postalcode"
                label="Postal Code"
                variant="outlined"
                fullWidth
                margin="normal"
              />
              <TextField
                id="streetaddress"
                label="Street Address"
                variant="outlined"
                fullWidth
                margin="normal"
              />
            </form>
          )}
          {activeStep === 1 && (
            <div className="">
              <form>
                <FormGroup>
                  <TextField
                    id="height"
                    label="Height"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                  />
                  <TextField
                    id="width"
                    label="Width"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                  />
                  <TextField
                    id="length"
                    label="Length"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                  />
                  <TextField
                    id="weight"
                    label="Weight (in kg)"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                  />
                </FormGroup>
              </form>
            </div>
          )}
          {activeStep === 2 && (
            <form>
              <FormGroup>
                <FormControlLabel
                  control={<Checkbox />}
                  label="100$ - Priority Shipping"
                />
                <FormControlLabel
                  control={<Checkbox />}
                  label="50$ - Damage Warranty"
                />
                <FormControlLabel
                  control={<Checkbox />}
                  label="20$ - Signature required"
                />
              </FormGroup>
            </form>
          )}
        </React.Fragment>
      )}
    </div>
  );
}

export default DeliverPage;
