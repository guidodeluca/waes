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
curl -XGET "Content-type: application/json" 'http://localhost:8080/v1/diff/key'
```

##### Add left side data:
```
[PUT] /v1/diff/{id}/left: 

Example:
curl -XPUT "Content-type: application/json" -d '{ "mime": "file/plain", "data": "TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNlY3RldHVyIGFkaXBpc2NpbmcgZWxpdC4gRG9uZWMgcmhvbmN1cyBzY2VsZXJpc3F1ZSBxdWFtLCBuZWMgbG9ib3J0aXMgcmlzdXMuCg==" }' 'http://localhost:8080/v1/diff/17b4019b-4eb0-4c14-b6cf-de7754a2b108/left'
```

##### Add right side data:
```
[PUT] /v1/diff/{id}/right

Example: 
curl -XPUT "Content-type: application/json" -d '{ "mime": "file/plain", "data": "TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNlY3RldHVyIGFkaXBpc2NpbmcgZWxpdC4gRG9uZWMgcmhvbmN1cyBzY2VsZXJpc3F1ZSBxdWFtLCBuZWMgbG9ib3J0aXMgcmlzdXMuCg==" }' 'http://localhost:8080/v1/diff/17b4019b-4eb0-4c14-b6cf-de7754a2b108/right'
```

##### Compare sides:
```
[POST] /v1/diff/{id}

Example:
curl -XPOST 'http://localhost:8081/v1/diff/17b4019b-4eb0-4c14-b6cf-de7754a2b108'
```

## APM

For producction monitoring is configured Datadog APM (https://www.datadoghq.com) free account.
Por running the agent, execute

`DOCKER_CONTENT_TRUST=1 docker run -d --name dd-agent -v /var/run/docker.sock:/var/run/docker.sock:ro -v /proc/:/host/proc/:ro -v /sys/fs/cgroup/:/host/sys/fs/cgroup:ro -e DD_API_KEY=c262d3b0a19fca78de3ddec52c03e414 -e DD_SITE="datadoghq.eu" datadog/agent:latest`

## Running the tests

`mvn test` for running all test

