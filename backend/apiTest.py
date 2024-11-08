import requests

url = "http://localhost:8080/login"
data = {
    "email": "hoboslime@gmail.com",
    "password": "supersafe"
}

response = requests.post(url, json=data)
print(response.json())