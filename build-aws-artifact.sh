#!/bin/bash

set -e

echo "#################################"
echo "##### Cleaning the project #####"
echo "#################################"

echo "deleting front-end/build .."
rm -rf front-end/build
echo "deleting target .."
rm -rf target
echo "deleting build .."
rm -rf build
echo "done."

echo "#################################"
echo "##### Building the project #####"
echo "#################################"

echo "npm install .."
npm install --prefix ./front-end
echo "npm run build .."
npm run build --prefix ./front-end
echo "mvn package .."
mvn package
echo "done."

echo "#################################"
echo "##### Packaging the project #####"
echo "#################################"

echo "creating build directory .."
mkdir -p build
echo "zipping artifact .."
zip target/mementor-aws.zip target/mementor.jar Dockerfile
echo "done."