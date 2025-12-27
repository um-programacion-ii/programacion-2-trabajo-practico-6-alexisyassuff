package com.example.dataservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

/**
 * JPA entity that represents a product category.
 * Categories are used to organize and classify products within the system.
 * Each category can contain multiple products.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category name is required")
    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

    /**
     * Default no-args constructor required by JPA.
     */
    public Category() {
    }

    /**
     * Constructs a new Category instance with the specified name and description.
     *
     * @param name the category name
     * @param description the category description
     */
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    /**
     * Helper method to add a product to this category.
     * Maintains the bidirectional relationship between Category and Product.
     *
     * @param product the product to add to this category
     */
    public void addProduct(Product product) {
        products.add(product);
        product.setCategory(this);
    }

    /**
     * Helper method to remove a product from this category.
     * Clears the bidirectional relationship between Category and Product.
     *
     * @param product the product to remove from this category
     */
    public void removeProduct(Product product) {
        products.remove(product);
        product.setCategory(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return id != null && id.equals(category.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}