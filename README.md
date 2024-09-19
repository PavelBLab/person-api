# **Person API**

The **Person API** is a RESTful service that allows managing person-related data stored in the system. This API provides endpoints to interact with the data of individuals such as retrieving a list of persons, creating a new person, updating existing records, or deleting a person. It's designed for flexibility, making it easy to integrate into applications for use cases like data visualization, reporting, or system integration.

---

## **Application Description**

The **Person Management System** offers a centralized way to manage person-related data with fields such as names, addresses, country of origin, and job titles. The system can be used in various contexts, including:

- Displaying profiles or contact information in user interfaces
- Populating data for reporting or analytics
- Integrating person-related data with external systems (e.g., CRM, HR systems)
- Managing and updating personal details efficiently

The API is built to be intuitive, secure, and scalable for both small and large datasets.

---
## **Project Folder Structure**

Here is an overview of the project's folder structure:

```
├── Dockerfile
├── HELP.md
├── README.md
├── dev-scripts
│   └── init-person-db.sql
├── docker-compose-dev.yml
├── docker-compose-docker.yml
├── docker-compose-local.yml
├── docs
│   ├── 1_RUN_DOCKERFILE_README.md
│   ├── 2.1_DOCKER_COMPOSE_LOCAL_README.md
│   ├── 2.2_DOCKER_COMPOSE_DOCKER_README.md
│   ├── 2.3_DOCKER_COMPOSE_AND_KUBERNETES_README.md
│   ├── 2.4_KUBERNETES_README.md
│   ├── 3_HPA.md
├── helm
│   └── abnamro-person-api-chart
│       ├── Chart.yaml
│       ├── charts
│       │   └── postgres-chart
│       │       ├── Chart.yaml
│       │       ├── templates
│       │       │   ├── deployment.yaml
│       │       │   ├── persistent-volume-claim.yaml
│       │       │   ├── persistent-volume.yaml
│       │       │   ├── service.yaml
│       │       │   ├── storage-class.yaml
│       │       └── values.yaml
│       ├── templates
│       │   ├── config-map.yaml
│       │   ├── deployment.yaml
│       │   ├── hpa.yaml
│       │   ├── limit-range.yaml
│       │   ├── registry.yaml
│       │   ├── secret.yaml
│       │   ├── service.yaml
│       ├── values-dev.yaml
│       ├── values-test.yaml
│       └── values.yaml
├── load-testing
│   ├── load_test_dev.js
│   └── load_test_test.js
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── testcase
│   │   │           └── api
│   │   │               └── persons
│   │   │                   ├── PersonsApplication.java
│   │   │                   ├── configurations
│   │   │                   │   └── HealthCheckIndicatorConfiguration.java
│   │   │                   ├── controllers
│   │   │                   │   └── PersonController.java
│   │   │                   ├── converters
│   │   │                   │   └── CountryCodeConverter.java
│   │   │                   ├── exceptions
│   │   │                   │   ├── GlobalExceptionHandler.java
│   │   │                   │   └── PersonNotFoundException.java
│   │   │                   ├── mappers
│   │   │                   │   └── PersonMapper.java
│   │   │                   ├── persistence
│   │   │                   │   ├── entities
│   │   │                   │   │   └── Person.java
│   │   │                   │   └── repositories
│   │   │                   │       ├── PersonRepository.java
│   │   │                   │       └── specifications
│   │   │                   │           └── PersonSpecification.java
│   │   │                   ├── services
│   │   │                   │   └── PersonService.java
│   │   │                   └── validators
│   │   │                       └── GenderValidator.java
│   │   └── resources
│   │       ├── application-dev.yml
│   │       ├── application-docker.yml
│   │       ├── application-local.yml
│   │       ├── application-test.yml
│   │       ├── application.yml
│   │       ├── liquibase
│   │       │   ├── changelogs
│   │       │   │   ├── 0.1.0
│   │       │   │   │   └── db.changelog-v0.1.0-init-person.sql
│   │       │   │   └── 0.1.2
│   │       │   │       └── db.changelog-v0.1.2-alter-person.sql
│   │       │   └── master.yml
│   │       └── open-api
│   │           └── provider
│   │               └── person-api.yml
│   └── test
│       ├── java
│       │   └── com
│       │       └── testcase
│       │           └── api
│       │               └── persons
│       │                   ├── integration
│       │                   │   ├── IntegrationTest.java
│       │                   │   └── PersonsCrudIT.java
│       │                   └── utils
│       │                       └── TestDataFactory.java
│       └── resources
│           ├── application-it.yml
│           └── init-db.sql
```

