# Documentation

### Documents

- [Sprint 1 Docs](https://docs.google.com/document/d/1E1hDRnq1JdwVTNN4_kbrR4meCf0MkF0RAz2bThp6g8s/edit?usp=sharing)

- [Sprint 2 Docs](https://docs.google.com/document/d/1kGCXuvp9XKdKInSqQUDWFgDJl1enweDrMSPCZ8ve_ic/edit?tab=t.0)

- [Sprint 3 Docs](https://docs.google.com/document/d/19A_7qKFYMrg6esX5F3NyFJg9eGfYO34FmZy8TgPjLWU/edit?tab=t.0)

- [Sprint 4 Docs](https://docs.google.com/document/d/1RoCCGSqYTVoNRvgNDRftAo6vvPY6rdROKlTo5lG-PD4/edit?usp=sharing)

- [Presentation Slides](https://docs.google.com/presentation/d/1b4Lhw6yehudaWA30O0aX0U9uFg-J7bRJamZubxW7Jk8/edit#slide=id.p)
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

note: if you are on wsl, you can alias `alias runjava='mvn clean package && java -jar target/CPS-backend-1.0-SNAPSHOT.jar'`, then simply run `runjava` to compile and run the entire thing (still from the backend directory).

### Make direct changes to the db

To connect to the mysql server and make direct changes run the following `mysql -h franco-db.czes8i20a6iw.us-east-1.rds.amazonaws.com -P 3306 -u admin -p`. To exit type `\q` and press enter.
