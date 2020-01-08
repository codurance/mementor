# Guru (Mementor)

This is the final project from the appretices from October to January 2019-20, this tool allows a better overview on the mentor/mentee relationship and track the meeting that they are having.

## Tech stack

Back-End (Java):
- Spring Boot
- JUnit4
- RestAssured
- Maven

Front-End:
- React.js
- Jest and Enzyme

Infrastructure:
- AWS
    - EB
    - RDS
- Docker
- CircleCI

## Build

To build the project in your localhost, you need to go to first of all
build the `front-end` application to package it. After this we package 
our `back-end`.

to do this run the `localdeploy.sh` file.

Now you can go to http://localhost:8080/ and the app will be deployed.

_note that this steps can be found in the Dockerfile._
