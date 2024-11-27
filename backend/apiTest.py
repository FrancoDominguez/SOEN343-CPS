import requests

url = "http://localhost:8080"


loginData = {
  "email":"email@email.com",
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
        "streetAddress": "123",
        "postalCode": "123",
        "city": "123",
        "country": "123"
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
        "length": 123.00,
        "width": 123.00,
        "height": 123.00,
        "weight": 213.00,
        "isFragile": False
    },
    "destination": {
        "streetAddress": "123",
        "postalCode": "123",
        "city": "1231",
        "country": "123"
    },
    "signatureRequired": False,
    "hasPriority": True,
    "warrantedAmount": 0,
    "stationId": 1
}


response2 = requests.post(url+"/login", json=loginData)
response3 = requests.post(url+"/contract", json=homePickupData)
response4 = requests.post(url+"/contract", json=stationDropoffData)

print(response2.json())
print(response3.json())
print(response4.json())




