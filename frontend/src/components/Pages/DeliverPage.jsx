import * as React from "react";
import formDataJSON from "../../assets/DeliveryFormInputFields.json";
import {
  Stepper,
  Step,
  StepLabel,
  Button,
  Typography,
  FormGroup,
  FormControlLabel,
  Checkbox,
} from "@mui/material";
import TextInput from "../TextInput";
import { useForm } from "react-hook-form";

const steps = Object.values(formDataJSON).map((step) => step.title);

function DeliverPage() {
  const [activeStep, setActiveStep] = React.useState(0);
  const {
    control,
    handleSubmit,
    formState: { isValid },
    getValues,
  } = useForm({ mode: "onChange" });

  const isStepOptional = (step) => {
    return step === 2;
  };
  const handleNext = () => {
    if (isValid) {
      setActiveStep((prevActiveStep) => prevActiveStep + 1);
    } else {
      console.log("Form is invalid");
    }
  };
  const handleBack = () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  };
  const handleDone = () => {
    console.log(getValues());
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
            <Button onClick={handleSubmit(handleNext)} variant="contained">
              {activeStep === steps.length - 1 ? "Finish" : "Next"}
            </Button>
          </div>
          {activeStep === 0 && (
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
          {activeStep === 1 && (
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
          {activeStep === 3 &&
            Object.entries(getValues()).map((key, value) => (
              <Typography>{key + value}</Typography>
            ))}
        </React.Fragment>
      )}
    </div>
  );
}

export default DeliverPage;
