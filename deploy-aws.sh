#!/bin/bash

set -e

if [ $# -lt 1 ] ; then
    echo "USAGE: $0 environment-name"
    echo "  environment-name: prod | dev"
    echo $@
    exit 1
fi

ENV=$1
PLATFORM="64bit Amazon Linux 2018.03 v2.14.0 running Docker 18.09.9-ce"
APP_NAME="mementor"
REGION="eu-central-1"

eb init --region "$REGION" --platform "$PLATFORM" "$APP_NAME"
eb deploy "$APP_NAME-$ENV"
