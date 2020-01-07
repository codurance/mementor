FROM maven:3.6.3-jdk-11

WORKDIR /app

RUN \
  wget -O /opt/node-v13.5.0-linux-x64.tar.gz https://nodejs.org/dist/v13.5.0/node-v13.5.0-linux-x64.tar.gz \
  && tar -xzf /opt/node-v13.5.0-linux-x64.tar.gz -C /opt \
  && rm /opt/node-v13.5.0-linux-x64.tar.gz \
  && ln -s /opt/node-v13.5.0-linux-x64 /opt/node

ENV PATH $PATH:/opt/node/bin

COPY . .

WORKDIR /app/front-end
RUN npm install
RUN npm run build

WORKDIR /app
RUN mvn package

EXPOSE 8080

CMD ["java", "-jar", "target/mementor.jar"]
