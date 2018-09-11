package backend.repository;

import backend.entity.Category;
import backend.entity.Event;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category getCategoryByCategoryName(String categoryName);
}
