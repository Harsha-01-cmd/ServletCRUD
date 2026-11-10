package in.HarshaRashmi.service;

import in.HarshaRashmi.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserService — business logic layer for User CRUD operations.
 *
 * Uses an in-memory HashMap as a simple data store instead of a database.
 * Keys are user IDs (Integer), values are User objects.
 * Data is lost when the servlet container restarts.
 */
public class UserService {

    /** In-memory storage: maps user ID to User object. */
    private Map<Integer, User> userDB;

    /** Constructor — initializes the HashMap. */
    public UserService() {
        userDB = new HashMap<>();
    }

    /** Creates a new user and stores it in the in-memory map. */
    public User createUser(User userReq) {
        userDB.put(userReq.getId(), userReq);
        return userReq;
    }

    /** Returns all users stored in the map. */
    public List<User> getAllUsers() {
        List<User> usersResp = new ArrayList<>();
        for (User user : userDB.values()) {
            usersResp.add(user);
        }
        return usersResp;
    }

    /** Finds a user by ID, or returns null if not found. */
    public User getUserById(Integer id) {
        return userDB.getOrDefault(id, null);
    }
}
