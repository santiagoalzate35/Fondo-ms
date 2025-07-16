# 🏦 Plataforma de Gestión de Fondos – BTG Pactual

Prueba técnica para el cargo de **Ingeniero de Desarrollo Back End**. Esta aplicación permite a los clientes gestionar sus fondos de inversión de forma autónoma, brindando funcionalidades de suscripción, cancelación, historial de transacciones y notificaciones vía correo electrónico o SMS.

---

## 📌 Funcionalidades

- ✅ Suscribirse a un fondo de inversión.
- ✅ Cancelar una suscripción activa.
- ✅ Consultar el historial de transacciones.
- ✅ Notificaciones automáticas por email o SMS.
- ✅ Seguridad con JWT y manejo de roles.
- ✅ Despliegue automatizado con AWS CloudFormation.

---

## 🧱 Tecnologías Usadas

| Componente        | Tecnología                    |
|-------------------|-------------------------------|
| Lenguaje          | Java 17                       |
| Framework         | Spring Boot 3.x               |
| Base de Datos     | MongoDB                       |
| Seguridad         | Spring Security + JWT         |
| Arquitectura      | Hexagonal                     |
| Testing           | JUnit 5, Mockito              |
| Despliegue        | AWS EC2, AWS CloudFormation   |
| Notificaciones    | Email / Simulado SMS          |
| Documentación API | Swagger (springdoc-openapi)   |

---

## 📂 Estructura del Proyecto

```

 └── application/        # Casos de uso (Capa de aplicación)
     └── resources/
         ├── application.yml
         └── data.js           # Datos de prueba para MongoDB
 ├── domain/             # Entidades y lógica de negocio
 └── infrastructure/     # Repositorios, servicios externos (Adaptadores de salida)
```

---

## 🔐 Seguridad

- Autenticación mediante JWT (`/auth/login`)
- Roles definidos:
    - `CLIENTE`: Accede a sus fondos y transacciones.
    - `ADMIN`: Accede a todas las transacciones.
- Contraseñas encriptadas con BCrypt.
- Protección de rutas vía `@PreAuthorize` y filtros de seguridad.

---

## 🧪 Pruebas

- Pruebas unitarias para servicios, validadores y casos de uso.
- Pruebas de integración para endpoints REST.
- Cobertura de validaciones de negocio como saldo insuficiente, fondo inexistente, etc.

---

## 🚀 Despliegue en AWS

La infraestructura puede desplegarse con AWS CloudFormation:

```bash
aws cloudformation deploy \
  --template-file deployment/btg-app.yml \
  --stack-name btg-backend \
  --capabilities CAPABILITY_NAMED_IAM
```

Servicios usados:

- EC2 para la aplicación Spring Boot.
- DocumentDB o MongoDB en EC2.
- SNS/SES para notificaciones.
- Variables de entorno: `MONGO_URI`, `JWT_SECRET`, etc.

---

## 🧪 Datos de Prueba

Puedes insertar datos iniciales usando MongoDB shell o Compass:

```js
// Ver archivo: src/resources/data.js
```

Incluye:

- 3 usuarios con saldo inicial. (contraseña de los usarios = Maria100#)
- 5 fondos de inversión.
- 1 transacción de ejemplo.

---

## 📬 Endpoints REST

| Método | Ruta                          | Descripción                              | Autenticación |
|--------|-------------------------------|------------------------------------------|----------------|
| POST   | `/auth/login`                 | Iniciar sesión y obtener JWT             | ❌             |
| GET    | `/api/fondos/list`            | Obtener fondos disponibles               | ✅ (CLIENTE)   |
| POST   | `/api/fondos/suscribir`       | Suscripción a un fondo                   | ✅ (CLIENTE)   |
| POST   | `/api/fondos/cancelar`        | Cancelar suscripción                     | ✅ (CLIENTE)   |
| GET    | `/api/fondos/historial/{id}`  | Historial del cliente                    | ✅ (CLIENTE)   |
| GET    | `/api/admin/transacciones/all`| Ver todas las transacciones              | ✅ (ADMIN)     |

---

## 💻 Ejecución local

### 🔧 Requisitos
- Java 17
- MongoDB local (o remoto)
- Maven

### ▶️ Ejecutar el proyecto
```bash
./mvnw spring-boot:run
```

---

## 🧠 Reglas de Negocio

- Cada cliente inicia con COP $500.000.
- Fondos tienen monto mínimo de suscripción.
- Si no hay saldo suficiente, se bloquea la operación.
- Al cancelar, el monto se reintegra al saldo del cliente.
- Cada transacción tiene un ID único.

---