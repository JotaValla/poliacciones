# Estándares de Codificación

Como parte de la preparación para el desarrollo de este proyecto, se han definido algunos estándares de codificación para asegurar que el código sea limpio, mantenible y fácil de entender para todo el equipo. Aquí están las convenciones que se seguirán:

1. **Convenciones de Nombres:**
   - **Paquetes:** Se estructurará los paquetes de la siguiente manera para reflejar la organización del proyecto y seguir las buenas prácticas de Spring Boot:
     - Config
     - Controller
     - Model
     - Service
     - Repository
   - **Clases:** Los nombres de las clases seguirán la convención PascalCase.
   - **Métodos:** Los métodos seguirán la convención camelCase y serán descriptivos sobre lo que hacen.
   - **Variables:** Las variables también seguirán camelCase.

2. **Estructura del Código:**
   - **Archivos y carpetas:**
     - La estructura seguirá el patrón estándar de Spring Boot (Controller, Model, Repository, Services y Config).

3. **Manejo de Errores:**
   - **Control de errores global:**
     - Se implementará una clase que centralizará el manejo de errores en la aplicación, mejorando la consistencia en las respuestas ante excepciones.

4. **Uso de Spring y Configuraciones:**
   - **Spring Annotations:**
     - Se utilizarán las anotaciones estándar de Spring para definir los beans, servicios, controladores y repositorios. Estas anotaciones son fundamentales para que el contexto de Spring funcione correctamente.
   - **Archivos de Configuración:**
     - Se configurará un archivo para conectar la aplicación a la base de datos y controlar las configuraciones específicas de Spring Boot.

5. **Control de Versiones y Git:**
   - **Commits en Git:**
     - Los mensajes de commit serán claros y seguirán el formato de conventional commits:
       - feat: Nuevas funcionalidades.
       - fix: Corrección de errores.
       - docs: Cambios en la documentación.
       - style: Mejoras de estilo de código (sin cambios de funcionalidad).
       - test: Adición o corrección de pruebas.
       - refactor: Refactorización del código.
       - ci: Cambios en la configuración de integración continua o scripts relacionados con CI/CD.
       - build: Cambios que afectan el sistema de construcción, como la configuración de Maven o Gradle.
       - perf: Mejoras en el rendimiento del sistema.
