package backend.repository;

import backend.entity.Category;
import backend.entity.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findCategoriesByEvents(Event event);
}
