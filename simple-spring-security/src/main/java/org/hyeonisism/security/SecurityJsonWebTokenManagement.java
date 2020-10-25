package org.hyeonisism.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Base64;
import java.util.Date;

/**
 * @author hyeonisism
 */
@RequiredArgsConstructor
@Service
public class SecurityJsonWebTokenManagement implements SecurityTokenManagement {

    private final SecurityAuthenticationManagement securityAuthenticationManagement;

    private static final long TOKEN_VALID_TIME = 30 * 60 * 1000L;

    private static final String SECRET_KEY = Base64.getEncoder().encodeToString("HyeonisismSimpleJsonWebTokenSecurity".getBytes());

    @Override
    public String createToken(@NotNull final String username) {
        Claims claims = Jwts.claims().setSubject(username);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    @Override
    public Authentication getAuthentication(@NotNull final String token) {
        UserDetails userDetails = securityAuthenticationManagement.loadUserByUsername(this.getUsernameByToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "HyeonisismSimpleJsonWebTokenSecurity", userDetails.getAuthorities());
    }

    @Override
    public String getUsernameByToken(@NotNull final String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    @Override
    public String resolveToken(@NotNull final HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    @Override
    public boolean validateToken(@NotNull final String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}