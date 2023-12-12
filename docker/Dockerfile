FROM openjdk:17-jdk-slim-buster

WORKDIR /home/stock-service

RUN apt-get install -y bash

ADD docker/entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh

RUN mkdir -p /usr/local/stockservice
ADD target/*.jar /usr/local/stockservice/app.jar

ENTRYPOINT ["./entrypoint.sh"]