---
## **Endpoints**

### **1. Retrieve All Persons**

**`GET /persons`**

Returns a list of all persons available in the database. Optional filters can be applied using query parameters.

#### **Query Parameters:**
- **`personNameParam`** (optional) - Filter by the person's first name.
- **`personSurnameParam`** (optional) - Filter by the person's last name.

#### **Success Response:**
- **200 OK**: Returns a list of persons.

---

### **2. Retrieve a Person by ID**

**`GET /persons/{personIdParam}`**

Fetches detailed information about a specific person by their unique ID.

#### **Path Parameters:**
- **`personIdParam`** (required) - The unique ID of the person.

#### **Success Response:**
- **200 OK**: Returns the person's details.

---

### **3. Create a New Person**

**`POST /persons`**

Creates a new person record in the database.

#### **Request Body:**
- **Required Fields:**
  - **`name`**: First name of the person.
  - **`surname`**: Last name of the person.
  - **`dateOfBirth`**: Date of birth (YYYY-MM-DD).
  - **`address`**: Residential address.
  - **`country`**: Country of origin (Enum: US, DE, NL, CY, IT, UK, OTHER).
  - **`jobTitle`**: Job title of the person.

#### **Success Response:**
- **201 Created**: The person was created successfully.

---

### **4. Update a Person by ID**

**`PATCH /persons/{personIdParam}`**

Allows updating a specific person identified by their unique ID.

#### **Path Parameters:**
- **`personIdParam`** (required) - The unique ID of the person.

#### **Request Body:**
- Fields to update (e.g., `name`, `surname`, `jobTitle`).

#### **Success Response:**
- **200 OK**: The person was updated successfully.

---

### **5. Delete a Person by ID**

**`DELETE /persons/{personIdParam}`**

Deletes a person identified by their unique ID. This operation cannot be undone.

#### **Path Parameters:**
- **`personIdParam`** (required) - The unique ID of the person.

#### **Success Response:**
- **204 No Content**: The person was deleted successfully.

---

## **Common Error Responses**

All endpoints can return error responses when the request is invalid or if the operation fails due to authentication or server issues. The following are common errors across the API:

#### **Error Responses:**
- **400 Bad Request:** Invalid request parameters.
- **401 Unauthorized:** Authentication required.
- **403 Forbidden:** Insufficient permissions to perform the operation.
- **404 Not Found:** The requested resource (e.g., person) was not found.
- **500 Internal Server Error:** An unexpected server error occurred.

Error responses will follow this format:

```json
{
  "code": 400,
  "message": "Validation failed",
  "errors": {
    "surname": "must not be null",
    "name": "must not be null"
  }
}
```

---

## **Database SQL Structure**

The **Person API** stores data in a relational database. The table `person.person` contains detailed information about each person, with the following structure:

```sql
CREATE TABLE IF NOT EXISTS person.person(
    id            UUID         NOT NULL CONSTRAINT PK_person PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    surname       VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    address       VARCHAR(255),
    country       VARCHAR(2) NOT NULL,
    job_title     VARCHAR(255) NOT NULL,
    annual_salary NUMERIC(19, 2),
    employer      VARCHAR(255),
    gender        VARCHAR(255) NOT NULL DEFAULT 'NA'
);
```

### **Table Description:**

- **`id`**: Unique identifier (UUID) for each person, which serves as the primary key.
- **`name`**: The first name of the person (required).
- **`surname`**: The last name of the person (required).
- **`date_of_birth`**: The person's date of birth (required, in `YYYY-MM-DD` format).
- **`address`**: Residential address of the person (optional).
- **`country`**: Country of origin, represented by a two-letter country code (required).
- **`job_title`**: The person's job title or role (required).
- **`annual_salary`**: Annual salary of the person, stored as a numeric value with two decimal places (optional).
- **`employer`**: Name of the person's employer or organization (optional).
- **`gender`**: Gender of the person, with a default value of `'NA'` (required).