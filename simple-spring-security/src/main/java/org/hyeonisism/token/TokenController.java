package org.hyeonisism.token;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author hyeonisism
 */
@RequiredArgsConstructor
@RequestMapping("/tokens")
@RestController
public class TokenController {

    private final TokenManagement tokenManagement;

    @PostMapping
    public ResponseEntity getToken(@NotNull @Valid @RequestBody final TokenRequest tokenRequest) {
        return tokenManagement.createToken(tokenRequest)
                .map(token -> ResponseEntity.status(HttpStatus.CREATED).body(TokenResponse.of(token)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
