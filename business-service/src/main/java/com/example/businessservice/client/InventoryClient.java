package com.example.businessservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Cliente Feign para comunicarse con los endpoints de inventario en el servicio de datos.
 */
@FeignClient(name = "inventory-service", url = "${data-service.url}/data/inventory")
public interface InventoryClient {

    // ==================== Métodos GET - Consultas Básicas ====================

    /**
     * Obtiene todos los registros de inventario.
     *
     * @return ResponseEntity que contiene una lista de registros de inventario
     */
    @GetMapping
    ResponseEntity<List<Object>> getAllInventoryItems();

    /**
     * Obtiene un registro de inventario por su ID.
     *
     * @param id el ID del registro de inventario
     * @return ResponseEntity que contiene el registro de inventario
     */
    @GetMapping("/{id}")
    ResponseEntity<Object> getInventoryItemById(@PathVariable("id") Long id);

    // ==================== Métodos GET - Búsquedas y Filtros ====================

    /**
     * Obtiene registros de inventario por ID de producto.
     *
     * @param productId el ID del producto
     * @return ResponseEntity que contiene una lista de registros de inventario para el producto especificado
     */
    @GetMapping("/product/{productId}")
    ResponseEntity<List<Object>> getInventoryItemsByProductId(@PathVariable("productId") Long productId);

    /**
     * Obtiene registros de inventario por ubicación.
     *
     * @param location la ubicación
     * @return ResponseEntity que contiene una lista de registros de inventario en la ubicación especificada
     */
    @GetMapping("/location/{location}")
    ResponseEntity<List<Object>> getInventoryItemsByLocation(@PathVariable("location") String location);

    /**
     * Obtiene registros de inventario con cantidad menor al valor especificado.
     *
     * @param quantity el umbral de cantidad
     * @return ResponseEntity que contiene una lista de registros de inventario con cantidad menor al valor dado
     */
    @GetMapping("/quantity/less/{quantity}")
    ResponseEntity<List<Object>> getInventoryItemsByQuantityLessThan(@PathVariable("quantity") Integer quantity);

    /**
     * Obtiene registros de inventario con cantidad mayor al valor especificado.
     *
     * @param quantity el umbral de cantidad
     * @return ResponseEntity que contiene una lista de registros de inventario con cantidad mayor al valor dado
     */
    @GetMapping("/quantity/greater/{quantity}")
    ResponseEntity<List<Object>> getInventoryItemsByQuantityGreaterThan(@PathVariable("quantity") Integer quantity);

    /**
     * Obtiene registros de inventario con cantidad entre los valores especificados.
     *
     * @param minQuantity la cantidad mínima
     * @param maxQuantity la cantidad máxima
     * @return ResponseEntity que contiene una lista de registros de inventario con cantidad dentro del rango especificado
     */
    @GetMapping("/quantity/range")
    ResponseEntity<List<Object>> getInventoryItemsByQuantityBetween(
            @RequestParam("minQuantity") Integer minQuantity,
            @RequestParam("maxQuantity") Integer maxQuantity);

    /**
     * Obtiene registros de inventario por nombre de producto.
     *
     * @param productName el nombre del producto
     * @return ResponseEntity que contiene una lista de registros de inventario para productos que coinciden con el nombre dado
     */
    @GetMapping("/product/name/{productName}")
    ResponseEntity<List<Object>> getInventoryItemsByProductName(@PathVariable("productName") String productName);

    /**
     * Obtiene registros de inventario por categoría de producto.
     *
     * @param categoryId el ID de la categoría
     * @return ResponseEntity que contiene una lista de registros de inventario para productos en la categoría especificada
     */
    @GetMapping("/category/{categoryId}")
    ResponseEntity<List<Object>> getInventoryItemsByProductCategory(@PathVariable("categoryId") Long categoryId);

    /**
     * Obtiene registros de inventario sin stock (cantidad = 0).
     *
     * @return ResponseEntity que contiene una lista de registros de inventario sin stock
     */
    @GetMapping("/out-of-stock")
    ResponseEntity<List<Object>> getOutOfStockItems();

    // ==================== Métodos POST ====================

    /**
     * Crea un nuevo registro de inventario.
     *
     * @param inventory el registro de inventario a crear
     * @return ResponseEntity que contiene el registro de inventario creado
     */
    @PostMapping
    ResponseEntity<Object> createInventoryItem(@RequestBody Object inventory);

    // ==================== Métodos PUT ====================

    /**
     * Actualiza un registro de inventario existente.
     *
     * @param id el ID del registro de inventario
     * @param inventory los datos actualizados del inventario
     * @return ResponseEntity que contiene el registro de inventario actualizado
     */
    @PutMapping("/{id}")
    ResponseEntity<Object> updateInventoryItem(@PathVariable("id") Long id, @RequestBody Object inventory);

    /**
     * Actualiza la cantidad de un registro de inventario.
     *
     * @param id el ID del registro de inventario
     * @param quantity la nueva cantidad
     * @return ResponseEntity que contiene el registro de inventario actualizado
     */
    @PatchMapping("/{id}/quantity/{quantity}")
    ResponseEntity<Object> updateInventoryQuantity(
            @PathVariable("id") Long id,
            @PathVariable("quantity") Integer quantity);

    // ==================== Métodos DELETE ====================

    /**
     * Elimina un registro de inventario.
     *
     * @param id el ID del registro de inventario
     * @return ResponseEntity sin contenido
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteInventoryItem(@PathVariable("id") Long id);
}
