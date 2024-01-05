package es.uca.iw.views.global.perfil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.lowagie.text.Paragraph;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.ScrollOptions.Alignment;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import es.uca.iw.aplication.service.ContratoService;
import es.uca.iw.aplication.service.Contrato_TarifaService;
import es.uca.iw.aplication.service.CuentaUsuarioService;
import es.uca.iw.aplication.service.TarifaService;
import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.Contrato_Tarifa;
import es.uca.iw.aplication.tables.enumerados.Servicio;
import es.uca.iw.aplication.tables.tarifas.Tarifa;
import es.uca.iw.aplication.tables.usuarios.CuentaUsuario;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.views.templates.MainLayout;

@PageTitle("Tu contrato")
@Route(value = "profile/contrato", layout = MainLayout.class)
@RouteAlias(value = "profile/contrato", layout = MainLayout.class)
@AnonymousAllowed
public class PerfilContratoView extends Div {

    @Autowired
    private CuentaUsuarioService cuentaUsuarioService;

    @Autowired
    private Contrato_TarifaService contrato_TarifaService;

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private TarifaService tarifaService;

    @Autowired
    private UsuarioService usuarioService;

    private VaadinSession session = VaadinSession.getCurrent();
    private Optional<Tarifa> selectedTarifa;


    public PerfilContratoView(CuentaUsuarioService cuentaUsuarioService, Contrato_TarifaService contrato_TarifaService, ContratoService contratoService, TarifaService tarifaService, UsuarioService usuarioService) {
        this.cuentaUsuarioService = cuentaUsuarioService;
        this.contrato_TarifaService = contrato_TarifaService;
        this.contratoService = contratoService;
        this.tarifaService = tarifaService;
        this.usuarioService = usuarioService;
        
        if(session.getAttribute("loggedUserId") == null ) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Inicia sesión para ver tu contrato", "Iniciar sesión", event -> { 
                UI.getCurrent().navigate("login");
            });
            errorDialog.open();
        }
        else {
            Usuario loggedUser = usuarioService.findById(UUID.fromString(session.getAttribute("loggedUserId").toString()));
            if(loggedUser.getActivo() == false) {
                ConfirmDialog errorDialog = new ConfirmDialog("Error", "Debes activar tu cuenta para ver tu contrato", "profile", event -> { 
                    UI.getCurrent().navigate("profile");
                });
                errorDialog.open();
            }
            else {
                VerticalLayout listaLayout = new VerticalLayout();
                HorizontalLayout botonesLayout = new HorizontalLayout();
                botonesLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        
                Button nuevaTarifaMovil = new Button("Añadir tarifa movil");
                nuevaTarifaMovil.addClassName("boton-verde-secondary");
                nuevaTarifaMovil.addClickListener(event -> {
                    Dialog editDialog = crearModalNuevaTarifa(Servicio.MOVIL, loggedUser);
                    editDialog.open();
                });
        
                Button nuevaTarifaFibra = new Button("Añadir tarifa de fibra");
                nuevaTarifaFibra.addClassName("boton-verde-secondary");
                nuevaTarifaFibra.addClickListener(event -> {
                    Dialog editDialog = crearModalNuevaTarifa(Servicio.FIBRA, loggedUser);
                    editDialog.open();
                });
        
                Button nuevaTarifaFijo = new Button("Añadir tarifa de fijo");
                nuevaTarifaFijo.addClassName("boton-verde-secondary");
                nuevaTarifaFijo.addClickListener(event -> {
                    Dialog editDialog = crearModalNuevaTarifa(Servicio.FIJO, loggedUser);
                    editDialog.open();
                });
        
                List<Contrato_Tarifa> tarifasContratadas = contrato_TarifaService.findByContrato(loggedUser.getCuentaUsuario().getContrato());
                for(Contrato_Tarifa elemento : tarifasContratadas) {
                    if(elemento.getTarifa().getServicio() == Servicio.MOVIL) nuevaTarifaMovil.setVisible(false);
                    if(elemento.getTarifa().getServicio() == Servicio.FIBRA) nuevaTarifaFibra.setVisible(false);
                    if(elemento.getTarifa().getServicio() == Servicio.FIJO) nuevaTarifaFijo.setVisible(false);
                }
        
                Button cambiarTarifaButton = new Button("Cambiar de tarifa");
                cambiarTarifaButton.addClassName("boton-verde-secondary");
                cambiarTarifaButton.addClickListener(event -> {
                    Dialog editDialog = crearModalCambioTarifa(loggedUser);
                    editDialog.open();
                });
                
                Button bajaButton = new Button("Darse de baja");
                bajaButton.addClassName("boton-verde-secondary");
                bajaButton.addClickListener(event -> {
                    Contrato_Tarifa tarifaContratada = contrato_TarifaService.findByContratoAndTarifa(loggedUser.getCuentaUsuario().getContrato(), selectedTarifa.get());
                    contratoService.deleteTarifa(tarifaContratada);
                    contratoService.actualizarContrato(contratoService.findByCuentaUsuario(loggedUser.getCuentaUsuario()));
        
                    UI.getCurrent().getPage().setLocation("profile/contrato");
                });
                
                Button atrasButton = new Button("Volver");
                atrasButton.addClassName("boton-naranja-primary");
                atrasButton.addClickListener(event -> { 
                    UI.getCurrent().navigate("profile");
                });
        
                Image infoIcon = new Image("icons/info-icon.svg", "info");
                infoIcon.setWidth("30px");
                infoIcon.setHeight("30px");
                infoIcon.addClickListener(eventInfo -> {
                    Dialog infoDialog = new Dialog();
                    
                    VerticalLayout dialogLayout = new VerticalLayout();
                    dialogLayout.add(new Text("Podrá añadir una tarifa del servicio que no esté en su contrato pulsando el botón correspondiente. " +
                    "Si encuentra una tarifa que le interese más que la actual, seleccione la tarifa que desee cambiar y pulse el botón 'Cambiar tarifa'. " + 
                    "Si desea cancelar alguna tarifa de su contrato, selecciónela y pulse 'Darse de baja'"));
                    infoDialog.setHeaderTitle("Ayuda");
                    
                    Button cerrarDialog = new Button("Cerrar");
                    cerrarDialog.addClassName("boton-verde-secondary");
                    cerrarDialog.addClickListener(eventCerrar -> { infoDialog.close(); });
                    infoDialog.getFooter().add(cerrarDialog);
                    
                    infoDialog.setWidth("600px");
                    infoDialog.add(dialogLayout);
                    infoDialog.open();
                });

                botonesLayout.add(nuevaTarifaMovil, nuevaTarifaFibra, nuevaTarifaFijo, cambiarTarifaButton, bajaButton, atrasButton, infoIcon);
        
                listaLayout.add(gridTarifa(loggedUser), botonesLayout);
        
                add(listaLayout);
            }
        }
    }

    private Grid<Tarifa> gridTarifa(Usuario loggedUser) {
        Grid<Tarifa> gridTarifa = new Grid<Tarifa>(Tarifa.class, false);
        gridTarifa.addColumn(Tarifa::getServicio).setHeader("Servicio");
        gridTarifa.addColumn(Tarifa::getCapacidad).setHeader("Capacidad");
        gridTarifa.addColumn(Tarifa::getPrecio).setHeader("Precio");
        
        gridTarifa.setItems(getTarifasContratadas(loggedUser));

        gridTarifa.addSelectionListener(selection -> {
            Optional<Tarifa> tarifa = selection.getFirstSelectedItem();
            if(tarifa.isPresent()) {
                selectedTarifa = tarifa;
            }
        });
        
        return gridTarifa;
    } 
    
    private List<Tarifa> getTarifasContratadas(Usuario loggedUser) {
        CuentaUsuario cuenta = cuentaUsuarioService.findByDuennoCuenta(loggedUser);
        List<Contrato_Tarifa> contratoTarifaList = contrato_TarifaService.findByContrato(cuenta.getContrato());
        List<Tarifa> tarifaList = new ArrayList<Tarifa>();

        for(Contrato_Tarifa elemento : contratoTarifaList) {
            tarifaList.add(elemento.getTarifa());
        }

        return tarifaList;
    }

    private List<Tarifa> getTarifas(Servicio servicio, Tarifa selectedTarifa) {
        List<Tarifa> tarifas = new ArrayList<Tarifa>();
        tarifas = tarifaService.getTarifaByServicio(servicio);
        tarifas.remove(selectedTarifa);
        return tarifas;
    }

    private Dialog crearModalCambioTarifa(Usuario loggedUser) {
        Dialog editDialog = new Dialog();
        // Binding
        Binder<Tarifa> binder = new Binder<>(Tarifa.class);
        VerticalLayout dialogLayout = new VerticalLayout();
    
        Select<Tarifa> tarifaField = new Select<Tarifa>();
        tarifaField.setLabel("Nueva tarifa");
        tarifaField.setWidthFull();
        tarifaField.setItems(getTarifas(selectedTarifa.get().getServicio(), selectedTarifa.get()));
        if(selectedTarifa.get().getServicio() == Servicio.FIJO) {
            tarifaField.setItemLabelGenerator((ItemLabelGenerator<Tarifa>) tarifa ->
                "Capacidad: " + tarifa.getCapacidad() + " min/mes -- Precio: " + tarifa.getPrecio() + "€/mes"    
            );    
        }
        else {
            tarifaField.setItemLabelGenerator((ItemLabelGenerator<Tarifa>) tarifa ->
                "Capacidad: " + tarifa.getCapacidad() + " GB/mes -- Precio: " + tarifa.getPrecio() + "€/mes"    
            ); 
        }

        binder.forField(tarifaField)
                .asRequired("La nueva tarifa es obligatoria");

        dialogLayout.add(tarifaField);
        
        // Botones
        Button cerrarModal = new Button("Cerrar");
        cerrarModal.addClassName("boton-verde-secondary");
        cerrarModal.addClickListener(event1 -> {
            editDialog.close();
        });
        Button confirmar = new Button("Cambiar");
        confirmar.addClassName("boton-naranja-primary");
        confirmar.addClickListener(event2 -> {
            if(binder.validate().isOk()) {
                Contrato_Tarifa tarifaContratada = contrato_TarifaService.findByContratoAndTarifa(loggedUser.getCuentaUsuario().getContrato(), selectedTarifa.get());
                contratoService.deleteTarifa(tarifaContratada);
                contratoService.addTarifa(contratoService.findByCuentaUsuario(loggedUser.getCuentaUsuario()), tarifaField.getValue());
                contratoService.actualizarContrato(contratoService.findByCuentaUsuario(loggedUser.getCuentaUsuario()));

                UI.getCurrent().getPage().setLocation("profile/contrato");
            }
        });
        editDialog.getFooter().add(cerrarModal, confirmar);

        editDialog.setHeaderTitle("Selecciona una nueva tarifa de " + selectedTarifa.get().getServicio());
        editDialog.setWidth("600px");
        editDialog.add(dialogLayout);
        return editDialog;
    }

    private Dialog crearModalNuevaTarifa(Servicio servicio, Usuario loggedUser) {
        Dialog editDialog = new Dialog();
        // Binding
        Binder<Tarifa> binder = new Binder<>(Tarifa.class);
        VerticalLayout dialogLayout = new VerticalLayout();
    
        Select<Tarifa> tarifaField = new Select<Tarifa>();
        tarifaField.setLabel("Nueva tarifa");
        tarifaField.setWidthFull();
        tarifaField.setItems(tarifaService.getTarifaByServicio(servicio));
        if(servicio == Servicio.FIJO) {
            tarifaField.setItemLabelGenerator((ItemLabelGenerator<Tarifa>) tarifa ->
                "Capacidad: " + tarifa.getCapacidad() + " min/mes -- Precio: " + tarifa.getPrecio() + "€/mes"    
            );    
        }
        else {
            tarifaField.setItemLabelGenerator((ItemLabelGenerator<Tarifa>) tarifa ->
                "Capacidad: " + tarifa.getCapacidad() + " GB/mes -- Precio: " + tarifa.getPrecio() + "€/mes"    
            ); 
        }

        binder.forField(tarifaField)
                .asRequired("La nueva tarifa es obligatoria");

        dialogLayout.add(tarifaField);
        
        // Botones
        Button cerrarModal = new Button("Cerrar");
        cerrarModal.addClassName("boton-verde-secondary");
        cerrarModal.addClickListener(event1 -> {
            editDialog.close();
        });

        Button confirmar = new Button("Contratar");
        confirmar.addClassName("boton-naranja-primary");
        confirmar.addClickListener(event2 -> {
            if(binder.validate().isOk()) {
                contratoService.addTarifa(contratoService.findByCuentaUsuario(loggedUser.getCuentaUsuario()), tarifaField.getValue());
                contratoService.actualizarContrato(contratoService.findByCuentaUsuario(loggedUser.getCuentaUsuario()));

                UI.getCurrent().getPage().setLocation("profile/contrato");
            }
        });
        editDialog.getFooter().add(cerrarModal, confirmar);

        editDialog.setHeaderTitle("Selecciona una nueva tarifa de " + servicio);
        editDialog.setWidth("600px");
        editDialog.add(dialogLayout);
        return editDialog;
    }
}
