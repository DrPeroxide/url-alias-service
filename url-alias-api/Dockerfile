FROM eclipse-temurin:21-jdk-jammy AS build
COPY . .
RUN --mount=type=cache,target=/root/.m2 ./mvnw clean package

FROM eclipse-temurin:21 AS run
ARG JAR_FILE=target/*.jar
COPY --from=build ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
