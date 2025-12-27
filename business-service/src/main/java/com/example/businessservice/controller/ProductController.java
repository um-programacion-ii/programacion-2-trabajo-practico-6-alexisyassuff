package com.example.businessservice.controller;

import com.example.businessservice.dto.ProductDTO;
import com.example.businessservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controlador REST para la gestión de productos en el servicio de negocio.
 */
@RestController
@RequestMapping("/api/products")
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
     * GET /api/products : Obtiene todos los productos.
     *
     * @return ResponseEntity con estado 200 (OK) y la lista de productos en el cuerpo
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * GET /api/products/{id} : Obtiene un producto por su ID.
     *
     * @param id el ID del producto a obtener
     * @return ResponseEntity con estado 200 (OK) y el producto en el cuerpo,
     *         o con estado 404 (Not Found) si el producto no se encuentra
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /api/products/search : Busca productos por nombre.
     *
     * @param name el patrón de nombre a buscar
     * @return ResponseEntity con estado 200 (OK) y la lista de productos que coinciden en el cuerpo
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProductsByName(@RequestParam String name) {
        List<ProductDTO> products = productService.findProductsByNameContaining(name);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * GET /api/products/category/{categoryId} : Obtiene productos por ID de categoría.
     *
     * @param categoryId el ID de la categoría
     * @return ResponseEntity con estado 200 (OK) y la lista de productos en la categoría especificada en el cuerpo
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<ProductDTO> products = productService.findProductsByCategoryId(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * GET /api/products/category/name/{categoryName} : Obtiene productos por nombre de categoría.
     *
     * @param categoryName el nombre de la categoría
     * @return ResponseEntity con estado 200 (OK) y la lista de productos en la categoría especificada en el cuerpo
     */
    @GetMapping("/category/name/{categoryName}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryName(@PathVariable String categoryName) {
        List<ProductDTO> products = productService.findProductsByCategoryName(categoryName);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * GET /api/products/price/max/{maxPrice} : Obtiene productos con precio menor o igual al valor especificado.
     *
     * @param maxPrice el precio máximo
     * @return ResponseEntity con estado 200 (OK) y la lista de productos con precio menor o igual al valor dado en el cuerpo
     */
    @GetMapping("/price/max/{maxPrice}")
    public ResponseEntity<List<ProductDTO>> getProductsByMaxPrice(@PathVariable BigDecimal maxPrice) {
        List<ProductDTO> products = productService.findProductsByMaxPrice(maxPrice);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * GET /api/products/price/min/{minPrice} : Obtiene productos con precio mayor o igual al valor especificado.
     *
     * @param minPrice el precio mínimo
     * @return ResponseEntity con estado 200 (OK) y la lista de productos con precio mayor o igual al valor dado en el cuerpo
     */
    @GetMapping("/price/min/{minPrice}")
    public ResponseEntity<List<ProductDTO>> getProductsByMinPrice(@PathVariable BigDecimal minPrice) {
        List<ProductDTO> products = productService.findProductsByMinPrice(minPrice);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * GET /api/products/price/range : Obtiene productos con precio entre los valores especificados.
     *
     * @param minPrice el precio mínimo
     * @param maxPrice el precio máximo
     * @return ResponseEntity con estado 200 (OK) y la lista de productos con precio dentro del rango especificado en el cuerpo
     */
    @GetMapping("/price/range")
    public ResponseEntity<List<ProductDTO>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice) {
        List<ProductDTO> products = productService.findProductsByPriceRange(minPrice, maxPrice);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // ==================== Métodos POST ====================

    /**
     * POST /api/products : Crea un nuevo producto.
     *
     * @param productDTO el producto a crear
     * @return ResponseEntity con estado 201 (Created) y el nuevo producto en el cuerpo,
     *         o con estado 500 (Internal Server Error) si ocurre un error
     */
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO newProduct = productService.createProduct(productDTO);
        if (newProduct != null) {
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ==================== Métodos PUT ====================

    /**
     * PUT /api/products/{id} : Actualiza un producto existente.
     *
     * @param id el ID del producto a actualizar
     * @param productDTO los datos actualizados del producto
     * @return ResponseEntity con estado 200 (OK) y el producto actualizado en el cuerpo,
     *         o con estado 404 (Not Found) si el producto no se encuentra
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        if (updatedProduct != null) {
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * PUT /api/products/{productId}/category/{categoryId} : Asigna una categoría a un producto.
     *
     * @param productId el ID del producto
     * @param categoryId el ID de la categoría
     * @return ResponseEntity con estado 200 (OK) y el producto actualizado en el cuerpo,
     *         o con estado 404 (Not Found) si el producto o la categoría no se encuentran
     */
    @PutMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<ProductDTO> assignCategoryToProduct(
            @PathVariable Long productId, @PathVariable Long categoryId) {
        ProductDTO product = productService.assignCategoryToProduct(productId, categoryId);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ==================== Métodos DELETE ====================

    /**
     * DELETE /api/products/{id} : Elimina un producto.
     *
     * @param id el ID del producto a eliminar
     * @return ResponseEntity con estado 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * DELETE /api/products/{productId}/category : Remueve la categoría de un producto.
     *
     * @param productId el ID del producto
     * @return ResponseEntity con estado 200 (OK) y el producto actualizado en el cuerpo,
     *         o con estado 404 (Not Found) si el producto no se encuentra
     */
    @DeleteMapping("/{productId}/category")
    public ResponseEntity<ProductDTO> removeCategoryFromProduct(@PathVariable Long productId) {
        ProductDTO product = productService.removeCategoryFromProduct(productId);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
