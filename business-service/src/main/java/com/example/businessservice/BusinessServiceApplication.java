package com.example.businessservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Clase principal de la aplicación Business Service.
 * 
 * Microservicio encargado de la lógica de negocio y orquestación,
 * utilizando Feign Client para comunicarse con el data-service.
 * 
 * @author Sistema de Microservicios
 * @version 1.0
 */
@SpringBootApplication
@EnableFeignClients
public class BusinessServiceApplication {
    /**
     * Método principal que inicia la aplicación Spring Boot.
     * 
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(BusinessServiceApplication.class, args);
    }
}