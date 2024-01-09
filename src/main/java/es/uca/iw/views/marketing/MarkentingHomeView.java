package es.uca.iw.views.marketing;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
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
import com.vaadin.flow.server.VaadinSession;


import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.enumerados.Rol;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.views.templates.MainLayoutTrabajadores;

@PageTitle("Marketing Home")
@Route(value = "marketinghome", layout = MainLayoutTrabajadores.class)
@RouteAlias(value = "marketinghome", layout = MainLayoutTrabajadores.class)

public class MarkentingHomeView extends Div {

    @Autowired
    private UsuarioService usuarioService;

    private VaadinSession session = VaadinSession.getCurrent();
    
    public MarkentingHomeView(UsuarioService usuarioService) { 
        this.usuarioService = usuarioService;

        if(session.getAttribute("loggedUserId") == null) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Inicia sesión para entrar", "Iniciar sesión", event -> { 
                UI.getCurrent().navigate("login");
            });
            errorDialog.open();
        }
        else {
            Usuario loggedUser = usuarioService.findById(UUID.fromString(session.getAttribute("loggedUserId").toString()));
            if(loggedUser.getRol().equals(Rol.MARKETING)) add(markentingLayout());
            else {
                ConfirmDialog errorDialog = new ConfirmDialog("Error", "No tienes permisos para entrar aquí", "Inicio", event -> { 
                UI.getCurrent().navigate("home");
                });
                errorDialog.setCloseOnEsc(false);
                errorDialog.open();
            }
        }
    }

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