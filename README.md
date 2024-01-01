# Project Details

## Project Overview
Provide a brief overview about the project. (TODO)

## Architecture
Cover the overall architecture of the project. (TODO)

## Build Instructions
Follow the below commands to build service.

* If you do not plan to use `Docker` to run service, run the below commands:
    * Navigate to `Codebase Root` folder.
    * Type the below commands:
        * `mvn clean install -Ddockerfile.build.skip=true`
    * The above command should build all the artifacts.
* If you plan to use `Docker` to run service, run the below sequence of commands. These
  commands build the `Docker` images and uses [dockerfile-maven-plugin](https://github.com/spotify/dockerfile-maven)
  from Spotify.
    * Here we are trying to run database as container and connect to this database from service. So before building image for application service we need to check DB_HOST environment variable is properly configured or not. we can use below environment variable to set the database host.
        * `export DB_HOST={DB_SERVICE_NAME}`.
          here DB_SERVICE_NAME value will be database service name in docker-compose.yml file which is located at <code base root folder>/docker folder.
    * Navigate to `<Codebase Root>` folder and run the below command:
        * `mvn clean install -P docker`
    * This should build all the artifacts including docker images of infra services and application services (which will be available in the local docker
      repository).

## Deployment Instructions

## Run Without Docker
If you want to run application without `Docker`, there is need to follow below steps

* Make sure database is installed and running in your machine with same account details which you gave while configuring database settings at the time of data modelling.
* Check the host name in `<Codebase Root>/services/{application-service}/src/main/resources/application.yml' file whether it is localhost or not. As we are running the service with out docker and trying to connect to the local db then it should be localhost. If not, you can use below environment variable to set the database host.
  `export DB_HOST={DB_HOST_NAME}`.
## Setup iSymphony Configuration Root
We use Spring Cloud Config Server to serve the configurations of all Microservices from a centralized location. 
As this section focuses on running the application without Docker, there is need for a manual setup. 
Follow the below sequence of steps to setup the configuration location for configuration server.

* Open a Git Bash shell.
* Navigate to <Codebase Root>/modules/infra-services/platform-config-service/src/main/resources/config folder.
* Copy all yml files in this location to the location identified by the environment variable CONFIGURATION_ROOT, which is covered in the above section titled "Environment Variables".
* Navigate to the location identified by the environment variable CONFIGURATION_ROOT and execute the below commands:
 `git init` (this is a one-time activity)
 `git config --global user.email "xxx@yopmail.com"` (this is a one-time activity)
 `git config --global user.name "yyy"` (this is a one-time activity)`
 `git add .` (The next two steps have to be repeated each time there are changes in the configuration files)
 `git commit -a -m "Latest" `
* If the configuration files change, they have to be copied over to the location identified by the environment variable CONFIGURATION_ROOT and the last two steps indicated above have to be repeated. These steps have to be performed before re-starting the configuration service.

## Start the services
You can start the services from the IDE or from the command line.
* First start the Infrastructure Services, It is recommended to start config-service (<Codebase Root>/infra-services/xx-xx-config-service project) before staring the discovery-service and gateway-service.
To run service from command line:
* Open a Git Bash shell.
* Navigate to `<Codebase Root>/infra-services/{config-service}` Run `mvn spring-boot:run` to start configuration service.
* Same like infra-services/config-service, start discovery and gateway services with same command from their respective locations.
* Navigate to `<Codebase Root>/services/{application-service}` Run `mvn spring-boot:run`.
* If the application contains multiple services then start all services.


## With Docker

You do not need to make any changes if you want to run the services using `Docker`. Follow the below steps
to run using the `Docker Compose` yml file.

* Open Git Bash shell
* Navigate to `<Codebase Root>/docker` folder.
* First need to start infra-services. docker-infra-services.yml file contains all information regarding infra services container instances.
* To start all infra-services run `docker-compose -f docker-infra-services.yml up -d` .
* Once all infra services containers up and running, then run the below commands to launch database and application services.
    * `docker-compose -f docker-compose.yml up -d` (wait until it starts -
      Use `docker logs -f <container id>` for logs)
      
## To see the api documentation please visit
http://{host}:{service-port}/swagger-ui.html
