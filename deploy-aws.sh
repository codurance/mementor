#!/bin/bash

set -e

if [ $# -lt 1 ] ; then
    echo "USAGE: $0 environment-name"
    echo "  environment-name: prod | integ"
    echo "  --no-build: skip build phase, will fail if the artifact does not exist"
    echo $@
    exit 1
fi

ENV=$1
NO_BUILD=$2
PLATFORM="64bit Amazon Linux 2018.03 v2.14.0 running Docker 18.09.9-ce"
APP_NAME="mementor"
REGION="eu-central-1"

if [ "--no-build" = $2 ] ; then
  echo "AWS artifact build skipped"
else
  ./build-aws-artifact.sh
fi

echo "##############################"
echo "##### Deploying to AWS #####"
echo "##############################"

eb init --region "$REGION" --platform "$PLATFORM" "$APP_NAME"
eb deploy --staged "$APP_NAME-$ENV"
echo "done."
