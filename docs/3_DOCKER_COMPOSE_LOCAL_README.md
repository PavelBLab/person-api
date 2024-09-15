## Docker Compose for Local Environment

This project uses Docker Compose to automatically run a PostgreSQL database when the Spring Boot application starts and stop when it is down. The configuration is defined in the `docker-compose-local.yml` file, and the integration with Spring Boot is achieved by adding the following settings in `application.yml`:

```yaml
spring:
  docker:
    compose:
      enabled: true
      file: docker-compose-local.yml
      lifecycle-management: start-and-stop
```
Required Dependency

To enable the automatic management of Docker Compose within the Spring Boot application, ensure that the following dependency is added to your `pom.xml` file:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-docker-compose</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

Docker Compose Configuration
1. File Location: The compose.yaml file must be placed in the root folder of your project. This file contains the configuration for running PostgreSQL.
2. PostgreSQL Setup: The database runs on PostgreSQL version 16.4-alpine and will be exposed on localhost:9000. The data is persisted in a Docker volume (postgres_data) to ensure it is not lost across restarts.
3. abase Initialization: A SQL initialization script (init-person-db.sql) located in the ./dev-scripts/ folder is executed when the PostgreSQL container starts. This script can be used to set up the database schema and insert initial data.

Application Behavior

When you start the Spring Boot application, the following will happen:
1. Automatic Database Startup: Spring Boot will automatically manage and start the PostgreSQL container using Docker Compose.
2. Port Mapping: PostgreSQL will be accessible at localhost:5434, with the container’s internal port mapped from 5432.
3. Persistent Data: Any data generated or modified during the runtime will be saved in the postgres_data volume.
4. Database Initialization: If the init-person-db.sql script exists, it will run during container startup, setting up your database with the predefined schema and data.

Important Notes
1. This setup is for local development and is intended to make it easier to work with a database without manually managing containers.
2. Ensure that both Docker and Docker Compose are installed and running on your machine.
3. You do not need to manually run Docker commands—Spring Boot will manage the lifecycle of the PostgreSQL container.