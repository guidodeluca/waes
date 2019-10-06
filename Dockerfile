#FROM openjdk:8-jdk-alpine
#RUN apk --no-cache add curl

FROM ubuntu:18.04
#RUN apt-get update && apt-get install curl bash

#RUN DD_API_KEY=c262d3b0a19fca78de3ddec52c03e414 DD_SITE="datadoghq.eu" bash -c "$(curl -L https://raw.githubusercontent.com/DataDog/datadog-agent/master/cmd/agent/install_script.sh)"

VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]