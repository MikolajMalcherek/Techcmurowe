FROM openjdk:21-jdk-slim
LABEL authors="malch"

ARG JAR_FILE=target/Techcmurowe-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

COPY src/main/resources/keystore.p12 /app/keystore.p12

ENTRYPOINT ["java", "-jar", "/app.jar"]

# Expose port 443 dla HTTPS
EXPOSE 443
EXPOSE 8081