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