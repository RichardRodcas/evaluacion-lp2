# 🎬 Block Buster Fake — Sistema de alquiler de películas 

Este proyecto es una aplicación web construida con Java y Spring Boot que simula el flujo completo de alquiler de películas. Proyecto académico del curso LP2 - Cibertec.

---

## Características principales
- Registro y mantenimiento de clientes
- Registro y mantenimiento de películas
- Registro de alquileres con cálculo automático de total y penalidades
- Control de stock de películas en tiempo real
- Flujo de devolución que restituye el inventario
- Plantilla base reutilizable con diseño responsive (Bootstrap)
- Validaciones en formulario e interfaz intuitiva

---

## Tecnologías utilizadas

- Java 17
- Spring Boot 3
- Thymeleaf
- JPA / Hibernate
- Bootstrap 5
- Maven
- MySQL / H2 (adaptable)
- Git

---

## Instalación y ejecución

1. Clona el repositorio:
 
   https://github.com/RichardRodcas/evaluacion-lp2

2. Importa el proyecto en tu IDE (Eclipse, NetBeans, VS Code)

3. Configura tu archivo application.properties
	spring.datasource.url=jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:5432/postgres
	spring.datasource.username=postgres.hqvpxvmhtdhwzkelmlsh
	spring.datasource.password=Password1$Suppa
4. Ejecuta el proyecto con la clase principal AlquilerWebappApplication.java
5. Accede desde el navegador a:
	http://localhost:8080/

## Estructura del Proyecto

. controller/: lógica de rutas y controladores
. model/: entidades de negocio (Alquiler, Cliente, Película…)
. repository/: interfaces JPA para acceso a base de datos
. resources/: vistas Thymeleaf con diseño limpio y layout unificado
. .gitignore: configurado para evitar archivos de sistema

## Desarroyo
Creado por Richard Rodriguez Casanova, estudiante de la carrera Computación e Informática - Cibertec


