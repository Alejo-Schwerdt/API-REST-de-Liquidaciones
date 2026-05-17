# API Liquidaciones - Sistema Transportista

API REST complementaria a la [API de Operaciones](https://github.com/Alejo-Schwerdt/API-REST-de-Transportistas), desarrollada con Spring Boot. Gestiona el proceso de liquidación de pagos a camioneros, aplicando retenciones, descuentos y registrando los pagos realizados.

## Relación con la API de Operaciones

Esta API es una extensión del sistema de transportista. Recibe los IDs de camioneros y empresas desde la API de Operaciones y calcula automáticamente:

- **Retención** de la empresa sobre el monto bruto del flete
- **Descuentos** por combustible, multas u otros conceptos
- **Neto a pagar** al camionero

## Tecnologías

- Java 17
- Spring Boot 3.2.5
- Spring Security + JWT
- Spring Data JPA + Hibernate
- MySQL
- MapStruct
- Lombok
- Swagger (SpringDoc OpenAPI)
- Maven

## Requisitos previos

- Java 17
- Maven 3.9+
- MySQL 8+
- API de Operaciones corriendo en puerto 8081

## Configuración

1. Cloná el repositorio
2. Copiá el archivo de ejemplo:
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```
3. Editá `application.properties` con tus datos de MySQL

## Cómo ejecutar

```bash
mvn spring-boot:run -DskipTests
```

La API queda disponible en `http://localhost:8082`

## Documentación
http://localhost:8082/swagger-ui/index.html
## Autenticación

1. Registrarse: `POST /api/auth/register`
2. Login: `POST /api/auth/login`
3. Usar el token en el header: `Authorization: Bearer <token>`

## Flujo de liquidación
Crear liquidación → calcula retención y neto automáticamente
Agregar detalles → descuentos que se restan del neto
Registrar pago → cambia el estado a PAGADA
## Endpoints principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | /api/auth/register | Registro |
| POST | /api/auth/login | Login |
| GET | /api/liquidaciones | Listar liquidaciones |
| POST | /api/liquidaciones | Crear liquidación |
| GET | /api/liquidaciones/{id} | Obtener liquidación |
| GET | /api/liquidaciones/camionero/{id} | Liquidaciones por camionero |
| POST | /api/liquidacion-detalles | Agregar detalle/descuento |
| GET | /api/liquidacion-detalles/liquidacion/{id} | Detalles de liquidación |
| POST | /api/pagos | Registrar pago |
| GET | /api/tipos-descuento | Listar tipos de descuento |
| POST | /api/tipos-descuento | Crear tipo de descuento |

## Ejemplo de liquidación

**Datos:**
- Total bruto: $150.000
- Retención empresa: 15%
- Descuento combustible: $8.500

**Resultado automático:**
- Total retención: $22.500
- Total descuentos: $8.500
- **Neto a pagar: $119.000**

## Tests

```bash
mvn test
```
