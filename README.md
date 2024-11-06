### Documents

## Sprint 1

- [Sprint 1 Docs](https://docs.google.com/document/d/1E1hDRnq1JdwVTNN4_kbrR4meCf0MkF0RAz2bThp6g8s/edit?usp=sharing)

- [Sprint 2 Docs](https://docs.google.com/document/d/1kGCXuvp9XKdKInSqQUDWFgDJl1enweDrMSPCZ8ve_ic/edit?tab=t.0)

- [Context Diagram](https://app.diagrams.net/#G1Jo-fCdNV0BeeOHu39IpNm5gcvtYM7EvU#%7B%22pageId%22%3A%22FhaqLYx5OxTo6mxjSn0A%22%7D)

- [Domain Model](https://app.diagrams.net/#G1Jo-fCdNV0BeeOHu39IpNm5gcvtYM7EvU#%7B%22pageId%22%3A%22FhaqLYx5OxTo6mxjSn0A%22%7D)

### Install Springboot

https://www.oracle.com/ca-en/java/technologies/downloads/

# Running the App

### Run the Frontend

note: vite works better on cmd rather than wsl

1. Navigate to the frontend directory `cd frontend`
2. Install dependencies `npm i`
3. Run the app `npm run dev`

### Compile and run the backend

1. Install maven
2. Stay on the root directory
3. run `mvn -f backend/pom.xml clean package` to compile and install dependencies
4. run `java -jar path/to/your/project/target/your-app.jar` to run the code