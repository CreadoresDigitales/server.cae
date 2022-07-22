package mx.com.server.cae.security.auth;

import mx.com.server.cae.services.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            throw new AuthenticationCredentialsNotFoundException("Credentials is null");
        }
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails loadedUser = myUserDetailsService.loadUserByUsername(username);
        if (loadedUser != null) {
            /* checker */ if (!loadedUser.getPassword().equals(password) || !loadedUser.getUsername().equals(username)) {
                throw new BadCredentialsException("User's username | password don't match stored value");
            }
            /* checker */ if (!loadedUser.isAccountNonExpired()) {
                throw new AccountExpiredException("User has expired");
            }
            /* checker */ if (!loadedUser.isAccountNonLocked()) {
                throw new LockedException("User is locked");
            }
            /* checker */ if (!loadedUser.isCredentialsNonExpired()) {
                throw new CredentialsExpiredException("User credentials have expired");
            }
            /* checker */ if (!loadedUser.isEnabled()) {
                log.info("User is disabled");
                /* throw new DisabledException("Account is disabled"); */
            } else if (loadedUser.isEnabled()) {
                log.info("User is enabled");
            }
            /* return Authentication*/ UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(loadedUser, loadedUser.getPassword(), loadedUser.getAuthorities());
            result.setDetails(authentication.getDetails());
            return result;
        } else {
            throw new UsernameNotFoundException("User not found by username");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
