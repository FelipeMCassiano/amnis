FROM openjdk:21-jdk-slim AS build

WORKDIR /app
COPY . /app
RUN ./mvnw package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8082
CMD ["java", "-jar", "app.jar"]
