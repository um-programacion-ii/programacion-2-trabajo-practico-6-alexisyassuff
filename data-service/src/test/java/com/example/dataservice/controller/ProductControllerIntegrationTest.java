package com.example.dataservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests de integración para ProductController.
 * 
 * Prueba los endpoints REST del controlador de productos utilizando MockMvc
 * y datos de prueba cargados mediante el perfil 'dev' con H2 en memoria.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // --------------------------------------------------------------------------
    // Tests de obtención de productos
    // --------------------------------------------------------------------------

    /**
     * Test para obtener todos los productos.
     * Verifica que se retorne una lista no vacía con los datos iniciales.
     */
    @Test
    void getAllProducts_shouldReturnSeededData() throws Exception {
        mockMvc.perform(get("/data/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[0].name", not(isEmptyOrNullString())));
    }

    /**
     * Test para obtener un producto por ID.
     * Verifica que se retorne el producto correcto basado en los datos de prueba.
     * 
     * Nota: Los datos de prueba provienen de data-h2.sql (id=1, name='Smartphone')
     */
    @Test
    void getProductById_shouldReturnKnownItem() throws Exception {
        mockMvc.perform(get("/data/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Smartphone")));
    }

    // --------------------------------------------------------------------------
    // Tests de búsqueda de productos
    // --------------------------------------------------------------------------

    /**
     * Test para buscar productos por nombre.
     * Verifica que el filtrado funcione correctamente y retorne solo productos
     * cuyo nombre contenga el término de búsqueda.
     */
    @Test
    void searchProductsByName_shouldFilter() throws Exception {
        mockMvc.perform(get("/data/products/search").param("name", "phone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[*].name", everyItem(containsStringIgnoringCase("phone"))));
    }
}
