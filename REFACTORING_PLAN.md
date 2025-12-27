# Plan de Refactorización por Etapas

## Etapa 1 – Data Service y Persistencia

### Etapa 1 – Commit 1

**Cambios a realizar:**

- Mejorar comentarios JavaDoc en entidades (Product, Category, Inventory)
- Estandarizar formato de comentarios en constructores
- Agregar comentarios faltantes en métodos helper de entidades

**Archivos a tocar:**

- `data-service/src/main/java/com/example/dataservice/entity/Product.java`
- `data-service/src/main/java/com/example/dataservice/entity/Category.java`
- `data-service/src/main/java/com/example/dataservice/entity/Inventory.java`

**Comandos git:**

```bash
git add data-service/src/main/java/com/example/dataservice/entity/Product.java data-service/src/main/java/com/example/dataservice/entity/Category.java data-service/src/main/java/com/example/dataservice/entity/Inventory.java
git commit -m "docs: improve JavaDoc comments in entity classes"
```

---

### Etapa 1 – Commit 2

**Cambios a realizar:**

- Reorganizar métodos en servicios (orden: constructores, métodos de lectura/consulta, métodos de escritura/modificación, métodos privados)
- Mejorar consistencia en espaciado y agrupación lógica de métodos
- Estandarizar formato de comentarios en métodos de servicio

**Archivos a tocar:**

- `data-service/src/main/java/com/example/dataservice/service/impl/ProductServiceImpl.java`
- `data-service/src/main/java/com/example/dataservice/service/impl/CategoryServiceImpl.java`
- `data-service/src/main/java/com/example/dataservice/service/impl/InventoryServiceImpl.java`

**Comandos git:**

```bash
git add data-service/src/main/java/com/example/dataservice/service/impl/ProductServiceImpl.java data-service/src/main/java/com/example/dataservice/service/impl/CategoryServiceImpl.java data-service/src/main/java/com/example/dataservice/service/impl/InventoryServiceImpl.java
git commit -m "refactor: reorganize service methods by logical order"
```

---

### Etapa 1 – Commit 3

**Cambios a realizar:**

- Mejorar y estandarizar comentarios JavaDoc en interfaces de repositorio
- Agrupar métodos relacionados en repositorios (finders por tipo: por ID, por nombre, por precio, por categoría)
- Agregar comentarios descriptivos a queries personalizadas

**Archivos a tocar:**

- `data-service/src/main/java/com/example/dataservice/repository/ProductRepository.java`
- `data-service/src/main/java/com/example/dataservice/repository/CategoryRepository.java`
- `data-service/src/main/java/com/example/dataservice/repository/InventoryRepository.java`
- `data-service/src/main/java/com/example/dataservice/service/ProductService.java`
- `data-service/src/main/java/com/example/dataservice/service/CategoryService.java`
- `data-service/src/main/java/com/example/dataservice/service/InventoryService.java`

**Comandos git:**

```bash
git add data-service/src/main/java/com/example/dataservice/repository/ProductRepository.java data-service/src/main/java/com/example/dataservice/repository/CategoryRepository.java data-service/src/main/java/com/example/dataservice/repository/InventoryRepository.java data-service/src/main/java/com/example/dataservice/service/ProductService.java data-service/src/main/java/com/example/dataservice/service/CategoryService.java data-service/src/main/java/com/example/dataservice/service/InventoryService.java
git commit -m "docs: standardize repository and service interface documentation"
```

---

## Etapa 2 – Business Service y Feign

### Etapa 2 – Commit 1

**Cambios a realizar:**

- Mejorar comentarios y organización de código en FeignConfig
- Estandarizar formato de logging
- Reorganizar métodos privados al final de la clase CustomErrorDecoder

**Archivos a tocar:**

- `business-service/src/main/java/com/example/businessservice/config/FeignConfig.java`

**Comandos git:**

```bash
git add business-service/src/main/java/com/example/businessservice/config/FeignConfig.java
git commit -m "refactor: improve FeignConfig structure and documentation"
```

---

### Etapa 2 – Commit 2

**Cambios a realizar:**

- Reorganizar métodos en client interfaces agrupándolos por funcionalidad (GET all/byId, GET search/filter, POST, PUT, DELETE)
- Mejorar consistencia en nombres de parámetros @PathVariable y @RequestParam
- Estandarizar JavaDoc en métodos de client

