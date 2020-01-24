FROM maven:3.6.3-jdk-11

WORKDIR /app

COPY target/mementor.jar .

EXPOSE 8080

CMD ["java", "-jar", "mementor.jar"]
