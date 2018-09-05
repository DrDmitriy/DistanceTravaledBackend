package backend.repository;

import org.springframework.data.repository.CrudRepository;
import backend.entity.User;
// import backend.entity.User;


/**
 * repository for User Entity
 */
public interface UserRepository extends CrudRepository<User, Long> {
     User getUserByLogin(String login);
}
