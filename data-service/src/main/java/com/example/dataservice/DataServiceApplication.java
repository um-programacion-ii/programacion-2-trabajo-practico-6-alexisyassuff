package com.example.dataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Data Service.
 * 
 * Microservicio encargado de la persistencia de datos, gestionando
 * las entidades Product, Category e Inventory mediante JPA.
 * 
 * @author Sistema de Microservicios
 * @version 1.0
 */
@SpringBootApplication
public class DataServiceApplication {
    /**
     * Método principal que inicia la aplicación Spring Boot.
     * 
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(DataServiceApplication.class, args);
    }
}