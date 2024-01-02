package es.uca.iw.views.global.perfil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import es.uca.iw.aplication.tables.Factura;
import es.uca.iw.aplication.tables.Mensaje;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.views.templates.MainLayout;

@PageTitle("Enviar mensajes")
@Route(value = "profile/mensajes", layout = MainLayout.class)
@RouteAlias(value = "profile/mensaje", layout = MainLayout.class)
@AnonymousAllowed
public class PerfilMensajeView extends Div {

    private Mensaje selectedMensaje;
    
    public PerfilMensajeView() {
        VerticalLayout listaLayout = new VerticalLayout();
        HorizontalLayout botonesLayout = new HorizontalLayout();
        
        Button atrasButton = new Button("Volver");
        atrasButton.addClassName("boton-naranja-primary");
        atrasButton.addClickListener(event -> { 
            UI.getCurrent().navigate("profile");
        });

        botonesLayout.add(atrasButton);

        listaLayout.add(gridMensajes(), botonesLayout);

        add(listaLayout);
    }

    private Grid<Mensaje> gridMensajes() {
        Grid<Mensaje> gridMensaje = new Grid<Mensaje>(Mensaje.class, false);
        gridMensaje.addColumn(Mensaje::getFechaEmision).setHeader("Fecha de emisión");
        gridMensaje.addColumn(Mensaje::getTipo).setHeader("Tipo");
        gridMensaje.addColumn(Mensaje::getEstado).setHeader("Estado");
        
        gridMensaje.setItems(getMensajes());

        gridMensaje.addSelectionListener(selection -> {
            Optional<Mensaje> mensaje = selection.getFirstSelectedItem();
            if(mensaje.isPresent()) {
                selectedMensaje = mensaje.get();
            }
        });
        
        return gridMensaje;
    }

    private List<Mensaje> getMensajes() {
        List<Mensaje> mensajes = new ArrayList<Mensaje>();


        return mensajes;
    }
}
