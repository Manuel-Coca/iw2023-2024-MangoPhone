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
import es.uca.iw.endPoints.CallRecord;
import es.uca.iw.endPoints.customerLineController;
import es.uca.iw.views.templates.MainLayout;

@PageTitle("Tus llamadas")
@Route(value = "profile/llamadas", layout = MainLayout.class)
@RouteAlias(value = "profile/llamadas", layout = MainLayout.class)
public class PerfilLlamadasView extends Div{

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private customerLineController customerLineController;

    private VaadinSession session = VaadinSession.getCurrent();

    public PerfilLlamadasView(UsuarioService usuarioService, customerLineController customerLineController) {
        this.usuarioService = usuarioService;
        this.customerLineController = customerLineController;

        if(session.getAttribute("loggedUserId") == null ) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Inicia sesión para ver tus llamadas", "Iniciar sesión", event -> { 
                UI.getCurrent().navigate("login");
            });
            errorDialog.open();
        }
        else {
            Usuario loggedUser = usuarioService.findById(UUID.fromString(session.getAttribute("loggedUserId").toString()));
            if(loggedUser.getActivo() == false) {
                ConfirmDialog errorDialog = new ConfirmDialog("Error", "Debes activar tu cuenta para ver tus llamadas", "profile", event -> { 
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

        Button volumenButton = new Button("Consultar volumen mensual");
        volumenButton.addClassName("boton-verde-secondary");
        volumenButton.addClickListener(event -> { 
            Dialog volumenDialog = new Dialog();
            
            Button atrasButton = new Button("Cerrar");
            atrasButton.addClassName("boton-naranja-primary");
            atrasButton.addClickListener(event1 -> { 
                volumenDialog.close();
            });

            volumenDialog.getFooter().add(atrasButton);
            volumenDialog.setHeaderTitle("Volumen mensual de llamadas");
            volumenDialog.add("Has llamado un total de: " + customerLineController.volumenMensualLlamadas(loggedUser) +" segundos este mes");
            volumenDialog.setWidth("400px");
            volumenDialog.open();
        });

        Button atrasButton = new Button("Volver");
        atrasButton.addClassName("boton-naranja-primary");
        atrasButton.addClickListener(event -> { 
            UI.getCurrent().navigate("profile");
        });
        botonesLayout.add(volumenButton,atrasButton);

        listaLayout.add(gridLlamadas(loggedUser), botonesLayout);
        add(listaLayout);
    }

    private Grid<CallRecord> gridLlamadas(Usuario loggedUser) {
        Grid<CallRecord> gridLlamada = new Grid<CallRecord>(CallRecord.class, false);
        gridLlamada.addColumn(CallRecord::getDateTime).setHeader("Fecha").setSortable(true);
        gridLlamada.addColumn(CallRecord::getDestinationPhoneNumber).setHeader("Número destinatario");
        gridLlamada.addColumn(CallRecord::getSeconds).setHeader("Duración(s)").setSortable(true);

        gridLlamada.setItems(customerLineController.callRecordLine(loggedUser, "", ""));
        
        return gridLlamada;
    }
}
