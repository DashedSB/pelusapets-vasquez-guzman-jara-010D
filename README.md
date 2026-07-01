# Proyecto: PelusaPets - Arquitectura Integral de Microservicios

Este proyecto implementa el backend completo de un e-commerce para mascotas (PelusaPets) utilizando una arquitectura basada en microservicios con Spring Boot. El sistema está diseñado con alta cohesión y bajo acoplamiento, garantizando que procesos críticos como la facturación, el inventario, la logística y los pagos operen de forma independiente y escalable.

Desarrollado como proyecto de nivel empresarial para la carrera de Analista Programador, aplicando versionamiento de APIs y persistencia inteligente.

---
## Nombre de integrantes:
* Esteban Vasquez Gutierrez
* Bryan Guzman Puebla
* Juan Jara Gutierrez
---

## Tecnologías y Herramientas


* **Java** 21 & Spring Boot 3.x
* **Spring Data JPA / Hibernate** (Mapeo objeto-relacional)
* **MySQL** (Motor de Base de Datos Relacional)
* **Lombok** (Reducción de código boilerplate)
* **Jakarta Validation** (Validación estricta de integridad de datos)
* **Maven** (Gestión de dependencias)
* **Postman** (Pruebas de API REST e integración)
* **XAMPP** (Servidor local y administración MySQL mediante phpMyAdmin)
* **Springdoc OpenAPI**(Swagger) (Documentación interactiva de la API)


---

## Arquitectura y Lógica de Negocio (Modelos)

El ecosistema PelusaPets divide su responsabilidad en dominios de negocio, implementando patrones de diseño y reglas de integridad de datos avanzadas en sus 10 microservicios:

1. **Gestión de Ventas y Carrito:**
   * `Carrito` & `CarritoItem`: Relación 1 a Muchos. Utiliza `CascadeType.ALL` y `orphanRemoval = true` para que la eliminación de productos en memoria se refleje automáticamente en la base de datos, limpiando registros "huérfanos".
   * `Orden` & `DetalleVenta`: Registro histórico inmutable. Guarda los subtotales en el momento exacto de la compra, independizando la boleta de futuros cambios de precio en el inventario.

2. **Gestión de Logística y Proveedores:**
   * `Envio`: Gestiona el despacho vinculado a un cliente con estados dinámicos (Ej: EN_PREPARACION, EN_RUTA).
   * `Proveedor`: Empresas de transporte categorizadas.
   * *Relación Inteligente:* Un envío pertenece a un único proveedor (ManyToOne). Se implementa `FetchType.EAGER` para optimizar consultas, trayendo los datos del transportista de forma inmediata al consultar un paquete.

3. **Gestión de Pagos:**
   * `Pago`: Historial financiero que se auto-asigna su fecha de creación (`LocalDateTime.now()`) directamente desde su constructor, garantizando precisión temporal y protegiendo la auditoría de manipulaciones externas (evita depender de los Controllers para el timestamp).

4. **Gestión de Inventario (Catálogo):**
   * `Producto` & `Categoria`: Relación bidireccional optimizada para rendimiento. La categoría utiliza `FetchType.LAZY` para evitar sobrecarga de memoria al listar, mientras que el producto usa `FetchType.EAGER` para inyectar su familia inmediatamente.

   * *Prevención de Recursividad:* Implementación estratégica de `@JsonIgnore` y `@JsonIgnoreProperties` para evitar bucles infinitos (StackOverflowError) durante la serialización JSON.

   * *Prevención de Recursividad:* Implementación estratégica de `@JsonIgnore` y `@JsonIgnoreProperties` para evitar bucles infinitos durante la serialización JSON.


5. **Gestión de Usuarios e Identidad:**
   * `Usuario` & `Rol`: Módulo blindado con una doble barrera de seguridad. Utiliza validaciones en memoria (`@Email`, `@Size`) y restricciones de base de datos (`unique = true`) para evitar cuentas duplicadas.
   * *Patrón DTO:* Implementación de la clase `UsuarioDTO` para aislar y proteger información sensible (como el `password`), asegurando que la API solo exponga los datos estrictamente necesarios al cliente.

6. **Gestión de Promociones:**
   * `Promocion`: Control centralizado de descuentos y cupones de la tienda. Los objetos se reciben mediante `@RequestBody` en la capa del controlador para su procesamiento y validación antes de persistir las reglas de descuento aplicadas al catálogo.

