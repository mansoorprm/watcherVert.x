FROM openjdk:8-jdk-slim
COPY target/finOSWatcher-jar-with-dependencies.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]