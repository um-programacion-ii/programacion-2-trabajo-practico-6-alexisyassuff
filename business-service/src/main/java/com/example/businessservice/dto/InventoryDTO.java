package com.example.businessservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Objeto de transferencia de datos (DTO) para la entidad Inventory.
 * Se utiliza para transferir datos de inventario entre el servicio de negocio y el servicio de datos.
 */
public class InventoryDTO {

    private Long id;

    @NotNull(message = "Product is required")
    private ProductDTO product;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @NotNull(message = "Location is required")
    private String location;

    /**
     * Constructor por defecto sin argumentos.
     */
    public InventoryDTO() {
    }

    /**
     * Constructor con todos los campos del inventario.
     *
     * @param id el ID del registro de inventario
     * @param product el producto asociado
     * @param quantity la cantidad en stock
     * @param location la ubicación del inventario
     */
    public InventoryDTO(Long id, ProductDTO product, Integer quantity, String location) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.location = location;
    }

    // ==================== Getters y Setters ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // ==================== Métodos heredados ====================

    @Override
    public String toString() {
        return "InventoryDTO{" +
                "id=" + id +
                ", product=" + (product != null ? product.getName() : null) +
                ", quantity=" + quantity +
                ", location='" + location + '\'' +
                '}';
    }
}