7. **Gestión de Reseñas:**
   * `Resena`: Almacenamiento y listado de calificaciones y comentarios analizados por producto. Permite a los clientes retroalimentar el catálogo de compras de forma aislada, garantizando la integridad referencial sin generar acoplamiento con los módulos principales.

8. **Gestión de Postventa:**
   * `Garantia`, `Reclamacion` & `Devolucion`: Estructura diseñada para el ciclo post-compra. Las garantías manejan un historial dinámico por cliente o producto; las reclamaciones procesan respuestas formales y el posterior cierre de tickets; y las devoluciones coordinan la actualización del estado logístico de los retornos.

9. **Gestión de Suscripciones:**
   * `Suscripcion`: Automatización de membresías de clientes gestionada directamente en la capa de negocio (`Service.java`). Al registrar un nuevo elemento, el sistema asigna el timestamp actual mediante `LocalDate.now()` y el estado `"ACTIVA"` de manera predeterminada si no se envían valores en la petición.

10. **Gestión de Fidelización:**
    * `Cliente` / `Fidelizacion`: Sistema de fidelización basado en un esquema de acumulación de puntos. Cuenta con una lógica de negocio que evalúa el puntaje actual del cliente para asignarle automáticamente una categoría de beneficio: **BRONCE, PLATA u ORO**.

---

## Endpoints REST Disponibles

### Microservicio de Ventas (Versionado v1)

**🛒 Carrito de Compras (`/api/v1/carrito`)**
* `GET /{usuarioId}` - Recuperar el carrito activo de un cliente.
* `POST /{usuarioId}/items` - Agregar producto al carrito. (Asume cantidad 1 por defecto mediante `@RequestParam`).
* `PUT /{usuarioId}/items/{productoId}` - Actualizar la cantidad exacta de un ítem.
* `DELETE /{usuarioId}` - Vaciar el carrito por completo de forma segura.

**🧾 Órdenes de Venta (`/api/v1/ordenes`)**
* `POST /` - Procesar compra y generar orden (Devuelve 201 Created).
* `GET /{id}` - Consultar detalle de boleta (Uso de `ResponseEntity<?>` para devolver texto amigable si no existe).
* `GET /usuario/{usuarioId}` - Historial de compras (Paginado con `Pageable`).
* `PUT /{id}/estado` - Modificar estado logístico de la boleta.
* `PUT /{id}/cancelar` - Cancelar boleta (Control de errores con bloque `try-catch`).

### Microservicio de Logística

**🏢 Proveedores (`/api/proveedores`)**
* `GET /` - Listar todas las empresas de transporte registradas.
* `POST /` - Registrar un nuevo proveedor logístico (Protegido con `@Valid`).

**📦 Envíos (`/api/envios`)**
* `GET /` - Obtener el registro completo de todos los despachos.
* `POST /` - Crear un nuevo registro de envío vinculado a un usuario y proveedor.

### Microservicio de Inventario

**🦴 Productos (`/api/productos`)**
* `GET /` - Listar el catálogo completo de productos.
* `GET /{id}` - Obtener el detalle de un producto específico.
* `POST /` - Registrar un nuevo producto en el inventario.
* `PUT /{id}` - Actualizar información y stock de un producto.
* `DELETE /{id}` - Eliminar un producto del sistema.

### Microservicio de Pagos

**💳 Pagos (`/api/pagos`)**
* `GET /` - Listar el historial completo de pagos registrados.
* `GET /{id}` - Consultar el detalle de una transacción específica.
* `POST /` - Registrar y guardar un nuevo pago en el sistema.
* `DELETE /{id}` - Eliminar un registro de pago.

### Microservicio de Usuarios

**👥 Usuarios (`/api/usuarios`)**
* `GET /` - Listar todos los clientes registrados.
* `GET /{id}` - Obtener perfil de usuario (Devuelve 404 Not Found mediante contenedores `Optional` si no existe).
* `POST /` - Registrar un nuevo cliente (201 Created).
* `DELETE /{id}` - Eliminar un cliente del sistema (204 No Content).

### Microservicio de Promociones

**🎁 Promociones (`/api/promociones`)**
* `GET /` - Obtener todas las promociones.
* `POST /` - Crear nueva promoción.
* `DELETE /{id}` - Eliminar promoción por ID.

### Microservicio de Reseñas

