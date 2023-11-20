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

- El directorio `views` en `src/main/java/es/uca/iw` contiene todas las vistas de la aplicación.
- `MainLayout.java` in `src/main/java` contains the navigation setup (i.e., the
  side/top bar and the main menu). This setup uses
  [App Layout](https://vaadin.com/docs/components/app-layout).
- `views` package in `src/main/java` contains the server-side Java views of your application.
- `views` folder in `frontend/` contains the client-side JavaScript views of your application.
- `themes` folder in `frontend/` contains the custom CSS styles.

## Useful links

- Read the documentation at [vaadin.com/docs](https://vaadin.com/docs).
- Follow the tutorial at [vaadin.com/docs/latest/tutorial/overview](https://vaadin.com/docs/latest/tutorial/overview).
- Create new projects at [start.vaadin.com](https://start.vaadin.com/).
- Search UI components and their usage examples at [vaadin.com/docs/latest/components](https://vaadin.com/docs/latest/components).
- View use case applications that demonstrate Vaadin capabilities at [vaadin.com/examples-and-demos](https://vaadin.com/examples-and-demos).
- Build any UI without custom CSS by discovering Vaadin's set of [CSS utility classes](https://vaadin.com/docs/styling/lumo/utility-classes). 
- Find a collection of solutions to common use cases at [cookbook.vaadin.com](https://cookbook.vaadin.com/).
- Find add-ons at [vaadin.com/directory](https://vaadin.com/directory).
- Ask questions on [Stack Overflow](https://stackoverflow.com/questions/tagged/vaadin) or join our [Discord channel](https://discord.gg/MYFq5RTbBn).
- Report issues, create pull requests in [GitHub](https://github.com/vaadin).
