package com.example.dataservice.service;

import com.example.dataservice.entity.Inventory;

import java.util.List;

/**
 * Interfaz de servicio para la gestión de registros de inventario.
 * Define los métodos para realizar operaciones CRUD y búsquedas personalizadas sobre inventario.
 */
public interface InventoryService {
    
    /**
     * Obtiene todos los registros de inventario.
     *
     * @return lista de todos los registros de inventario
     */
    List<Inventory> getAllInventoryItems();
    
    /**
     * Obtiene un registro de inventario por su ID.
     *
     * @param id el ID del registro de inventario
     * @return el registro de inventario si se encuentra
     * @throws com.example.dataservice.exception.ResourceNotFoundException si el registro de inventario no se encuentra
     */
    Inventory getInventoryItemById(Long id);
    
    /**
     * Busca registros de inventario por ID de producto.
     *
     * @param productId el ID del producto
     * @return lista de registros de inventario para el producto especificado
     */
    List<Inventory> findInventoryItemsByProductId(Long productId);
    
    /**
     * Busca registros de inventario por ubicación.
     *
     * @param location la ubicación
     * @return lista de registros de inventario en la ubicación especificada
     */
    List<Inventory> findInventoryItemsByLocation(String location);
    
    /**
     * Busca registros de inventario con cantidad menor al valor especificado.
     *
     * @param quantity el umbral de cantidad
     * @return lista de registros de inventario con cantidad menor al valor dado
     */
    List<Inventory> findInventoryItemsByQuantityLessThan(Integer quantity);
    
    /**
     * Busca registros de inventario con cantidad mayor al valor especificado.
     *
     * @param quantity el umbral de cantidad
     * @return lista de registros de inventario con cantidad mayor al valor dado
     */
    List<Inventory> findInventoryItemsByQuantityGreaterThan(Integer quantity);
    
    /**
     * Busca registros de inventario con cantidad entre los valores especificados (inclusive).
     *
     * @param minQuantity la cantidad mínima
     * @param maxQuantity la cantidad máxima
     * @return lista de registros de inventario con cantidad dentro del rango especificado
     */
    List<Inventory> findInventoryItemsByQuantityBetween(Integer minQuantity, Integer maxQuantity);
    
    /**
     * Busca registros de inventario por nombre de producto.
     *
     * @param productName el nombre del producto
     * @return lista de registros de inventario para productos que coinciden con el nombre dado
     */
    List<Inventory> findInventoryItemsByProductName(String productName);
    
    /**
     * Busca registros de inventario por categoría de producto.
     *
     * @param categoryId el ID de la categoría
     * @return lista de registros de inventario para productos en la categoría especificada
     */
    List<Inventory> findInventoryItemsByProductCategory(Long categoryId);
    
    /**
     * Busca registros de inventario sin stock (cantidad = 0).
     *
     * @return lista de registros de inventario sin stock
     */
    List<Inventory> findOutOfStockItems();
    
    /**
     * Crea un nuevo registro de inventario.
     *
     * @param inventory el registro de inventario a crear
     * @return el registro de inventario creado
     * @throws com.example.dataservice.exception.ValidationException si los datos del inventario son inválidos
     * @throws com.example.dataservice.exception.ResourceNotFoundException si el producto referenciado no se encuentra
     */
    Inventory createInventoryItem(Inventory inventory);
    
    /**
     * Actualiza un registro de inventario existente.
     *
     * @param id el ID del registro de inventario
     * @param inventoryDetails los datos actualizados del inventario
     * @return el registro de inventario actualizado
     * @throws com.example.dataservice.exception.ResourceNotFoundException si el registro de inventario no se encuentra
     * @throws com.example.dataservice.exception.ValidationException si los datos del inventario son inválidos
     */
    Inventory updateInventoryItem(Long id, Inventory inventoryDetails);
    
    /**
     * Actualiza la cantidad de un registro de inventario.
     *
     * @param id el ID del registro de inventario
     * @param quantity la nueva cantidad
     * @return el registro de inventario actualizado
     * @throws com.example.dataservice.exception.ResourceNotFoundException si el registro de inventario no se encuentra
     * @throws com.example.dataservice.exception.ValidationException si la cantidad es inválida
     */
    Inventory updateInventoryQuantity(Long id, Integer quantity);
    
    /**
     * Elimina un registro de inventario por su ID.
     *
     * @param id el ID del registro de inventario
     * @throws com.example.dataservice.exception.ResourceNotFoundException si el registro de inventario no se encuentra
     */
    void deleteInventoryItem(Long id);
    
    /**
     * Verifica si existe un registro de inventario con el ID especificado.
     *
     * @param id el ID del registro de inventario
     * @return true si el registro de inventario existe
     */
    boolean existsById(Long id);
}
