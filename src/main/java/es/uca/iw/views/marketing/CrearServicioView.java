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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility;
import es.uca.iw.aplication.service.ServicioService;
import es.uca.iw.aplication.tables.Servicio;

@PageTitle("Crear Servicio")
@Route(value = "crearServicio")
@RouteAlias(value = "crearServicio")
public class CrearServicioView extends Div {
    @Autowired
    private ServicioService servicioService;

    public CrearServicioView() { add(registrarLayout()); }

    private VerticalLayout registrarLayout() {
        VerticalLayout registrarLayout = new VerticalLayout();
        HorizontalLayout fieldsLayout = new HorizontalLayout();
        VerticalLayout columnaLayout = new VerticalLayout();

        H1 titulo = new H1("Crear un Servicio");
        TextField nombreField = new TextField("Nombre del Servicio.");
        BigDecimalField precioField = new BigDecimalField("Precio");
        
        Binder<Servicio> binderServicio = new Binder<>(Servicio.class);

        binderServicio.forField(nombreField)
            .asRequired("El nombre del Servicio es obligatorio.")
            .bind(Servicio::getNombre, Servicio::setNombre);

        binderServicio.forField(precioField)
            .asRequired("Debes de introducir un valor.")
            .withValidator(value -> value != null && value.compareTo(BigDecimal.ZERO) > 0, "El precio debe ser mayor que 0.")
            .withValidator(value -> value != null && value.scale() <= 2, "El precio no debe tener más de 2 decimales.")
            .bind(Servicio::getPrecio, Servicio::setPrecio);
        
        Button crearBoton = new Button("Crear Servicio");
        crearBoton.addClassName("boton-naranja-primary");
        crearBoton.addClassName(LumoUtility.Margin.Bottom.LARGE);
        crearBoton.addClickShortcut(Key.ENTER);
        crearBoton.addClickListener(event -> {
            if(binderServicio.validate().isOk()) {
                Servicio servicio = new Servicio();
                servicio.setId(UUID.randomUUID());
                servicio.setNombre(nombreField.getValue());
                servicio.setPrecio(precioField.getValue());

                saveRequest(servicio);
            }
        });

        Anchor backToHome = new Anchor("marketinghome", "Inicio");
        backToHome.addClassName(LumoUtility.Margin.Top.LARGE);

        columnaLayout.add(nombreField,precioField);
        fieldsLayout.add(columnaLayout);
        registrarLayout.add(titulo, fieldsLayout, crearBoton, backToHome);

        registrarLayout.setSizeFull();
        registrarLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        registrarLayout.setAlignItems(Alignment.CENTER);

        return registrarLayout;
    }

    private void saveRequest(Servicio servicio){
        try {
            servicioService.createServicio(servicio);
            ConfirmDialog confirmDialog = new ConfirmDialog("Creación Correcto", "Creación realizada correctamente", "Aceptar", event1 -> {
                UI.getCurrent().navigate("marketinghome");
            });
            confirmDialog.open();
        }
        catch (Exception e) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Error al crear el servicio", "Aceptar", event -> { 
                UI.getCurrent().navigate("/crearServicio");
            });
            errorDialog.open();
        }
    }
}