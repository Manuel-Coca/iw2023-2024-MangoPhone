package es.uca.iw.views.sac;

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
import com.vaadin.flow.server.auth.AnonymousAllowed;

import es.uca.iw.views.templates.MainLayoutTrabajadores;

@PageTitle("SAC Home")
@Route(value = "sachome", layout = MainLayoutTrabajadores.class)
@RouteAlias(value = "sachome", layout = MainLayoutTrabajadores.class)
@AnonymousAllowed
public class SacHomeView extends Div {

    public SacHomeView() {
        add(crearContenido());
    }

    private VerticalLayout crearContenido() {
        VerticalLayout sacLayout = new VerticalLayout();
        VerticalLayout titleLayout = new VerticalLayout();

        H1 globalTitle = new H1("Mango");
        globalTitle.addClassName("titulo-custom");

        titleLayout.add(globalTitle, new Paragraph("Panel de control de Marketing"));
        titleLayout.setSizeFull();
        titleLayout.setAlignItems(Alignment.CENTER);

        HorizontalLayout cardsServicios = new HorizontalLayout();
        
        VerticalLayout gestionarContratosCard = new VerticalLayout();
        H2 tituloContrato = new H2("Gestionar contratos");
        Paragraph descripcionContrato = new Paragraph("Gestiona los contratos de los clientes");
        Button botonContrato = new Button("Entrar");
        botonContrato.addClassName("boton-naranja-primary");
        botonContrato.addClickListener(event -> {
            UI.getCurrent().navigate("sachome/contratos");
        });
        gestionarContratosCard.add(tituloContrato, descripcionContrato, botonContrato);
        gestionarContratosCard.addClassNames("card");
        cardsServicios.add(gestionarContratosCard);
        sacLayout.add(titleLayout, cardsServicios);

        return sacLayout;
    }
    
}
