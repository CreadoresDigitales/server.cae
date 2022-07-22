package mx.com.server.cae.services.impl;

import mx.com.server.cae.models.User;
import mx.com.server.cae.repositories.UserRepository;
import mx.com.server.cae.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        log.info("Method to create an user's data: {}", user);
        return this.userRepository.save(user);
    }

    @Override
    public User findUserById(String id) {
        log.info("Method to get an user's data by id: {}", id);
        return this.userRepository.findUserById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        log.info("Method to get an user's data by username: {}", username);
        return this.userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        log.info("Method to get an user's data by email: {}", email);
        return this.userRepository.findUserByEmail(email);
    }

    @Override
    public User updateUser(User user) {
        log.info("Method to update user's data: {}", user);
       return  this.userRepository.save(user);
    }

}
