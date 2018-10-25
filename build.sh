#!/usr/bin/env bash
mvn clean package -U -Dmaven.test.skip=true
docker build -t hankzhangorion/bootu-apigateway:latest .
docker push hankzhangorion/bootu-apigateway:latest