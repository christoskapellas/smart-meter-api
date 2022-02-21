# Smart Meter API

This is an API that was built using Java, Maven and Spring Boot.

## How to Run

* Clone this repository
* You can build the project and run the tests by running ```mvn clean package```
* Once successfully built, you can run the service with the following command:
```
        java -jar target/smart-meter-API-0.0.1-SNAPSHOT.jar
```

## About the Service

The service is just a smart meter reading REST service. It uses an in-memory database (H2) to store the data.

Here are some endpoints you can call:

```
http://localhost:8081/api/smart/reads/
http://localhost:8081/api/smart/reads/{id}
http://localhost:8081/api/smart/reads/{id}/compare/{comparisonId}
```

### Create an account or add gas and electricity meter readings

```
POST http://localhost:8081/api/smart/reads
Accept: application/json
Content-Type: application/json
{
    "accountId": "4",
    "gasReadings":[
        {
            "meterId":5,
            "reading":567.5,
            "date":"2022-03-01T00:00:00.000+00:00"
        }
    ],
    "elecReadings":[
        {
            "meterId":6,
            "reading":72.3,
            "date":"2022-03-02T00:00:00.000+00:00"
        }
    ]
}
```

### Retrieve a list of accounts and their meter readings

```
http://localhost:8081/api/smart/reads/
```

### Retrieve a specific account and its meter readings by its id

```
http://localhost:8081/api/smart/reads/{id}
```

### Compare an account with another one using their ids

```
http://localhost:8081/api/smart/reads/{id}/compare/{comparisonId}
```
