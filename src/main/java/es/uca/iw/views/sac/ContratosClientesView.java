package es.uca.iw.views.sac;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;


import es.uca.iw.aplication.service.ContratoService;
import es.uca.iw.aplication.service.Contrato_TarifaService;
import es.uca.iw.aplication.service.CuentaUsuarioService;
import es.uca.iw.aplication.service.EmailService;
import es.uca.iw.aplication.service.FacturaService;
import es.uca.iw.aplication.service.TarifaService;
import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.Contrato;
import es.uca.iw.aplication.tables.Contrato_Tarifa;
import es.uca.iw.aplication.tables.Factura;
import es.uca.iw.aplication.tables.Factura.Estado;
import es.uca.iw.aplication.tables.enumerados.Rol;
import es.uca.iw.aplication.tables.enumerados.Servicio;
import es.uca.iw.aplication.tables.tarifas.Tarifa;
import es.uca.iw.aplication.tables.usuarios.CuentaUsuario;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.views.templates.MainLayoutTrabajadores;

@PageTitle("Contratos clientes")
@Route(value = "sachome/contratos", layout = MainLayoutTrabajadores.class)
@RouteAlias(value = "sachome/contratos", layout = MainLayoutTrabajadores.class)

public class ContratosClientesView extends Div {    
    
    @Autowired
    private Contrato_TarifaService contrato_TarifaService;
    @Autowired
    private TarifaService tarifaService;
    @Autowired
    private CuentaUsuarioService cuentaUsuarioService;
    @Autowired
    private ContratoService contratoService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private FacturaService facturaService;
    @Autowired
    private UsuarioService usuarioService;

    private VaadinSession session = VaadinSession.getCurrent();

    private Usuario selectedUser;
    private Tarifa selectedTarifa;