**⭐ Reseñas (`/api/resenas`)**
* `GET /` - Listar todas las reseñas.
* `POST /` - Publicar reseña.
* `DELETE /{id}` - Eliminar reseña por ID.

### Microservicio de Postventa

**🛡️ Garantías (`/api/garantia`)**
* `GET /` - Listar todas las garantías activas.
* `GET /{id}` - Buscar el detalle de una garantía específica por ID.
* `POST /` - Registrar una nueva garantía.
* `GET /usuario/{usuarioId}` - Consultar el historial de garantías de un cliente.
* `GET /producto/{productoId}` - Consultar las garantías asociadas a un producto.
* `PUT /{id}/cancelar` - Cancelar el estado de una garantía vigente.
* `DELETE /{id}` - Eliminar el registro de una garantía.

**📝 Reclamaciones (`/api/reclamaciones`)**
* `GET /` - Obtener el listado general de reclamaciones.
* `GET /{id}` - Obtener el detalle de una reclamación por ID.
* `POST /` - Ingresar un nuevo ticket de reclamación.
* `PUT /{id}/responder` - Enviar una respuesta formal a una reclamación.
* `PUT /{id}/cerrar` - Dar por solucionada y cerrar una reclamación.
* `GET /usuario/{usuarioId}` - Buscar todas las reclamaciones emitidas por un usuario.
* `DELETE /{id}` - Eliminar el registro de una reclamación.

**🔄 Devoluciones (`/api/devoluciones`)**
* `GET /` - Listar todas las solicitudes de devolución.
* `GET /{id}` - Buscar el detalle de una devolución por ID.
* `POST /` - Registrar una nueva solicitud de devolución.
* `PUT /{id}` - Actualizar el estado logístico de una devolución.
* `GET /usuario/{usuarioId}` - Consultar devoluciones solicitadas por un usuario.
* `DELETE /{id}` - Eliminar un registro de devolución.

### Microservicio de Suscripciones

**🎫 Suscripciones (`/api/modelos`)**
* `GET /` - Recuperar la lista completa de todas las suscripciones registradas.
* `POST /` - Registrar una nueva suscripción en el sistema.

### Microservicio de Fidelización

**💎 Clientes y Fidelización (`/api/modelos`)**
* `GET /` - Recuperar la lista completa de todos los clientes registrados.
* `POST /` - Guardar o registrar un nuevo cliente.

---


## 📖 Documentación Interactiva (Swagger UI)

*Rutas centralizadas a través del API Gateway (Puerto 8080) para explorar y probar los endpoints en vivo.*

* **Servicio Usuarios e Identidad:**
  * `http://localhost:8080/webjars/swagger-ui/index.html?urls.primaryName=Servicio%20Usuarios%20e%20Identidad`
* **Servicio de Promociones:**
  * `http://localhost:8080/webjars/swagger-ui/index.html?urls.primaryName=Servicio%20de%20Promociones`
* **Servicio de Reseñas:**
  * `http://localhost:8080/webjars/swagger-ui/index.html?urls.primaryName=Servicio%20de%20Reseñas`
* **Servicio de Postventa:**
  * `http://localhost:8080/webjars/swagger-ui/index.html?urls.primaryName=Servicio%20de%20Postventa`
* **Servicio de Suscripciones:**
  * `http://localhost:8080/webjars/swagger-ui/index.html?urls.primaryName=Servicio%20de%20Suscripciones`

## Buenas Prácticas Aplicadas

1. **Validación Robusta:** Uso extensivo de validaciones de Jakarta para asegurar que a la base de datos no ingrese información corrupta o matemáticamente ilógica.
2. **Manejo de Errores Defensivo:** Prevención de `NullPointerException` mediante `Optional<T>`, uso de bloques `try-catch` y respuestas HTTP precisas (200, 201, 204, 404).
3. **Separación de Responsabilidades:** División estricta entre la capa de acceso a datos (`Repository`), la lógica matemática y de negocio (`Service`), y la exposición de la API (`Controller`).
4. **CORS Configurado:** Implementación de `@CrossOrigin` en módulos clave (Inventario y Pagos) para asegurar la comunicación fluida con la capa de presentación (Frontend).
5. **Seguridad y Serialización:** Uso de Data Transfer Objects (DTOs) y anotaciones de ignorancia JSON (`@JsonIgnore`) para proteger credenciales y evitar bucles de memoria infinitos.
