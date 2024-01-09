# MangoPhone

Este proyecto surge en la asignatura Ingeniería Web del grado universitario Ingeniería Informática de la UCA el curso 2023/2024.

## Lanzar la aplicación

Este proyecto sigue la estructura de directorios estándar de Maven. Para lanzar el proyecto desde la linea de comandos,
escribe `mvnw` (Windows), o `./mvnw` (Mac & Linux) y luego abre http://localhost:8080 en tu navegador web.

Puedes importar el proyecto al IDE de tu elección tal y como lo harías con cualquier proyecto Maven.
Aprende más en: [cómo importar un proyecto Maven en distintos IDEs](https://vaadin.com/docs/latest/guide/step-by-step/importing) (Eclipse, IntelliJ IDEA, NetBeans, y VS Code).

## Despliegue a producción

Para crear una build de producción, escribe `mvnw clean package -Pproduction` (Windows), o `./mvnw clean package -Pproduction` (Mac & Linux).
Esto constuirá un fichero JAR con todas las depencias y los recursos del front-end listo para ser desplegado. El fichero se puede encontrar en 
el directorio `target` después de que la construcción se complete. 

Una vez que el fichero JAR esté construido, lo puedes lanzar usando
`java -jar target/mangophone-1.0-SNAPSHOT.jar`

## Estructura del proyecto
### Frontend
- El directorio `views` en `src/main/java/es/uca/iw` contiene todas las vistas de la aplicación.
  - El directorio `global` contiene las vistas generales de la app. Estas son, entre otras, el inicio de sesión y registro, la bienvenida o el apartado "sobre nosotros".
  - El directorio `marketing` contiene las vistas que verán aquellos usuarios cuya sesión corresponda a los empleados del departamento de Marketing.
  - El directorio `sac` contiene las vistas que verán aquellos usuarios cuya sesión corresponda a los empleados del departamento de Atención al cliente.
  - El directorio `finanzas` contiene las vistas que verán aquellos usuarios cuya sesión corresponda a los empleados del departamento de Finanzas.
  - El directorio `templates` contiene las plantillas de la aplicación, entre ellas, `MainLayout.java`.
- El directorio `themes` en `frontend/` contiene los estilos CSS personalizados.

### Backend
- El directorio `aplication` en `src\main\java\es\uca\iw\aplication` contiene la lógica de la aplicación.
  - El directorio `tables` en `src\main\java\es\uca\iw\aplication\tables` contiene las entidades de la aplicación.
  - El directorio `serive` en `src\main\java\es\uca\iw\aplication\service` contiene los servicios usados para realizar las operaciones sobre las diferentes entidades de la app.
  - El directorio `repository` en `src\main\java\es\uca\iw\aplication\repository` contiene los repositorios que usarán los servicios para realizar operaciones lectura y escritura sobre la Base de datos.
## Enlaces útiles

- Vaadin
  - Documentación: [vaadin.com/docs](https://vaadin.com/docs).
  - Tutorial básico: [vaadin.com/docs/latest/tutorial/overview](https://vaadin.com/docs/latest/tutorial/overview).
  - Crear nuevos proyectos: [start.vaadin.com](https://start.vaadin.com/).
  - Componentes: [vaadin.com/docs/latest/components](https://vaadin.com/docs/latest/components). 
  - Soluciones a casos de uso comunes: [cookbook.vaadin.com](https://cookbook.vaadin.com/).
  - Complementos: [vaadin.com/directory](https://vaadin.com/directory).

- Spring
  - Documentación: [spring.com/docs](https://docs.spring.io/spring-data/jpa/reference/#repositories)
  - Tutorial básico: [docs.spring.io/spring-data/jpa/reference/jpa/getting-started.html](https://docs.spring.io/spring-data/jpa/reference/jpa/getting-started.html)
  - Wiki: [github.com/spring-projects/spring-data-commons/wiki](https://github.com/spring-projects/spring-data-commons/wiki)


## Contribuidores
- [Juan Garcia Candon](https://github.com/juuangarciac)
- [Manuel Coca Alba](https://github.com/Manuel-Coca)
- [Rafael Leal Pardo](https://github.com/falilp)
