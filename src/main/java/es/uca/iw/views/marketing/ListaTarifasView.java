package es.uca.iw.views.marketing;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import es.uca.iw.aplication.service.TarifaService;
import es.uca.iw.aplication.tables.tarifas.Tarifa;
import es.uca.iw.views.templates.MainLayout;
import es.uca.iw.views.templates.MainLayoutTrabajadores;

@PageTitle("Lista de Tarifas")
@Route(value = "listaTarifas", layout = MainLayoutTrabajadores.class)
@RouteAlias(value = "listaTarifas", layout = MainLayoutTrabajadores.class)
public class ListaTarifasView extends Div {

    @Autowired
    private TarifaService tarifaService;

    public ListaTarifasView(TarifaService tarifaService) { 
        this.tarifaService = tarifaService; 
        
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