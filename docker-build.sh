#!/bin/sh
./mvnw clean package
docker build -t delaweb-task .
