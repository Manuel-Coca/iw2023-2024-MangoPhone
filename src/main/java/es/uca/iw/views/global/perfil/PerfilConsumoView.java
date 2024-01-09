package es.uca.iw.views.global.perfil;

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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;

import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.endPoints.DataUsageRecord;
import es.uca.iw.endPoints.customerLineController;
import es.uca.iw.views.templates.MainLayout;

@PageTitle("Tu consumo de datos")
@Route(value = "profile/consumo", layout = MainLayout.class)
@RouteAlias(value = "profile/consumo", layout = MainLayout.class)
public class PerfilConsumoView extends Div{

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private customerLineController customerLineController;

    private VaadinSession session = VaadinSession.getCurrent();

    public PerfilConsumoView(UsuarioService usuarioService, customerLineController customerLineController) {
        this.usuarioService = usuarioService;
        this.customerLineController = customerLineController;

        if(session.getAttribute("loggedUserId") == null ) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Inicia sesión para ver tu consumo", "Iniciar sesión", event -> { 
                UI.getCurrent().navigate("login");
            });
            errorDialog.open();
        }
        else {
            Usuario loggedUser = usuarioService.findById(UUID.fromString(session.getAttribute("loggedUserId").toString()));
            if(loggedUser.getActivo() == false) {
                ConfirmDialog errorDialog = new ConfirmDialog("Error", "Debes activar tu cuenta para ver tu consumo", "profile", event -> { 
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

        Button volumenButton = new Button("Consultar consumo mensual");
        volumenButton.addClassName("boton-verde-secondary");
        volumenButton.addClickListener(event -> { 
            Dialog volumenDialog = new Dialog();
            
            Button atrasButton = new Button("Cerrar");
            atrasButton.addClassName("boton-naranja-primary");
            atrasButton.addClickListener(event1 -> { 
                volumenDialog.close();
            });

            volumenDialog.getFooter().add(atrasButton);
            volumenDialog.setHeaderTitle("Volumen mensual de datos");
            volumenDialog.add("Has consumido un total de: " + customerLineController.volumenMensualConsumoDatos(loggedUser) +" MegaBytes este mes");
            volumenDialog.setWidth("400px");
            volumenDialog.open();
        });

        Button atrasButton = new Button("Volver");
        atrasButton.addClassName("boton-naranja-primary");
        atrasButton.addClickListener(event -> { 
            UI.getCurrent().navigate("profile");
        });
        botonesLayout.add(volumenButton, atrasButton);

        listaLayout.add(gridConsumo(loggedUser), botonesLayout);
        add(listaLayout);
    }

    private Grid<DataUsageRecord> gridConsumo(Usuario loggedUser) {
        Grid<DataUsageRecord> gridConsumo = new Grid<DataUsageRecord>(DataUsageRecord.class, false);
        gridConsumo.addColumn(DataUsageRecord::getMegBytes).setHeader("Consumo (MegaBytes)").setSortable(true);
        gridConsumo.addColumn(DataUsageRecord::getDate).setHeader("Día").setSortable(true);

        gridConsumo.setItems(customerLineController.dataUsageLine(loggedUser, "", ""));
        
        return gridConsumo;
    }
}
