package com.example.businessservice.controller;

import com.example.businessservice.dto.CategoryDTO;
import com.example.businessservice.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de categorías en el servicio de negocio.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param categoryService servicio de categorías
     */
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // ==================== Métodos GET ====================

    /**
     * GET /api/categories : Obtiene todas las categorías.
     *
     * @return ResponseEntity con estado 200 (OK) y la lista de categorías en el cuerpo
     */
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    /**
     * GET /api/categories/{id} : Obtiene una categoría por su ID.
     *
     * @param id el ID de la categoría a obtener
     * @return ResponseEntity con estado 200 (OK) y la categoría en el cuerpo,
     *         o con estado 404 (Not Found) si la categoría no se encuentra
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        if (category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /api/categories/name/{name} : Obtiene una categoría por su nombre.
     *
     * @param name el nombre de la categoría a obtener
     * @return ResponseEntity con estado 200 (OK) y la categoría en el cuerpo,
     *         o con estado 404 (Not Found) si la categoría no se encuentra
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
        CategoryDTO category = categoryService.getCategoryByName(name);
        if (category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /api/categories/search : Busca categorías por nombre.
     *
     * @param name el patrón de nombre a buscar
     * @return ResponseEntity con estado 200 (OK) y la lista de categorías que coinciden en el cuerpo
     */
    @GetMapping("/search")
    public ResponseEntity<List<CategoryDTO>> searchCategoriesByName(@RequestParam String name) {
        List<CategoryDTO> categories = categoryService.findCategoriesByNameContaining(name);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // ==================== Métodos POST ====================

    /**
     * POST /api/categories : Crea una nueva categoría.
     *
     * @param categoryDTO la categoría a crear
     * @return ResponseEntity con estado 201 (Created) y la nueva categoría en el cuerpo,
     *         o con estado 500 (Internal Server Error) si ocurre un error
     */
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO newCategory = categoryService.createCategory(categoryDTO);
        if (newCategory != null) {
            return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== Métodos PUT ====================

    /**
     * PUT /api/categories/{id} : Actualiza una categoría existente.
     *
     * @param id el ID de la categoría a actualizar
     * @param categoryDTO los datos actualizados de la categoría
     * @return ResponseEntity con estado 200 (OK) y la categoría actualizada en el cuerpo,
     *         o con estado 404 (Not Found) si la categoría no se encuentra
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        if (updatedCategory != null) {
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ==================== Métodos DELETE ====================

    /**
     * DELETE /api/categories/{id} : Elimina una categoría.
     *
     * @param id el ID de la categoría a eliminar
     * @return ResponseEntity con estado 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
