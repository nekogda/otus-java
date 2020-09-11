package ru.otus.server;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.core.service.DBServiceUser;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.services.TemplateProcessor;
import ru.otus.servlet.AdminsServlet;
import ru.otus.servlet.UsersApiServlet;
import ru.otus.servlet.UsersServlet;

import java.util.Map;

public class UsersWebServerSimple implements UsersWebServer {
    protected static final String ROLE_NAME_USER = "user";
    protected static final String ROLE_NAME_ADMIN = "admin";
    protected static final String CONSTRAINT_NAME = "auth";

    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";

    private final DBServiceUser serviceUser;
    private final Gson gson;
    protected final TemplateProcessor templateProcessor;
    private final Server server;

    public UsersWebServerSimple(int port, DBServiceUser serviceUser, Gson gson, TemplateProcessor templateProcessor) {
        this.serviceUser = serviceUser;
        this.gson = gson;
        this.templateProcessor = templateProcessor;
        server = new Server(port);
    }

    @Override
    public void start() throws Exception {
        if (server.getHandlers().length == 0) {
            initContext();
        }
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    private Server initContext() {

        ResourceHandler resourceHandler = createResourceHandler();
        ServletContextHandler servletContextHandler = createServletContextHandler();

        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);

        var acl = Map.of(
                "/users/*", Map.of(
                        "ALL", new String[]{ROLE_NAME_USER, ROLE_NAME_ADMIN}),
                "/admins/*", Map.of(
                        "ALL", new String[]{ROLE_NAME_ADMIN}),
                "/api/users/*", Map.of(
                        "GET", new String[]{ROLE_NAME_USER, ROLE_NAME_ADMIN},
                        "POST", new String[]{ROLE_NAME_ADMIN},
                        "PUT", new String[]{},
                        "HEAD", new String[]{},
                        "TRACE", new String[]{},
                        "DELETE", new String[]{},
                        "OPTION", new String[]{}));
        handlers.addHandler(applySecurity(servletContextHandler, acl));
        server.setHandler(handlers);
        return server;
    }

    protected Handler applySecurity(ServletContextHandler servletContextHandler, Map<String, Map<String, String[]>> acl) {
        return servletContextHandler;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

        servletContextHandler.addServlet(
                new ServletHolder(new UsersServlet(templateProcessor, serviceUser)),
                "/users/*");

        servletContextHandler.addServlet(
                new ServletHolder(new AdminsServlet(templateProcessor, serviceUser)),
                "/admins/*");

        servletContextHandler.addServlet(
                new ServletHolder(new UsersApiServlet(serviceUser, gson)),
                "/api/users/*");

        return servletContextHandler;
    }
}