**Archivos a tocar:**

- `business-service/src/main/java/com/example/businessservice/client/ProductClient.java`
- `business-service/src/main/java/com/example/businessservice/client/CategoryClient.java`
- `business-service/src/main/java/com/example/businessservice/client/InventoryClient.java`

**Comandos git:**

```bash
git add business-service/src/main/java/com/example/businessservice/client/ProductClient.java business-service/src/main/java/com/example/businessservice/client/CategoryClient.java business-service/src/main/java/com/example/businessservice/client/InventoryClient.java
git commit -m "refactor: reorganize Feign client methods by operation type"
```

---

### Etapa 2 – Commit 3

**Cambios a realizar:**

- Reorganizar campos en DTOs (orden: id, campos principales, campos relacionados, timestamps)
- Estandarizar orden de métodos en DTOs (constructores, getters, setters, equals, hashCode, toString)
- Mejorar estructura y agrupación lógica en implementaciones de servicios de negocio

**Archivos a tocar:**

- `business-service/src/main/java/com/example/businessservice/dto/ProductDTO.java`
- `business-service/src/main/java/com/example/businessservice/dto/CategoryDTO.java`
- `business-service/src/main/java/com/example/businessservice/dto/InventoryDTO.java`
- `business-service/src/main/java/com/example/businessservice/service/impl/ProductServiceImpl.java`
- `business-service/src/main/java/com/example/businessservice/service/impl/CategoryServiceImpl.java`
- `business-service/src/main/java/com/example/businessservice/service/impl/InventoryServiceImpl.java`

**Comandos git:**

```bash
git add business-service/src/main/java/com/example/businessservice/dto/ProductDTO.java business-service/src/main/java/com/example/businessservice/dto/CategoryDTO.java business-service/src/main/java/com/example/businessservice/dto/InventoryDTO.java business-service/src/main/java/com/example/businessservice/service/impl/ProductServiceImpl.java business-service/src/main/java/com/example/businessservice/service/impl/CategoryServiceImpl.java business-service/src/main/java/com/example/businessservice/service/impl/InventoryServiceImpl.java
git commit -m "refactor: standardize DTO structure and reorganize business service implementations"
```

---

## Etapa 3 – Controllers y Profiles

### Etapa 3 – Commit 1

**Cambios a realizar:**

- Reorganizar métodos en controllers agrupándolos por tipo HTTP (GET, POST, PUT, DELETE)
- Dentro de cada grupo, ordenar por ruta (simple a compleja: /, /{id}, /search, /category, etc.)
- Mejorar consistencia en formato de JavaDoc entre controllers

**Archivos a tocar:**

- `data-service/src/main/java/com/example/dataservice/controller/ProductController.java`
- `data-service/src/main/java/com/example/dataservice/controller/CategoryController.java`
- `data-service/src/main/java/com/example/dataservice/controller/InventoryController.java`

**Comandos git:**

```bash
git add data-service/src/main/java/com/example/dataservice/controller/ProductController.java data-service/src/main/java/com/example/dataservice/controller/CategoryController.java data-service/src/main/java/com/example/dataservice/controller/InventoryController.java
git commit -m "refactor: reorganize controller methods by HTTP verb and path complexity"
```

---

### Etapa 3 – Commit 2

**Cambios a realizar:**

- Mejorar formato y organización de application.properties
- Agrupar propiedades relacionadas (server, spring.application, spring.datasource, spring.jpa, etc.)
- Agregar comentarios descriptivos para cada grupo de propiedades
- Estandarizar formato de comentarios en todos los profiles

**Archivos a tocar:**

- `data-service/src/main/resources/application.properties`
- `data-service/src/main/resources/application-dev.properties`
- `data-service/src/main/resources/application-mysql.properties`
- `data-service/src/main/resources/application-postgres.properties`
- `business-service/src/main/resources/application.properties`
- `business-service/src/main/resources/application-dev.properties`
- `business-service/src/main/resources/application-mysql.properties`
- `business-service/src/main/resources/application-postgres.properties`

**Comandos git:**

