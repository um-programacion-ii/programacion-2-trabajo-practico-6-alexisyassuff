package com.example.dataservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * JPA entity that represents inventory information for a product.
 * Tracks the quantity and location of products in stock within the system.
 */
@Entity
@Table(name = "inventories")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String location;

    /**
     * Default no-args constructor required by JPA.
     */
    public Inventory() {
    }

    /**
     * Constructs a new Inventory instance with the specified product, quantity, and location.
     *
     * @param product the product associated with this inventory record
     * @param quantity the quantity of products in stock
     * @param location the storage location of the inventory
     */
    public Inventory(Product product, Integer quantity, String location) {
        this.product = product;
        this.quantity = quantity;
        this.location = location;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inventory)) return false;
        Inventory inventory = (Inventory) o;
        return id != null && id.equals(inventory.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", product=" + (product != null ? product.getId() : null) +
                ", quantity=" + quantity +
                ", location='" + location + '\'' +
                '}';
    }
}