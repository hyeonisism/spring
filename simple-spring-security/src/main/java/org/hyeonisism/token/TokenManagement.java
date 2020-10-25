package org.hyeonisism.token;

import lombok.RequiredArgsConstructor;
import org.hyeonisism.security.SecurityTokenManagement;
import org.hyeonisism.user.User;
import org.hyeonisism.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @author hyeonisism
 */
@RequiredArgsConstructor
@Service
public class TokenManagement {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityTokenManagement securityTokenManagement;

    Optional<String> createToken(@NotNull final TokenRequest tokenRequest) {
        Optional<User> user = userRepository.findByUsername(tokenRequest.getUsername());
        if (user.isPresent() && passwordEncoder.matches(tokenRequest.getPassword(), user.get().getPassword())) {
            return Optional.of(securityTokenManagement.createToken(user.get().getUsername()));
        }
        return Optional.empty();
    }
}
