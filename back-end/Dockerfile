FROM azul/zulu-openjdk:11

WORKDIR /app

COPY target/guru-back-0.0.1-SNAPSHOT.war .

CMD ["apk add ca-certificates", "java", "-jar", "guru-back-0.0.1-SNAPSHOT.war"]