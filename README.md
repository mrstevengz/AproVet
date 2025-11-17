# AproVet - Sistema de Gestión para Veterinaria de Mascotas

AproVet es un sistema integral diseñado para la gestión operativa de una veterinaria de mascotas. Permite administrar información de clientes, mascotas, historiales médicos y más, facilitando el trabajo diario del personal veterinario.

## 🐾 ¿En qué consiste el sistema?
AproVet proporciona un conjunto de módulos que permiten:
- Registrar clientes y sus mascotas.
- Llevar control de citas y recordatorios.
- Administrar inventario de productos veterinarios.

El sistema está desarrollado en **Java** utilizando el framework **OpenXava**, lo que permite una rápida construcción de aplicaciones empresariales basadas en JPA y arquitectura MVC.

## 🛠️ Tecnologías utilizadas
- **Java 8+**
- **OpenXava** (versión correspondiente del proyecto)
- **PostgreSQL** como base de datos
- **JPA/Hibernate**

## 🗄️ Requerimientos de base de datos (PostgreSQL)
Para conectar AproVet con PostgreSQL, asegúrate de lo siguiente:
Configurar los archivos de conexión en OpenXava
En el archivo `persistence.xml` (ubicado en `src/main/resources/META-INF/`), actualiza:
```xml
<property name="javax.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/aprovet_db" />
<property name="javax.persistence.jdbc.user" value="aprovet_user" />
<property name="javax.persistence.jdbc.password" value="aprovet_pass" />
