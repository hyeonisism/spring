package org.hyeonisism.user;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @author hyeonisism
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(@NotNull final String username);
}
