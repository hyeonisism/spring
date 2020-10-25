package org.hyeonisism.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author hyeonisism
 */
class UserManagementTests {

    private UserManagement userManagement;

    private UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void setUp() {
        userManagement = new UserManagement(userRepository, new BCryptPasswordEncoder());
    }

    @Test
    void shouldThrowException() {
        // given
        final UserRequest request = new UserRequest("username", "password", "password");
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(User.builder().username("username").build()));

        // expect
        assertThatThrownBy(() -> userManagement.create(request)).isInstanceOf(IllegalArgumentException.class);
    }
}
