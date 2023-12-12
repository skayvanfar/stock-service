FROM openjdk:17-jdk-slim-buster

WORKDIR /home/stock-service

RUN apt-get update -y && apt-get upgrade -y  && apt-get install -y bash
RUN mkdir -p /usr/local/stockservice
ADD target/*.jar /usr/local/stockservice/app.jar
ADD docker/run.sh run.sh
RUN chmod +x ./run.sh
CMD ./run.sh

