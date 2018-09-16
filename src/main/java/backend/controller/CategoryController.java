package backend.controller;

import backend.entity.Category;
import backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/categories")
    public @ResponseBody List<Category> getAll(HttpServletResponse response) {
        List<Category> categoriesList = new ArrayList<>();
        this.categoryService.findAll().forEach(categoriesList::add);
        response.setStatus(HttpStatus.OK.value());
        return categoriesList;
    }
}
