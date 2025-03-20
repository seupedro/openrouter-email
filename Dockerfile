# Stage 1: Build the application (both JAR and native)
FROM gradle:8.7-jdk21 AS builder

WORKDIR /app
COPY gradle gradle
COPY gradlew build.gradle settings.gradle ./
COPY src src

# Download dependencies first (better layer caching)
RUN ./gradlew dependencies

# Build JAR
RUN ./gradlew bootJar

# Build native image
RUN ./gradlew nativeCompile

# Stage 2: Native image runtime
FROM ubuntu:22.04 AS native

WORKDIR /app
COPY --from=builder /app/build/native/nativeCompile/openrouter-email ./application

EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["./application"]

# Stage 3: JVM runtime
FROM eclipse-temurin:21-jre-jammy AS jvm

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar ./application.jar

EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "application.jar"]

# Default to JVM version (can be overridden at build time with --target)
FROM jvm
