import requests

url = "http://localhost:8080/signup"
data = {
    "firstname" : "antoine",
    "lastname" : "mansour",
    "email": "hoboslime@gmail.com",
    "password": "supersafe"
}

response = requests.post(url, json=data)
print(response.json())



