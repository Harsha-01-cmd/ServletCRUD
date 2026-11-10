# Servlet Lifecycle & Key Concepts

## Servlet Lifecycle

The servlet container (Tomcat, Jetty) manages the servlet lifecycle in 4 phases:

| Phase | Method | When Called | How Many Times |
|-------|--------|-------------|----------------|
| 1. Instantiation | Constructor | When the servlet is first loaded | Once |
| 2. Initialization | `init()` | After construction | Once |
| 3. Service | `doGet()`, `doPost()`, etc. | For each HTTP request | Many times |
| 4. Destruction | `destroy()` | When the servlet is removed | Once |

## @WebServlet Annotation

`@WebServlet` replaces the traditional `web.xml` configuration.

```java
@WebServlet("/hello")  // Maps this servlet to the URL path /hello
public class HelloServlet extends HttpServlet {
```

Full URL when deployed: `http://localhost:8080/crud-app/hello`

- `http://localhost:8080` — Tomcat server
- `/crud-app` — context path (WAR file name)
- `/hello` — servlet mapping

## HTTP Methods in Servlets

| HTTP Method | Servlet Method | Use Case |
|-------------|---------------|----------|
| `GET` | `doGet()` | Read data |
| `POST` | `doPost()` | Create data |
| `PUT` | `doPut()` | Update data |
| `DELETE` | `doDelete()` | Delete data |
| `PATCH` | `doPatch()` | Partial update |

## HttpServletRequest

Represents the client's HTTP request. Common methods:

| Method | Purpose |
|--------|---------|
| `getParameter("name")` | Gets a query parameter or form field value |
| `getHeader("Content-Type")` | Gets an HTTP header value |
| `getMethod()` | Returns the HTTP method (GET, POST, etc.) |

## HttpServletResponse

Represents the server's HTTP response. Common methods:

| Method | Purpose |
|--------|---------|
| `setStatus(200)` | Sets the HTTP status code |
| `setContentType("application/json")` | Sets the response Content-Type header |
| `getWriter().write(...)` | Writes the response body |

## In-Memory Storage

This demo uses `HashMap<Integer, User>` instead of a database:

- **Pros**: No database setup needed, fast for learning
- **Cons**: Data is lost when the server restarts, not thread-safe for production

## Manual JSON Serialization

Without Jackson or Gson, JSON is built using string concatenation:

```java
private String userToJson(User user) {
    return "{\n" +
            "    \"id\" : " + user.getId() + ",\n" +
            "    \"name\" : \"" + user.getName() + "\"\n" +
            "}";
}
```

In production, use a JSON library like Jackson for safety and convenience.

## Complete CRUD Flow

```
Client (Browser/Thunder Client)
    ↓ HTTP Request
Servlet Container (Tomcat)
    ↓ Routes by @WebServlet mapping
UserServlet.doPost() / doGet() / doPut() / doDelete()
    ↓ Delegates to
UserService.createUser() / getAllUsers() / updateUser() / deleteUser()
    ↓ In-memory storage
HashMap<Integer, User>
    ↓ HTTP Response
Client receives JSON
```

## WAR Deployment

1. Build: `mvn clean package` → produces `target/crud-app.war`
2. Deploy: Copy `crud-app.war` to Tomcat's `webapps/` directory
3. Start Tomcat → the WAR is auto-extracted and deployed
4. Access: `http://localhost:8080/crud-app/`

## Testing with Thunder Client

| Method | URL | Parameters |
|--------|-----|-----------|
| `POST` | `http://localhost:8080/crud-app/users` | id=1, name=John, email=john@example.com, mobile=1234567890 |
| `GET` | `http://localhost:8080/crud-app/users` | (none — returns all users) |
| `GET` | `http://localhost:8080/crud-app/users?id=1` | id=1 |
| `PUT` | `http://localhost:8080/crud-app/users` | id=1, name=Jane, email=jane@example.com, mobile=0987654321 |
| `DELETE` | `http://localhost:8080/crud-app/users?id=1` | id=1 |
