
FROM eclipse-temurin:21 AS build
ENV BUILD_DIR=/app-build
RUN mkdir -p $BUILD_DIR
WORKDIR $BUILD_DIR
ADD . $BUILD_DIR
RUN ./mvnw -f $BUILD_DIR/pom.xml package

FROM eclipse-temurin:21
COPY --from=build /app-build/target/*.jar /app/application.jar
EXPOSE 8080
ENTRYPOINT java -jar /app/application.jar