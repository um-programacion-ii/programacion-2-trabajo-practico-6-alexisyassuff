package com.example.dataservice.service;

import com.example.dataservice.entity.Category;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión de categorías.
 * Define los métodos para realizar operaciones CRUD y búsquedas personalizadas sobre categorías.
 */
public interface CategoryService {
    
    /**
     * Obtiene todas las categorías.
     *
     * @return lista de todas las categorías
     */
    List<Category> getAllCategories();
    
    /**
     * Obtiene una categoría por su ID.
     *
     * @param id el ID de la categoría
     * @return la categoría si se encuentra
     * @throws com.example.dataservice.exception.ResourceNotFoundException si la categoría no se encuentra
     */
    Category getCategoryById(Long id);
    
    /**
     * Obtiene una categoría por su nombre.
     *
     * @param name el nombre de la categoría
     * @return la categoría si se encuentra
     * @throws com.example.dataservice.exception.ResourceNotFoundException si la categoría no se encuentra
     */
    Category getCategoryByName(String name);
    
    /**
     * Busca categorías cuyo nombre contenga la cadena especificada (sin distinguir mayúsculas/minúsculas).
     *
     * @param name el patrón de nombre a buscar
     * @return lista de categorías que coinciden con el patrón de nombre
     */
    List<Category> findCategoriesByNameContaining(String name);
    
    /**
     * Crea una nueva categoría.
     *
     * @param category la categoría a crear
     * @return la categoría creada
     * @throws com.example.dataservice.exception.DuplicateResourceException si ya existe una categoría con el mismo nombre
     * @throws com.example.dataservice.exception.ValidationException si los datos de la categoría son inválidos
     */
    Category createCategory(Category category);
    
    /**
     * Actualiza una categoría existente.
     *
     * @param id el ID de la categoría
     * @param categoryDetails los datos actualizados de la categoría
     * @return la categoría actualizada
     * @throws com.example.dataservice.exception.ResourceNotFoundException si la categoría no se encuentra
     * @throws com.example.dataservice.exception.DuplicateResourceException si el nuevo nombre entra en conflicto con una categoría existente
     * @throws com.example.dataservice.exception.ValidationException si los datos de la categoría son inválidos
     */
    Category updateCategory(Long id, Category categoryDetails);
    
    /**
     * Elimina una categoría por su ID.
     *
     * @param id el ID de la categoría
     * @throws com.example.dataservice.exception.ResourceNotFoundException si la categoría no se encuentra
     */
    void deleteCategory(Long id);
    
    /**
     * Verifica si existe una categoría con el ID especificado.
     *
     * @param id el ID de la categoría
     * @return true si la categoría existe
     */
    boolean existsById(Long id);
    
    /**
     * Verifica si existe una categoría con el nombre especificado.
     *
     * @param name el nombre de la categoría
     * @return true si la categoría existe
     */
    boolean existsByName(String name);
}
