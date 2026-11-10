# Servlet CRUD Demo

A Jakarta Servlet-based CRUD application for managing User records, deployed as a WAR file to a servlet container like Apache Tomcat.

## Features

- Create users via POST request
- Retrieve all users or a single user by ID via GET request
- Servlet lifecycle demonstration (HelloServlet)
- In-memory data storage using HashMap
- Manual JSON serialization (no external JSON library)

## Tech Stack

- **Jakarta Servlet 6.1** — `@WebServlet`, `HttpServlet`, request/response handling
- **Maven** — build tool with WAR packaging
- **Java 23** — compiler source and target
- **Apache Tomcat** (or any Jakarta EE 10+ container) — servlet runtime

## Prerequisites

- Java 23+
- Maven
- Apache Tomcat 10.x+ (or any Jakarta EE 10+ servlet container)

## Setup

1. Build the WAR file:
   ```bash
   mvn clean package
   ```

2. Copy `target/crud-app.war` to your Tomcat `webapps/` directory.

3. Start Tomcat and access the application at `http://localhost:8080/crud-app`.

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/crud-app/hello` | Servlet lifecycle demo — returns "Hello" |
| `POST` | `/crud-app/users` | Create a new user |
| `GET` | `/crud-app/users` | Get all users |
| `GET` | `/crud-app/users?id=1` | Get user by ID |
| `PUT` | `/crud-app/users` | Update an existing user |
| `DELETE` | `/crud-app/users?id=1` | Delete a user by ID |

## Example Request

**Create a user:**

```
POST /crud-app/users
Parameters: id=1&name=John+Doe&email=john@example.com&mobile=1234567890
```

**Response:** `201 Created`
```json
{
    "message" : "User Added successfully"
}
```

**Get all users:**

```
GET /crud-app/users
```

**Response:** `200 OK`
```json
[
    {
        "id" : 1,
        "name" : "John Doe",
        "email" : "john@example.com",
        "mobile" : "1234567890"
    }
]
```

## Project Structure

```
src/main/java/in/HarshaRashmi/
├── Main.java                    # Main entry point (empty — container manages lifecycle)
├── package-info.java            # Package documentation
├── model/
│   └── User.java                # User POJO (id, name, email, mobile)
├── service/
│   └── UserService.java         # Business logic with in-memory HashMap storage
└── servlet/
    ├── HelloServlet.java        # Simple servlet demonstrating lifecycle methods
    └── UserServlet.java         # CRUD endpoints for User operations
```

## Key Concepts

- **@WebServlet**: Maps a servlet class to a URL path without web.xml configuration.
- **Servlet Lifecycle**: Constructor → init() → service (doGet/doPost) → destroy().
- **In-memory storage**: Uses HashMap instead of a database; data is lost on restart.
- **Manual JSON**: Serializes objects to JSON strings without Jackson or Gson.
