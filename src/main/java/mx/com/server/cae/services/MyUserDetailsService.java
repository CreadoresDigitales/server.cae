package mx.com.server.cae.services;

import mx.com.server.cae.models.User;
import mx.com.server.cae.repositories.UserRepository;
import mx.com.server.cae.support.utils.UserUtil;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * Add spring security for user
    referent https://www.baeldung.com/spring-security-authentication-with-a-database
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserUtil userUtil;

    @org.springframework.beans.factory.annotation.Autowired
    public MyUserDetailsService(UserRepository userRepository, UserUtil userUtil) {
        this.userRepository = userRepository;
        this.userUtil = userUtil;
    }

    @Override
    @Transactional
    public MyUserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User's username : " + username + " didn't find in the database");
        }
        return this.userUtil.localUser(user);
    }
}
