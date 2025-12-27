package com.example.businessservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para el servicio de negocio.
 * Proporciona respuestas HTTP consistentes para todas las excepciones de la aplicación.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Maneja DataServiceException y sus subclases.
     *
     * @param ex la excepción
     * @param request la petición web
     * @return ResponseEntity con el cuerpo de error formateado
     */
    @ExceptionHandler(DataServiceException.class)
    public ResponseEntity<Object> handleDataServiceException(DataServiceException ex, WebRequest request) {
        log.error("Excepción del servicio de datos: {}", ex.getMessage(), ex);
        
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", ex.getStatus().value());
        body.put("error", ex.getStatus().getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(body, ex.getStatus());
    }

    /**
     * Maneja ResourceNotFoundException.
     *
     * @param ex la excepción
     * @param request la petición web
     * @return ResponseEntity con el cuerpo de error formateado
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        log.error("Recurso no encontrado: {}", ex.getMessage(), ex);
        
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", ex.getStatus().value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(body, ex.getStatus());
    }

    /**
     * Maneja BadRequestException.
     *
     * @param ex la excepción
     * @param request la petición web
     * @return ResponseEntity con el cuerpo de error formateado
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        log.error("Petición incorrecta: {}", ex.getMessage(), ex);
        
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", ex.getStatus().value());
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(body, ex.getStatus());
    }

    /**
     * Maneja ServiceUnavailableException.
     *
     * @param ex la excepción
     * @param request la petición web
     * @return ResponseEntity con el cuerpo de error formateado
     */
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<Object> handleServiceUnavailableException(ServiceUnavailableException ex, WebRequest request) {
        log.error("Servicio no disponible: {}", ex.getMessage(), ex);
        
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", ex.getStatus().value());
        body.put("error", "Service Unavailable");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(body, ex.getStatus());
    }
}
