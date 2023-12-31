package es.uca.iw.views.global.perfil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import es.uca.iw.aplication.service.ContratoService;
import es.uca.iw.aplication.service.Contrato_TarifaService;
import es.uca.iw.aplication.service.CuentaUsuarioService;
import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.Contrato;
import es.uca.iw.aplication.tables.Contrato_Tarifa;
import es.uca.iw.aplication.tables.Factura;
import es.uca.iw.aplication.tables.tarifas.Tarifa;
import es.uca.iw.aplication.tables.usuarios.CuentaUsuario;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.views.templates.MainLayout;

@PageTitle("Tus contratos")
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
    private UsuarioService usuarioService;

    private VaadinSession session = VaadinSession.getCurrent();
    private Optional<Tarifa> selectedTarifa;


    public PerfilContratoView(CuentaUsuarioService cuentaUsuarioService, Contrato_TarifaService contrato_TarifaService, ContratoService contratoService, UsuarioService usuarioService) {
        this.cuentaUsuarioService = cuentaUsuarioService;
        this.contrato_TarifaService = contrato_TarifaService;
        this.contratoService = contratoService;
        this.usuarioService = usuarioService;
        
        VerticalLayout listaLayout = new VerticalLayout();
        Button atrasButton = new Button("Volver");
        atrasButton.addClassName("boton-naranja-primary");
        atrasButton.addClickListener(event -> { 
            UI.getCurrent().navigate("profile");
        });
        
        Button bajaButton = new Button("Darse de baja");
        bajaButton.addClassName("boton-verde-secondary");
        bajaButton.addClickListener(event -> {
            Contrato_Tarifa tarifaContratada = contrato_TarifaService.findByContratoAndTarifa(((Usuario)session.getAttribute("loggedUser")).getCuentaUsuario().getContrato(), selectedTarifa.get());
            contratoService.deleteTarifa(((Usuario)session.getAttribute("loggedUser")).getCuentaUsuario().getContrato(), tarifaContratada);
            UI.getCurrent().getPage().setLocation("profile/contrato");
        });

        listaLayout.add(atrasButton, gridTarifa(), bajaButton);

        
        add(listaLayout);
    }

    private Grid<Tarifa> gridTarifa() {
        Grid<Tarifa> gridTarifa = new Grid<Tarifa>(Tarifa.class, false);
        gridTarifa.addColumn(Tarifa::getServicio).setHeader("Servicio");
        gridTarifa.addColumn(Tarifa::getCapacidad).setHeader("Capacidad");
        gridTarifa.addColumn(Tarifa::getPrecio).setHeader("Precio");
        
        gridTarifa.setItems(getTarifasContratadas());

        gridTarifa.addSelectionListener(selection -> {
            Optional<Tarifa> tarifa = selection.getFirstSelectedItem();
            if(tarifa.isPresent()) {
                selectedTarifa = tarifa;
            }
        });
        
        return gridTarifa;
    } 
    
    private List<Tarifa> getTarifasContratadas() {
        CuentaUsuario cuenta = cuentaUsuarioService.findByDuennoCuenta(((Usuario)session.getAttribute("loggedUser")));
        List<Contrato_Tarifa> contratoTarifaList = contrato_TarifaService.findByContrato(cuenta.getContrato());
        List<Tarifa> tarifaList = new ArrayList<Tarifa>();

        for(Contrato_Tarifa elemento : contratoTarifaList) {
            tarifaList.add(elemento.getTarifa());
        }

        return tarifaList;
    }

    private void SaveRequest(Usuario usuario) {
        try {
            usuarioService.createUsuario(usuario);
            ConfirmDialog confirmDialog = new ConfirmDialog("Éxito", "Cambios realizados correctamente", "Confirmar", event1 -> {
                UI.getCurrent().getPage().setLocation("profile");
            });
            confirmDialog.open();
        }
        catch (Exception e) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Error al cambiar los datos", "Volver", event -> { 
                UI.getCurrent().navigate("/profile");
            });
            errorDialog.open();
        }
    }
}
