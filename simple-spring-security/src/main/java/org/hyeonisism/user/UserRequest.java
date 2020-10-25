package org.hyeonisism.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * @author hyeonisism
 */
@Getter
@RequiredArgsConstructor
class UserRequest {

    @NotBlank(message = "username can not be blank")
    private final String username;
    @NotBlank(message = "password can not be blank")
    private final String password;
    @NotBlank(message = "confirmPassword can not be blank")
    private final String confirmPassword;

    boolean matchPassword() {
        return Objects.equals(password, confirmPassword);
    }
}
