package de.interseroh.tmb.profile.server.filter;

import org.mitre.openid.connect.client.OIDCAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TpiSsoAuthFilterWithPostStrategy extends OIDCAuthenticationFilter {

    public TpiSsoAuthFilterWithPostStrategy() {
        super();
        SessionAuthenticationStrategy sessionStrategy = (authentication, request, response) -> {
            logger.info("Authenticated "+authentication.getName());
        };
        setSessionAuthenticationStrategy(sessionStrategy);
    }
}
