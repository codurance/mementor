#!/bin/bash

echo "#################################"
echo "##### Cleaning the project #####"
echo "#################################"

echo "deleting front- end/build .."
rm -rf front-end/build
echo "deleting target .."
rm -rf target
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
echo "##### Packaging AWS artifact #####"
echo "#################################"

echo "zipping artifact .."
zip target/mementor-aws.zip target/mementor.jar Dockerfile
echo "done."