    public ContratosClientesView(TarifaService tarifaService, ContratoService contratoService, UsuarioService usuarioService, FacturaService facturaService, EmailService emailService, CuentaUsuarioService cuentaUsuarioService) {
        this.tarifaService = tarifaService;
        this.contratoService = contratoService;
        this.usuarioService = usuarioService;
        this.facturaService = facturaService;
        this.emailService = emailService;
        this.cuentaUsuarioService = cuentaUsuarioService;
        
        if(session.getAttribute("loggedUserId") == null) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Inicia sesión para entrar", "Iniciar sesión", event -> { 
                UI.getCurrent().navigate("login");
            });
            errorDialog.open();
        }
        else {
            Usuario loggedUser = usuarioService.findById(UUID.fromString(session.getAttribute("loggedUserId").toString()));
            if(loggedUser.getRol().equals(Rol.SAC)) add(crearContenido());
            else {
                ConfirmDialog errorDialog = new ConfirmDialog("Error", "No tienes permisos para entrar aquí", "Inicio", event -> { 
                UI.getCurrent().navigate("home");
                });
                errorDialog.setCloseOnEsc(false);
                errorDialog.open();
            }
        }
    }
    
    private VerticalLayout crearContenido() {
        VerticalLayout layoutGlobal = new VerticalLayout();
        
        Button atrasButton = new Button("Volver");
        atrasButton.addClassName("boton-naranja-primary");
        atrasButton.addClickListener(event -> { 
            UI.getCurrent().navigate("sachome");
        });

        Button verContrato = new Button("Ver contrato");
        verContrato.addClassName("boton-naranja-primary");
        verContrato.addClickListener(event -> {
            if(selectedUser.getCuentaUsuario().getContrato() == null) {
                Dialog errorDialog = new Dialog();
                VerticalLayout dialogLayout = new VerticalLayout();
                dialogLayout.add(new Text("El usuario " + selectedUser.getNombre() + " no tiene un contrato. ¿Desea crearle uno?"));
                // Botones
                Button cerrarModal = new Button("Cerrar");
                cerrarModal.addClassName("boton-verde-secondary");
                cerrarModal.addClickListener(event1 -> {
                    errorDialog.close();
                });
                Button crearContrato = new Button("Crear contrato");
                crearContrato.addClassName("boton-naranja-primary");
                crearContrato.addClickListener(event1 -> {
                    crearContrato();
                    errorDialog.close();
                });

                errorDialog.getFooter().add(cerrarModal, crearContrato);

                errorDialog.setHeaderTitle("Error");
                errorDialog.setWidth("300px");
                errorDialog.add(dialogLayout);
                errorDialog.open();
            }
            else {
                Dialog dialog = modalContrato();
                dialog.open();
            }
        });
    
        layoutGlobal.add(atrasButton, gridClientes(), verContrato);
        
        return layoutGlobal;
    }

    private VerticalLayout gridClientes() {
        VerticalLayout gridLayout = new VerticalLayout();

        Grid<Usuario> gridUsuario = new Grid<Usuario>(Usuario.class, false);
        gridUsuario.addColumn(Usuario::getNombre).setHeader("Nombre");
        gridUsuario.addColumn(Usuario::getApellidos).setHeader("Apellidos");
        gridUsuario.addColumn(Usuario::getDNI).setHeader("DNI");
        gridUsuario.addColumn(Usuario::getCorreoElectronico).setHeader("Correo electrónico");
        gridUsuario.addColumn(Usuario::getTelefono).setHeader("Teléfono");

        gridUsuario.addSelectionListener(selection -> {
            Optional<Usuario> usuario = selection.getFirstSelectedItem();
            if(usuario.isPresent()) {
                selectedUser = usuario.get();
            }
        });

        GridListDataView<Usuario> dataView = gridUsuario.setItems(getClientes());

        TextField searchField = new TextField();
        searchField.setPlaceholder("Buscar");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> dataView.refreshAll());

        dataView.addFilter(usuario -> {
            String searchTerm = searchField.getValue().trim();
            if(searchTerm.isEmpty()) return true;

            boolean matchName = matchesTerm(usuario.getNombre(), searchTerm);
            boolean matchesApellidos = matchesTerm(usuario.getApellidos(), searchTerm);
            boolean matchesDNI = matchesTerm(usuario.getDNI(), searchTerm);
            boolean matchesEmail = matchesTerm(usuario.getCorreoElectronico(), searchTerm);
            boolean matchesTlfn = matchesTerm(usuario.getTelefono(), searchTerm);

            return matchName || matchesApellidos || matchesDNI || matchesEmail || matchesTlfn;
        });

        gridLayout.add(searchField, gridUsuario);
        return gridLayout;
    }

    private Dialog modalContrato() {
        Dialog contratoDialog = new Dialog();
        VerticalLayout dialogLayout = new VerticalLayout();

        // GRID
        Grid<Tarifa> gridTarifa = new Grid<Tarifa>(Tarifa.class, false);
        gridTarifa.addColumn(Tarifa::getServicio).setHeader("Servicio");
        gridTarifa.addColumn(Tarifa::getCapacidad).setHeader("Capacidad");
        gridTarifa.addColumn(Tarifa::getPrecio).setHeader("Precio");
        
        gridTarifa.setItems(getTarifasContratadas(selectedUser));

        gridTarifa.addSelectionListener(selection -> {
            Optional<Tarifa> tarifa = selection.getFirstSelectedItem();
            if(tarifa.isPresent()) {
                selectedTarifa = tarifa.get();
            }
        });

        gridTarifa.setWidthFull();
        dialogLayout.add(gridTarifa);
        
        // BOTONES
        HorizontalLayout botonesLayout = new HorizontalLayout();
        Button nuevaTarifaMovil = new Button("Añadir tarifa movil");
        nuevaTarifaMovil.addClassName("boton-verde-secondary");
        nuevaTarifaMovil.addClickListener(event -> {
            Dialog a = new Dialog();
            a = crearModalNuevaTarifa(Servicio.MOVIL);
            a.open();
        });

        Button nuevaTarifaFibra = new Button("Añadir tarifa de fibra");
        nuevaTarifaFibra.addClassName("boton-verde-secondary");
        nuevaTarifaFibra.addClickListener(event -> {
            Dialog a = new Dialog();
            a = crearModalNuevaTarifa(Servicio.FIBRA);
            a.open();
        });

        Button nuevaTarifaFijo = new Button("Añadir tarifa de fijo");
        nuevaTarifaFijo.addClassName("boton-verde-secondary");
        nuevaTarifaFijo.addClickListener(event -> {
            Dialog a = new Dialog();
            a = crearModalNuevaTarifa(Servicio.FIJO);
            a.open();
        });

        List<Contrato_Tarifa> tarifasContratadas = contrato_TarifaService.findByContrato(selectedUser.getCuentaUsuario().getContrato());
        for(Contrato_Tarifa elemento : tarifasContratadas) {
            if(elemento.getTarifa().getServicio() == Servicio.MOVIL) nuevaTarifaMovil.setVisible(false);
            if(elemento.getTarifa().getServicio() == Servicio.FIBRA) nuevaTarifaFibra.setVisible(false);
            if(elemento.getTarifa().getServicio() == Servicio.FIJO) nuevaTarifaFijo.setVisible(false);
        }

        Button cambiarTarifaButton = new Button("Cambiar de tarifa");
        cambiarTarifaButton.addClassName("boton-verde-secondary");
        cambiarTarifaButton.addClickListener(event -> {
            Dialog a = new Dialog();
            a = crearModalCambioTarifa();
            a.open();
        });
        
        Button bajaButton = new Button("Dar de baja");
        bajaButton.addClassName("boton-verde-secondary");
        bajaButton.addClickListener(event -> {
            Contrato_Tarifa tarifaContratada = contrato_TarifaService.findByContratoAndTarifa(selectedUser.getCuentaUsuario().getContrato(), selectedTarifa);
            contratoService.deleteTarifa(tarifaContratada);
            contratoService.actualizarContrato(contratoService.findByCuentaUsuario(selectedUser.getCuentaUsuario()));

            UI.getCurrent().getPage().setLocation("sachome/contratos");
        });

        botonesLayout.add(nuevaTarifaMovil, nuevaTarifaFibra, nuevaTarifaFijo, cambiarTarifaButton, bajaButton);
        botonesLayout.getStyle().set("flex-wrap", "wrap");
        dialogLayout.add(botonesLayout);
        
        // Botones
        Button cerrarModal = new Button("Cerrar");
        cerrarModal.addClassName("boton-verde-secondary");
        cerrarModal.addClickListener(event1 -> {
            contratoDialog.close();
        });

        contratoDialog.getFooter().add(cerrarModal);

        contratoDialog.setHeaderTitle("Contrato de " + selectedUser.getNombre());
        contratoDialog.setWidth("800px");
        contratoDialog.add(dialogLayout);
        return contratoDialog;
    }

    private Dialog crearModalNuevaTarifa(Servicio servicio) {
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
        Button cerrarModal = new Button("Volver");
        cerrarModal.addClassName("boton-verde-secondary");
        cerrarModal.addClickListener(event1 -> {
            editDialog.close();
        });

        Button confirmar = new Button("Contratar");
        confirmar.addClassName("boton-naranja-primary");
        confirmar.addClickListener(event2 -> {
            if(binder.validate().isOk()) {
                contratoService.addTarifa(contratoService.findByCuentaUsuario(selectedUser.getCuentaUsuario()), tarifaField.getValue());
                contratoService.actualizarContrato(contratoService.findByCuentaUsuario(selectedUser.getCuentaUsuario()));

                UI.getCurrent().getPage().setLocation("sachome/contratos");
            }
        });
        editDialog.getFooter().add(cerrarModal, confirmar);

        editDialog.setHeaderTitle("Selecciona una nueva tarifa de " + servicio);
        editDialog.setWidth("600px");
        editDialog.add(dialogLayout);
        return editDialog;

    }

    private Dialog crearModalCambioTarifa() {
        Dialog editDialog = new Dialog();
        // Binding
        Binder<Tarifa> binder = new Binder<>(Tarifa.class);
        VerticalLayout dialogLayout = new VerticalLayout();
    
        Select<Tarifa> tarifaField = new Select<Tarifa>();
        tarifaField.setLabel("Nueva tarifa");
        tarifaField.setWidthFull();
        tarifaField.setItems(getTarifas(selectedTarifa.getServicio(), selectedTarifa));
        if(selectedTarifa.getServicio() == Servicio.FIJO) {
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
        Button cerrarModal = new Button("Volver");
        cerrarModal.addClassName("boton-verde-secondary");
        cerrarModal.addClickListener(event1 -> {
            editDialog.close();
        });
        Button confirmar = new Button("Cambiar");
        confirmar.addClassName("boton-naranja-primary");
        confirmar.addClickListener(event2 -> {
            if(binder.validate().isOk()) {
                Contrato_Tarifa tarifaContratada = contrato_TarifaService.findByContratoAndTarifa(selectedUser.getCuentaUsuario().getContrato(), selectedTarifa);
                contratoService.deleteTarifa(tarifaContratada);
                contratoService.addTarifa(contratoService.findByCuentaUsuario(selectedUser.getCuentaUsuario()), tarifaField.getValue());
                contratoService.actualizarContrato(contratoService.findByCuentaUsuario(selectedUser.getCuentaUsuario()));

                UI.getCurrent().getPage().setLocation("profile/contrato");
            }
        });
        editDialog.getFooter().add(cerrarModal, confirmar);

        editDialog.setHeaderTitle("Selecciona una nueva tarifa de " + selectedTarifa.getServicio());
        editDialog.setWidth("600px");
        editDialog.add(dialogLayout);
        return editDialog;
    }

    private List<Usuario> getClientes() {
        List<Usuario> clientes = usuarioService.findByRol(Rol.CLIENTE);
        return clientes;
    }

    private List<Tarifa> getTarifasContratadas(Usuario user) {
        CuentaUsuario cuenta = cuentaUsuarioService.findByDuennoCuenta(user);
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

    private boolean matchesTerm(String s1, String s2) { return s1.equals(s2); }

    private void crearContrato() {
        //Recuperamos usuario
        Contrato contrato = selectedUser.getCuentaUsuario().getContrato();

        //Si no tiene contrato creamos uno, junto a su factura correspondiente y lo asignamos a la cuenta de usuario y viceversa
        if(contrato == null) {
            contrato = new Contrato();
            contrato.setFechaInicio(LocalDate.now());

            contratoService.asignarCuentaUsuario(contrato, selectedUser.getCuentaUsuario());
            cuentaUsuarioService.asignarContrato(selectedUser.getCuentaUsuario(), contrato);

            contratoService.save(contrato);
            cuentaUsuarioService.save(selectedUser.getCuentaUsuario());

            contratoService.actualizarContrato(contrato);
        }
        
        boolean alta = true;
        if(alta) {
            ConfirmDialog confirmDialog = new ConfirmDialog("Éxito", "Se ha creado el contrato", "Cerrar", event -> {
                UI.getCurrent().navigate("sachome/contratos");
            });
            confirmDialog.open();

            Factura factura = new Factura(Estado.Pagado, LocalDate.now(), contrato, facturaService.generarNombreFactura(selectedUser));
            facturaService.save(factura);
            contratoService.addFactura(contrato, factura);
            contratoService.actualizarContrato(contrato);

            facturaService.crearFacturaPDFLocal(contrato, factura);
            emailService.sendFacturaEmail(selectedUser, factura);

            facturaService.save(factura);
        }
    }
}
