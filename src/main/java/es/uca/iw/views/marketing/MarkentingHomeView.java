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
import es.uca.iw.views.templates.MainLayoutTrabajadores;

@PageTitle("Marketing Home")
@Route(value = "marketinghome", layout = MainLayoutTrabajadores.class)
@RouteAlias(value = "marketinghome", layout = MainLayoutTrabajadores.class)
public class MarkentingHomeView extends Div {
    
    public MarkentingHomeView() { add(markentingLayout()); }

    private VerticalLayout markentingLayout(){
        VerticalLayout markentingLayout = new VerticalLayout();
        VerticalLayout titleLayout = new VerticalLayout();

        H1 globalTitle = new H1("Mango");
        globalTitle.addClassName("titulo-custom");

        titleLayout.add(globalTitle, new Paragraph("Panel de control de Marketing"));
        titleLayout.setSizeFull();
        titleLayout.setAlignItems(Alignment.CENTER);

        HorizontalLayout cardsServicios = new HorizontalLayout();
        VerticalLayout anadirServiciosCard = new VerticalLayout();
        VerticalLayout consultarServiciosCard = new VerticalLayout();

        H2 crearTitulo = new H2("Crear tarifa");
        Paragraph crearDescription = new Paragraph("Crear una nueva tarifa para los clientes");
        Button anadirButton = new Button("Crear");
        anadirButton.addClassName("boton-naranja-primary");
        anadirButton.addClickListener(event -> {
            UI.getCurrent().navigate("crearTarifa");
        });
        anadirServiciosCard.add(crearTitulo, crearDescription, anadirButton);
        anadirServiciosCard.addClassNames("card");

        H2 consultarTitulo = new H2("Ver tarifas");
        Paragraph consultarDescription = new Paragraph("Ver la lista de todas las tarifas disponibles");
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