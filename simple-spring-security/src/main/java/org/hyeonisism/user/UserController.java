package org.hyeonisism.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;

/**
 * @author hyeonisism
 */
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserManagement userManagement;

    @PostMapping
    public ResponseEntity create(@NotNull @Valid @RequestBody final UserRequest userRequest) {
        if (!userRequest.matchPassword()) {
            throw new IllegalArgumentException("not correct password confirm");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userManagement.create(userRequest).getUsername());
    }

    @GetMapping("/hello")
    public ResponseEntity hello(final Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }
}
