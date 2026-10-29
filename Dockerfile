# ==========================================
# STAGE 1: Build the application using Maven
# ==========================================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /projectGEMC

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Package the application (skipping tests to speed up builds)
RUN mvn clean package -DskipTests

# ==========================================
# STAGE 2: Run the application with a slim JRE
# ==========================================
FROM eclipse-temurin:17-jre-jammy
WORKDIR /projectGEMC

# Copy only the compiled JAR file from the build stage
COPY --from=build /app/target/*.jar projectGEMC.jar

# Render or other platforms use dynamic ports, but 8080 is standard
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "projectGEMC.jar"]