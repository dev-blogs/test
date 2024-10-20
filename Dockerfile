FROM openjdk:11

RUN mkdir -p /usr/src/app #выполнить эту команду
WORKDIR /usr/src/app #переходим в текущий каталог и начанаются выполняться все команды с указанного каталога

COPY target/webserver-0.0.1-SNAPSHOT.jar /usr/src/app #скопировать из текущей папки локальной машины в папку на контейнере

EXPOSE 8080

CMD ["java", "-jar", "webserver-0.0.1-SNAPSHOT.jar"] #что нужно делать когда запустим контейнер