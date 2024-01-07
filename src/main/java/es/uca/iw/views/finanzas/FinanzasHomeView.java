package es.uca.iw.views.finanzas;

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
import com.vaadin.flow.server.auth.AnonymousAllowed;

import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.enumerados.Rol;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.views.templates.MainLayoutTrabajadores;

@PageTitle("Finanzas Home")
@Route(value = "finanzashome", layout = MainLayoutTrabajadores.class)
@RouteAlias(value = "finanzashome", layout = MainLayoutTrabajadores.class)
@AnonymousAllowed
public class FinanzasHomeView extends Div {
    
    @Autowired
    private UsuarioService usuarioService;

    private VaadinSession session = VaadinSession.getCurrent();

    public FinanzasHomeView(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;

        if(session.getAttribute("loggedUserId") == null) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Inicia sesión para entrar", "Iniciar sesión", event -> { 
                UI.getCurrent().navigate("login");
            });
            errorDialog.open();
        }
        else {
            Usuario loggedUser = usuarioService.findById(UUID.fromString(session.getAttribute("loggedUserId").toString()));
            if(loggedUser.getRol().equals(Rol.FINANZAS)) crearContenido();
            else {
                ConfirmDialog errorDialog = new ConfirmDialog("Error", "No tienes permisos para entrar aquí", "Inicio", event -> { 
                    UI.getCurrent().navigate("home");
                });
                errorDialog.setCloseOnEsc(false);
                errorDialog.open();
            }
        }
    }

    private void crearContenido() {
        VerticalLayout finanzasLayout = new VerticalLayout();
        VerticalLayout titleLayout = new VerticalLayout();

        H1 globalTitle = new H1("Mango");
        globalTitle.addClassName("titulo-custom");

        titleLayout.add(globalTitle, new Paragraph("Panel de control de Finanzas"));
        titleLayout.setSizeFull();
        titleLayout.setAlignItems(Alignment.CENTER);

        HorizontalLayout cardsServicios = new HorizontalLayout();
        
        VerticalLayout gesionarFacturas = new VerticalLayout();
        H2 tituloFactura = new H2("Gestionar facturas");
        Paragraph descripcionFactura = new Paragraph("Lanza facturas a los clientes");
        Button botonContrato = new Button("Entrar");
        botonContrato.addClassName("boton-naranja-primary");
        botonContrato.addClickListener(event -> {
            UI.getCurrent().navigate("finanzashome/facturas");
        });
        gesionarFacturas.add(tituloFactura, descripcionFactura, botonContrato);
        gesionarFacturas.addClassNames("card");
        cardsServicios.add(gesionarFacturas);
        
        finanzasLayout.add(titleLayout, cardsServicios);
        add(finanzasLayout);
    }
}
