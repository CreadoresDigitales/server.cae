package mx.com.server.cae.services;

import mx.com.server.cae.models.User;

public interface UserService {

    User saveUser(User user);

    User findUserById(String id);

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User updateUser(User user);

}
