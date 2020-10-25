package org.hyeonisism.security;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hyeonisism
 */
public interface SecurityTokenManagement {

    String createToken(String username);

    Authentication getAuthentication(String token);

    String getUsernameByToken(String token);

    String resolveToken(HttpServletRequest request);

    boolean validateToken(String jwtToken);
}
