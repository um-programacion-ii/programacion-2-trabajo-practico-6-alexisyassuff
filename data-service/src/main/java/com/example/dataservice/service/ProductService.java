package com.example.dataservice.service;

import com.example.dataservice.entity.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interfaz de servicio para la gestión de productos.
 * Define los métodos para realizar operaciones CRUD y búsquedas personalizadas sobre productos.
 */
public interface ProductService {
    
    /**
     * Obtiene todos los productos.
     *
     * @return lista de todos los productos
     */
    List<Product> getAllProducts();
    
    /**
     * Obtiene un producto por su ID.
     *
     * @param id el ID del producto
     * @return el producto si se encuentra
     * @throws com.example.dataservice.exception.ResourceNotFoundException si el producto no se encuentra
     */
    Product getProductById(Long id);
    
    /**
     * Busca productos cuyo nombre contenga la cadena especificada (sin distinguir mayúsculas/minúsculas).
     *
     * @param name el patrón de nombre a buscar
     * @return lista de productos que coinciden con el patrón de nombre
     */
    List<Product> findProductsByNameContaining(String name);
    
    /**
     * Busca productos por ID de categoría.
     *
     * @param categoryId el ID de la categoría
     * @return lista de productos en la categoría especificada
     */
    List<Product> findProductsByCategoryId(Long categoryId);
    
    /**
     * Busca productos por nombre de categoría.
     *
     * @param categoryName el nombre de la categoría
     * @return lista de productos en la categoría especificada
     */
    List<Product> findProductsByCategoryName(String categoryName);
    
    /**
     * Busca productos con precio menor o igual al valor especificado.
     *
     * @param price el precio máximo
     * @return lista de productos con precio menor o igual al valor dado
     */
    List<Product> findProductsByPriceLessThanEqual(BigDecimal price);
    
    /**
     * Busca productos con precio mayor o igual al valor especificado.
     *
     * @param price el precio mínimo
     * @return lista de productos con precio mayor o igual al valor dado
     */
    List<Product> findProductsByPriceGreaterThanEqual(BigDecimal price);
    
    /**
     * Busca productos con precio entre los valores especificados (inclusive).
     *
     * @param minPrice el precio mínimo
     * @param maxPrice el precio máximo
     * @return lista de productos con precio dentro del rango especificado
     */
    List<Product> findProductsByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * Crea un nuevo producto.
     *
     * @param product el producto a crear
     * @return el producto creado
     * @throws com.example.dataservice.exception.ValidationException si los datos del producto son inválidos
     * @throws com.example.dataservice.exception.ResourceNotFoundException si la categoría referenciada no se encuentra
     */
    Product createProduct(Product product);
    
    /**
     * Actualiza un producto existente.
     *
     * @param id el ID del producto
     * @param productDetails los datos actualizados del producto
     * @return el producto actualizado
     * @throws com.example.dataservice.exception.ResourceNotFoundException si el producto no se encuentra
     * @throws com.example.dataservice.exception.ValidationException si los datos del producto son inválidos
     */
    Product updateProduct(Long id, Product productDetails);
    
    /**
     * Elimina un producto por su ID.
     *
     * @param id el ID del producto
     * @throws com.example.dataservice.exception.ResourceNotFoundException si el producto no se encuentra
     */
    void deleteProduct(Long id);
    
    /**
     * Verifica si existe un producto con el ID especificado.
     *
     * @param id el ID del producto
     * @return true si el producto existe
     */
    boolean existsById(Long id);
    
    /**
     * Asigna una categoría a un producto.
     *
     * @param productId el ID del producto
     * @param categoryId el ID de la categoría
     * @return el producto actualizado
     * @throws com.example.dataservice.exception.ResourceNotFoundException si el producto o categoría no se encuentran
     */
    Product assignCategoryToProduct(Long productId, Long categoryId);
    
    /**
     * Remueve la categoría de un producto.
     *
     * @param productId el ID del producto
     * @return el producto actualizado
     * @throws com.example.dataservice.exception.ResourceNotFoundException si el producto no se encuentra
     */
    Product removeCategoryFromProduct(Long productId);
}
