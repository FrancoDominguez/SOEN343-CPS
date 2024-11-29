import requests

import time
random_value = time.time()

url = "http://localhost:8080"

signupData = {
  "firstname": "userFirstname",
  "lastname": "userLastname",
  "email": random_value,
  "password":"supersafe"
}

loginData = {
  "email":random_value,
  "password":"supersafe"
}

homePickupData = {
    "clientId": 2,
    "parcel": {
        "length": 123.00,
        "width": 123.00,
        "height": 123.00,
        "weight": 123.00,
        "isFragile": False
    },
    "destination": {
        "streetAddress": "1455 Blvd. De Maisonneuve Ouest, Montreal, Quebec H3G 1M8",
        "postalCode": "",
        "city": "",
        "country": ""
    },
    "signatureRequired": False,
    "hasPriority": False,
    "warrantedAmount": 0,
    "isFlexible": True,
    "origin": {
        "streetAddress": "123",
        "postalCode": "123",
        "city": "123",
        "country": "123"
    },
    "pickupTime": "2024-11-26T21:36:49.247Z"
}

stationDropoffData = {
    "clientId": 2,
    "parcel": {
        "length": 200.00,
        "width": 200.00,
        "height": 200.00,
        "weight": 200.00,
        "isFragile": False
    },
    "destination": {
        "streetAddress": "station dropoff street",
        "postalCode": "station postal code",
        "city": "station city",
        "country": "station country"
    },
    "signatureRequired": False,
    "hasPriority": True,
    "warrantedAmount": 0,
    "stationId": 1
}

trackOrderData = {
  "trackingId":123552
}

response1 = requests.post(url+"/signup", json=signupData)
response2 = requests.post(url+"/login", json=loginData)
response3 = requests.post(url+"/contract", json=homePickupData)
homePickupContractId = response3.json()["id"]
response4 = requests.post(url+"/delivery", json={"contractId":homePickupContractId})
response5 = requests.post(url+"/contract", json=stationDropoffData)
response6 = requests.get(url+"/stations")
response7 = requests.get(url+"/delivery/status?trackingId=123552")

print("\nprinting signup response: \n")
print(response1.json())
print("\nprinting login response: \n")
print(response2.json())
print("\nprinting creating contract response: \n")
print("\nprinting creating delivery response: \n")
print("\nprinting creating contract response: \n")
print("\nprinting creating new contract response: \n")
print(response4.json())
print("\nprinting creating new contract response: \n")
print("\nprinting tracking: \n", response7.json())



