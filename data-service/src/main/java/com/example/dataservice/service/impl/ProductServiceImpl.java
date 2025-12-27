package com.example.dataservice.service.impl;

import com.example.dataservice.entity.Category;
import com.example.dataservice.entity.Product;
import com.example.dataservice.exception.ResourceNotFoundException;
import com.example.dataservice.exception.ValidationException;
import com.example.dataservice.repository.ProductRepository;
import com.example.dataservice.service.CategoryService;
import com.example.dataservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementación de la interfaz ProductService.
 * Proporciona la lógica de negocio para la gestión de productos.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param productRepository repositorio de productos
     * @param categoryService servicio de categorías
     */
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    // ==================== Métodos de Lectura/Consulta ====================

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    @Override
    public List<Product> findProductsByNameContaining(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Product> findProductsByCategoryId(Long categoryId) {
        // Verificar que la categoría existe
        if (!categoryService.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category", "id", categoryId);
        }
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> findProductsByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    @Override
    public List<Product> findProductsByPriceLessThanEqual(BigDecimal price) {
        return productRepository.findByPriceLessThanEqual(price);
    }

    @Override
    public List<Product> findProductsByPriceGreaterThanEqual(BigDecimal price) {
        return productRepository.findByPriceGreaterThanEqual(price);
    }

    @Override
    public List<Product> findProductsByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    // ==================== Métodos de Escritura/Modificación ====================

    @Override
    @Transactional
    public Product createProduct(Product product) {
        validateProduct(product);
        
        // Si se especifica una categoría, verificar que existe
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Category category = categoryService.getCategoryById(product.getCategory().getId());
            product.setCategory(category);
        }
        
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);
        
        validateProduct(productDetails);
        
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        
        // Si se especifica una categoría, verificar que existe
        if (productDetails.getCategory() != null && productDetails.getCategory().getId() != null) {
            Category category = categoryService.getCategoryById(productDetails.getCategory().getId());
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }
        
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product assignCategoryToProduct(Long productId, Long categoryId) {
        Product product = getProductById(productId);
        Category category = categoryService.getCategoryById(categoryId);
        
        product.setCategory(category);
        
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product removeCategoryFromProduct(Long productId) {
        Product product = getProductById(productId);
        
        product.setCategory(null);
        
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        
        productRepository.deleteById(id);
    }

    // ==================== Métodos Privados ====================

    /**
     * Valida los datos de un producto.
     *
     * @param product el producto a validar
     * @throws ValidationException si los datos del producto son inválidos
     */
    private void validateProduct(Product product) {
        ValidationException validationException = new ValidationException("Product validation failed");
        
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            validationException.addError("name", "Product name is required");
        }
        
        if (product.getPrice() == null) {
            validationException.addError("price", "Product price is required");
        } else if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            validationException.addError("price", "Product price must be positive");
        }
        
        if (validationException.hasErrors()) {
            throw validationException;
        }
    }
}
