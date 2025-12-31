package com.example.dataservice.repository;

import com.example.dataservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz de repositorio para la entidad Inventory.
 * Extiende JpaRepository proporcionando operaciones CRUD básicas y permite
 * definir consultas personalizadas para buscar registros de inventario por diferentes criterios.
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    
    /**
     * Busca registros de inventario por ID de producto.
     *
     * @param productId el ID del producto
     * @return lista de registros de inventario para el producto especificado
     */
    List<Inventory> findByProductId(Long productId);
    
    /**
     * Busca registros de inventario por ubicación (sin distinguir mayúsculas/minúsculas).
     *
     * @param location la ubicación
     * @return lista de registros de inventario en la ubicación especificada
     */
    List<Inventory> findByLocationIgnoreCase(String location);
    
    /**
     * Busca registros de inventario con cantidad menor al valor especificado.
     *
     * @param quantity el umbral de cantidad
     * @return lista de registros de inventario con cantidad menor al valor dado
     */
    List<Inventory> findByQuantityLessThan(Integer quantity);
    
    /**
     * Busca registros de inventario con cantidad mayor al valor especificado.
     *
     * @param quantity el umbral de cantidad
     * @return lista de registros de inventario con cantidad mayor al valor dado
     */
    List<Inventory> findByQuantityGreaterThan(Integer quantity);
    
    /**
     * Busca registros de inventario con cantidad entre los valores especificados (inclusive).
     *
     * @param minQuantity la cantidad mínima
     * @param maxQuantity la cantidad máxima
     * @return lista de registros de inventario con cantidad dentro del rango especificado
     */
    List<Inventory> findByQuantityBetween(Integer minQuantity, Integer maxQuantity);
    
    /**
     * Consulta personalizada para buscar registros de inventario por nombre de producto.
     * Realiza un JOIN entre Inventory y Product y busca productos cuyo nombre contenga la cadena especificada.
     *
     * @param productName el nombre del producto (o parte de él)
     * @return lista de registros de inventario para productos que coinciden con el nombre dado
     */
    @Query("SELECT i FROM Inventory i JOIN i.product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :productName, '%'))")
    List<Inventory> findByProductNameContaining(@Param("productName") String productName);
    
    /**
     * Consulta personalizada para buscar registros de inventario por categoría de producto.
     * Realiza un JOIN entre Inventory, Product y Category para filtrar por ID de categoría.
     *
     * @param categoryId el ID de la categoría
     * @return lista de registros de inventario para productos en la categoría especificada
     */
    @Query("SELECT i FROM Inventory i JOIN i.product p WHERE p.category.id = :categoryId")
    List<Inventory> findByProductCategoryId(@Param("categoryId") Long categoryId);
    
    /**
     * Busca registros de inventario con cantidad igual al valor especificado.
     * Útil para encontrar productos sin stock (cantidad = 0).
     *
     * @param quantity la cantidad exacta a buscar
     * @return lista de registros de inventario con la cantidad especificada
     */
    List<Inventory> findByQuantityEquals(Integer quantity);
}