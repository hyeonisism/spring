package org.hyeonisism.token;

import org.hyeonisism.IntegrationTests;
import org.hyeonisism.user.User;
import org.hyeonisism.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author hyeonisism
 */
class TokenIntegrationTests extends IntegrationTests {

    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(createUser()));
    }

    @Test
    void shouldReturnToken() throws Exception {
        // expect
        mvc.perform(post("/tokens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new TokenRequest(USER_NAME, PASSWORD))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("accessToken").exists());
    }

    private User createUser() {
        return User.builder()
                .username(USER_NAME)
                .password(passwordEncoder.encode(PASSWORD))
                .build();
    }
}
