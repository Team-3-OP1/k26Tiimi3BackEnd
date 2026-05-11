# ============================
# Stage 1: Build
# ============================
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Copy Maven wrapper and pom first (for layer caching)
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Download dependencies (cached if pom.xml unchanged)
RUN ./mvnw dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN ./mvnw package -DskipTests -B

# ============================
# Stage 2: Runtime
# ============================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# OpenShift runs containers as a random non-root UID.
# Group 0 (root group) gives that UID access to files.
RUN chown -R 1001:0 /app && chmod -R g=u /app

# Copy the built jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Use non-root user (required for OpenShift / Rahti)
USER 1001

# Spring Boot default port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]