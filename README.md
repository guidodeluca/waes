# WAES test application

Test application for comparing two binary file and get the differences between them.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites
```
- Maven 3
- Java 8+
- Docker (Optional for release testing)
````

### Deployment

#### Spring-boot
`mvn spring-boot:run` default port: `8081`

#### Docker
`docker-compose up --build` default port: `8081`

### Rest services

#### Swagger
For use the application internally, the application has
http://[server]:[port]/swagger-ui.html

#### Endpoints

##### Creates a random key:
```
[GET] /v1/diff/key

Example:

```

##### Add left side data:
```
[PUT] /v1/diff/{id}/left: 

Example:
 
```

##### Add right side data:
```
[PUT] /v1/diff/{id}/right

Example: 

```

##### Compare sides:
```
[POST] /v1/diff/{id}

Example:

```

## Running the tests

`mvn test` for running all test

