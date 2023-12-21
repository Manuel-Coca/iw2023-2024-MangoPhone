package es.uca.iw.views.marketing;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import es.uca.iw.views.templates.MainLayout;

@PageTitle("Marketing Home")
@Route(value = "marketinghome", layout = MainLayout.class)
@RouteAlias(value = "marketinghome", layout = MainLayout.class)
public class MarkentingHomeView extends Div {
    
    public MarkentingHomeView() { add(markentingLayout()); }

    private VerticalLayout markentingLayout(){
        VerticalLayout markentingLayout = new VerticalLayout();
        VerticalLayout titleLayout = new VerticalLayout();

        H1 globalTitle = new H1("Mango");
        globalTitle.addClassName("titulo-custom");

        titleLayout.add(globalTitle, new Paragraph("Gestión de Servicios"));
        titleLayout.setSizeFull();
        titleLayout.setAlignItems(Alignment.CENTER);

        HorizontalLayout cardsServicios = new HorizontalLayout();
        VerticalLayout anadirServiciosCard = new VerticalLayout();
        VerticalLayout modificarServiciosCard = new VerticalLayout();
        VerticalLayout consultarServiciosCard = new VerticalLayout();
        VerticalLayout eliminarServiciosCard = new VerticalLayout();

        //serviciosCard.setPadding(true);
        //serviciosCard.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        H2 crearTitulo = new H2("Crear");
        Paragraph crearDescription = new Paragraph("Registro de servicios a la web");
        Button anadirButton = new Button("Crear");
        anadirButton.addClassName("boton-naranja-primary");
        anadirButton.addClickListener(event -> {
            UI.getCurrent().navigate("crearServicio");
        });
        anadirServiciosCard.add(crearTitulo, crearDescription, anadirButton);
        anadirServiciosCard.addClassNames("card");

        H2 modificarTitulo = new H2("Modificar");
        Paragraph modificarDescription = new Paragraph("Lista de servicios de la web");
        Button modificarButton = new Button("Modificar");
        modificarButton.addClassName("boton-verde-primary");
        modificarButton.addClickListener(event -> { 
            UI.getCurrent().navigate("home");
        });
        modificarServiciosCard.add(modificarTitulo, modificarDescription, modificarButton);
        modificarServiciosCard.addClassNames("card");

        H2 consultarTitulo = new H2("Consultar");
        Paragraph consultarDescription = new Paragraph("Lista de servicios de la web");
        Button consultarButton = new Button("Consultar");
        consultarButton.addClassName("boton-naranja-primary");
        consultarButton.addClickListener(event -> { 
            UI.getCurrent().navigate("listaServicios");
        });
        consultarServiciosCard.add(consultarTitulo, consultarDescription, consultarButton);
        consultarServiciosCard.addClassNames("card");

        H2 eliminarTitulo = new H2("Eliminar");
        Paragraph eliminarDescription = new Paragraph("Elegir servicio de la web");
        Button eliminarButton = new Button("Eliminar");
        eliminarButton.addClassName("boton-verde-primary");
        eliminarButton.addClickListener(event -> { 
            UI.getCurrent().navigate("home");
        });
        eliminarServiciosCard.add(eliminarTitulo, eliminarDescription, eliminarButton);
        eliminarServiciosCard.addClassNames("card");

        cardsServicios.add(anadirServiciosCard, modificarServiciosCard, consultarServiciosCard, eliminarServiciosCard);
        markentingLayout.add(titleLayout, cardsServicios);
        return markentingLayout;
    }
}