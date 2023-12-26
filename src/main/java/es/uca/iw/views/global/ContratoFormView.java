package es.uca.iw.views.global;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    
    private BigDecimal precioTotal = new BigDecimal(0);
    private H3 precioTitle = new H3("Precio total: " + String.valueOf(precioTotal) + " €");
    
    // Manejadores de selección para cada servicio
    private SeleccionadorDeTarifas seleccionadorMovil = new SeleccionadorDeTarifas();
    private SeleccionadorDeTarifas seleccionadorFibra = new SeleccionadorDeTarifas();
    private SeleccionadorDeTarifas seleccionadorFijo = new SeleccionadorDeTarifas();
    
    public ContratoFormView(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
        add(crearContenido());
    }
    
    private Div crearContenido() {
        Div globalDiv = new Div();
        VerticalLayout globalVerticalLayout = new VerticalLayout();  
        
        // Lista de tarifas por servicio
        List<Tarifa> tarifasMovil = tarifaService.getTarifaByServicio(Servicio.MOVIL);
        List<Tarifa> tarifasFibra = tarifaService.getTarifaByServicio(Servicio.FIBRA);
        List<Tarifa> tarifasFijo = tarifaService.getTarifaByServicio(Servicio.FIJO);
        
        // Crear una lista para almacenar todas las tarjetas (Divs) de los servicios
        List<Div> cardsMovil = new ArrayList<>();
        List<Div> cardsFibra = new ArrayList<>();
        List<Div> cardsFijo = new ArrayList<>();

        for (Tarifa tarifa : tarifasMovil) {
            Div card = crearTarjeta(tarifa, seleccionadorMovil, cardsMovil);
            cardsMovil.add(card);
        }

        for (Tarifa tarifa : tarifasFibra) {
            Div card = crearTarjeta(tarifa, seleccionadorFibra, cardsFibra);
            cardsFibra.add(card);
        }

        for (Tarifa tarifa : tarifasFijo) {
            Div card = crearTarjeta(tarifa, seleccionadorFijo, cardsFijo);
            cardsFijo.add(card);
        }
        
        // Agregar a vista
        globalVerticalLayout.add(precioTitle);

        HorizontalLayout cardsLayoutMovil = new HorizontalLayout();
        for (Div card : cardsMovil) {
            cardsLayoutMovil.add(card);
        }
        globalVerticalLayout.add(new H1("Selecciona tu tarifa de móvil"), cardsLayoutMovil);

        HorizontalLayout cardsLayoutFibra = new HorizontalLayout();
        for (Div card : cardsFibra) {
            cardsLayoutFibra.add(card);
        }
        globalVerticalLayout.add(new H1("Selecciona tu tarifa de fibra"), cardsLayoutFibra);

        HorizontalLayout cardsLayoutFijo = new HorizontalLayout();
        for (Div card : cardsFijo) {
            cardsLayoutFijo.add(card);
        }
        globalVerticalLayout.add(new H1("Selecciona tu tarifa de fijo"), cardsLayoutFijo);

        globalDiv.add(globalVerticalLayout);
        return globalDiv;
    }

    private Div crearTarjeta(Tarifa tarifa, SeleccionadorDeTarifas seleccionador, List<Div> cards) {
        Div card = new Div();
        card.addClassName("card-contrato");

        card.addClickListener(event -> {
            boolean yaSeleccionada = card.hasClassName("card-contrato-clicked");
            // Deseleccionar todas las tarjetas del mismo servicio si se selecciona otra
            if (!yaSeleccionada) {
                for (Div otraCard : cards) {
                    otraCard.removeClassName("card-contrato-clicked");
                }
            }

            boolean seleccionada = seleccionador.seleccionarTarifa(tarifa);
            if (seleccionada) {
                card.addClassName("card-contrato-clicked");
                actualizarPrecioTotal(tarifa.getPrecio(), true);
            } 
            else {
                card.removeClassName("card-contrato-clicked");
                seleccionador.deseleccionarTarifa();
                actualizarPrecioTotal(tarifa.getPrecio(), false);
            }
        });

        if (tarifa.getServicio().equals(Servicio.FIJO)) {
            Paragraph capacidad = new Paragraph(String.valueOf(tarifa.getCapacidad()) + " minutos");
            card.add(capacidad);
        }
        else {
            Paragraph capacidad = new Paragraph(String.valueOf(tarifa.getCapacidad()) + " GB");
            card.add(capacidad);
        }
        Paragraph precio = new Paragraph(String.valueOf(tarifa.getPrecio()) + " €/mes");
        card.add(precio);

        return card;
    }

    private void actualizarPrecioTotal(BigDecimal precio, boolean suma) {
        if(suma) precioTotal = precioTotal.add(precio);
        else precioTotal = precioTotal.subtract(precio);
        
        precioTitle.setText("Precio total: " + String.valueOf(precioTotal) + " €");
    }

    // Clase para manejar la selección de tarifas por servicio
    class SeleccionadorDeTarifas {
        private Tarifa tarifaSeleccionada;

        boolean seleccionarTarifa(Tarifa tarifa) {
            if (tarifaSeleccionada == null || !tarifaSeleccionada.equals(tarifa)) {
                tarifaSeleccionada = tarifa;
                return true; // La tarifa ha sido seleccionada
            }
            return false; // La tarifa ya estaba seleccionada
        }

        void deseleccionarTarifa() { tarifaSeleccionada = null; }
    }
}