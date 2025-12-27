package com.example.dataservice.service.impl;

import com.example.dataservice.entity.Inventory;
import com.example.dataservice.entity.Product;
import com.example.dataservice.exception.ResourceNotFoundException;
import com.example.dataservice.exception.ValidationException;
import com.example.dataservice.repository.InventoryRepository;
import com.example.dataservice.service.InventoryService;
import com.example.dataservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación de la interfaz InventoryService.
 * Proporciona la lógica de negocio para la gestión de inventario.
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductService productService;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param inventoryRepository repositorio de inventario
     * @param productService servicio de productos
     */
    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository, ProductService productService) {
        this.inventoryRepository = inventoryRepository;
        this.productService = productService;
    }

    // ==================== Métodos de Lectura/Consulta ====================

    @Override
    public List<Inventory> getAllInventoryItems() {
        return inventoryRepository.findAll();
    }

    @Override
    public Inventory getInventoryItemById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "id", id));
    }

    @Override
    public List<Inventory> findInventoryItemsByProductId(Long productId) {
        // Verificar que el producto existe
        if (!productService.existsById(productId)) {
            throw new ResourceNotFoundException("Product", "id", productId);
        }
        return inventoryRepository.findByProductId(productId);
    }

    @Override
    public List<Inventory> findInventoryItemsByLocation(String location) {
        return inventoryRepository.findByLocationIgnoreCase(location);
    }

    @Override
    public List<Inventory> findInventoryItemsByQuantityLessThan(Integer quantity) {
        return inventoryRepository.findByQuantityLessThan(quantity);
    }

    @Override
    public List<Inventory> findInventoryItemsByQuantityGreaterThan(Integer quantity) {
        return inventoryRepository.findByQuantityGreaterThan(quantity);
    }

    @Override
    public List<Inventory> findInventoryItemsByQuantityBetween(Integer minQuantity, Integer maxQuantity) {
        return inventoryRepository.findByQuantityBetween(minQuantity, maxQuantity);
    }

    @Override
    public List<Inventory> findInventoryItemsByProductName(String productName) {
        return inventoryRepository.findByProductNameContaining(productName);
    }

    @Override
    public List<Inventory> findInventoryItemsByProductCategory(Long categoryId) {
        return inventoryRepository.findByProductCategoryId(categoryId);
    }

    @Override
    public List<Inventory> findOutOfStockItems() {
        return inventoryRepository.findByQuantityEquals(0);
    }

    @Override
    public boolean existsById(Long id) {
        return inventoryRepository.existsById(id);
    }

    // ==================== Métodos de Escritura/Modificación ====================

    @Override
    @Transactional
    public Inventory createInventoryItem(Inventory inventory) {
        validateInventory(inventory);
        
        // Verificar que el producto existe
        if (inventory.getProduct() != null && inventory.getProduct().getId() != null) {
            Product product = productService.getProductById(inventory.getProduct().getId());
            inventory.setProduct(product);
        } else {
            throw new ValidationException("Product is required for inventory item")
                    .addError("product", "Product is required");
        }
        
        return inventoryRepository.save(inventory);
    }

    @Override
    @Transactional
    public Inventory updateInventoryItem(Long id, Inventory inventoryDetails) {
        Inventory inventory = getInventoryItemById(id);
        
        validateInventory(inventoryDetails);
        
        // Verificar que el producto existe si se está cambiando
        if (inventoryDetails.getProduct() != null && inventoryDetails.getProduct().getId() != null) {
            if (!inventory.getProduct().getId().equals(inventoryDetails.getProduct().getId())) {
                Product product = productService.getProductById(inventoryDetails.getProduct().getId());
                inventory.setProduct(product);
            }
        }
        
        inventory.setQuantity(inventoryDetails.getQuantity());
        inventory.setLocation(inventoryDetails.getLocation());
        
        return inventoryRepository.save(inventory);
    }

    @Override
    @Transactional
    public Inventory updateInventoryQuantity(Long id, Integer quantity) {
        Inventory inventory = getInventoryItemById(id);
        
        if (quantity == null || quantity < 0) {
            throw new ValidationException("Invalid quantity")
                    .addError("quantity", "Quantity cannot be negative");
        }
        
        inventory.setQuantity(quantity);
        
        return inventoryRepository.save(inventory);
    }

    @Override
    @Transactional
    public void deleteInventoryItem(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inventory", "id", id);
        }
        
        inventoryRepository.deleteById(id);
    }

    // ==================== Métodos Privados ====================

    /**
     * Valida los datos de un registro de inventario.
     *
     * @param inventory el inventario a validar
     * @throws ValidationException si los datos del inventario son inválidos
     */
    private void validateInventory(Inventory inventory) {
        ValidationException validationException = new ValidationException("Inventory validation failed");
        
        if (inventory.getQuantity() == null) {
            validationException.addError("quantity", "Quantity is required");
        } else if (inventory.getQuantity() < 0) {
            validationException.addError("quantity", "Quantity cannot be negative");
        }
        
        if (inventory.getLocation() == null || inventory.getLocation().trim().isEmpty()) {
            validationException.addError("location", "Location is required");
        }
        
        if (validationException.hasErrors()) {
            throw validationException;
        }
    }
}
