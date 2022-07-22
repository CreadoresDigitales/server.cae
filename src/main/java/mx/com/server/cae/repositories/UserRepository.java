package mx.com.server.cae.repositories;

import mx.com.server.cae.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{ 'id' : :#{#id} }")
    User findUserById(@Param("id") String id);

    @Query("{ 'username' : :#{#username} }")
    User findUserByUsername(@Param("username") String username);

    @Query("{ 'email' : :#{#email} }")
    User findUserByEmail(@Param("email") String email);

    @Query(value = "{ 'email' : :#{#email}}", exists = true)
    Boolean existsUserByEmail(@Param("email") String email);

}
