package com.example.businessservice.controller;

import com.example.businessservice.dto.InventoryDTO;
import com.example.businessservice.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de registros de inventario en el servicio de negocio.
 */
@RestController
@RequestMapping("/api/inventory")
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
     * GET /api/inventory : Obtiene todos los registros de inventario.
     *
     * @return ResponseEntity con estado 200 (OK) y la lista de registros de inventario en el cuerpo
     */
    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllInventoryItems() {
        List<InventoryDTO> inventoryItems = inventoryService.getAllInventoryItems();
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    /**
     * GET /api/inventory/{id} : Obtiene un registro de inventario por su ID.
     *
     * @param id el ID del registro de inventario a obtener
     * @return ResponseEntity con estado 200 (OK) y el registro de inventario en el cuerpo,
     *         o con estado 404 (Not Found) si el registro de inventario no se encuentra
     */
    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getInventoryItemById(@PathVariable Long id) {
        InventoryDTO inventoryItem = inventoryService.getInventoryItemById(id);
        if (inventoryItem != null) {
            return new ResponseEntity<>(inventoryItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /api/inventory/product/{productId} : Obtiene registros de inventario por ID de producto.
     *
     * @param productId el ID del producto
     * @return ResponseEntity con estado 200 (OK) y la lista de registros de inventario para el producto especificado en el cuerpo
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<InventoryDTO>> getInventoryItemsByProductId(@PathVariable Long productId) {
        List<InventoryDTO> inventoryItems = inventoryService.findInventoryItemsByProductId(productId);
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    /**
     * GET /api/inventory/location/{location} : Obtiene registros de inventario por ubicación.
     *
     * @param location la ubicación
     * @return ResponseEntity con estado 200 (OK) y la lista de registros de inventario en la ubicación especificada en el cuerpo
     */
    @GetMapping("/location/{location}")
    public ResponseEntity<List<InventoryDTO>> getInventoryItemsByLocation(@PathVariable String location) {
        List<InventoryDTO> inventoryItems = inventoryService.findInventoryItemsByLocation(location);
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    /**
     * GET /api/inventory/quantity/less/{quantity} : Obtiene registros de inventario con cantidad menor al valor especificado.
     *
     * @param quantity el umbral de cantidad
     * @return ResponseEntity con estado 200 (OK) y la lista de registros de inventario con cantidad menor al valor dado en el cuerpo
     */
    @GetMapping("/quantity/less/{quantity}")
    public ResponseEntity<List<InventoryDTO>> getInventoryItemsByQuantityLessThan(@PathVariable Integer quantity) {
        List<InventoryDTO> inventoryItems = inventoryService.findInventoryItemsByQuantityLessThan(quantity);
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    /**
     * GET /api/inventory/quantity/greater/{quantity} : Obtiene registros de inventario con cantidad mayor al valor especificado.
     *
     * @param quantity el umbral de cantidad
     * @return ResponseEntity con estado 200 (OK) y la lista de registros de inventario con cantidad mayor al valor dado en el cuerpo
     */
    @GetMapping("/quantity/greater/{quantity}")
    public ResponseEntity<List<InventoryDTO>> getInventoryItemsByQuantityGreaterThan(@PathVariable Integer quantity) {
        List<InventoryDTO> inventoryItems = inventoryService.findInventoryItemsByQuantityGreaterThan(quantity);
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    /**
     * GET /api/inventory/quantity/range : Obtiene registros de inventario con cantidad entre los valores especificados.
     *
     * @param minQuantity la cantidad mínima
     * @param maxQuantity la cantidad máxima
     * @return ResponseEntity con estado 200 (OK) y la lista de registros de inventario con cantidad dentro del rango especificado en el cuerpo
     */
    @GetMapping("/quantity/range")
    public ResponseEntity<List<InventoryDTO>> getInventoryItemsByQuantityBetween(
            @RequestParam Integer minQuantity, @RequestParam Integer maxQuantity) {
        List<InventoryDTO> inventoryItems = inventoryService.findInventoryItemsByQuantityBetween(minQuantity, maxQuantity);
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    /**
     * GET /api/inventory/product/name/{productName} : Obtiene registros de inventario por nombre de producto.
     *
     * @param productName el nombre del producto
     * @return ResponseEntity con estado 200 (OK) y la lista de registros de inventario para productos que coinciden con el nombre dado en el cuerpo
     */
    @GetMapping("/product/name/{productName}")
    public ResponseEntity<List<InventoryDTO>> getInventoryItemsByProductName(@PathVariable String productName) {
        List<InventoryDTO> inventoryItems = inventoryService.findInventoryItemsByProductName(productName);
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    /**
     * GET /api/inventory/category/{categoryId} : Obtiene registros de inventario por categoría de producto.
     *
     * @param categoryId el ID de la categoría
     * @return ResponseEntity con estado 200 (OK) y la lista de registros de inventario para productos en la categoría especificada en el cuerpo
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<InventoryDTO>> getInventoryItemsByProductCategory(@PathVariable Long categoryId) {
        List<InventoryDTO> inventoryItems = inventoryService.findInventoryItemsByProductCategory(categoryId);
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    /**
     * GET /api/inventory/out-of-stock : Obtiene registros de inventario sin stock (cantidad = 0).
     *
     * @return ResponseEntity con estado 200 (OK) y la lista de registros de inventario sin stock en el cuerpo
     */
    @GetMapping("/out-of-stock")
    public ResponseEntity<List<InventoryDTO>> getOutOfStockItems() {
        List<InventoryDTO> inventoryItems = inventoryService.findOutOfStockItems();
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    // ==================== Métodos POST ====================

    /**
     * POST /api/inventory : Crea un nuevo registro de inventario.
     *
     * @param inventoryDTO el registro de inventario a crear
     * @return ResponseEntity con estado 201 (Created) y el nuevo registro de inventario en el cuerpo,
     *         o con estado 500 (Internal Server Error) si ocurre un error
     */
    @PostMapping
    public ResponseEntity<InventoryDTO> createInventoryItem(@Valid @RequestBody InventoryDTO inventoryDTO) {
        InventoryDTO newInventory = inventoryService.createInventoryItem(inventoryDTO);
        if (newInventory != null) {
            return new ResponseEntity<>(newInventory, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== Métodos PUT/PATCH ====================

    /**
     * PUT /api/inventory/{id} : Actualiza un registro de inventario existente.
     *
     * @param id el ID del registro de inventario a actualizar
     * @param inventoryDTO los datos actualizados del inventario
     * @return ResponseEntity con estado 200 (OK) y el registro de inventario actualizado en el cuerpo,
     *         o con estado 404 (Not Found) si el registro de inventario no se encuentra
     */
    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> updateInventoryItem(@PathVariable Long id, @Valid @RequestBody InventoryDTO inventoryDTO) {
        InventoryDTO updatedInventory = inventoryService.updateInventoryItem(id, inventoryDTO);
        if (updatedInventory != null) {
            return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * PATCH /api/inventory/{id}/quantity/{quantity} : Actualiza la cantidad de un registro de inventario.
     *
     * @param id el ID del registro de inventario a actualizar
     * @param quantity la nueva cantidad
     * @return ResponseEntity con estado 200 (OK) y el registro de inventario actualizado en el cuerpo,
     *         o con estado 404 (Not Found) si el registro de inventario no se encuentra
     */
    @PatchMapping("/{id}/quantity/{quantity}")
    public ResponseEntity<InventoryDTO> updateInventoryQuantity(@PathVariable Long id, @PathVariable Integer quantity) {
        InventoryDTO updatedInventory = inventoryService.updateInventoryQuantity(id, quantity);
        if (updatedInventory != null) {
            return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ==================== Métodos DELETE ====================

    /**
     * DELETE /api/inventory/{id} : Elimina un registro de inventario.
     *
     * @param id el ID del registro de inventario a eliminar
     * @return ResponseEntity con estado 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable Long id) {
        inventoryService.deleteInventoryItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
