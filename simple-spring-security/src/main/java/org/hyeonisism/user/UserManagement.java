package org.hyeonisism.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * @author hyeonisism
 */
@RequiredArgsConstructor
@Service
public class UserManagement {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    User create(@NotNull final UserRequest userRequest) {

        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Already registered username");
        }

        User user = User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .build();

        user.encodePassword(passwordEncoder);

        return userRepository.save(user);
    }
}
