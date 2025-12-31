package com.example.dataservice.repository;

import com.example.dataservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interfaz de repositorio para la entidad Product.
 * Extiende JpaRepository proporcionando operaciones CRUD básicas y permite
 * definir consultas personalizadas para buscar productos por diferentes criterios.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * Busca productos cuyo nombre contenga la cadena especificada (sin distinguir mayúsculas/minúsculas).
     *
     * @param name el patrón de nombre a buscar
     * @return lista de productos que coinciden con el patrón de nombre
     */
    List<Product> findByNameContainingIgnoreCase(String name);
    
    /**
     * Busca productos por el ID de categoría.
     *
     * @param categoryId el ID de la categoría
     * @return lista de productos pertenecientes a la categoría especificada
     */
    List<Product> findByCategoryId(Long categoryId);
    
    /**
     * Busca productos con precio menor o igual al valor especificado.
     *
     * @param price el precio máximo
     * @return lista de productos con precio menor o igual al valor dado
     */
    List<Product> findByPriceLessThanEqual(BigDecimal price);
    
    /**
     * Busca productos con precio mayor o igual al valor especificado.
     *
     * @param price el precio mínimo
     * @return lista de productos con precio mayor o igual al valor dado
     */
    List<Product> findByPriceGreaterThanEqual(BigDecimal price);
    
    /**
     * Busca productos con precio entre los valores especificados (inclusive).
     *
     * @param minPrice el precio mínimo
     * @param maxPrice el precio máximo
     * @return lista de productos con precio dentro del rango especificado
     */
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * Consulta personalizada para buscar productos por nombre de categoría.
     * Realiza un JOIN entre Product y Category y compara nombres sin distinguir mayúsculas/minúsculas.
     *
     * @param categoryName el nombre de la categoría
     * @return lista de productos pertenecientes a la categoría con el nombre especificado
     */
    @Query("SELECT p FROM Product p JOIN p.category c WHERE LOWER(c.name) = LOWER(:categoryName)")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);
}