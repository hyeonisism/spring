package org.hyeonisism.token;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author hyeonisism
 */
@Getter
@RequiredArgsConstructor
public class TokenRequest {

    @NotBlank(message = "username can not be blank")
    private final String username;

    @NotBlank(message = "password can not be blank")
    private final String password;
}
