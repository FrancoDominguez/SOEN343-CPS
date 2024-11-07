import * as React from "react";
import formDataJSON from "../../assets/DeliveryFormInputFields.json";
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

const steps = Object.values(formDataJSON).map((step) => step.title);

function DeliverPage() {
  const [activeStep, setActiveStep] = React.useState(0);
  const [formData, setFormData] = React.useState(formDataJSON);

  const isStepOptional = (step) => {
    return step === 2;
  };
  const handleNext = () => {
    setActiveStep((prevActiveStep) => prevActiveStep + 1);
  };
  const handleBack = () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  };
  const handleDone = () => {
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
            <Button onClick={handleDone}>Reset</Button>
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
            <Button onClick={handleNext} variant="contained">
              {activeStep === steps.length - 1 ? "Finish" : "Next"}
            </Button>
          </div>
          {activeStep === 0 && (
            <form name="packageinfo">
              <FormGroup>
                {formData.destinationAddress.textFields.map((field) => (
                  <TextField
                    key={field.id}
                    id={field.id}
                    label={field.label}
                    variant="outlined"
                    fullWidth
                    margin="normal"
                  />
                ))}
              </FormGroup>
            </form>
          )}
          {activeStep === 1 && (
            <div className="">
              <form>
                <FormGroup>
                  {formData.packageInfo.textFields.map((field) => (
                    <TextField
                      key={field.id}
                      id={field.id}
                      label={field.label}
                      variant="outlined"
                      fullWidth
                      margin="normal"
                    />
                  ))}
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
