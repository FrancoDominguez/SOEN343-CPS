# Documentation

### Documents

- [Sprint 1 Docs](https://docs.google.com/document/d/1E1hDRnq1JdwVTNN4_kbrR4meCf0MkF0RAz2bThp6g8s/edit?usp=sharing)

- [Sprint 2 Docs](https://docs.google.com/document/d/1kGCXuvp9XKdKInSqQUDWFgDJl1enweDrMSPCZ8ve_ic/edit?tab=t.0)

- [Sprint 3 Docs](https://docs.google.com/document/d/19A_7qKFYMrg6esX5F3NyFJg9eGfYO34FmZy8TgPjLWU/edit?tab=t.0)

### Diagrams

- [Context Diagram and Domain Model](https://app.diagrams.net/#G1Jo-fCdNV0BeeOHu39IpNm5gcvtYM7EvU#%7B%22pageId%22%3A%22FhaqLYx5OxTo6mxjSn0A%22%7D)

# Running the App

### Run the Frontend

note: vite works better on cmd than wsl.

1. Navigate to the frontend directory `cd frontend`
2. Install dependencies `npm i`
3. Run the app `npm run dev`

### Compile and run the backend

1. Install maven
2. Navigate to the backend directory `cd backend`
3. run `mvn clean package` to compile and install dependencies
4. run `java -jar target/CPS-backend-1.0-SNAPSHOT.jar` to run the code

note: if you are on wsl, you can alias `alias runjava='mvn package && java -jar target/CPS-backend-1.0-SNAPSHOT.jar'`, then simply run `runjava` to compile and run the entire thing (still from the backend directory).

### Make direct changes to the db

To connect to the mysql server and make direct changes run the following `mysql -h franco-db.czes8i20a6iw.us-east-1.rds.amazonaws.com -P 3306 -u admin -p`. To exit type `\q` and press enter.

ALTER TABLE contracts ADD CONSTRAINT chk_origin_exclusivity CHECK ((origin_location_id IS NOT NULL AND origin_station_id IS NULL AND pickup_time IS NOT NULL AND is_flexible IS NOT NULL) OR (origin_location_id IS NULL AND origin_station_id IS NOT NULL AND pickup_time IS NULL AND is_flexible IS NULL));

// CONTRACTS
contract_id: number PK autoincrement
client_id: number FK -> clients
price: Number
eta: Duration
signature_required: Bool
priority_shipping: Bool
warranted_amount: Number
parcel_id: Number FK -> parcels
destination_id: FK number -> locations
origin_station_id: FK number -> stations default null optional
origin_location_id: FK number -> locations default null optional
pickupTime: DateTime default null optional
isFlexible: Boolean default null optional

// PARCEL (DONE)
parcel_id: int PK autoincrement
parcel_height: Number
parcel_length: Number
parcel_width: Number
parcel_weight: Number
parcel_isFragile: Bool

// LOCATION (DONE)
location_id: int PK autoincrement
address: String
postal_code: String
city: String
country: String

// STATION (DONE)
station_id: int PK autoincrement
name: String
address: String
postal_code: String
city: String
country: String

// clients (DONE)
email: String
firstname: String
lastname: String
password: String
home_address: FK int -> locations optional default null
