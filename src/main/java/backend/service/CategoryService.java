package backend.service;

import backend.entity.Category;
import backend.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;


@Service
public class CategoryService {
    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    public Category findById(@NotNull Long id) {
        Category category = this.categoryRepository.findById(id).get();
        log.info("CategoryService: findById id = " + id + " category = " + category);
        return category;
    }

    public Category findByName(@NotNull String name) {
        Category category = this.categoryRepository.getCategoryByCategoryName(name);
        log.info("CategoryService: findById id = " + name + " category = " + category);
        return category;
    }

    public Iterable<Category> findAll() {
       return this.categoryRepository.findAll();
    }
}
