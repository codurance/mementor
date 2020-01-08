#!/bin/bash

cd front-end/

npm install && npm run build

cd ..

mvn package

java -jar target/mementor.jar
