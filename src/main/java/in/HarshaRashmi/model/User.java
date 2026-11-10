package in.HarshaRashmi.model;

/**
 * User model — represents a user record in the CRUD application.
 *
 * This is a plain Java object (POJO) with four fields: id, name, email, and mobile.
 * No annotations are needed because this is a simple servlet app without JPA/Hibernate.
 * The UserService stores User objects in an in-memory HashMap.
 */
public class User {
    private Integer id;
    private String name;
    private String email;
    private String mobile;

    /** Constructor — creates a new User with all fields. */
    public User(Integer id, String name, String email, String mobile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /** Returns a string representation of the User object. */
    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "', mobile='" + mobile + "'}";
    }
}
