package es.uca.iw.views.marketing;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;

import es.uca.iw.aplication.service.TarifaService;
import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.enumerados.Rol;
import es.uca.iw.aplication.tables.tarifas.Tarifa;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.views.templates.MainLayoutTrabajadores;

@PageTitle("Lista de Tarifas")
@Route(value = "marketinghome/listatarifas", layout = MainLayoutTrabajadores.class)
@RouteAlias(value = "marketinghome/listatarifas", layout = MainLayoutTrabajadores.class)

public class ListaTarifasView extends Div {

    @Autowired
    private TarifaService tarifaService;

    @Autowired
    private UsuarioService usuarioService;

    private VaadinSession session = VaadinSession.getCurrent();

    public ListaTarifasView(TarifaService tarifaService, UsuarioService usuarioService) {
        this.tarifaService = tarifaService; 
        this.usuarioService = usuarioService;

        if(session.getAttribute("loggedUserId") == null) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", "Inicia sesión para entrar", "Iniciar sesión", event -> { 
                UI.getCurrent().navigate("login");
            });
            errorDialog.open();
        }
        else {
            Usuario loggedUser = usuarioService.findById(UUID.fromString(session.getAttribute("loggedUserId").toString()));
            if(loggedUser.getRol().equals(Rol.MARKETING)) crearContenido();
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
        VerticalLayout listaLayout = new VerticalLayout();
        Button atrasButton = new Button("Volver");
        atrasButton.addClassName("boton-naranja-primary");
        atrasButton.addClickListener(event -> { 
            UI.getCurrent().navigate("marketinghome");
        });

        listaLayout.add(atrasButton, tarifasLayout());
        
        add(listaLayout);
    }

    private GridCrud<Tarifa> tarifasLayout() {

        GridCrud<Tarifa> gridTarifa = new GridCrud<Tarifa>(Tarifa.class);
        gridTarifa.getGrid().setColumns("servicio", "capacidad", "precio");
        gridTarifa.getCrudFormFactory().setUseBeanValidation(true);
        gridTarifa.setCrudListener(new CrudListener<Tarifa>() {
            @Override
            public Collection<Tarifa> findAll() { return tarifaService.getAllTarifas(); }

            @Override
            public Tarifa add(Tarifa tarifa) { return tarifaService.createTarifa(tarifa); }

            @Override
            public Tarifa update(Tarifa tarifa) { return tarifaService.updateTarifa(tarifa); }

            @Override
            public void delete(Tarifa tarifa) { tarifaService.deleteTarifa(tarifa); }
        });
        
        return gridTarifa;
    }
}