FROM openjdk:17

VOLUME /tmp
EXPOSE 8080

COPY target/code-mastery-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
