package es.uca.iw.views.sac;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import es.uca.iw.aplication.service.MensajeService;
import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.Mensaje;
import es.uca.iw.aplication.tables.enumerados.Rol;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.views.templates.MainLayoutTrabajadores;

@PageTitle("Mensajes clientes")
@Route(value = "sachome/mensajes", layout = MainLayoutTrabajadores.class)
@RouteAlias(value = "sachome/mensajes", layout = MainLayoutTrabajadores.class)
@AnonymousAllowed
public class MensajesClientesView extends Div {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MensajeService mensajeService;

    private Mensaje selectedMensaje;
    private VaadinSession session = VaadinSession.getCurrent();

    public MensajesClientesView(UsuarioService usuarioService, MensajeService mensajeService) {
        this.usuarioService = usuarioService;
        this.mensajeService = mensajeService;

        if(session.getAttribute("loggedUserId") == null) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Inicia sesión para entrar", "Iniciar sesión", event -> { 
                UI.getCurrent().navigate("login");
            });
            errorDialog.open();
        }
        else {
            Usuario loggedUser = usuarioService.findById(UUID.fromString(session.getAttribute("loggedUserId").toString()));
            if(loggedUser.getRol().equals(Rol.SAC)) crearContenido();
            else {
                ConfirmDialog errorDialog = new ConfirmDialog("Error", "No tienes permisos para entrar aquí", "Inicio", event -> { 
                UI.getCurrent().navigate("home");
                });
                errorDialog.open();
            }
        }
    }

    public void crearContenido() {
        VerticalLayout listaLayout = new VerticalLayout();
        HorizontalLayout botonesLayout = new HorizontalLayout();
        
        Button verMensaje = new Button("Ver el mensaje");
        verMensaje.addClassName("boton-verde-secondary");
        verMensaje.addClickListener(event -> {
            Dialog verMensajeDialog = new Dialog();
            verMensajeDialog = verMensajeDialog();
            verMensajeDialog.open();
        });

        Button atrasButton = new Button("Volver");
        atrasButton.addClassName("boton-naranja-primary");
        atrasButton.addClickListener(event -> { 
            UI.getCurrent().getPage().setLocation("sachome");
        });

        botonesLayout.add(verMensaje, atrasButton);

        listaLayout.add(gridMensajes(), botonesLayout);

        add(listaLayout);
    }

    public Grid<Mensaje> gridMensajes() {
        Grid<Mensaje> gridMensaje = new Grid<Mensaje>(Mensaje.class, false);
        gridMensaje.addColumn(Mensaje::getFechaEmision).setHeader("Fecha de emisión");
        gridMensaje.addColumn(Mensaje::getTipo).setHeader("Tipo");
        gridMensaje.addColumn(Mensaje::getAsunto).setHeader("Asunto");
        gridMensaje.addColumn(Mensaje::getEstado).setHeader("Estado");
        
        gridMensaje.setItems(mensajeService.getAll());

        gridMensaje.addSelectionListener(selection -> {
            Optional<Mensaje> mensaje = selection.getFirstSelectedItem();
            if(mensaje.isPresent()) {
                selectedMensaje = mensaje.get();
            }
        });
        
        return gridMensaje;
    }

    public Dialog verMensajeDialog() {
        Dialog crearMensajeDialog = new Dialog();
        // Binding
        Binder<Mensaje> binder = new Binder<>(Mensaje.class);
        VerticalLayout dialogLayout = new VerticalLayout();
    
        TextField tipoMensajeField = new TextField();
        tipoMensajeField.setLabel("Tipo de mensaje");
        tipoMensajeField.setWidthFull();
        tipoMensajeField.setValue(selectedMensaje.getTipo().toString());
        tipoMensajeField.setReadOnly(true);
 
        TextField asuntoField = new TextField();
        asuntoField.setLabel("Asunto");
        asuntoField.setWidthFull();
        asuntoField.setValue(selectedMensaje.getAsunto());
        asuntoField.setReadOnly(true);
        
        TextArea cuerpoField = new TextArea();
        cuerpoField.setLabel("Mensaje");
        cuerpoField.setWidthFull();
        cuerpoField.setMaxHeight("300px");
        cuerpoField.setValue(selectedMensaje.getCuerpo());
        cuerpoField.setReadOnly(true);

        TextArea respuestaField = new TextArea();
        respuestaField.setLabel("Tu respuesta");
        respuestaField.setWidthFull();
        respuestaField.setMaxHeight("300px");
        binder.forField(respuestaField).asRequired("Introduce tu respuesta").bind(Mensaje::getCuerpo, Mensaje::setCuerpo);
        
        dialogLayout.add(tipoMensajeField, asuntoField, cuerpoField, respuestaField);
        
        // Botones
        Button cerrarModal = new Button("Cerrar");
        cerrarModal.addClassName("boton-verde-secondary");
        cerrarModal.addClickListener(eventCerrar -> {
            crearMensajeDialog.close();
        });
        Button confirmar = new Button("Responder");
        confirmar.addClassName("boton-naranja-primary");
        confirmar.addClickListener(eventEnviar -> {
            if(binder.validate().isOk()) {
                // Respuesta por correo
                // respuestaEmail(selectedMensaje, respuestField.GetValue());
            }
        });
        crearMensajeDialog.getFooter().add(cerrarModal, confirmar);

        crearMensajeDialog.setHeaderTitle("Responder al mensaje");
        crearMensajeDialog.setWidth("600px");
        crearMensajeDialog.add(dialogLayout);
        return crearMensajeDialog;
    }
}
