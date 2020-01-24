# Guru (Mementor)

This is the final project from the apprentices from October to January 2019-20, this tool allows a better overview on the mentor/mentee relationship and track the meeting that they are having.

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

To build the project in your localhost, you need to 
build the `front-end` application to package it. After this we package 
our `back-end`, and then run our `.jar` file.

to do this run the `localdeploy.sh` file.

Now you can go to http://localhost:8080/ and the app will be deployed.

_note that this steps can be found in the Dockerfile._

## Frontend dev development

If you're only working on the frontend, you won't need to redeploy the entire application on every change.
You can deploy the backend one and start a dev server that will hot reload the frontend code for you.

- `./localdeploy.sh`
- `npm --prefix front-end start`

The app will now be accessible on http://localhost:3000

## AWS Deployment

In order to deploy to AWS from your local machine, you need to run `aws configure` and setup the credentials that can
be found on BitWarden.  
Then you can run the deploy script:

`./deploy-aws.sh integ`

The artifact will be generated locally and pushed to AWS.
Folders such as `front-end/build` and `target` will be cleaned before the build.

If you already built the artifact and want to deploy it to multiples environment, 
you can use append `--no-build` to skip the build.

## Format Front End VSCode

To format the project and the files acording to the agreed convention, you'll need to download prettier extention. Follow this guide to do it https://github.com/prettier/prettier-vscode#installation, after the extention is downloaded, you'll have to manually save the files to trigger the format `cmd+S`

## OAuth Client ID

The client ID for oauth is located at two different locations:
- `application.properties`
- `App.js` - `GoogleLogin` component