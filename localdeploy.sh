#!/bin/bash

npm install --prefix ./front-end && npm run build --prefix ./front-end

mvn package

java -jar target/mementor.jar

npm --prefix ./front-end start
