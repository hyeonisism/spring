package org.hyeonisism.user;

import org.hyeonisism.IntegrationTests;
import org.hyeonisism.security.SecurityTokenManagement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author hyeonisism
 */
class UserIntegrationTests extends IntegrationTests {

    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_CONFIRM = "password";

    @Autowired
    SecurityTokenManagement securityTokenManagement;

    @MockBean
    UserRepository userRepository;

    @Test
    void shouldReturnToken() throws Exception {
        final UserRequest user = new UserRequest(USER_NAME, PASSWORD, PASSWORD_CONFIRM);

        when(userRepository.save(any())).thenReturn(User.builder().username(USER_NAME).build());

        // expect
        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().string(USER_NAME));
    }

    @Test
    void shouldGetSuccess() throws Exception {
        // given
        when(userRepository.findByUsername(USER_NAME)).thenReturn(Optional.of(User.builder().username(USER_NAME).build()));

        // expect
        mvc.perform(get("/users/hello")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, securityTokenManagement.createToken(USER_NAME)))
                .andExpect(status().isOk())
                .andExpect(content().string(USER_NAME));
    }

    @Test
    void shouldGetFailed() throws Exception {
        // given
        when(userRepository.findByUsername(USER_NAME)).thenReturn(Optional.of(User.builder().username(USER_NAME).build()));

        // expect
        mvc.perform(get("/users/hello")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Wrong Token"))
                .andExpect(status().isForbidden());
    }
}
