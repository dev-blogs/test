FROM openjdk:11

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY target/webserver-0.0.1-SNAPSHOT.jar /usr/src/app

EXPOSE 8080

CMD ["java", "-jar", "webserver-0.0.1-SNAPSHOT.jar"]