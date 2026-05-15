# Proyecto: PelusaPets - Arquitectura Integral de Microservicios 🐾

Este proyecto implementa el backend completo de un e-commerce para mascotas (PelusaPets) utilizando una arquitectura basada en microservicios con **Spring Boot**. El sistema está diseñado con alta cohesión y bajo acoplamiento, garantizando que procesos críticos como la facturación, el inventario, la logística y los pagos operen de forma independiente y escalable.

Desarrollado como proyecto de nivel empresarial para la carrera de **Analista Programador**, aplicando principios KISS, SOLID, versionamiento de APIs y persistencia inteligente.

---

## 🛠️ Tecnologías y Herramientas

* **Java 21** & **Spring Boot 3.x**
* **Spring Data JPA / Hibernate** (Mapeo objeto-relacional)
* **MySQL** (Motor de Base de Datos Relacional)
* **Lombok** (Reducción de código boilerplate)
* **Jakarta Validation** (Validación estricta de integridad de datos)
* **Maven** (Gestión de dependencias)
* **Postman** (Pruebas de API REST e integración)

---

## 📊 Arquitectura y Lógica de Negocio (Modelos)

El ecosistema PelusaPets divide su responsabilidad en dominios de negocio, implementando patrones de diseño y reglas de integridad de datos avanzadas en sus 5 microservicios:

1. **Gestión de Ventas y Carrito:**
   * `Carrito` & `CarritoItem`: Relación **1 a Muchos**. Utiliza `CascadeType.ALL` y `orphanRemoval = true` para que la eliminación de productos en memoria se refleje automáticamente en la base de datos, limpiando registros "huérfanos".
   * `Orden` & `DetalleVenta`: Registro histórico inmutable. Guarda los subtotales en el momento exacto de la compra, independizando la boleta de futuros cambios de precio en el inventario.

2. **Gestión de Logística y Proveedores:**
   * `Envio`: Gestiona el despacho vinculado a un cliente con estados dinámicos (Ej: EN_PREPARACION, EN_RUTA).
   * `Proveedor`: Empresas de transporte categorizadas.
   * *Relación Inteligente:* Un envío pertenece a un único proveedor (**ManyToOne**). Se implementa `FetchType.EAGER` para optimizar consultas, trayendo los datos del transportista de forma inmediata al consultar un paquete.

3. **Gestión de Pagos:**
   * `Pago`: Historial financiero que se auto-asigna su fecha de creación (`LocalDateTime.now()`) directamente desde su constructor, garantizando precisión temporal y protegiendo la auditoría de manipulaciones externas (evita depender de los Controllers para el timestamp).

4. **Gestión de Inventario (Catálogo):**
   * `Producto` & `Categoria`: Relación bidireccional optimizada para rendimiento. La categoría utiliza `FetchType.LAZY` para evitar sobrecarga de memoria al listar, mientras que el producto usa `FetchType.EAGER` para inyectar su familia inmediatamente.
   * *Prevención de Recursividad:* Implementación estratégica de `@JsonIgnore` y `@JsonIgnoreProperties` para evitar bucles infinitos (*StackOverflowError*) durante la serialización JSON.

5. **Gestión de Usuarios e Identidad:**
   * `Usuario` & `Rol`: Módulo blindado con una doble barrera de seguridad. Utiliza validaciones en memoria (`@Email`, `@Size`) y restricciones de base de datos (`unique = true`) para evitar cuentas duplicadas.
   * *Patrón DTO (Data Transfer Object):* Implementación de la clase `UsuarioDTO` para aislar y proteger información sensible (como el `password`), asegurando que la API solo exponga los datos estrictamente necesarios al cliente.

---

## 🚀 Endpoints REST Disponibles

### 🛒 Microservicio de Ventas (Versionado v1)
*Rutas protegidas bajo el estándar de versionamiento `/api/v1/` para asegurar compatibilidad futura.*

**Carrito de Compras (`/api/v1/carrito`)**
* `GET /{usuarioId}` - Recuperar el carrito activo de un cliente.
* `POST /{usuarioId}/items` - Agregar producto al carrito. (Asume cantidad `1` por defecto mediante `@RequestParam`).
* `PUT /{usuarioId}/items/{productoId}` - Actualizar la cantidad exacta de un ítem.
* `DELETE /{usuarioId}` - Vaciar el carrito por completo de forma segura.

**Órdenes de Venta (`/api/v1/ordenes`)**
* `POST /` - Procesar compra y generar orden (Devuelve `201 Created`).
* `GET /{id}` - Consultar detalle de boleta (Uso de `ResponseEntity<?>` para devolver texto amigable si no existe).
* `GET /usuario/{usuarioId}` - Historial de compras (Paginado con `Pageable`).
* `PUT /{id}/estado` - Modificar estado logístico de la boleta.
* `PUT /{id}/cancelar` - Cancelar boleta (Control de errores con bloque `try-catch`).

### 🚚 Microservicio de Logística
**Proveedores (`/api/proveedores`)**
* `GET /` - Listar todas las empresas de transporte registradas.
* `POST /` - Registrar un nuevo proveedor logístico (Protegido con `@Valid`).

**Envíos (`/api/envios`)**
* `GET /` - Obtener el registro completo de todos los despachos.
* `POST /` - Crear un nuevo registro de envío vinculado a un usuario y proveedor.

### 🦴 Microservicio de Inventario
*Ruta: `/api/productos` (Habilitado con `@CrossOrigin(origins = "*")` para consumo desde cualquier Frontend).*
* `GET /` - Listar el catálogo completo de productos.
* `GET /{id}` - Obtener el detalle de un producto específico.
* `POST /` - Registrar un nuevo producto en el inventario.
* `PUT /{id}` - Actualizar información y stock de un producto.
* `DELETE /{id}` - Eliminar un producto del sistema.

### 💳 Microservicio de Pagos
*Ruta: `/api/pagos` (Habilitado con `@CrossOrigin` para integraciones Frontend).*
* `GET /` - Listar el historial completo de pagos registrados.
* `GET /{id}` - Consultar el detalle de una transacción específica.
* `POST /` - Registrar y guardar un nuevo pago en el sistema.
* `DELETE /{id}` - Eliminar un registro de pago.

### 👥 Microservicio de Usuarios
*Ruta: `/api/usuarios`*
* `GET /` - Listar todos los clientes registrados.
* `GET /{id}` - Obtener perfil de usuario (Devuelve `404 Not Found` mediante contenedores `Optional` si no existe).
* `POST /` - Registrar un nuevo cliente (`201 Created`).
* `DELETE /{id}` - Eliminar un cliente del sistema (`204 No Content`).

---

## 🏆 Buenas Prácticas Aplicadas

1. **Validación Robusta:** Uso extensivo de validaciones de Jakarta para asegurar que a la base de datos no ingrese información corrupta o matemáticamente ilógica.
2. **Manejo de Errores Defensivo:** Prevención de `NullPointerException` mediante `Optional<T>`, uso de bloques `try-catch` y respuestas HTTP precisas (200, 201, 204, 404).
3. **Separación de Responsabilidades (KISS & SOLID):** División estricta entre la capa de acceso a datos (`Repository`), la lógica matemática y de negocio (`Service`), y la exposición de la API (`Controller`).
4. **CORS Configurado:** Implementación de `@CrossOrigin` en módulos clave (Inventario y Pagos) para asegurar la comunicación fluida con la capa de presentación (Frontend).
5. **Seguridad y Serialización:** Uso de Data Transfer Objects (DTOs) y anotaciones de ignorancia JSON (`@JsonIgnore`) para proteger credenciales y evitar bucles de memoria infinitos.
