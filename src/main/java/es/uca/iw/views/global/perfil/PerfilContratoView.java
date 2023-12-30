package es.uca.iw.views.global.perfil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;

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

import es.uca.iw.aplication.service.Contrato_FacturaService;
import es.uca.iw.aplication.service.CuentaUsuarioService;
import es.uca.iw.aplication.tables.Contrato_Factura;
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
    private Contrato_FacturaService contrato_FacturaService;

    private VaadinSession session = VaadinSession.getCurrent();
    private Optional<Tarifa> selectedTarifa;


    public PerfilContratoView(CuentaUsuarioService cuentaUsuarioService, Contrato_FacturaService contrato_FacturaService) {
        this.cuentaUsuarioService = cuentaUsuarioService;
        this.contrato_FacturaService = contrato_FacturaService;   
        
        VerticalLayout listaLayout = new VerticalLayout();
        Button atrasButton = new Button("Volver");
        atrasButton.addClassName("boton-naranja-primary");
        atrasButton.addClickListener(event -> { 
            UI.getCurrent().navigate("profile");
        });
        
        Button cosa = new Button("Cosa");
        cosa.addClickListener(event -> {
            System.out.println(selectedTarifa.get().getId());
        });

        listaLayout.add(atrasButton, gridTarifa(), cosa);

        
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
        List<Contrato_Factura> contratoFacturaList = contrato_FacturaService.findByContrato(cuenta.getContrato());
        List<Factura> facturaList = new ArrayList<Factura>();

        for(Contrato_Factura e : contratoFacturaList) {
            facturaList.add(e.getFactura());
        }

        List<Tarifa> tarifaList = new ArrayList<Tarifa>();

        for(Factura factura : facturaList) {
            tarifaList.add(factura.getTarifa());
        }

        return tarifaList;
    }
}
