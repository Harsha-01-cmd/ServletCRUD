package in.HarshaRashmi.servlet;

import in.HarshaRashmi.model.User;
import in.HarshaRashmi.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * UserServlet — handles HTTP requests for User CRUD operations.
 *
 * @WebServlet("/users") maps this servlet to the URL path "/users".
 * Routes requests based on HTTP method:
 *   - POST /users → create a new user
 *   - GET /users → get all users
 *   - GET /users?id=1 → get user by ID
 *   - PUT /users → update a user (not yet implemented)
 *   - DELETE /users → delete a user (not yet implemented)
 *
 * Manually serializes User objects to JSON strings since no Jackson library is used.
 */
@WebServlet("/users")
public class UserServlet extends HttpServlet {

    /** Service layer instance for business logic. */
    private UserService userService = new UserService();

    /**
     * doPost() — creates a new user.
     *
     * Expects form parameters: id, name, email, mobile.
     * Returns 201 Created on success, 400 Bad Request if fields are missing.
     */
    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        // Set response content type for JSON
        response.setContentType("application/json");

        Integer id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");

        // Validate that all required fields are present
        if (id == null || email == null ||
                name == null || mobile == null) {
            response.setStatus(400);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\n" +
                            "    \"message\" : \"Some fields are missing\"\n" +
                            "}"
            );
            return;
        }

        User user = new User(id, name, email, mobile);
        User createdUser = userService.createUser(user);

        response.setStatus(201);
        response.setContentType("application/json");
        response.getWriter().write(
                "{\n" +
                        "    \"message\" : \"User Added successfully\"\n" +
                        "}"
        );
    }

    /**
     * doGet() — retrieves users.
     *
     * If no "id" parameter is provided, returns all users.
     * If "id" parameter is provided, returns the specific user.
     * Returns 404 if the requested user is not found.
     */
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        String idParam = request.getParameter("id");

        // No ID parameter → return all users
        if (idParam == null) {
            List<User> users = userService.getAllUsers();
            response.setStatus(200);
            response.setContentType("application/json");
            response.getWriter().write(usersToJson(users));
            return;
        }

        // ID parameter provided → return single user
        Integer id = Integer.parseInt(idParam);
        User userResp = userService.getUserById(id);

        if (userResp == null) {
            response.setStatus(404);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\n" +
                            "    \"message\" : \"User not found\"\n" +
                            "}"
            );
            return;
        }

        response.setStatus(200);
        response.setContentType("application/json");
        response.getWriter().write(userToJson(userResp));
    }

    /**
     * doPut() — updates an existing user.
     *
     * Expects form parameters: id, name, email, mobile.
     * Returns 200 OK on success, 404 if user not found.
     */
    @Override
    public void doPut(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        Integer id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");

        User existingUser = userService.getUserById(id);

        if (existingUser == null) {
            response.setStatus(404);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\n" +
                            "    \"message\" : \"User not found\"\n" +
                            "}"
            );
            return;
        }

        // Update the fields
        existingUser.setName(name);
        existingUser.setEmail(email);
        existingUser.setMobile(mobile);

        response.setStatus(200);
        response.setContentType("application/json");
        response.getWriter().write(
                "{\n" +
                        "    \"message\" : \"User updated successfully\"\n" +
                        "}"
        );
    }

    /**
     * doDelete() — deletes a user by ID.
     *
     * Expects query parameter: id.
     * Returns 200 OK on success, 404 if user not found.
     */
    @Override
    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        String idParam = request.getParameter("id");

        if (idParam == null) {
            response.setStatus(400);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\n" +
                            "    \"message\" : \"ID parameter is required\"\n" +
                            "}"
            );
            return;
        }

        Integer id = Integer.parseInt(idParam);
        User existingUser = userService.getUserById(id);

        if (existingUser == null) {
            response.setStatus(404);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\n" +
                            "    \"message\" : \"User not found\"\n" +
                            "}"
            );
            return;
        }

        // Remove the user from the in-memory store
        userService.deleteUser(id);

        response.setStatus(200);
        response.setContentType("application/json");
        response.getWriter().write(
                "{\n" +
                        "    \"message\" : \"User deleted successfully\"\n" +
                        "}"
        );
    }

    /**
     * Converts a single User object to a JSON string.
     * Manual serialization since no JSON library (Jackson/Gson) is used.
     */
    private String userToJson(User user) {
        return "{\n" +
                "    \"id\" : " + user.getId() + ",\n" +
                "    \"name\" : \"" + user.getName() + "\",\n" +
                "    \"email\" : \"" + user.getEmail() + "\",\n" +
                "    \"mobile\" : \"" + user.getMobile() + "\"\n" +
                "}";
    }

    /**
     * Converts a list of User objects to a JSON array string.
     * Iterates over the list, converts each user, and joins with commas.
     */
    private String usersToJson(List<User> users) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        for (int i = 0; i < users.size(); i++) {
            stringBuilder.append(userToJson(users.get(i)));

            // Add comma between objects, but not after the last one
            if (i < users.size() - 1) {
                stringBuilder.append(",");
            }
        }

        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
