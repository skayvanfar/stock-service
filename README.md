# Stock-service
## Software needed
1.	Apache Maven (http://maven.apache.org).
2.	Docker (http://docker.com).
3.	Git Client (http://git-scm.com).

## Building the Docker Image
To build the code for project as a docker image, open a command-line window change to the directory where you have downloaded the project source code.

Run the following maven command.  This command will execute the [Spotify docker plugin](https://github.com/spotify/docker-maven-plugin) defined in the pom.xml file.  

   **mvn clean package docker:build**

If everything builds successfully you should see a message indicating that the build was successful.

## Running the Application

Now we are going to use docker-compose to start the actual image.  To start the docker image,
change to the directory containing  your project source code.  Issue the following docker-compose command:

   **docker-compose -f compose/common/docker-compose.yml up**

If everything starts correctly you should see a bunch of spring boot information fly by on standard out.  At this point all of the services needed for the project code will be running.
