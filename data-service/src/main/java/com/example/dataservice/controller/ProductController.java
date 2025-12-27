package com.example.dataservice.controller;

import com.example.dataservice.entity.Product;
import com.example.dataservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controlador REST para la gestión de productos.
 */
@RestController
@RequestMapping("/data/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param productService servicio de productos
     */
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ==================== Métodos GET ====================

    /**
     * GET /data/products : Obtiene todos los productos.
     *
     * @return ResponseEntity con estado 200 (OK) y la lista de productos en el cuerpo
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * GET /data/products/{id} : Obtiene un producto por su ID.
     *
     * @param id el ID del producto a obtener
     * @return ResponseEntity con estado 200 (OK) y el producto en el cuerpo,
     *         o con estado 404 (Not Found) si el producto no se encuentra
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * GET /data/products/search : Busca productos por nombre.
     *
     * @param name el patrón de nombre a buscar
     * @return ResponseEntity con estado 200 (OK) y la lista de productos que coinciden en el cuerpo
     */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProductsByName(@RequestParam String name) {
        List<Product> products = productService.findProductsByNameContaining(name);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * GET /data/products/category/{categoryId} : Obtiene productos por ID de categoría.
     *
     * @param categoryId el ID de la categoría
     * @return ResponseEntity con estado 200 (OK) y la lista de productos en la categoría especificada en el cuerpo,
     *         o con estado 404 (Not Found) si la categoría no se encuentra
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<Product> products = productService.findProductsByCategoryId(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * GET /data/products/category/name/{categoryName} : Obtiene productos por nombre de categoría.
     *
     * @param categoryName el nombre de la categoría
     * @return ResponseEntity con estado 200 (OK) y la lista de productos en la categoría especificada en el cuerpo
     */
    @GetMapping("/category/name/{categoryName}")
    public ResponseEntity<List<Product>> getProductsByCategoryName(@PathVariable String categoryName) {
        List<Product> products = productService.findProductsByCategoryName(categoryName);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * GET /data/products/price/max/{maxPrice} : Obtiene productos con precio menor o igual al valor especificado.
     *
     * @param maxPrice el precio máximo
     * @return ResponseEntity con estado 200 (OK) y la lista de productos con precio menor o igual al valor dado en el cuerpo
     */
    @GetMapping("/price/max/{maxPrice}")
    public ResponseEntity<List<Product>> getProductsByMaxPrice(@PathVariable BigDecimal maxPrice) {
        List<Product> products = productService.findProductsByPriceLessThanEqual(maxPrice);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * GET /data/products/price/min/{minPrice} : Obtiene productos con precio mayor o igual al valor especificado.
     *
     * @param minPrice el precio mínimo
     * @return ResponseEntity con estado 200 (OK) y la lista de productos con precio mayor o igual al valor dado en el cuerpo
     */
    @GetMapping("/price/min/{minPrice}")
    public ResponseEntity<List<Product>> getProductsByMinPrice(@PathVariable BigDecimal minPrice) {
        List<Product> products = productService.findProductsByPriceGreaterThanEqual(minPrice);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * GET /data/products/price/range : Obtiene productos con precio entre los valores especificados.
     *
     * @param minPrice el precio mínimo
     * @param maxPrice el precio máximo
     * @return ResponseEntity con estado 200 (OK) y la lista de productos con precio dentro del rango especificado en el cuerpo
     */
    @GetMapping("/price/range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice) {
        List<Product> products = productService.findProductsByPriceBetween(minPrice, maxPrice);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // ==================== Métodos POST ====================

    /**
     * POST /data/products : Crea un nuevo producto.
     *
     * @param product el producto a crear
     * @return ResponseEntity con estado 201 (Created) y el nuevo producto en el cuerpo,
     *         o con estado 400 (Bad Request) si los datos del producto son inválidos,
     *         o con estado 404 (Not Found) si la categoría referenciada no se encuentra
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product newProduct = productService.createProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    // ==================== Métodos PUT ====================

    /**
     * PUT /data/products/{id} : Actualiza un producto existente.
     *
     * @param id el ID del producto a actualizar
     * @param product los datos actualizados del producto
     * @return ResponseEntity con estado 200 (OK) y el producto actualizado en el cuerpo,
     *         o con estado 400 (Bad Request) si los datos del producto son inválidos,
     *         o con estado 404 (Not Found) si el producto o la categoría referenciada no se encuentran
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    /**
     * PUT /data/products/{productId}/category/{categoryId} : Asigna una categoría a un producto.
     *
     * @param productId el ID del producto
     * @param categoryId el ID de la categoría
     * @return ResponseEntity con estado 200 (OK) y el producto actualizado en el cuerpo,
     *         o con estado 404 (Not Found) si el producto o la categoría no se encuentran
     */
    @PutMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<Product> assignCategoryToProduct(
            @PathVariable Long productId, @PathVariable Long categoryId) {
        Product product = productService.assignCategoryToProduct(productId, categoryId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // ==================== Métodos DELETE ====================

    /**
     * DELETE /data/products/{id} : Elimina un producto.
     *
     * @param id el ID del producto a eliminar
     * @return ResponseEntity con estado 204 (No Content),
     *         o con estado 404 (Not Found) si el producto no se encuentra
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * DELETE /data/products/{productId}/category : Remueve la categoría de un producto.
     *
     * @param productId el ID del producto
     * @return ResponseEntity con estado 200 (OK) y el producto actualizado en el cuerpo,
     *         o con estado 404 (Not Found) si el producto no se encuentra
     */
    @DeleteMapping("/{productId}/category")
    public ResponseEntity<Product> removeCategoryFromProduct(@PathVariable Long productId) {
        Product product = productService.removeCategoryFromProduct(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
