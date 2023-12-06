package es.uca.iw.views.marketing;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import es.uca.iw.aplication.service.ServicioService;
import es.uca.iw.aplication.tables.Servicio;
import es.uca.iw.views.templates.MainLayout;

@PageTitle("Lista de Servicios")
@Route(value = "listaServicios", layout = MainLayout.class)
@RouteAlias(value = "listaServicios", layout = MainLayout.class)
public class ListaServiciosView extends Div {

    @Autowired
    private ServicioService servicioService;

    public ListaServiciosView(){
        add(ServiciosLayout());
    }

    private VerticalLayout ServiciosLayout(){
        VerticalLayout ServiciosLayout = new VerticalLayout();
        List<Servicio> listaServicios = servicioService.getAllServicios();

        Paragraph nombreServicio = new Paragraph(listaServicios.get(0).getNombre()); 
        float valor = listaServicios.get(0).getPrecio();
        Paragraph precioServicio = new Paragraph("El precio es:" + valor + "€");

        ServiciosLayout.add(nombreServicio, precioServicio);

        return ServiciosLayout;
    }
}