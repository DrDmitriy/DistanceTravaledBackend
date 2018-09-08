package backend.controller;

import backend.entity.Category;
import backend.entity.Event;
import backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

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
    @CrossOrigin("*")
    public @ResponseBody
    List<Category> getAll() {
        List<Category> categoriesList = new ArrayList<>();
        this.categoryService.findAll().forEach(category -> categoriesList.add(category));

        return categoriesList;
    }
}