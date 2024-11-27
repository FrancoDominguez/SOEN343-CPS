import dayjs from "dayjs";

export const contractDefaultValues = {
  country: "",
  province: "",
  city: "",
  postalCode: "",
  streetAddress: "",
  height: "",
  width: "",
  length: "",
  weight: "",
  isFragile: false,
  priorityShipping: false,
  signatureRequired: false,
  isFlexible: false,
  warrantyAmount: 0.0,
  originCountry: "",
  originProvince: "",
  originCity: "",
  originPostalCode: "",
  originStreetAddress: "",
  pickUpTime: null,
};

export const MockStations = [
  { name: "Station 1", address: "Station 1 Address", id: 1 },
  { name: "Station 2", address: "Station 2 Address", id: 2 },
  { name: "Station 3", address: "Station 3 Address", id: 3 },
  { name: "Station 4", address: "Station 4 Address", id: 4 },
  { name: "Station 5", address: "Station 5 Address", id: 5 },
];

export const transformData = (data, user, deliveryType, station) => {
  const transformedData = {
    clientId: user.userId,
    parcel: {
      length: parseFloat(data.length),
      width: parseFloat(data.width),
      height: parseFloat(data.height),
      weight: parseFloat(data.weight),
      isFragile: data.isFragile,
    },
    destination: {
      streetAddress: data.streetAddress,
      postalCode: data.postalCode,
      city: data.city,
      country: data.country,
    },
    signatureRequired: data.signatureRequired,
    hasPriority: data.priorityShipping,
    warrantedAmount: parseFloat(data.warrantyAmount),
  };

  if (deliveryType === "pickup") {
    transformedData.origin = {
      streetAddress: data.originStreetAddress,
      postalCode: data.originPostalCode,
      city: data.originCity,
      country: data.originCountry,
    };
    transformedData.pickupTime = dayjs(data.pickUpTime).toISOString();
    transformedData.isFlexible = data.isFlexible;
  } else if (deliveryType === "dropoff") {
    transformedData.stationId = station ? parseInt(station) : undefined;
  }

  return transformedData;
};
