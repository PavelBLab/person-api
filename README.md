# Person API

The **Person API** is a RESTful service that allows managing person-related data stored in the system. This API provides endpoints to interact with the data of individuals such as retrieving a list of persons, creating a new person, updating existing records, or deleting a person. It's designed for flexibility, making it easy to integrate into applications for use cases like data visualization, reporting, or system integration.

## Application Description

The **Person Management System** offers a centralized way to manage person-related data with fields such as names, addresses, country of origin, and job titles. The system can be used in various contexts, including:
- Displaying profiles or contact information in user interfaces
- Populating data for reporting or analytics
- Integrating person-related data with external systems (e.g., CRM, HR systems)
- Managing and updating personal details efficiently

The API is built to be intuitive, secure, and scalable for both small and large datasets.

---

## Endpoints

### 1. Retrieve All Persons
**`GET /persons`**

Returns a list of all persons available in the database. Optional filters can be applied using query parameters.

#### Query Parameters:
- `personNameParam` (optional) - Filter by the person's first name.
- `personSurnameParam` (optional) - Filter by the person's last name.

#### Success Response:
- **200 OK**: Returns a list of persons.

### 2. Retrieve a Person by ID
**`GET /persons/{personIdParam}`**

Fetches detailed information about a specific person by their unique ID.

#### Path Parameters:
- `personIdParam` (required) - The unique ID of the person.

#### Success Response:
- **200 OK**: Returns the person's details.

### 3. Create a New Person
**`POST /persons`**

Creates a new person record in the database.

#### Request Body:
- **Required Fields**:
    - `name`: First name of the person.
    - `surname`: Last name of the person.
    - `dateOfBirth`: Date of birth (YYYY-MM-DD).
    - `address`: Residential address.
    - `country`: Country of origin (Enum: US, DE, NL, CY, IT, UK, OTHER).
    - `jobTitle`: Job title of the person.

#### Success Response:
- **201 Created**: The person was created successfully.

### 4. Update a Person by ID
**`PATCH /persons/{personIdParam}`**

Allows updating a specific person identified by their unique ID.

#### Path Parameters:
- `personIdParam` (required) - The unique ID of the person.

#### Request Body:
- Fields to update (e.g., `name`, `surname`, `jobTitle`).

#### Success Response:
- **200 OK**: The person was updated successfully.

### 5. Delete a Person by ID
**`DELETE /persons/{personIdParam}`**

Deletes a person identified by their unique ID. This operation cannot be undone.

#### Path Parameters:
- `personIdParam` (required) - The unique ID of the person.

#### Success Response:
- **204 No Content**: The person was deleted successfully.

---

## Common Error Responses

All endpoints can return error responses when the request is invalid or if the operation fails due to authentication or server issues. The following are common errors across the API:

#### Error Responses:
- **400 Bad Request**: Invalid request parameters.
- **401 Unauthorized**: Authentication required.
- **403 Forbidden**: Insufficient permissions to perform the operation.
- **404 Not Found**: The requested resource (e.g., person) was not found.
- **500 Internal Server Error**: An unexpected server error occurred.

Error responses will follow this format:

```json
{
  "code": 400,
  "message": "Invalid request parameters."
}
```

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

