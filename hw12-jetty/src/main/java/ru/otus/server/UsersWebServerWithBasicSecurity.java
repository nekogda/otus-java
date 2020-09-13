package ru.otus.server;

import com.google.gson.Gson;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.security.Constraint;
import ru.otus.core.service.DBServiceUser;
import ru.otus.services.TemplateProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsersWebServerWithBasicSecurity extends UsersWebServerSimple {


    private final LoginService loginService;

    public UsersWebServerWithBasicSecurity(int port,
                                           LoginService loginService,
                                           DBServiceUser serviceUser,
                                           Gson gson,
                                           TemplateProcessor templateProcessor) {
        super(port, serviceUser, gson, templateProcessor);
        this.loginService = loginService;
    }

    /**
     * @param pathSpec protected url
     * @param method   method name. Use "ALL" value if all method allowed.
     * @param roles    list of associated roles
     * @return ConstraintMapping
     */
    private ConstraintMapping createConstraintMapping(String pathSpec, String method, String[] roles) {

        Constraint constraint = new Constraint();
        constraint.setAuthenticate(true);
        constraint.setRoles(roles);

        var mapping = new ConstraintMapping();
        mapping.setConstraint(constraint);
        mapping.setPathSpec(pathSpec);
        if (!"ALL".equals(method)) {
            mapping.setMethod(method);
        }
        return mapping;
    }

    /**
     * @param servletContextHandler servletContext for wrapping
     * @param acl                   {@code Map<PathSpec, Map<MethodName, []RoleNames>>}
     * @return servletContextHandler wrapped by security handler
     */
    protected Handler applySecurity(ServletContextHandler servletContextHandler, Map<String, Map<String, String[]>> acl) {
        List<ConstraintMapping> constraintMappings = new ArrayList<>();

        acl.forEach((pathSpec, methodsToRolesMap) -> methodsToRolesMap.forEach(
                (methodName, roles) -> constraintMappings.add(createConstraintMapping(pathSpec, methodName, roles))));

        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        security.setAuthenticator(new BasicAuthenticator());

        security.setLoginService(loginService);
        security.setConstraintMappings(constraintMappings);
        security.setHandler(new HandlerList(servletContextHandler));

        return security;
    }
}
