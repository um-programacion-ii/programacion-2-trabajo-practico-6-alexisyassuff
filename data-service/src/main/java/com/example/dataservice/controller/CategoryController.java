package com.example.dataservice.controller;

import com.example.dataservice.entity.Category;
import com.example.dataservice.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de categorías.
 */
@RestController
@RequestMapping("/data/categories")
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
     * GET /data/categories : Obtiene todas las categorías.
     *
     * @return ResponseEntity con estado 200 (OK) y la lista de categorías en el cuerpo
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    /**
     * GET /data/categories/{id} : Obtiene una categoría por su ID.
     *
     * @param id el ID de la categoría a obtener
     * @return ResponseEntity con estado 200 (OK) y la categoría en el cuerpo,
     *         o con estado 404 (Not Found) si la categoría no se encuentra
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /**
     * GET /data/categories/name/{name} : Obtiene una categoría por su nombre.
     *
     * @param name el nombre de la categoría a obtener
     * @return ResponseEntity con estado 200 (OK) y la categoría en el cuerpo,
     *         o con estado 404 (Not Found) si la categoría no se encuentra
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String name) {
        Category category = categoryService.getCategoryByName(name);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /**
     * GET /data/categories/search : Busca categorías por nombre.
     *
     * @param name el patrón de nombre a buscar
     * @return ResponseEntity con estado 200 (OK) y la lista de categorías que coinciden en el cuerpo
     */
    @GetMapping("/search")
    public ResponseEntity<List<Category>> searchCategoriesByName(@RequestParam String name) {
        List<Category> categories = categoryService.findCategoriesByNameContaining(name);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // ==================== Métodos POST ====================

    /**
     * POST /data/categories : Crea una nueva categoría.
     *
     * @param category la categoría a crear
     * @return ResponseEntity con estado 201 (Created) y la nueva categoría en el cuerpo,
     *         o con estado 400 (Bad Request) si los datos de la categoría son inválidos,
     *         o con estado 409 (Conflict) si ya existe una categoría con el mismo nombre
     */
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        Category newCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    // ==================== Métodos PUT ====================

    /**
     * PUT /data/categories/{id} : Actualiza una categoría existente.
     *
     * @param id el ID de la categoría a actualizar
     * @param category los datos actualizados de la categoría
     * @return ResponseEntity con estado 200 (OK) y la categoría actualizada en el cuerpo,
     *         o con estado 400 (Bad Request) si los datos de la categoría son inválidos,
     *         o con estado 404 (Not Found) si la categoría no se encuentra,
     *         o con estado 409 (Conflict) si el nuevo nombre entra en conflicto con una categoría existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    // ==================== Métodos DELETE ====================

    /**
     * DELETE /data/categories/{id} : Elimina una categoría.
     *
     * @param id el ID de la categoría a eliminar
     * @return ResponseEntity con estado 204 (No Content),
     *         o con estado 404 (Not Found) si la categoría no se encuentra
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
