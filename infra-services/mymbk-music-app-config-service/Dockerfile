# Use openjdk's 8-jre as the base image
FROM openjdk:8-jre

# Get the profile argument
ARG CONTAINER_BASE_DIRECTORY=/opt/apps/container
ARG CONFIGURATION_ROOT=${CONTAINER_BASE_DIRECTORY}/config
ARG ARTIFACT_NAME
ARG SERVICE_PORT
ARG ROOT_ARTIFACT_ID
ARG ISY_GIT_USERNAME=iSymphony
ARG ISY_GIT_USER_EMAIL=isymphony@innominds.com
# Environment variables
ENV CONTAINER_BASE_DIRECTORY=${CONTAINER_BASE_DIRECTORY} \
    CONFIGURATION_ROOT=${CONFIGURATION_ROOT} \
    SERVICE_PORT=${SERVICE_PORT} \
    ARTIFACT_NAME=${ARTIFACT_NAME}

# Install git
RUN apt-get update && apt-get install -y git procps

# create a folder where the config server and the related dependencies are copied
RUN bash -c 'mkdir -p ${CONFIGURATION_ROOT}'
ADD src/main/resources/config ${CONFIGURATION_ROOT}

# Initialize the git
RUN bash -c 'cd ${CONFIGURATION_ROOT} && git init && git config --global user.email "${ISY_GIT_USER_EMAIL}" && git config --global user.name "${ISY_GIT_USERNAME}" && git add . && git commit -a -m "Latest" && cd -'

# Copy the artifact jar
ADD target/${ARTIFACT_NAME} ${CONTAINER_BASE_DIRECTORY}/${ARTIFACT_NAME}

# Change permissions
RUN bash -c 'touch ${CONTAINER_BASE_DIRECTORY}/${ARTIFACT_NAME}'

# Expose the port
EXPOSE ${SERVICE_PORT}

# Command that will be executed to startup the container
CMD ["bash", "-c", "java -Dspring.profiles.active=$PROFILE -DXms512m -jar ${CONTAINER_BASE_DIRECTORY}/${ARTIFACT_NAME}"]
