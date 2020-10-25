package org.hyeonisism.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hyeonisism.user.User;
import org.hyeonisism.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author hyeonisism
 */
@RequiredArgsConstructor
@Component
public class SecurityAuthenticationManagement implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetail(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User: " + username + "not found")));
    }

    @Getter
    @EqualsAndHashCode
    static class UserDetail implements UserDetails {
        private final String username;
        private final String password;
        private final boolean isEnabled;
        private final Collection<? extends GrantedAuthority> authorities;

        UserDetail(@NotNull final User user) {
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.isEnabled = true;
            authorities = new ArrayList<>();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }
    }

}
