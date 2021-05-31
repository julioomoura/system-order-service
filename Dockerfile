FROM maven:3.8.1-jdk-11 AS MAVEN_BUILD
COPY ./ ./
RUN mvn clean package -DskipTests
FROM adoptopenjdk/openjdk11:ubi
COPY --from=MAVEN_BUILD /target/sos-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar"]