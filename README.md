# Gestor de Entrenamientos (Workout Tracker)

## 📄 Descripción del Proyecto

Aplicación Full-Stack para gestionar planes de entrenamiento y ejercicios de forma personalizada. Cada usuario cuenta con su propia cuenta protegida mediante autenticación JWT, pudiendo crear, organizar y rastrear sus rutinas, accediendo únicamente a sus propios datos a través de una interfaz moderna y responsiva.

## Tecnologías Utilizadas

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)
![TailwindCSS](https://img.shields.io/badge/tailwindcss-%2338B2AC.svg?style=for-the-badge&logo=tailwind-css&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

## Características Principales
- **Interfaz Gráfica Full-Stack:** Vistas modernas, intuitivas y responsivas desarrolladas con Thymeleaf y Tailwind CSS.
- **Autenticación Frontend/Backend:** Integración transparente de tokens JWT mediante peticiones asíncronas de Vanilla JavaScript.
- **Seguridad Robusta:** Autenticación y Autorización con JWT y Spring Security para proteger los recursos de la API y evitar accesos cruzados entre usuarios.
- **Gestión de Planes y Ejercicios:** Relación de muchos a muchos que permite crear un catálogo de ejercicios reutilizables y asignarlos a diferentes rutinas.
- **Data Seeder Automático:** La aplicación detecta si la base de datos está vacía y automáticamente genera un usuario de demostración (`demo` / `demo123`) con ejercicios y rutinas precargadas.
- **Dockerizado:** Entorno completamente contenerizado (Backend + Base de datos) listo para desplegar con Docker Compose.

## 🗂️ Índice

- [Descripción del Proyecto](#-descripción-del-proyecto)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Características Principales](#características-principales)
- [Configuración del Entorno](#configuración-del-entorno)
- [Modelo de Datos](#modelo-de-datos)
- [Seguridad 🔒](#seguridad-)
- [Vistas de la Aplicación (UI) 🖥️](#vistas-de-la-aplicación-ui-️)
- [API REST 🚀](#api-rest-)

## Configuración del Entorno

### Requisitos del Sistema
- Docker y Docker Compose
- *Alternativa local:* Java 17+, MySQL y Maven.

### Instrucciones de Implementación (Con Docker)

1.  Clona el repositorio desde Github.
2.  Levanta la infraestructura completa (Base de datos MySQL + Aplicación Spring Boot) con un solo comando:
    ```bash
    docker-compose up -d --build
    ```
3.  El servidor estará disponible en el **puerto 9090**. Ingresa desde tu navegador a `http://localhost:9090/`.


### Instrucciones de Implementación (Manual)

1.  Crea una base de datos en MySQL local llamada `workout_db`.
2.  Asegúrate de que tus credenciales en `src/main/resources/application.properties` coincidan con tu servidor MySQL.
3.  Ejecuta la aplicación usando el wrapper de Maven (`./mvnw spring-boot:run`).

## Modelo de Datos

### Descripción de Entidades

#### Exercise (Ejercicio)
- **Descripción:** Representa un ejercicio individual en el sistema que puede ser reutilizado en múltiples planes de entrenamiento.
- **Atributos:**
  - `id`: Long (Autogenerado)
  - `idUser`: Long (Dueño del ejercicio)
  - `name`: String (Nombre del ejercicio)
  - `description`: String (Descripción opcional)
  - `category`: String (Categoría muscular)
  - `repetition`: Integer (Cantidad de repeticiones)
  - `series`: Integer (Cantidad de series)
  - `weight`: Double (Peso utilizado)
  - `planList`: Set<Plan> (Relación Inversa con los planes que lo contienen)

#### Plan
- **Descripción:** Representa un plan o rutina asignada a un usuario.
- **Atributos:**
  - `id`: Long (Autogenerado)
  - `idUser`: Long (Dueño del plan)
  - `name`: String (Nombre del plan)
  - `description`: String (Descripción de la rutina)
  - `category`: String (Categoría del plan)
  - `initDate`: LocalDate (Fecha de inicio programada)
  - `initTime`: LocalTime (Hora de inicio programada)
  - `comentary`: String (Comentarios o notas)
  - `state`: String (Estado: Activo, Pausa, Hecho)
  - `exerciseList`: Set<Exercise> (Ejercicios pertenecientes a este plan mediante tabla intermedia `plan_ejercicios`)

#### Rol (Role) y Usuario (UserSec)
- **Descripción:** Arquitectura de seguridad bajo el estándar RBAC (Control de Acceso Basado en Roles) vinculando Usuarios con sus Roles y Permisos.

## Seguridad 🔒

### Configuración de Spring Security y JWT
La aplicación aplica seguridad `STATELESS` garantizando que no se guarden sesiones en el servidor. Todas las peticiones al backend deben incluir en sus cabeceras un token `Authorization: Bearer <jwt>` el cual es validado dinámicamente mediante el filtro `JwtTokenValidator`.

### Protección de Rutas y Recursos
Los controladores HTML (Thymeleaf) son públicos, pero toda la información inyectada depende 100% de la API REST que está blindada con anotaciones `@PreAuthorize("isAuthenticated()")`. Si el token caduca o no existe, el frontend detecta el HTTP 401 y expulsa al usuario devolviéndolo a la pantalla de Login.

## Vistas de la Aplicación (UI) 🖥️

La aplicación expone una interfaz visual moderna que hace el puente automático con la API.

| Vista | Ruta                    | Descripción                                                  |
| ------ | ----------------------- | ------------------------------------------------------------ |
| Inicio | `/`                     | Página de aterrizaje (Landing page). |
| Login  | `/login`                | Formulario para iniciar sesión y recuperar el JWT. |
| Registro| `/register`            | Formulario para dar de alta nuevas cuentas. |
| Dashboard| `/dashboard`          | Panel principal con el resumen de la aplicación y navegación principal. |
| Planes | `/plans`                | Interfaz de gestión completa (CRUD visual) para rutinas. |
| Ejercicios| `/exercises`         | Interfaz de gestión completa (CRUD visual) para el catálogo de ejercicios. |

---

## API REST 🚀

### Exercises

| Método | Endpoint                    | Descripción                                                  |
| ------ | --------------------------  | ------------------------------------------------------------ |
| GET    | `/api/exercise`             | Obtiene todos los ejercicios del usuario autenticado.        |
| GET    | `/api/exercise/{id}`        | Obtiene un ejercicio específico por su ID.                   |
| POST   | `/api/exercise`             | Crea un nuevo ejercicio asociado al token de sesión actual.  |
| PUT    | `/api/exercise/edit/{id}`   | Actualiza un ejercicio (el ID de usuario debe coincidir).    |
| DELETE | `/api/exercise/delete/{id}` | Borra un ejercicio del catálogo.                             |

#### Crear nuevo ejercicio (Ejemplo Request Body)
- **Endpoint:** `POST /api/exercise`
- **Respuesta:** `201 Created`
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

### Plan

| Método | Endpoint                    | Descripción                                                  |
| ------ | --------------------------  | ------------------------------------------------------------ |
| GET    | `/api/training`             | Obtiene todos los planes del usuario autenticado.            |
| GET    | `/api/training/{id}`        | Obtiene un plan específico por su ID.                        |
| POST   | `/api/training`             | Crea un nuevo plan asociado al token actual.                 |
| PUT    | `/api/training/edit/{id}`   | Edita un plan de entrenamiento.                              |
| DELETE | `/api/training/delete/{id}` | Elimina el plan y rompe las asociaciones con sus ejercicios. |

#### Crear nuevo plan (Ejemplo Request Body)
- **Endpoint:** `POST /api/training`
- **Respuesta:** `201 Created`
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

### AuthController

| Método | Endpoint             | Descripción                                        
| ------ | -------------------- | -------------------------------------------------- |
| POST   | `/api/auth/login`    | Autentica credenciales y devuelve un token JWT.    |
| POST   | `/api/auth/register` | Crea una cuenta de usuario nueva.                  |

#### Iniciar Sesión (Login)
- **Endpoint:** `POST /api/auth/login`
- **Request Body:**
  ```json
  {
    "username": "String",
    "password": "String"
  }
  ```
- **Response (200 OK):**
  ```json
  {
    "username": "String",
    "message": "String",
    "jwt": "String",
    "status": "Boolean"
  }
  ```
