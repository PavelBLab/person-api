# Project Documentation

### 1. API Documentation

The API documentation is generated automatically by the [OpenAPI Generator](https://openapi-generator.tech/docs/usage/). To generate the documentation, run the following Maven command:

```bash
mvn clean compile
```

You can find the generated documentation in the following directory:  
`person-api/target/generated-docs`

### 2. Accessing Swagger Documentation

To view the Swagger API documentation, navigate to the following URL in your web browser:

```
http://localhost:{port}/person-api-documentation
```

### 3. Additional Documentation

For further documentation related to Docker, including Dockerfile, Docker Compose, and Horizontal Pod Autoscaler (HPA) configurations, please refer to the `docs` folder in the project repository.

### 4. Database SQL Structure

To view the database SQL structure, you can find the relevant files in the following directory:

```
src/main/resources/liquibase
```

This directory contains the Liquibase migration scripts that define the database schema and structure.

## **Project Folder Structure**

Here is an overview of the project's folder structure:

```
├── dev-scripts        - contains SQL scripts for database initialization
├── docs               - contains the project documentation
├── helm               - contains the charts for Kubernetes deployments
├── load-testing       - contains the K6 scripts used for testing the Horizontal Pod Autoscaler
├── src                - contains the source code for the Java application
```