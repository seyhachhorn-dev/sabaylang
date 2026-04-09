FROM gradle:8.5-jdk17 AS builder

WORKDIR /app
COPY . .

# Build the 'app' sub-project specifically
RUN ./gradlew :app:bootJar --no-daemon

# 2️⃣ Run stage
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# The jar is located in the 'app' sub-project's build directory
COPY --from=builder /app/app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
