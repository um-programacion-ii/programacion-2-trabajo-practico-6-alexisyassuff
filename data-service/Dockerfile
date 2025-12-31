# ============================================================================
# Dockerfile para Data Service
# ============================================================================
# Construye una imagen Docker para el microservicio de datos
# que gestiona la persistencia de productos, categorías e inventario
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
COPY data-service/pom.xml data-service/

# Hacer ejecutable el Maven wrapper
RUN chmod +x mvnw

# --------------------------------------------------------------------------
# Descargar dependencias de Maven
# --------------------------------------------------------------------------
# Descargar todas las dependencias de antemano para aprovechar la caché de Docker
RUN ./mvnw dependency:go-offline -pl data-service

# --------------------------------------------------------------------------
# Copiar código fuente y construir la aplicación
# --------------------------------------------------------------------------
# Copiar el código fuente del servicio
COPY data-service/src data-service/src

# Construir la aplicación (saltando tests para acelerar el build)
RUN ./mvnw package -DskipTests -pl data-service

# --------------------------------------------------------------------------
# Configuración de red y ejecución
# --------------------------------------------------------------------------
# Exponer el puerto en el que corre la aplicación
EXPOSE 8081

# Comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "data-service/target/data-service-0.0.1-SNAPSHOT.jar"]