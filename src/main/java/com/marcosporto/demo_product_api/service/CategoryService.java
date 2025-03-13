package com.marcosporto.demo_product_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marcosporto.demo_product_api.entity.Category;
import com.marcosporto.demo_product_api.exception.EntityNotFoundException;
import com.marcosporto.demo_product_api.repository.CategoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category searchCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Categoria id=%s não encotrada", id)));
    }

    public Category deleteCategory(Long id) {
        Category category = searchCategory(id);
        categoryRepository.deleteById(id);

        return category;
    }

    public Category updateCategory(Long id, String name) {
        Category category = searchCategory(id);

        category.setName(name);

        return categoryRepository.save(category);
    }

}
