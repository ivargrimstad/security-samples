package eu.agilejava.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashSet;

import static java.util.Arrays.asList;

@ApplicationScoped
public class SimpleAuthenticationMechanism implements HttpAuthenticationMechanism {

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {

        if (!request.getRequestURI().contains("protected")) {
            return httpMessageContext.doNothing();
        }

        final String key = request.getHeader("MY-API-KEY");
        if (key != null && key.equalsIgnoreCase("DUKE ROCKS")) {
            return httpMessageContext.notifyContainerAboutLogin(
                    "app", new HashSet<>(asList("foo")));
        }

        return httpMessageContext.responseUnauthorized();
    }
}
