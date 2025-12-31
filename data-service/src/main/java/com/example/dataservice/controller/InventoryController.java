package com.example.dataservice.controller;

import com.example.dataservice.entity.Inventory;
import com.example.dataservice.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de registros de inventario.
 */
@RestController
@RequestMapping("/data/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param inventoryService servicio de inventario
     */
    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // ==================== Métodos GET ====================

    /**
     * GET /data/inventory : Obtiene todos los registros de inventario.
     *
     * @return ResponseEntity con estado 200 (OK) y la lista de registros de inventario en el cuerpo
     */
    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventoryItems() {
        List<Inventory> inventoryItems = inventoryService.getAllInventoryItems();
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    /**
     * GET /data/inventory/{id} : Obtiene un registro de inventario por su ID.
     *
     * @param id el ID del registro de inventario a obtener
     * @return ResponseEntity con estado 200 (OK) y el registro de inventario en el cuerpo,
     *         o con estado 404 (Not Found) si el registro de inventario no se encuentra
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryItemById(@PathVariable Long id) {
        Inventory inventoryItem = inventoryService.getInventoryItemById(id);
        return new ResponseEntity<>(inventoryItem, HttpStatus.OK);
    }

    /**
     * GET /data/inventory/product/{productId} : Obtiene registros de inventario por ID de producto.
     *
     * @param productId el ID del producto
     * @return ResponseEntity con estado 200 (OK) y la lista de registros de inventario para el producto especificado en el cuerpo,
     *         o con estado 404 (Not Found) si el producto no se encuentra
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Inventory>> getInventoryItemsByProductId(@PathVariable Long productId) {
        List<Inventory> inventoryItems = inventoryService.findInventoryItemsByProductId(productId);
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    /**
     * GET /data/inventory/location/{location} : Obtiene registros de inventario por ubicación.
     *
     * @param location la ubicación
     * @return ResponseEntity con estado 200 (OK) y la lista de registros de inventario en la ubicación especificada en el cuerpo
     */
    @GetMapping("/location/{location}")
    public ResponseEntity<List<Inventory>> getInventoryItemsByLocation(@PathVariable String location) {
        List<Inventory> inventoryItems = inventoryService.findInventoryItemsByLocation(location);
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    /**
     * GET /data/inventory/quantity/less/{quantity} : Obtiene registros de inventario con cantidad menor al valor especificado.
     *
     * @param quantity el umbral de cantidad
     * @return ResponseEntity con estado 200 (OK) y la lista de registros de inventario con cantidad menor al valor dado en el cuerpo
     */
    @GetMapping("/quantity/less/{quantity}")
    public ResponseEntity<List<Inventory>> getInventoryItemsByQuantityLessThan(@PathVariable Integer quantity) {
        List<Inventory> inventoryItems = inventoryService.findInventoryItemsByQuantityLessThan(quantity);
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    /**
     * GET /data/inventory/quantity/greater/{quantity} : Obtiene registros de inventario con cantidad mayor al valor especificado.
     *
     * @param quantity el umbral de cantidad
     * @return ResponseEntity con estado 200 (OK) y la lista de registros de inventario con cantidad mayor al valor dado en el cuerpo
     */
    @GetMapping("/quantity/greater/{quantity}")
    public ResponseEntity<List<Inventory>> getInventoryItemsByQuantityGreaterThan(@PathVariable Integer quantity) {
        List<Inventory> inventoryItems = inventoryService.findInventoryItemsByQuantityGreaterThan(quantity);
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    /**
     * GET /data/inventory/quantity/range : Obtiene registros de inventario con cantidad entre los valores especificados.
     *
     * @param minQuantity la cantidad mínima
     * @param maxQuantity la cantidad máxima
     * @return ResponseEntity con estado 200 (OK) y la lista de registros de inventario con cantidad dentro del rango especificado en el cuerpo
     */
    @GetMapping("/quantity/range")
    public ResponseEntity<List<Inventory>> getInventoryItemsByQuantityBetween(
            @RequestParam Integer minQuantity, @RequestParam Integer maxQuantity) {
        List<Inventory> inventoryItems = inventoryService.findInventoryItemsByQuantityBetween(minQuantity, maxQuantity);
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    /**
     * GET /data/inventory/product/name/{productName} : Obtiene registros de inventario por nombre de producto.
     *
     * @param productName el nombre del producto
     * @return ResponseEntity con estado 200 (OK) y la lista de registros de inventario para productos que coinciden con el nombre dado en el cuerpo
     */
    @GetMapping("/product/name/{productName}")
    public ResponseEntity<List<Inventory>> getInventoryItemsByProductName(@PathVariable String productName) {
        List<Inventory> inventoryItems = inventoryService.findInventoryItemsByProductName(productName);
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    /**
     * GET /data/inventory/category/{categoryId} : Obtiene registros de inventario por categoría de producto.
     *
     * @param categoryId el ID de la categoría
     * @return ResponseEntity con estado 200 (OK) y la lista de registros de inventario para productos en la categoría especificada en el cuerpo
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Inventory>> getInventoryItemsByProductCategory(@PathVariable Long categoryId) {
        List<Inventory> inventoryItems = inventoryService.findInventoryItemsByProductCategory(categoryId);
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    /**
     * GET /data/inventory/out-of-stock : Obtiene registros de inventario sin stock (cantidad = 0).
     *
     * @return ResponseEntity con estado 200 (OK) y la lista de registros de inventario sin stock en el cuerpo
     */
    @GetMapping("/out-of-stock")
    public ResponseEntity<List<Inventory>> getOutOfStockItems() {
        List<Inventory> inventoryItems = inventoryService.findOutOfStockItems();
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    // ==================== Métodos POST ====================

    /**
     * POST /data/inventory : Crea un nuevo registro de inventario.
     *
     * @param inventory el registro de inventario a crear
     * @return ResponseEntity con estado 201 (Created) y el nuevo registro de inventario en el cuerpo,
     *         o con estado 400 (Bad Request) si los datos del inventario son inválidos,
     *         o con estado 404 (Not Found) si el producto referenciado no se encuentra
     */
    @PostMapping
    public ResponseEntity<Inventory> createInventoryItem(@Valid @RequestBody Inventory inventory) {
        Inventory newInventory = inventoryService.createInventoryItem(inventory);
        return new ResponseEntity<>(newInventory, HttpStatus.CREATED);
    }

    // ==================== Métodos PUT/PATCH ====================

    /**
     * PUT /data/inventory/{id} : Actualiza un registro de inventario existente.
     *
     * @param id el ID del registro de inventario a actualizar
     * @param inventory los datos actualizados del inventario
     * @return ResponseEntity con estado 200 (OK) y el registro de inventario actualizado en el cuerpo,
     *         o con estado 400 (Bad Request) si los datos del inventario son inválidos,
     *         o con estado 404 (Not Found) si el registro de inventario o el producto referenciado no se encuentran
     */
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventoryItem(@PathVariable Long id, @Valid @RequestBody Inventory inventory) {
        Inventory updatedInventory = inventoryService.updateInventoryItem(id, inventory);
        return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
    }

    /**
     * PATCH /data/inventory/{id}/quantity/{quantity} : Actualiza la cantidad de un registro de inventario.
     *
     * @param id el ID del registro de inventario a actualizar
     * @param quantity la nueva cantidad
     * @return ResponseEntity con estado 200 (OK) y el registro de inventario actualizado en el cuerpo,
     *         o con estado 400 (Bad Request) si la cantidad es inválida,
     *         o con estado 404 (Not Found) si el registro de inventario no se encuentra
     */
    @PatchMapping("/{id}/quantity/{quantity}")
    public ResponseEntity<Inventory> updateInventoryQuantity(@PathVariable Long id, @PathVariable Integer quantity) {
        Inventory updatedInventory = inventoryService.updateInventoryQuantity(id, quantity);
        return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
    }

    // ==================== Métodos DELETE ====================

    /**
     * DELETE /data/inventory/{id} : Elimina un registro de inventario.
     *
     * @param id el ID del registro de inventario a eliminar
     * @return ResponseEntity con estado 204 (No Content),
     *         o con estado 404 (Not Found) si el registro de inventario no se encuentra
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable Long id) {
        inventoryService.deleteInventoryItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
