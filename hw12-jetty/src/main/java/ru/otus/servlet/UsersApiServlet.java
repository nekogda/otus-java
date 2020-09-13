package ru.otus.servlet;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;


public class UsersApiServlet extends HttpServlet {
    public static final Logger logger = LoggerFactory.getLogger(UsersApiServlet.class);

    private static final int ID_PATH_PARAM_POSITION = 1;

    private final DBServiceUser serviceUser;
    private final Gson gson;

    public UsersApiServlet(DBServiceUser serviceUser, Gson gson) {
        this.serviceUser = serviceUser;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        String jsonBody;

        if (request.getPathInfo() == null || request.getPathInfo().equals("/")) {
            logger.info("getting all users");
            var all = serviceUser.getUsers();
            jsonBody = gson.toJson(all);
        } else {
            long id = extractIdFromRequest(request);
            logger.info("getting user with id={}", id);
            User user = serviceUser.getUser(id).orElse(null);
            jsonBody = gson.toJson(user);
        }

        out.print(jsonBody);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("inserting new user");

        var body = req.getReader().lines().collect(Collectors.joining());
        var user = gson.fromJson(body, User.class);

        user.getPhones().forEach(phone -> phone.setUser(user));

        String responseBody;
        try {
            long id = serviceUser.saveUser(user);
            responseBody = gson.toJson(Map.of("status", "created", "id", id));
        } catch (DbServiceException e) {
            responseBody = gson.toJson(Map.of("status", "error", "cause", e.getMessage()));
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        resp.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = resp.getOutputStream();
        out.print(responseBody);
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1) ? path[ID_PATH_PARAM_POSITION] : String.valueOf(-1);
        return Long.parseLong(id);
    }

}
