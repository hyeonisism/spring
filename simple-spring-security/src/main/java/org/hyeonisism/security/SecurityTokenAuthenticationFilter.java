package org.hyeonisism.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author hyeonisism
 */
@RequiredArgsConstructor
@Component
public class SecurityTokenAuthenticationFilter extends GenericFilterBean {

    private final SecurityTokenManagement securityTokenManagement;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = securityTokenManagement.resolveToken((HttpServletRequest) request);
        if (token != null && securityTokenManagement.validateToken(token)) {
            Authentication authentication = securityTokenManagement.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
