FROM maven:3.6.3-jdk-11

WORKDIR /build

# install node
RUN \
  wget -O /opt/node-v13.5.0-linux-x64.tar.gz https://nodejs.org/dist/v13.5.0/node-v13.5.0-linux-x64.tar.gz \
  && tar -xzf /opt/node-v13.5.0-linux-x64.tar.gz -C /opt \
  && rm /opt/node-v13.5.0-linux-x64.tar.gz \
  && ln -s /opt/node-v13.5.0-linux-x64 /opt/node
ENV PATH $PATH:/opt/node/bin

# install dependencies
COPY front-end/*.json front-end/
RUN npm --no-color --prefix front-end install
COPY pom.xml .
RUN mvn dependency:go-offline

# build
COPY . .
RUN npm --no-color --prefix front-end run build
RUN mvn package -B -o -DskipTests

# clean up build artifacts
WORKDIR /app
RUN mv /build/target/mementor.jar /app/
RUN rm -rf /build

EXPOSE 8080

CMD ["java", "-jar", "mementor.jar"]
