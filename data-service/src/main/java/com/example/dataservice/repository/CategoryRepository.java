package com.example.dataservice.repository;

import com.example.dataservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de repositorio para la entidad Category.
 * Extiende JpaRepository proporcionando operaciones CRUD básicas y permite
 * definir consultas personalizadas para buscar categorías por diferentes criterios.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    /**
     * Busca una categoría por su nombre (sin distinguir mayúsculas/minúsculas).
     *
     * @param name el nombre de la categoría
     * @return un Optional que contiene la categoría si se encuentra
     */
    Optional<Category> findByNameIgnoreCase(String name);
    
    /**
     * Verifica si existe una categoría con el nombre especificado (sin distinguir mayúsculas/minúsculas).
     *
     * @param name el nombre de la categoría
     * @return true si existe una categoría con el nombre dado
     */
    boolean existsByNameIgnoreCase(String name);
    
    /**
     * Busca categorías cuyo nombre contenga la cadena especificada (sin distinguir mayúsculas/minúsculas).
     *
     * @param name el patrón de nombre a buscar
     * @return lista de categorías que coinciden con el patrón de nombre
     */
    List<Category> findByNameContainingIgnoreCase(String name);
    
    /**
     * Busca categorías cuya descripción contenga la cadena especificada (sin distinguir mayúsculas/minúsculas).
     *
     * @param description el patrón de descripción a buscar
     * @return lista de categorías que coinciden con el patrón de descripción
     */
    List<Category> findByDescriptionContainingIgnoreCase(String description);
}