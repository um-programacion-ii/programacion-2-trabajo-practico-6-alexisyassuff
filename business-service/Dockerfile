# ============================================================================
# Dockerfile para Business Service
# ============================================================================
# Construye una imagen Docker para el microservicio de negocio
# que gestiona la lógica de negocio y orquestación con data-service
# ============================================================================

# Imagen base: OpenJDK 21 (Eclipse Temurin)
FROM eclipse-temurin:21-jdk

# Establecer directorio de trabajo dentro del contenedor
WORKDIR /app

# --------------------------------------------------------------------------
# Copiar archivos de configuración de Maven
# --------------------------------------------------------------------------
# Copiar Maven wrapper y archivos de configuración para habilitar builds sin Maven instalado
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY business-service/pom.xml business-service/

# Hacer ejecutable el Maven wrapper
RUN chmod +x mvnw

# --------------------------------------------------------------------------
# Descargar dependencias de Maven
# --------------------------------------------------------------------------
# Descargar todas las dependencias de antemano para aprovechar la caché de Docker
RUN ./mvnw dependency:go-offline -pl business-service

# --------------------------------------------------------------------------
# Copiar código fuente y construir la aplicación
# --------------------------------------------------------------------------
# Copiar el código fuente del servicio
COPY business-service/src business-service/src

# Construir la aplicación (saltando tests para acelerar el build)
RUN ./mvnw package -DskipTests -pl business-service

# --------------------------------------------------------------------------
# Configuración de red y ejecución
# --------------------------------------------------------------------------
# Exponer el puerto en el que corre la aplicación
EXPOSE 8082

# Comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "business-service/target/business-service-0.0.1-SNAPSHOT.jar"]