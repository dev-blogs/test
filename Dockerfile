FROM openjdk:11

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY target/standalone-test-app-0.0.1-SNAPSHOT.jar /usr/src/app

CMD ["java", "-jar", "standalone-test-app-0.0.1-SNAPSHOT.jar"]
