#!/bin/bash

mvn clean package -o -B -DskipTests

java -jar target/mementor.jar
