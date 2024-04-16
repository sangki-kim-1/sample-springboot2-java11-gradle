#!/bin/bash

BUILD_NAME=sample-springboot2-jdk11-gradle
EXPOSE_PORT=8082

docker rmi $BUILD_NAME
docker build -t $BUILD_NAME .

# foreground -> script exit = docker process stop
docker run --rm -p $EXPOSE_PORT:8080 --name $BUILD_NAME $BUILD_NAME

# for docker data remaining local
#docker run --rm -p $EXPOSE_PORT:8080 --name $BUILD_NAME -v ./:/app $BUILD_NAME

# background -> must docker stop manually for process exit
# docker run -d --rm -p $EXPOSE_PORT:8080 --name $BUILD_NAME $BUILD_NAME
# docker stop $BUILD_NAME
