package es.uca.iw.views.global;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import es.uca.iw.aplication.service.TarifaService;
import es.uca.iw.aplication.tables.enumerados.Servicio;
import es.uca.iw.aplication.tables.tarifas.Tarifa;
import es.uca.iw.views.templates.MainLayout;

@PageTitle("Contratar")
@Route(value = "contratar", layout = MainLayout.class)
@RouteAlias(value = "contratar", layout = MainLayout.class)
@AnonymousAllowed
public class ContratoFormView extends Div {
    
    @Autowired
    private TarifaService tarifaService;

    public BigDecimal precioTotal = new BigDecimal(0);
    
    public ContratoFormView(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
        add(crearContenido());
    }
    
    private Div crearContenido() {
        Div globalDiv = new Div();
        VerticalLayout globalVerticalLayout = new VerticalLayout();
        
        // Precio
        H3 precioTitle = new H3("Precio total: " + String.valueOf(precioTotal) + " €");
        globalVerticalLayout.add(precioTitle);

        // Listado movil

        H1 tituloMovil = new H1("Selecciona tu tarifa móvil");
        globalVerticalLayout.add(tituloMovil);
        HorizontalLayout cardsMovil = new HorizontalLayout();        
        List<Tarifa> tarifasMovil = tarifaService.getTarifaByServicio(Servicio.MOVIL);
        for (Tarifa tarifa : tarifasMovil) {
            Div card = new Div();
            card.addClickListener(event -> {
                card.addClassName("card-contrato-clicked");
                precioTotal.add(tarifa.getPrecio());
                precioTitle.setText("Precio total: " + String.valueOf(precioTotal.add(tarifa.getPrecio())) + " €");
            });
            card.addClassName("card-contrato");
            
            Paragraph capacidad = new Paragraph(String.valueOf(tarifa.getCapacidad()) + " GB");
            Paragraph precio = new Paragraph(String.valueOf(tarifa.getPrecio()) + " €/mes");
            
            card.add(capacidad, precio);
            cardsMovil.add(card);
        }
        globalVerticalLayout.add(cardsMovil);

        // Listado fibra

        H1 tituloFibra = new H1("Selecciona tu tarifa de fibra");
        tituloFibra.addClassName("margen-top-mid");
        globalVerticalLayout.add(tituloFibra);
        HorizontalLayout cardsFibra = new HorizontalLayout();        
        List<Tarifa> tarifasFibra = tarifaService.getTarifaByServicio(Servicio.FIBRA);
        for (Tarifa tarifa : tarifasFibra) {
            Div card = new Div();
            card.addClickListener(event -> {
                card.addClassName("card-contrato-clicked");
                precioTotal.add(tarifa.getPrecio());
                precioTitle.setText("Precio total: " + String.valueOf(precioTotal.add(tarifa.getPrecio())) + " €");
            });
            card.addClassName("card-contrato");
            
            Paragraph capacidad = new Paragraph(String.valueOf(tarifa.getCapacidad()) + " GB");
            Paragraph precio = new Paragraph(String.valueOf(tarifa.getPrecio()) + " €/mes");
            
            card.add(capacidad, precio);
            cardsFibra.add(card);
        }
        globalVerticalLayout.add(cardsFibra);

        // Listado fijo

        H1 tituloFijo = new H1("Selecciona tu tarifa de fijo");
        tituloFijo.addClassName("margen-top-mid");
        globalVerticalLayout.add(tituloFijo);
        HorizontalLayout cardsFijo = new HorizontalLayout();        
        List<Tarifa> tarifasFijo = tarifaService.getTarifaByServicio(Servicio.FIJO);
        for (Tarifa tarifa : tarifasFijo) {
            Div card = new Div();
            card.addClickListener(event -> {
                card.addClassName("card-contrato-clicked");
                precioTotal.add(tarifa.getPrecio());
                precioTitle.setText("Precio total: " + String.valueOf(precioTotal.add(tarifa.getPrecio())) + " €");
            });
            card.addClassName("card-contrato");
            
            Paragraph capacidad = new Paragraph(String.valueOf(tarifa.getCapacidad()) + " minutos");
            Paragraph precio = new Paragraph(String.valueOf(tarifa.getPrecio()) + " €/mes");
            
            card.add(capacidad, precio);
            cardsFijo.add(card);
        }
        globalVerticalLayout.add(cardsFijo);

        globalDiv.add(globalVerticalLayout);
        return globalDiv;
    }
}
