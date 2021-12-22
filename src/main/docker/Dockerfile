FROM openjdk:11-jdk-slim-buster
RUN apt-get update && apt-get upgrade && apt-get install -y netcat-openbsd && apt-get install -y bash
RUN mkdir -p /usr/local/stockservice
ADD @project.build.finalName@.jar /usr/local/stockservice/
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh
