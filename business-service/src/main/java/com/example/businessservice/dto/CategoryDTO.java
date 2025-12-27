package com.example.businessservice.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Objeto de transferencia de datos (DTO) para la entidad Category.
 * Se utiliza para transferir datos de categorías entre el servicio de negocio y el servicio de datos.
 */
public class CategoryDTO {

    private Long id;

    @NotBlank(message = "Category name is required")
    private String name;

    private String description;

    /**
     * Constructor por defecto sin argumentos.
     */
    public CategoryDTO() {
    }

    /**
     * Constructor con todos los campos de la categoría.
     *
     * @param id el ID de la categoría
     * @param name el nombre de la categoría
     * @param description la descripción de la categoría
     */
    public CategoryDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // ==================== Getters y Setters ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ==================== Métodos heredados ====================

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