```bash
git add data-service/src/main/resources/application.properties data-service/src/main/resources/application-dev.properties data-service/src/main/resources/application-mysql.properties data-service/src/main/resources/application-postgres.properties business-service/src/main/resources/application.properties business-service/src/main/resources/application-dev.properties business-service/src/main/resources/application-mysql.properties business-service/src/main/resources/application-postgres.properties
git commit -m "refactor: organize and document application properties by category"
```

---

### Etapa 3 – Commit 3

**Cambios a realizar:**

- Mejorar estructura y agrupación de endpoints en business-service controllers
- Consolidar lógica de manejo de respuestas (eliminar verificaciones redundantes de null que ya manejan las excepciones)
- Mejorar documentación JavaDoc en controllers del business-service
- Estandarizar formato de responses entre controllers

**Archivos a tocar:**

- `business-service/src/main/java/com/example/businessservice/controller/ProductController.java`
- `business-service/src/main/java/com/example/businessservice/controller/CategoryController.java`
- `business-service/src/main/java/com/example/businessservice/controller/InventoryController.java`
- `data-service/src/main/java/com/example/dataservice/exception/GlobalExceptionHandler.java`
- `business-service/src/main/java/com/example/businessservice/exception/GlobalExceptionHandler.java`

**Comandos git:**

```bash
git add business-service/src/main/java/com/example/businessservice/controller/ProductController.java business-service/src/main/java/com/example/businessservice/controller/CategoryController.java business-service/src/main/java/com/example/businessservice/controller/InventoryController.java data-service/src/main/java/com/example/dataservice/exception/GlobalExceptionHandler.java business-service/src/main/java/com/example/businessservice/exception/GlobalExceptionHandler.java
git commit -m "refactor: improve business controller structure and exception handling consistency"
```

---

## Etapa 4 – Docker, Testing y Documentación

### Etapa 4 – Commit 1

**Cambios a realizar:**

- Agregar comentarios descriptivos en Dockerfiles explicando cada etapa del build
- Mejorar organización de comandos RUN agrupando operaciones relacionadas
- Estandarizar formato y espaciado en ambos Dockerfiles

**Archivos a tocar:**

- `data-service/Dockerfile`
- `business-service/Dockerfile`

**Comandos git:**

```bash
git add data-service/Dockerfile business-service/Dockerfile
git commit -m "docs: add descriptive comments to Dockerfiles"
```

---

### Etapa 4 – Commit 2

**Cambios a realizar:**

- Mejorar estructura de tests: organizar por secciones (setup, test methods, teardown)
- Agregar comentarios descriptivos en métodos de test explicando qué se está probando
- Estandarizar nombres de métodos de test (given_when_then pattern o descriptivos)
- Mejorar agrupación lógica de assertions

**Archivos a tocar:**

- `data-service/src/test/java/com/example/dataservice/controller/ProductControllerIntegrationTest.java`
- `business-service/src/test/java/com/example/businessservice/service/ProductServiceImplTest.java`
- `business-service/src/test/java/com/example/businessservice/service/CategoryServiceImplTest.java`

**Comandos git:**

```bash
git add data-service/src/test/java/com/example/dataservice/controller/ProductControllerIntegrationTest.java business-service/src/test/java/com/example/businessservice/service/ProductServiceImplTest.java business-service/src/test/java/com/example/businessservice/service/CategoryServiceImplTest.java
git commit -m "refactor: improve test structure and add descriptive comments"
```

---

### Etapa 4 – Commit 3

**Cambios a realizar:**

- Actualizar README.md con estructura mejorada del proyecto
- Agregar sección de arquitectura con diagrama de componentes (texto)
- Mejorar documentación de configuración de profiles
- Actualizar instrucciones de despliegue con Docker
- Estandarizar formato markdown y agregar tabla de contenidos

**Archivos a tocar:**

- `README.md`

**Comandos git:**

```bash
git add README.md
git commit -m "docs: update README with improved project structure and documentation"
```

---

## Resumen

Este plan de refactorización organiza el trabajo en 4 etapas, cada una con 3 commits incrementales que van de cambios menores (comentarios y formato) a cambios más estructurales (reorganización de métodos y mejoras de documentación). Todos los cambios son no funcionales y no afectan el comportamiento del sistema.
