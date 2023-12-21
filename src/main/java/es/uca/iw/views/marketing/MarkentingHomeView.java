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

        titleLayout.add(globalTitle, new Paragraph("Gestión de Tarifas"));
        titleLayout.setSizeFull();
        titleLayout.setAlignItems(Alignment.CENTER);

        HorizontalLayout cardsServicios = new HorizontalLayout();
        VerticalLayout anadirServiciosCard = new VerticalLayout();
        VerticalLayout consultarServiciosCard = new VerticalLayout();

        H2 crearTitulo = new H2("Crear");
        Paragraph crearDescription = new Paragraph("Registro de servicios a la web");
        Button anadirButton = new Button("Crear");
        anadirButton.addClassName("boton-naranja-primary");
        anadirButton.addClickListener(event -> {
            UI.getCurrent().navigate("crearTarifa");
        });
        anadirServiciosCard.add(crearTitulo, crearDescription, anadirButton);
        anadirServiciosCard.addClassNames("card");

        H2 consultarTitulo = new H2("Consultar");
        Paragraph consultarDescription = new Paragraph("Lista de servicios de la web");
        Button consultarButton = new Button("Consultar");
        consultarButton.addClassName("boton-verde-primary");
        consultarButton.addClickListener(event -> { 
            UI.getCurrent().navigate("listaTarifas");
        });
        consultarServiciosCard.add(consultarTitulo, consultarDescription, consultarButton);
        consultarServiciosCard.addClassNames("card");

        cardsServicios.add(anadirServiciosCard, consultarServiciosCard);
        markentingLayout.add(titleLayout, cardsServicios);
        return markentingLayout;
    }
}