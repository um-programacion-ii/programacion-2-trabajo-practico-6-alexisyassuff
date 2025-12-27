package com.example.businessservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Objeto de transferencia de datos (DTO) para la entidad Product.
 * Se utiliza para transferir datos de productos entre el servicio de negocio y el servicio de datos.
 */
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Product name is required")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    private CategoryDTO category;

    /**
     * Constructor por defecto sin argumentos.
     */
    public ProductDTO() {
    }

    /**
     * Constructor con los campos principales del producto.
     *
     * @param id el ID del producto
     * @param name el nombre del producto
     * @param description la descripción del producto
     * @param price el precio del producto
     */
    public ProductDTO(Long id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * Constructor completo con todos los campos incluyendo la categoría.
     *
     * @param id el ID del producto
     * @param name el nombre del producto
     * @param description la descripción del producto
     * @param price el precio del producto
     * @param category la categoría del producto
     */
    public ProductDTO(Long id, String name, String description, BigDecimal price, CategoryDTO category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    // ==================== Métodos heredados ====================

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + (category != null ? category.getName() : null) +
                '}';
    }
}
