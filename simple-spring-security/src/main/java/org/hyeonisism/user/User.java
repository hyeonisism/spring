package org.hyeonisism.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * @author hyeonisism
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    void encodePassword(@NotNull final PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }
}
