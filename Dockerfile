FROM openjdk:11-jre-slim

COPY /build/lib/demo.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]