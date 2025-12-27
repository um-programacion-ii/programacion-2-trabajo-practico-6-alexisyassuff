package com.example.dataservice.service.impl;

import com.example.dataservice.entity.Category;
import com.example.dataservice.exception.DuplicateResourceException;
import com.example.dataservice.exception.ResourceNotFoundException;
import com.example.dataservice.exception.ValidationException;
import com.example.dataservice.repository.CategoryRepository;
import com.example.dataservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz CategoryService.
 * Proporciona la lógica de negocio para la gestión de categorías.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param categoryRepository repositorio de categorías
     */
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // ==================== Métodos de Lectura/Consulta ====================

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", name));
    }

    @Override
    public List<Category> findCategoriesByNameContaining(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByNameIgnoreCase(name);
    }

    // ==================== Métodos de Escritura/Modificación ====================

    @Override
    @Transactional
    public Category createCategory(Category category) {
        validateCategory(category);
        
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new DuplicateResourceException("Category", "name", category.getName());
        }
        
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = getCategoryById(id);
        
        validateCategory(categoryDetails);
        
        // Verificar si el nuevo nombre entra en conflicto con una categoría existente (excluyendo la actual)
        if (!category.getName().equalsIgnoreCase(categoryDetails.getName()) && 
                categoryRepository.existsByNameIgnoreCase(categoryDetails.getName())) {
            throw new DuplicateResourceException("Category", "name", categoryDetails.getName());
        }
        
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category", "id", id);
        }
        
        categoryRepository.deleteById(id);
    }

    // ==================== Métodos Privados ====================

    /**
     * Valida los datos de una categoría.
     *
     * @param category la categoría a validar
     * @throws ValidationException si los datos de la categoría son inválidos
     */
    private void validateCategory(Category category) {
        ValidationException validationException = new ValidationException("Category validation failed");
        
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            validationException.addError("name", "Category name is required");
        }
        
        if (validationException.hasErrors()) {
            throw validationException;
        }
    }
}
