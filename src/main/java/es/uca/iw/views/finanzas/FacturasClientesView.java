package es.uca.iw.views.finanzas;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;


import es.uca.iw.aplication.service.ContratoService;
import es.uca.iw.aplication.service.FacturaService;
import es.uca.iw.aplication.service.MyEmailService;
import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.Factura;
import es.uca.iw.aplication.tables.Factura.Estado;
import es.uca.iw.aplication.tables.enumerados.Rol;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.views.templates.MainLayoutTrabajadores;

@PageTitle("Facturas Clientes")
@Route(value = "finanzashome/facturas", layout = MainLayoutTrabajadores.class)
@RouteAlias(value = "finanzashome/facturas", layout = MainLayoutTrabajadores.class)

public class FacturasClientesView extends Div {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private MyEmailService myEmailService;

    @Autowired
    private ContratoService contratoService;

    private VaadinSession session = VaadinSession.getCurrent();
    private Usuario selectedUser;
    private Factura selectedFactura;

    public FacturasClientesView(UsuarioService usuarioService, FacturaService facturaService, MyEmailService myEmailService, ContratoService contratoService) {
        this.usuarioService = usuarioService;
        this.facturaService = facturaService;
        this.myEmailService = myEmailService;
        this.contratoService = contratoService;

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
        VerticalLayout layoutGlobal = new VerticalLayout();
        
        Button atrasButton = new Button("Volver");
        atrasButton.addClassName("boton-naranja-primary");
        atrasButton.addClickListener(event -> { 
            UI.getCurrent().navigate("finanzashome");
        });

        Button verFacturas = new Button("Ver facturas");
        verFacturas.addClassName("boton-naranja-primary");
        verFacturas.addClickListener(event -> {
            if(selectedUser.getCuentaUsuario().getContrato() == null) {
                Dialog errorDialog = new Dialog();
                VerticalLayout dialogLayout = new VerticalLayout();
                dialogLayout.add(new Text("El usuario " + selectedUser.getNombre() + " no tiene un contrato."));
                // Botones
                Button cerrarModal = new Button("Cerrar");
                cerrarModal.addClassName("boton-verde-secondary");
                cerrarModal.addClickListener(event1 -> {
                    errorDialog.close();
                });

                errorDialog.getFooter().add(cerrarModal);

                errorDialog.setHeaderTitle("Error");
                errorDialog.setWidth("300px");
                errorDialog.add(dialogLayout);
                errorDialog.open();
            } else {
                Dialog dialog = modalFactura();
                dialog.open();
            }
        });
    
        layoutGlobal.add(atrasButton, gridClientes(), verFacturas);
        
        add(layoutGlobal);
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

        GridListDataView<Usuario> dataView = gridUsuario.setItems(usuarioService.findByRol(Rol.CLIENTE));

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

    private Dialog modalFactura() {
        Dialog facturasDialog = new Dialog();
        VerticalLayout dialogLayout = new VerticalLayout();

        // GRID
        Grid<Factura> gridFacturas = new Grid<Factura>(Factura.class, false);
        gridFacturas.addColumn(Factura::getFechaEmision).setHeader("Fecha de emisión");
        gridFacturas.addColumn(Factura::getEstado).setHeader("Estado");
        
        gridFacturas.setItems(facturaService.findByContrato(selectedUser.getCuentaUsuario().getContrato()));

        gridFacturas.addSelectionListener(selection -> {
            Optional<Factura> factura = selection.getFirstSelectedItem();
            if(factura.isPresent()) {
                selectedFactura = factura.get();
            }
        });

        gridFacturas.setWidthFull();
        dialogLayout.add(gridFacturas);
        
        // BOTONES
        HorizontalLayout botonesLayout = new HorizontalLayout();
        Button enviarFacturaExistente = new Button("Enviar mail de la factura");
        enviarFacturaExistente.addClassName("boton-verde-secondary");
        enviarFacturaExistente.addClickListener(event -> {
            Dialog confirmDialog = new Dialog();
            confirmDialog.setHeaderTitle("¿Desea continuar?");
            confirmDialog.add("¿Estás seguro de que desea enviar la factura seleccionada?");

            Button cerrarDialog = new Button("No");
            cerrarDialog.addClassName("boton-verde-secondary");
            cerrarDialog.addClickListener(eventCerrar -> { confirmDialog.close(); });

            Button confirmarButton = new Button("Si");
            confirmarButton.addClassName("boton-naranja-primary");
            confirmarButton.addClickListener(eventConfirm -> { 
                facturaService.crearFacturaPDFLocal(selectedFactura.getContrato(), selectedFactura);
                myEmailService.sendFacturaEmail(selectedUser, selectedFactura);
                facturaService.eliminarFacturaPDFLocal(selectedFactura);

                confirmDialog.close(); 
            });
            
            confirmDialog.getFooter().add(cerrarDialog, confirmarButton);
            confirmDialog.open();
        });

        Button enviarFacturaActual = new Button("Emitir una nueva factura");
        enviarFacturaActual.addClassName("boton-verde-secondary");
        enviarFacturaActual.addClickListener(event -> {
            Dialog confirmDialog = new Dialog();
            confirmDialog.setHeaderTitle("¿Desea continuar?");
            confirmDialog.add("¿Estás seguro de que desea enviar una nueva factura con los datos del contrato actual?");

            Button cerrarDialog = new Button("No");
            cerrarDialog.addClassName("boton-verde-secondary");
            cerrarDialog.addClickListener(eventCerrar -> { confirmDialog.close(); });

            Button confirmarButton = new Button("Si");
            confirmarButton.addClassName("boton-naranja-primary");
            confirmarButton.addClickListener(eventConfirm -> { 
                Factura factura = new Factura(Estado.Pagado, LocalDate.now(), selectedUser.getCuentaUsuario().getContrato(), facturaService.generarNombreFactura(selectedUser));
                facturaService.save(factura);
                contratoService.addFactura(selectedUser.getCuentaUsuario().getContrato(), factura);
                contratoService.actualizarContrato(selectedUser.getCuentaUsuario().getContrato());

                facturaService.crearFacturaPDFLocal(selectedUser.getCuentaUsuario().getContrato(), factura);
                myEmailService.sendFacturaEmail(selectedUser, factura);
                facturaService.eliminarFacturaPDFLocal(factura);

                facturaService.save(factura);

                confirmDialog.close(); 
            });
            
            confirmDialog.getFooter().add(cerrarDialog, confirmarButton);
            confirmDialog.open();
        });

        botonesLayout.add(enviarFacturaExistente, enviarFacturaActual);
        botonesLayout.getStyle().set("flex-wrap", "wrap");
        dialogLayout.add(botonesLayout);
        
        // Botones
        Button cerrarModal = new Button("Cerrar");
        cerrarModal.addClassName("boton-verde-secondary");
        cerrarModal.addClickListener(event1 -> {
            facturasDialog.close();
        });

        facturasDialog.getFooter().add(cerrarModal);

        facturasDialog.setHeaderTitle("Facturas de " + selectedUser.getNombre());
        facturasDialog.setWidth("800px");
        facturasDialog.add(dialogLayout);
        return facturasDialog;
    }

    private boolean matchesTerm(String s1, String s2) { return s1.equals(s2); }
}
