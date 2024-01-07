package es.uca.iw.views.marketing;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import es.uca.iw.aplication.service.TarifaService;
import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.enumerados.Rol;
import es.uca.iw.aplication.tables.enumerados.Servicio;
import es.uca.iw.aplication.tables.tarifas.Tarifa;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.views.templates.MainLayoutTrabajadores;

@PageTitle("Crear Tarifa")
@Route(value = "crearTarifa", layout = MainLayoutTrabajadores.class)
@RouteAlias(value = "crearTarifa", layout = MainLayoutTrabajadores.class)
@AnonymousAllowed
public class CrearTarifaView extends Div {
    @Autowired
    private TarifaService tarifaService;

    @Autowired
    private UsuarioService usuarioService;

    private VaadinSession session = VaadinSession.getCurrent();

    public CrearTarifaView(UsuarioService usuarioService, TarifaService tarifaService) { 
        this.usuarioService = usuarioService;
        this.tarifaService = tarifaService;

        if(session.getAttribute("loggedUserId") == null) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Inicia sesión para entrar", "Iniciar sesión", event -> { 
                UI.getCurrent().navigate("login");
            });
            errorDialog.open();
        }
        else {
            Usuario loggedUser = usuarioService.findById(UUID.fromString(session.getAttribute("loggedUserId").toString()));
            if(loggedUser.getRol().equals(Rol.MARKETING)) add(registrarLayout()); 
            else {
                ConfirmDialog errorDialog = new ConfirmDialog("Error", "No tienes permisos para entrar aquí", "Inicio", event -> { 
                UI.getCurrent().navigate("home");
                });
                errorDialog.setCloseOnEsc(false);
                errorDialog.open();
            }
        } 
    }

    private VerticalLayout registrarLayout() {
        VerticalLayout registrarLayout = new VerticalLayout();
        HorizontalLayout fieldsLayout = new HorizontalLayout();
        VerticalLayout columnaLayout = new VerticalLayout();

        H1 titulo = new H1("Crear un Tarifa");
        Select<Servicio> servicioField = new Select<Servicio>();
        servicioField.setLabel("Servicio");
        servicioField.setItems(Servicio.FIBRA, Servicio.FIJO, Servicio.MOVIL);
        IntegerField capacidadField = new IntegerField("Capacidad");
        BigDecimalField precioField = new BigDecimalField("Precio");
        
        Binder<Tarifa> binderTarifa = new Binder<>(Tarifa.class);

        binderTarifa.forField(servicioField)
            .asRequired("El servicio de la tarifa es obligatorio.")
            .bind(Tarifa::getServicio, Tarifa::setServicio);

        binderTarifa.forField(precioField)
            .asRequired("El precio de la tarifa es obligatorio.")
            .withValidator(value -> value != null && value.compareTo(BigDecimal.ZERO) > 0, "El precio debe ser mayor que 0.")
            .withValidator(value -> value != null && value.scale() <= 2, "El precio no debe tener más de 2 decimales.")
            .bind(Tarifa::getPrecio, Tarifa::setPrecio);
        
        Button crearBoton = new Button("Crear Tarifa");
        crearBoton.addClassName("boton-naranja-primary");
        crearBoton.addClassName(LumoUtility.Margin.Bottom.LARGE);
        crearBoton.addClickShortcut(Key.ENTER);
        crearBoton.addClickListener(event -> {
            if(binderTarifa.validate().isOk()) {
                Tarifa Tarifa = new Tarifa();
                Tarifa.setId(UUID.randomUUID());
                Tarifa.setServicio(servicioField.getValue());
                Tarifa.setCapacidad(capacidadField.getValue());
                Tarifa.setPrecio(precioField.getValue());

                saveRequest(Tarifa);
            }
        });

        Anchor backToHome = new Anchor("marketinghome", "Volver");
        backToHome.addClassName(LumoUtility.Margin.Top.LARGE);

        columnaLayout.add(servicioField, capacidadField, precioField);
        fieldsLayout.add(columnaLayout);
        registrarLayout.add(titulo, fieldsLayout, crearBoton, backToHome);

        registrarLayout.setSizeFull();
        registrarLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        registrarLayout.setAlignItems(Alignment.CENTER);
        
        return registrarLayout;
    }

    private void saveRequest(Tarifa Tarifa){
        try {
            tarifaService.createTarifa(Tarifa);
            ConfirmDialog confirmDialog = new ConfirmDialog("Tarifa creada", "La tarifa se creó correctamente", "Aceptar", event1 -> {
                UI.getCurrent().navigate("marketinghome");
            });
            confirmDialog.open();
        }
        catch (Exception e) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Error al crear la tarifa", "Reintentar", event -> { 
                UI.getCurrent().navigate("/crearTarifa");
            });
            errorDialog.open();
        }
    }
}