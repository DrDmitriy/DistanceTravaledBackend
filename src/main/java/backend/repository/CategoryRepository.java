package backend.repository;

import backend.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category getCategoryByCategoryName(String categoryName);
}
