package in.HarshaRashmi.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * HelloServlet — a simple servlet to demonstrate the servlet lifecycle.
 *
 * @WebServlet("/hello") maps this servlet to the URL path "/hello".
 * When a GET request hits /hello, it returns plain text "Hello".
 *
 * Servlet lifecycle methods:
 *   - Constructor: called when the servlet instance is created.
 *   - init(): called once after construction to initialize the servlet.
 *   - doGet(): called for each GET request.
 *   - destroy(): called once when the servlet is being removed.
 */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    /** Constructor — called when the servlet container creates this servlet. */
    public HelloServlet() {
        System.out.println("HelloServlet Constructor called");
    }

    /** init() — called once after construction to initialize the servlet. */
    @Override
    public void init() {
        System.out.println("init() method called");
    }

    /**
     * doGet() — handles GET requests to /hello.
     * Returns plain text "Hello" with HTTP 200 status.
     */
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        response.setStatus(200);
        response.setContentType("text/plain");
        response.getWriter().write("Hello");
    }

    /** destroy() — called once when the servlet is being removed from service. */
    @Override
    public void destroy() {
        System.out.println("Destroy method called");
    }
}
