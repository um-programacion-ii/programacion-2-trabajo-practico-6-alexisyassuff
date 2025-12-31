package com.example.businessservice.config;

import com.example.businessservice.exception.BadRequestException;
import com.example.businessservice.exception.DataServiceException;
import com.example.businessservice.exception.ResourceNotFoundException;
import com.example.businessservice.exception.ServiceUnavailableException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Clase de configuración para los clientes Feign.
 * Define el decodificador de errores personalizado que traduce las excepciones
 * de Feign en excepciones específicas de la aplicación.
 */
@Configuration
public class FeignConfig {

    private static final Logger log = LoggerFactory.getLogger(FeignConfig.class);

    /**
     * Crea un decodificador de errores personalizado para los clientes Feign.
     * Este bean se utiliza automáticamente por todos los clientes Feign de la aplicación.
     *
     * @return el decodificador de errores personalizado
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    /**
     * Decodificador de errores personalizado que traduce las excepciones de Feign
     * en excepciones específicas de la aplicación según el código de estado HTTP.
     */
    public static class CustomErrorDecoder implements ErrorDecoder {

        private final ErrorDecoder defaultErrorDecoder = new Default();

        /**
         * Decodifica una respuesta de error de Feign y la convierte en una excepción específica.
         *
         * @param methodKey la clave del método que falló
         * @param response la respuesta HTTP de error
         * @return una excepción específica de la aplicación
         */
        @Override
        public Exception decode(String methodKey, Response response) {
            String requestUrl = response.request().url();
            HttpStatus responseStatus = HttpStatus.valueOf(response.status());
            String responseBody = getResponseBody(response);

            log.error("Error al llamar al servicio de datos: {} - Estado: {} - Cuerpo: {}", 
                    requestUrl, responseStatus, responseBody);

            switch (responseStatus) {
                case NOT_FOUND:
                    return new ResourceNotFoundException(
                            "Recurso no encontrado en el servicio de datos: " + requestUrl, 
                            new Exception(responseBody));
                case BAD_REQUEST:
                    return new BadRequestException(
                            "Solicitud inválida al servicio de datos: " + responseBody, 
                            new Exception(responseBody));
                case SERVICE_UNAVAILABLE:
                    return new ServiceUnavailableException(
                            "Servicio de datos no disponible: " + requestUrl, 
                            new Exception(responseBody));
                default:
                    if (responseStatus.is5xxServerError()) {
                        return new ServiceUnavailableException(
                                "Error del servicio de datos: " + responseStatus.value() + " - " + requestUrl, 
                                new Exception(responseBody));
                    } else if (responseStatus.is4xxClientError()) {
                        return new DataServiceException(
                                "Error del cliente al llamar al servicio de datos: " + responseStatus.value() + " - " + responseBody, 
                                new Exception(responseBody), 
                                responseStatus);
                    }
                    return defaultErrorDecoder.decode(methodKey, response);
            }
        }

        /**
         * Obtiene el cuerpo de la respuesta como una cadena de texto.
         * Maneja errores de lectura de forma segura.
         *
         * @param response la respuesta HTTP
         * @return el cuerpo de la respuesta como cadena, o un mensaje de error si no se puede leer
         */
        private String getResponseBody(Response response) {
            try (InputStream bodyIs = response.body().asInputStream()) {
                byte[] bodyBytes = bodyIs.readAllBytes();
                return new String(bodyBytes, StandardCharsets.UTF_8);
            } catch (IOException e) {
                log.error("Error al leer el cuerpo de la respuesta", e);
                return "No se pudo leer el cuerpo de la respuesta";
            }
        }
    }
}
