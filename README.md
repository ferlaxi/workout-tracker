# Workout tracker - Planes de ejercicios

## 📄 Descripción del Proyecto

Aplicación para gestionar planes y ejercicios con autenticación. Cada usuario puede crear, personalizar y rastrear sus rutinas, accediendo únicamente a sus propios planes y progreso.

## Tecnologías Utilizadas

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)

## Características Principales
- Autenticación y Autorización con JWT para proteger los recursos de la API. 
- Gestión de planes para clientes: Crear, actualizar, eliminar y marcar como completos o pendientes los planes con sus respectivos ejercicios. 
- Sistema de roles: Admin y usuarios con diferentes permisos. 

## 🗂️ Índice

- [Descripción del Proyecto](#-descripción-del-proyecto)
- [Tecnologías Utilizadas](#️-tecnologías-utilizadas)
- [Características Principales](#-características-principales)
- [Configuración del Entorno](#configuración-del-entorno)
- [Modelo de Datos](#modelo-de-datos)
  - [Descripción de Entidades](#descripción-de-entidades)
    - [Excercise](#exercise)
    - [Plan](#plan)
    - [User](#rol-role)
- [Seguridad 🔒](#seguridad)
   - [Configuración de Spring Security y JWT](#configuración-de-spring-security-y-jwt)
   - [Protección de Rutas y Recursos](#protección-de-rutas-y-recursos)
- [API REST 🚀](#api-rest)
   - [Exercises](#exersices)
     - [Obtener ejercicios por ID](#obtener-ejercicio-por-id)
     - [Obtener todos los ejercicios](#obtener-todas-las-ejercicios)
     - [Crear ejercicio](#crear-ejercicio)
     - [Actualizar ejercicio](#actualizar-ejercicio)
     - [Borrar ejercicio](#borrar-ejercicio)
  - [Plans](#plans)
    - [Obtener plan por ID](#obtener-plan-por-id)
    - [Obtener todos los planes](#obtener-todas-los-planes)
    - [Crear plan](#crear-plan)
    - [Actualizar plan](#actualizar-plan)
    - [Borrar plan](#borrar-plan)
   - [AuthController](#authcontroller)
     - [Iniciar Sesión (Login)](#iniciar-sesión-login)
     - [Registrar Nuevo Usuario](#registrar-nuevo-usuario)

## Configuración del Entorno

### Requisitos del Sistema

- Java 17
- MySQL
- Maven

### Instrucciones de Implementación

1.  Clona el repositorio desde Github.

2.  Configura el archivo application.properties con los datos de tu base de datos MySQL local:
    (El archivo se encuentra en la ruta `src/main/resources/application.properties`)

   ```properties
        spring.jpa.hibernate.ddl-auto=update
        spring.datasource.url=${DB_URL}
        spring.datasource.username=${DB_USERNAME}
        spring.datasource.password=${DB_PASSWORD}
        spring.hibernate.database-platform=${DB_DIALECT}
   ```

4.  Ejecuta la aplicación.

## Modelo de Datos

## Descripción de Entidades

### Exercise

- **Descripción:** Representa una ejercicio en el sistema y que puede agregarse a varios planes.
- **Atributos:**
  - `id`: Long (ID de la tarea, autogenerado)
  - `idUser`: Long (ID del usuario el cual crea el ejercicio, debe estar logeado)
  - `name`: String (nombre del ejercicio a crear)
  - `description`: String (descripción del ejercicio a crear)
  - `category`: String (categoria perteneciente)
  - `repetition`: Integer (opcional - cantidad de repeticion del ejercicio)
  - `series`: Integer (opcional - numero de series del ejercicio)
  - `weight`: Double (opcional - depeendiendo el ejercicio tendra un respectivo peso o no)

### Plan

- **Descripción:** Representa un plan asignado a un usuario del sistema.
- **Atributos:**
  - `id`: Long (ID del usuario, autogenerado)
  - `idUser`: Long (ID del usuario el cual crea el plan, debe estar logeado)
  - `name`: String (nombre del plan)
  - `description`: String (descripción del plan)
  - `category`: String (categoria que eprtenece el plan)
  - `initDate`: LocalDate (Fecha de inicio dle plan)
  - `initTime`: LocalTime (Hora de inicio del plan)
  - `comentary`: String (opcional - comentario sobre el plan)
  - `state`: String (Estado del plan: Activo - Pausa - Hecho)

### Rol (Role)

- **Descripción:** Enumeración de roles posibles para un usuario.
- **Valores:**
  - `USER`: Rol de usuario normal.

## Seguridad

### Configuración de Spring Security y JWT

La aplicación implementa seguridad mediante Spring Security y utiliza JWT (JSON Web Tokens) para la autenticación de usuarios.

### Protección de Rutas y Recursos

Las rutas y recursos sensibles de la API están asegurados con Spring Security. La autenticación se gestiona mediante tokens JWT, permitiendo que solo usuarios autenticados y con los permisos adecuados accedan a recursos específicos o ejecuten acciones restringidas en la aplicación.
## API REST

### Exercises

| Método | Endpoint                    | Descripción                                                  |
| ------ | --------------------------  | ------------------------------------------------------------ |
| GET    | `/api/exercise`             | Obtiene todos los ejercios del usuario autenticado.          |
| GET    | `/api/exercise/{id}`        | Obtiene el ejercicio asociado a un usuario por su ID de ejercicio.|
| POST   | `/api/exercise`             | Crea un ejercicio asociado a un usuario autenticado.|
| PUT    | `/api/exercise/edit/{id}`   | Edita un ejercicio asociado a un usuario autenticado.|
| DELETE | `/api/exercise/delete/{id}` | Borra un ejercicio asociado a un usuario autenticado.|

#### Obtener Todos los ejercicios

- **Endpoint:** `GET /api/exercies`
- **Descripción:** Obtiene toos los ejercicios vinculados al usuario logeado.
- **Respuesta:**
  - `200 OK`: La lista de tareas se recuperó con éxito.
  - `404 Not Found`: El usuario no existe o no tiene tareas asociadas.
 
#### Obtener ejercicio por su ID

- **Endpoint:** `GET /api/exersice/{id}`
- **Descripción:** Obtiene un ejercicio por su ID.
- **Parámetros:**
  - `id`: Long (ID del ejercicio)
- **Respuesta:**
  - `200 OK`: Tarea obtenida exitosamente.
  - `404 Not Found`: Tarea no encontrada.
 
#### Crear nuevo ejercicio

- **Endpoint:** `POST /api/exercise`
- **Descripción:** Crea un ejercicio nuevo para un usuario autenticado.
- **Parámetros:**
  - `exersice`: Exercise (Cuerpo de la solicitud con la información del ejercicio)
- **Respuesta:**
  - `201 Created`: El ejercicio fue creada correctamente.
  - `400 Bad Request`: Solicitud inválida.
- **Request Body:**

  ```json
  {
    "name": "String",
    "description": "String",
    "category": "String",
    "repetition": "Integer",
    "series": "Integer",
    "weight": "Double"
  }
  ```

#### Actualizar ejericio existente

- **Endpoint:** `PUT /api/exercise/edit/{id}`
- **Descripción:** Actualiza un ejercicio para un usuario autenticado.
- **Parámetros:**
  - `id`: Long (iD del ejercicio)
- **Respuesta:**
  - `200 OK`: Tarea actualizada exitosamente.
  - `400 Bad Request`: Solicitud inválida.
- **Request Body:**

  ```json
  {
    "name": "String",
    "description": "String",
    "category": "String",
    "repetition": "Integer",
    "series": "Integer",
    "weight": "Double"
  }
  ```
 
#### Borrrar ejercicio por su ID

- **Endpoint:** `GET /api/exersice/{id}`
- **Descripción:** Borra un ejercicio por su ID.
- **Parámetros:**
  - `id`: Long (ID del ejercicio)
- **Respuesta:**
  - `204 No Content`: Borrado exitosamente.



### Plan

| Método | Endpoint                    | Descripción                                                  |
| ------ | --------------------------  | ------------------------------------------------------------ |
| GET    | `/api/training`             | Obtiene todos los planes del usuario autenticado.          |
| GET    | `/api/training7{id}`        | Obtiene el plan asociado a un usuario por su ID de plan.|
| POST   | `/api/training             | Crea un plan asociado a un usuario autenticado.|
| PUT    | `/api/training/edit/{id}`   | Edita un plan asociado a un usuario autenticado.|
| DELETE | `/api/training/delete/{id}` | Borra un plan asociado a un usuario autenticado.|

#### Obtener Todos los planes

- **Endpoint:** `GET /api/training`
- **Descripción:** Obtiene toos los planes vinculados al usuario logeado.
- **Respuesta:**
  - `200 OK`: Lista de planes obtenida.
 
#### Obtener plan por su ID

- **Endpoint:** `GET /api/training/{id}`
- **Descripción:** Obtiene un plan por su ID.
- **Parámetros:**
  - `id`: Long (ID del plan)
- **Respuesta:**
  - `200 OK`: Plan obtenido exitosamente.
  - `404 Not Found`: Plan no encontrado.
 
#### Crear nuevo plan

- **Endpoint:** `POST /api/training`
- **Descripción:** Crea un plan nuevo para un usuario autenticado.
- **Parámetros:**
  - `plan`: Plan (Cuerpo de la solicitud con la información del plan)
- **Respuesta:**
  - `201 Created`: El plan fue creado correctamente.
  - `400 Bad Request`: Solicitud inválida.
- **Request Body:**

  ```json
  {
    "name": "String",
    "description": "String",
    "category": "String",
    "initDate": "LocalDate",
    "initTime": "LocalTime",
    "comentary": "String",
    "state": "String"
  }
  ```

#### Actualizar plan existente

- **Endpoint:** `PUT /api/taining/edit/{id}`
- **Descripción:** Actualiza un ejercicio para un usuario autenticado.
- **Parámetros:**
  - `id`: Long (ID del plan)
- **Respuesta:**
  - `200 OK`: Ejercicio actualizado exitosamente.
  - `400 Bad Request`: Solicitud inválida.
- **Request Body:**

   ```json
  {
    "name": "String",
    "description": "String",
    "category": "String",
    "initDate": "LocalDate",
    "initTime": "LocalTime",
    "comentary": "String",
    "state": "String"
  }
  ```
 
#### Borrrar plan por su ID

- **Endpoint:** `GET /api/training/{id}`
- **Descripción:** Borra un plan por su ID.
- **Parámetros:**
  - `id`: Long (ID del plan)
- **Respuesta:**
  - `204 No Content`: Borrado exitosamente.


### AuthController

| Método | Endpoint             | Descripción                                        
| ------ | -------------------- | -------------------------------------------------- |
| POST   | `/api/auth/login`    | Inicia sesión y devuelve un token JWT.             |
| POST   | `/api/auth/register` | Registra un nuevo usuario.                         |

#### Iniciar Sesión (Login)

- **Endpoint:** `POST /api/auth/login`
- **Descripción:** Inicia sesión y devuelve un token JWT.
- **Respuesta:**

  - `200 OK`: Inicio de sesión exitoso, devuelve en el cuerpo información.
  - `401 Unauthorized`: Credenciales incorrectas.
  - **Request Body:**

  ```json
  {
    "username": "String",
    "password": "String"
  }
  ```
  - **Response:**

  ```json
  {
    "username": "String",
    "message": "String",
    "jwt": "String",
    "status": "Boolean"
  }
  ```

#### Registrar Nuevo Usuario

- **Endpoint:** `POST /api/auth/register`
- **Descripción:** Registra un nuevo usuario.
- **Respuesta:**
  - `200 OK`: Registro exitoso.
  - `400 Bad Request`: Error en la solicitud.
- **Request Body:**

  ```json
  {
    "username": "String",
    "password": "String",
  }
  ```
