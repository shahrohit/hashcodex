# -------------------------------
# Stage 1: Build
# -------------------------------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src

RUN ./gradlew clean bootJar -x test


# -------------------------------
# Stage 2: Runtime
# -------------------------------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy built JAR
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "app.jar"]