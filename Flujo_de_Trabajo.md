# Flujo de Trabajo

## Roles y Responsabilidades

### Paola Gamboa y Alexander Morales
- Serán responsables del diseño en HTML y la implementación de los estilos CSS para la aplicación.

### Jimmy Valladares
- Será responsable del desarrollo del backend.
- Implementará las funcionalidades principales de la lógica del negocio utilizando Java y el framework Spring Boot.

### Santiago Torres
- Será responsable de la documentación del proyecto, que incluirá:
  - Historias de usuario.
  - Diagramas UML, como el diagrama de clases y el modelo entidad-relación.
  - Documentación técnica de estándares de codificación y mejores prácticas.

---

## Herramientas y Tecnologías

### Backend:
- **Lenguaje de Programación**: Java.
- **Framework**: Spring Boot.

### Dependencias:
- **spring-boot-starter-data-jpa**: Para la gestión de datos.
- **spring-boot-starter-web**: Para la construcción de API REST.
- **Lombok**: Para simplificar el código reduciendo el boilerplate.
- **jakarta.validation-api** y **hibernate-validator**: Para validaciones.
- **mysql-connector-j**: Para la conexión a la base de datos MySQL.

### Gestión de Base de Datos:
- **Base de datos relacional** MySQL.

### Frontend:
- HTML y estilos definidos mediante **CSS**.

### Control de Versiones:
- **Git** se utilizará para la gestión del código fuente.
- El repositorio estará alojado en **GitHub**.

### Gestión de Dependencias:
- **Maven** será la herramienta de construcción utilizada para manejar las dependencias y empaquetar la aplicación.

### Integración con APIs Externas:
- **Tiingo API**:
  - Se integrará para obtener datos financieros en tiempo real e históricos sobre precios de acciones.


## Modelo Vista Controlador (MVC)
- El sistema se desarrollará siguiendo el patrón de diseño **Modelo Vista Controlador (MVC)**, lo que permitirá una clara separación de responsabilidades entre la lógica de negocio, la interfaz de usuario y el control del flujo de la aplicación.

## Estructura del Repositorio
El repositorio contará con las siguientes **4 ramas** principales:

1. **main**  
   Contendrá el código principal de la aplicación. Esta rama será la base de desarrollo estable y se utilizará para desplegar las versiones finales.

2. **frontend**  
   Albergara el diseño de la aplicación. Todos los archivos relacionados con el diseño y la interfaz de usuario se encontrarán en esta rama.

3. **backend**  
   Contendrá toda la lógica de negocio y los componentes relacionados con el backend. Aquí se desarrollarán las funcionalidades del servidor y la integración con la base de datos.

4. **docs**  
   En esta rama se almacenarán todos los documentos generados durante el desarrollo, incluyendo las historias de usuario, diagramas UML, documentación técnica, y cualquier otro archivo relacionado con la gestión del proyecto.
