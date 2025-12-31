package com.example.businessservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Cliente Feign para comunicarse con los endpoints de productos en el servicio de datos.
 */
@FeignClient(name = "product-service", url = "${data-service.url}/data/products")
public interface ProductClient {

    // ==================== Métodos GET - Consultas Básicas ====================

    /**
     * Obtiene todos los productos.
     *
     * @return ResponseEntity que contiene una lista de productos
     */
    @GetMapping
    ResponseEntity<List<Object>> getAllProducts();

    /**
     * Obtiene un producto por su ID.
     *
     * @param id el ID del producto
     * @return ResponseEntity que contiene el producto
     */
    @GetMapping("/{id}")
    ResponseEntity<Object> getProductById(@PathVariable("id") Long id);

    // ==================== Métodos GET - Búsquedas y Filtros ====================

    /**
     * Busca productos por nombre.
     *
     * @param name el patrón de nombre a buscar
     * @return ResponseEntity que contiene una lista de productos que coinciden
     */
    @GetMapping("/search")
    ResponseEntity<List<Object>> searchProductsByName(@RequestParam("name") String name);

    /**
     * Obtiene productos por ID de categoría.
     *
     * @param categoryId el ID de la categoría
     * @return ResponseEntity que contiene una lista de productos en la categoría especificada
     */
    @GetMapping("/category/{categoryId}")
    ResponseEntity<List<Object>> getProductsByCategoryId(@PathVariable("categoryId") Long categoryId);

    /**
     * Obtiene productos por nombre de categoría.
     *
     * @param categoryName el nombre de la categoría
     * @return ResponseEntity que contiene una lista de productos en la categoría especificada
     */
    @GetMapping("/category/name/{categoryName}")
    ResponseEntity<List<Object>> getProductsByCategoryName(@PathVariable("categoryName") String categoryName);

    /**
     * Obtiene productos con precio menor o igual al valor especificado.
     *
     * @param maxPrice el precio máximo
     * @return ResponseEntity que contiene una lista de productos con precio menor o igual al valor dado
     */
    @GetMapping("/price/max/{maxPrice}")
    ResponseEntity<List<Object>> getProductsByMaxPrice(@PathVariable("maxPrice") BigDecimal maxPrice);

    /**
     * Obtiene productos con precio mayor o igual al valor especificado.
     *
     * @param minPrice el precio mínimo
     * @return ResponseEntity que contiene una lista de productos con precio mayor o igual al valor dado
     */
    @GetMapping("/price/min/{minPrice}")
    ResponseEntity<List<Object>> getProductsByMinPrice(@PathVariable("minPrice") BigDecimal minPrice);

    /**
     * Obtiene productos con precio entre los valores especificados.
     *
     * @param minPrice el precio mínimo
     * @param maxPrice el precio máximo
     * @return ResponseEntity que contiene una lista de productos con precio dentro del rango especificado
     */
    @GetMapping("/price/range")
    ResponseEntity<List<Object>> getProductsByPriceRange(
            @RequestParam("minPrice") BigDecimal minPrice,
            @RequestParam("maxPrice") BigDecimal maxPrice);

    // ==================== Métodos POST ====================

    /**
     * Crea un nuevo producto.
     *
     * @param product el producto a crear
     * @return ResponseEntity que contiene el producto creado
     */
    @PostMapping
    ResponseEntity<Object> createProduct(@RequestBody Object product);

    // ==================== Métodos PUT ====================

    /**
     * Actualiza un producto existente.
     *
     * @param id el ID del producto
     * @param product los datos actualizados del producto
     * @return ResponseEntity que contiene el producto actualizado
     */
    @PutMapping("/{id}")
    ResponseEntity<Object> updateProduct(@PathVariable("id") Long id, @RequestBody Object product);

    /**
     * Asigna una categoría a un producto.
     *
     * @param productId el ID del producto
     * @param categoryId el ID de la categoría
     * @return ResponseEntity que contiene el producto actualizado
     */
    @PutMapping("/{productId}/category/{categoryId}")
    ResponseEntity<Object> assignCategoryToProduct(
            @PathVariable("productId") Long productId,
            @PathVariable("categoryId") Long categoryId);

    // ==================== Métodos DELETE ====================

    /**
     * Elimina un producto.
     *
     * @param id el ID del producto
     * @return ResponseEntity sin contenido
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id);

    /**
     * Remueve la categoría de un producto.
     *
     * @param productId el ID del producto
     * @return ResponseEntity que contiene el producto actualizado
     */
    @DeleteMapping("/{productId}/category")
    ResponseEntity<Object> removeCategoryFromProduct(@PathVariable("productId") Long productId);
}
