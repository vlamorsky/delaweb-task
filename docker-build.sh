#!/bin/sh
mvn clean package
docker build -t delaweb-task .
