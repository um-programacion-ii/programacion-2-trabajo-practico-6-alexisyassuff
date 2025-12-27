package com.example.businessservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Cliente Feign para comunicarse con los endpoints de categorías en el servicio de datos.
 */
@FeignClient(name = "category-service", url = "${data-service.url}/data/categories")
public interface CategoryClient {

    // ==================== Métodos GET - Consultas Básicas ====================

    /**
     * Obtiene todas las categorías.
     *
     * @return ResponseEntity que contiene una lista de categorías
     */
    @GetMapping
    ResponseEntity<List<Object>> getAllCategories();

    /**
     * Obtiene una categoría por su ID.
     *
     * @param id el ID de la categoría
     * @return ResponseEntity que contiene la categoría
     */
    @GetMapping("/{id}")
    ResponseEntity<Object> getCategoryById(@PathVariable("id") Long id);

    /**
     * Obtiene una categoría por su nombre.
     *
     * @param name el nombre de la categoría
     * @return ResponseEntity que contiene la categoría
     */
    @GetMapping("/name/{name}")
    ResponseEntity<Object> getCategoryByName(@PathVariable("name") String name);

    // ==================== Métodos GET - Búsquedas y Filtros ====================

    /**
     * Busca categorías por nombre.
     *
     * @param name el patrón de nombre a buscar
     * @return ResponseEntity que contiene una lista de categorías que coinciden
     */
    @GetMapping("/search")
    ResponseEntity<List<Object>> searchCategoriesByName(@RequestParam("name") String name);

    // ==================== Métodos POST ====================

    /**
     * Crea una nueva categoría.
     *
     * @param category la categoría a crear
     * @return ResponseEntity que contiene la categoría creada
     */
    @PostMapping
    ResponseEntity<Object> createCategory(@RequestBody Object category);

    // ==================== Métodos PUT ====================

    /**
     * Actualiza una categoría existente.
     *
     * @param id el ID de la categoría
     * @param category los datos actualizados de la categoría
     * @return ResponseEntity que contiene la categoría actualizada
     */
    @PutMapping("/{id}")
    ResponseEntity<Object> updateCategory(@PathVariable("id") Long id, @RequestBody Object category);

    // ==================== Métodos DELETE ====================

    /**
     * Elimina una categoría.
     *
     * @param id el ID de la categoría
     * @return ResponseEntity sin contenido
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id);
}
