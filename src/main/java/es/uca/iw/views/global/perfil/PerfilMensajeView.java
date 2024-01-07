package es.uca.iw.views.global.perfil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import es.uca.iw.aplication.service.MensajeService;
import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.Contrato_Tarifa;
import es.uca.iw.aplication.tables.Factura;
import es.uca.iw.aplication.tables.Mensaje;
import es.uca.iw.aplication.tables.enumerados.Servicio;
import es.uca.iw.aplication.tables.tarifas.Tarifa;
import es.uca.iw.aplication.tables.usuarios.Token;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.views.templates.MainLayout;

@PageTitle("Enviar mensajes")
@Route(value = "profile/mensajes", layout = MainLayout.class)
@RouteAlias(value = "profile/mensajes", layout = MainLayout.class)
@AnonymousAllowed
public class PerfilMensajeView extends Div {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MensajeService mensajeService;

    private Mensaje selectedMensaje;
    private VaadinSession session = VaadinSession.getCurrent();
    
    public PerfilMensajeView(UsuarioService usuarioService, MensajeService mensajeService) {
        this.usuarioService = usuarioService;
        this.mensajeService = mensajeService;

        if(session.getAttribute("loggedUserId") == null ) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Inicia sesión para ver tus mensajes", "Iniciar sesión", event -> { 
                UI.getCurrent().navigate("login");
            });
            errorDialog.open();
        }
        else {
            Usuario loggedUser = usuarioService.findById(UUID.fromString(session.getAttribute("loggedUserId").toString()));
            if(loggedUser.getActivo() == false) {
                ConfirmDialog errorDialog = new ConfirmDialog("Error", "Debes activar tu cuenta para ver tus mensajes", "profile", event -> { 
                    UI.getCurrent().navigate("profile");
                });
                errorDialog.open();
            }
            else {
                crearContenido(loggedUser);
            }
        }
    }

    private void crearContenido(Usuario loggedUser) {
        VerticalLayout listaLayout = new VerticalLayout();
        HorizontalLayout botonesLayout = new HorizontalLayout();
        
        Button crearMensaje = new Button("Enviar un mensaje");
        crearMensaje.addClassName("boton-verde-secondary");
        crearMensaje.addClickListener(event -> {
            Dialog crearMensajeDialog = new Dialog();
            crearMensajeDialog = crearMensajeDialog(loggedUser);
            crearMensajeDialog.open();
        });

        Button atrasButton = new Button("Volver");
        atrasButton.addClassName("boton-naranja-primary");
        atrasButton.addClickListener(event -> { 
            UI.getCurrent().getPage().setLocation("profile");
        });

        botonesLayout.add(crearMensaje, atrasButton);

        listaLayout.add(gridMensajes(loggedUser), botonesLayout);

        add(listaLayout);
    }

    private Grid<Mensaje> gridMensajes(Usuario loggedUser) {
        Grid<Mensaje> gridMensaje = new Grid<Mensaje>(Mensaje.class, false);
        gridMensaje.addColumn(Mensaje::getFechaEmision).setHeader("Fecha de emisión");
        gridMensaje.addColumn(Mensaje::getTipo).setHeader("Tipo");
        gridMensaje.addColumn(Mensaje::getAsunto).setHeader("Asunto");
        gridMensaje.addColumn(Mensaje::getEstado).setHeader("Estado");
        
        gridMensaje.setItems(getMensajes(loggedUser));

        gridMensaje.addSelectionListener(selection -> {
            Optional<Mensaje> mensaje = selection.getFirstSelectedItem();
            if(mensaje.isPresent()) {
                selectedMensaje = mensaje.get();
            }
        });
        
        return gridMensaje;
    }

    private List<Mensaje> getMensajes(Usuario loggedUser) {
        List<Mensaje> mensajes = new ArrayList<Mensaje>();
        mensajes = mensajeService.findByCuentaUsuario(loggedUser.getCuentaUsuario());
        return mensajes;
    }

    private Dialog crearMensajeDialog(Usuario loggedUser) {
        Dialog crearMensajeDialog = new Dialog();
        // Binding
        Binder<Mensaje> binder = new Binder<>(Mensaje.class);
        VerticalLayout dialogLayout = new VerticalLayout();
    
        Select<Mensaje.Tipo> tipoMensajeField = new Select<Mensaje.Tipo>();
        tipoMensajeField.setLabel("Tipo de mensaje");
        tipoMensajeField.setWidthFull();
        tipoMensajeField.setItems(Mensaje.Tipo.Consulta, Mensaje.Tipo.Reclamacion);
        binder.forField(tipoMensajeField)
            .asRequired("Seleccione un tipo de mensaje")
            .bind(Mensaje::getTipo, Mensaje::setTipo);
 
        TextField asuntoField = new TextField();
        asuntoField.setLabel("Asunto");
        asuntoField.setWidthFull();
        binder.forField(asuntoField)
            .asRequired("Escriba un asunto para su mensaje")
            .bind(Mensaje::getAsunto, Mensaje::setAsunto);

        
        TextArea cuerpoField = new TextArea();
        cuerpoField.setLabel("Mensaje");
        cuerpoField.setWidthFull();
        cuerpoField.setMaxHeight("300px");
        cuerpoField.setMaxLength(1500);
        cuerpoField.setValueChangeMode(ValueChangeMode.EAGER);
        cuerpoField.addValueChangeListener(e -> {
            e.getSource().setHelperText(e.getValue().length() + "/" + 1500);
        });
        binder.forField(cuerpoField)
            .asRequired("Escriba un mensaje")
            .bind(Mensaje::getCuerpo, Mensaje::setCuerpo);
        
        dialogLayout.add(tipoMensajeField, asuntoField, cuerpoField);
        
        // Botones
        Button cerrarModal = new Button("Cerrar");
        cerrarModal.addClassName("boton-verde-secondary");
        cerrarModal.addClickListener(eventCerrar -> {
            crearMensajeDialog.close();
        });
        Button confirmar = new Button("Enviar");
        confirmar.addClassName("boton-naranja-primary");
        confirmar.addClickListener(eventEnviar -> {
            if(binder.validate().isOk()) {
                Dialog confirmDialog = new Dialog();
                confirmDialog.setHeaderTitle("¿Desea continuar?");
                confirmDialog.add("¿Estás seguro de que quieres enviar el mensaje?");

                Button cerrarDialog = new Button("No");
                cerrarDialog.addClassName("boton-verde-secondary");
                cerrarDialog.addClickListener(eventCerrar -> { confirmDialog.close(); });

                Button confirmarButton = new Button("Si");
                confirmarButton.addClassName("boton-naranja-primary");
                confirmarButton.addClickListener(eventConfirm -> {
                    Mensaje mensaje = new Mensaje();
                    mensaje.setTipo(tipoMensajeField.getValue());
                    mensaje.setEstado(Mensaje.Estado.Abierto);
                    mensaje.setEmisor(Mensaje.Emisor.Cliente);
                    mensaje.setFechaEmision(LocalDate.now());
                    mensaje.setUsuario(loggedUser.getCuentaUsuario());
                    mensaje.setAsunto(asuntoField.getValue());
                    mensaje.setCuerpo(cuerpoField.getValue());

                    validateRequest(mensaje);

                    confirmDialog.close(); 
                });
                
                confirmDialog.getFooter().add(cerrarDialog, confirmarButton);
                confirmDialog.open();

                UI.getCurrent().getPage().setLocation("profile/mensajes");
            }
        });
        crearMensajeDialog.getFooter().add(cerrarModal, confirmar);

        crearMensajeDialog.setHeaderTitle("Enviar un mensaje");
        crearMensajeDialog.setWidth("600px");
        crearMensajeDialog.add(dialogLayout);
        return crearMensajeDialog;
    }

    public void validateRequest(Mensaje mensaje) {
        try {            
            mensajeService.save(mensaje);
            ConfirmDialog confirmDialog = new ConfirmDialog("Éxito", "Mensaje enviado correctamente", "Confirmar", event1 -> {
                UI.getCurrent().getPage().setLocation("profile/mensajes");
            });
            confirmDialog.open();
        }
        catch (Exception e) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Error al enviar el mensaje", "Reintentar", event -> { 
                UI.getCurrent().getPage().setLocation("profile/mensajes");
            });
            errorDialog.open();
        }
    }
}
