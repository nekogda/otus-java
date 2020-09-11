package ru.otus.servlet;

import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminsServlet extends HttpServlet {

    private static final String ADMIN_PAGE_TEMPLATE = "admins.html";
    private static final String TEMPLATE_ATTR_ADMINS = "dataSet";

    private final DBServiceUser serviceUser;
    private final TemplateProcessor templateProcessor;

    public AdminsServlet(TemplateProcessor templateProcessor, DBServiceUser serviceUser) {
        this.templateProcessor = templateProcessor;
        this.serviceUser = serviceUser;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        List<User> users = serviceUser.getUsers();
        paramsMap.put(TEMPLATE_ATTR_ADMINS, users);

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, paramsMap));
    }

}
