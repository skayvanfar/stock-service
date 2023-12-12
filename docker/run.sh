#!/bin/sh

echo "********************************************************"
echo "Starting Stock Server"
echo "********************************************************"
exec java $JAVA_OPTS -Dspring.profiles.active=$PROFILE -jar /usr/local/stockservice/app.jar
