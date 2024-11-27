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
        "streetAddress": "home pickup street",
        "postalCode": "home pickup postal",
        "city": "home pickup city",
        "country": "home country"
    },
    "signatureRequired": False,
    "hasPriority": False,
    "warrantedAmount": 0,
    "isFlexible": False,
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



response1 = requests.post(url+"/signup", json=signupData)
response2 = requests.post(url+"/login", json=loginData)
response3 = requests.post(url+"/contract", json=homePickupData)
response4 = requests.post(url+"/contract", json=stationDropoffData)
response5 = requests.get(url+"/stations")

print(response2.json())
print(response3.json())
print(response4.json())
print(response5.json())




