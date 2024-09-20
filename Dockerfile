# Use Eclipse Temurin 21 base image
FROM eclipse-temurin:21 AS build

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set build directory
ENV BUILD_DIR=/app-build

# Create and set the working directory
RUN mkdir -p $BUILD_DIR
WORKDIR $BUILD_DIR

# Add the project files to the build directory
ADD . $BUILD_DIR

# Add a Maven settings.xml and settings-security.xml to configure access
ADD root/.m2/settings.xml /root/.m2/settings.xml
ADD root/.m2/settings-security.xml /root/.m2/settings-security.xml

# Run Maven package with verbose output
RUN mvn -f $BUILD_DIR/pom.xml package -X

# Use the Eclipse Temurin base image for the runtime
FROM eclipse-temurin:21

# Copy the built jar file from the build stage
COPY --from=build /app-build/target/*.jar /app/application.jar

# Use JSON array for ENTRYPOINT to handle signals properly
ENTRYPOINT ["java", "-jar", "/app/application.jar"]