package backend.repository;

import backend.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * repository for UserEntity Entity
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity getUserByEmail(String email);
}