<<<<<<< Updated upstream
import requests

url = "http://localhost:8080/signup"
data = {
    "firstname": "antoine",
    "lastname": "mansour",
    "email": "antoine.rmcr77@gmail.com",
    "password": "supersafe"
}

response = requests.post(url, json=data)

try:
    # Try to parse JSON if available
    response_json = response.json()
    print("Response JSON:", response_json)
except ValueError:
    # Handle the case where the response is plain text
    print("Response is plain text:", response.text)
=======
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


>>>>>>> Stashed changes
