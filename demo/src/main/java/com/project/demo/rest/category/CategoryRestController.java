package com.project.demo.rest.category;

import com.project.demo.logic.entity.category.Category;
import com.project.demo.logic.entity.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoryRestController {
    @Autowired
    private CategoryRepository CategoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
    public List<Category> getAllCategory() {
        return CategoryRepository.findAll();
    }

    @PostMapping
    public Category addCategory(@RequestBody Category category) {
        return CategoryRepository.save(category);
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return CategoryRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @GetMapping("/filterByName/{name}")
    public List<Category> getCategoryById(@PathVariable String name) {
        return CategoryRepository.findCategoryWithCharacterInName(name);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return CategoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(category.getName());
                    existingCategory.setDescription(category.getDescription());
                    return CategoryRepository.save(existingCategory);
                })
                .orElseGet(() -> {
                    category.setId(id);
                    return CategoryRepository.save(category);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        CategoryRepository.deleteById(id);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public Category authenticatedCategory() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Category) authentication.getPrincipal();
    }

}