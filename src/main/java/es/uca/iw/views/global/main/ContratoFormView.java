package es.uca.iw.views.global.main;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import es.uca.iw.aplication.service.ContratoService;
import es.uca.iw.aplication.service.CuentaUsuarioService;
import es.uca.iw.aplication.service.EmailService;
import es.uca.iw.aplication.service.FacturaService;
import es.uca.iw.aplication.service.TarifaService;
import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.aplication.tables.Contrato;
import es.uca.iw.aplication.tables.Factura;
import es.uca.iw.aplication.tables.Factura.Estado;
import es.uca.iw.aplication.tables.enumerados.Servicio;
import es.uca.iw.aplication.tables.tarifas.Tarifa;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.views.templates.MainLayout;


@PageTitle("Contratar")
@Route(value = "contratar", layout = MainLayout.class)
@RouteAlias(value = "contratar", layout = MainLayout.class)
@AnonymousAllowed
public class ContratoFormView extends Div {
    
    private VaadinSession session = VaadinSession.getCurrent();

    @Autowired
    private TarifaService tarifaService;
    @Autowired
    private CuentaUsuarioService cuentaUsuarioService;
    @Autowired
    private ContratoService contratoService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private FacturaService facturaService;
    @Autowired
    private UsuarioService usuarioService;

    private BigDecimal precioTotal = new BigDecimal(0);
    private H3 precioTitle = new H3("Precio total: " + String.valueOf(precioTotal) + " €/mes");
    
    // Manejadores de selección para cada servicio
    private SeleccionadorDeTarifas seleccionadorMovil = new SeleccionadorDeTarifas();
    private SeleccionadorDeTarifas seleccionadorFibra = new SeleccionadorDeTarifas();
    private SeleccionadorDeTarifas seleccionadorFijo = new SeleccionadorDeTarifas();
    
    public ContratoFormView(TarifaService tarifaService, ContratoService contratoService, UsuarioService usuarioService) {
        this.tarifaService = tarifaService;
        this.contratoService = contratoService;
        this.usuarioService = usuarioService;

        if(session.getAttribute("loggedUser") == null) {
            ConfirmDialog dialog = new ConfirmDialog("Aviso", "Debes iniciar sesión", "Iniciar sesion", event1 -> {
                UI.getCurrent().getPage().setLocation("login");;
            });
            dialog.open();
        }
        else {
            if(((Usuario)session.getAttribute("loggedUser")).getCuentaUsuario().getContrato() != null) {
                ConfirmDialog dialog = new ConfirmDialog("Aviso", "Ya tienes un contrato con nosotros", "Ver mi contrato", event1 -> {
                UI.getCurrent().getPage().setLocation("profile/contrato");;
                });
                dialog.open();
            }
            else add(crearContenido());
        }

    }
    
    private Div crearContenido() {
        Div globalDiv = new Div();
        VerticalLayout globalVerticalLayout = new VerticalLayout();  

        // Recuperamos el usuario actual de la sesion
        Usuario usuario = usuarioService.findById((Usuario)session.getAttribute("loggedUser"));

        // Lista de tarifas por servicio
        List<Tarifa> tarifasMovil = tarifaService.getTarifaByServicio(Servicio.MOVIL);
        List<Tarifa> tarifasFibra = tarifaService.getTarifaByServicio(Servicio.FIBRA);
        List<Tarifa> tarifasFijo = tarifaService.getTarifaByServicio(Servicio.FIJO);
        
        // Crear una lista para almacenar todas las tarjetas (Divs) de los servicios
        List<Div> cardsMovil = new ArrayList<>();
        List<Div> cardsFibra = new ArrayList<>();
        List<Div> cardsFijo = new ArrayList<>();

        for(Tarifa tarifa : tarifasMovil) {
            if(!contratoService.existeTarifa(usuario.getCuentaUsuario().getContrato(), tarifa)){
                Div card = crearTarjeta(tarifa, seleccionadorMovil, cardsMovil);
                cardsMovil.add(card);
            }
        }

        for(Tarifa tarifa : tarifasFibra) {
            if(!contratoService.existeTarifa(usuario.getCuentaUsuario().getContrato(), tarifa)){
                Div card = crearTarjeta(tarifa, seleccionadorFibra, cardsFibra);
                cardsFibra.add(card);
            }
        }

        for(Tarifa tarifa : tarifasFijo) {
            if(!contratoService.existeTarifa(usuario.getCuentaUsuario().getContrato(), tarifa)){
                Div card = crearTarjeta(tarifa, seleccionadorFijo, cardsFijo);
                cardsFijo.add(card);
            }
        }
        
        // CREACION DE LAYOUT
        Button confirmButton = new Button("Continuar");
        confirmButton.addClassName("boton-contratar");
        confirmButton.addClickListener(event -> {
            verificarSeleccion();
        });

        HorizontalLayout headerBlock = new HorizontalLayout();
        headerBlock.setAlignItems(Alignment.CENTER);
        headerBlock.add(precioTitle, confirmButton);

        globalVerticalLayout.add(headerBlock);

        H1 movilTitle = new H1("Selecciona tu tarifa de móvil");
        HorizontalLayout cardsLayoutMovil = new HorizontalLayout();
        for(Div card : cardsMovil) {
            cardsLayoutMovil.add(card);
        }
        globalVerticalLayout.add(movilTitle, cardsLayoutMovil);

        H1 fibraTitle = new H1("Selecciona tu tarifa de fibra");
        fibraTitle.addClassName("margen-top-mid");
        HorizontalLayout cardsLayoutFibra = new HorizontalLayout();
        for(Div card : cardsFibra) {
            cardsLayoutFibra.add(card);
        }
        globalVerticalLayout.add(fibraTitle, cardsLayoutFibra);

        H1 fijoTitle = new H1("Selecciona tu tarifa de fijo");
        fijoTitle.addClassName("margen-top-mid");
        HorizontalLayout cardsLayoutFijo = new HorizontalLayout();
        for(Div card : cardsFijo) {
            cardsLayoutFijo.add(card);
        }
        globalVerticalLayout.add(fijoTitle, cardsLayoutFijo);

        globalDiv.add(globalVerticalLayout);
        return globalDiv;
    }

    private void verificarSeleccion(){
        try {
            if(seleccionadorFibra.tarifaSeleccionada == null && seleccionadorFijo.tarifaSeleccionada == null && seleccionadorMovil.tarifaSeleccionada == null) {
                ConfirmDialog errorDialog = new ConfirmDialog();
                errorDialog.setHeader("Error");
                errorDialog.setText("Debe seleccionar al menos una tarifa");
                errorDialog.setConfirmText("Cerrar");
                errorDialog.addConfirmListener(event -> { errorDialog.close(); });
                errorDialog.open();
            }
            else {
                //Recuperamos usuario
                Usuario usuario = usuarioService.findById((Usuario)session.getAttribute("loggedUser"));
                Contrato contrato = usuario.getCuentaUsuario().getContrato();

                //Si no tiene contrato creamos uno, junto a su factura correspondiente y lo asignamos a la cuenta de usuario y viceversa
                if(contrato == null) {
                    contrato = new Contrato();
                    contrato.setFechaInicio(LocalDate.now());

                    contratoService.asignarCuentaUsuario(contrato, usuario.getCuentaUsuario());
                    cuentaUsuarioService.asignarContrato(usuario.getCuentaUsuario(), contrato);

                    contratoService.save(contrato);
                    cuentaUsuarioService.save(usuario.getCuentaUsuario());

                    contratoService.actualizarContrato(contrato);
                }
                
                boolean alta = true;
                //Añadimos las tarifas al contrato
                if(seleccionadorFibra.tarifaSeleccionada != null)
                    alta = alta && contratoService.addTarifa(contrato, seleccionadorFibra.tarifaSeleccionada);
                
                if(seleccionadorFijo.tarifaSeleccionada != null)
                    alta = alta && contratoService.addTarifa(contrato, seleccionadorFijo.tarifaSeleccionada);
                
                if(seleccionadorMovil.tarifaSeleccionada != null)
                    alta = alta && contratoService.addTarifa(contrato, seleccionadorMovil.tarifaSeleccionada);
                

                if(alta){
                    ConfirmDialog errorDialog = new ConfirmDialog("Bienvenido", "Se le ha añadido su nueva tarifa", "Cerrar", event -> {
                        UI.getCurrent().navigate("/profile");
                    });
                    errorDialog.open();

                    Factura factura =  new Factura(Estado.Pagado, LocalDate.now(), contrato, ("Factura-" + "-" + usuario.getNombre() + "-" + LocalDateTime.now() + ".pdf"));
                    facturaService.save(factura);
                    contratoService.addFactura(contrato, factura);
                    contratoService.actualizarContrato(contrato);

                    facturaService.generarFacturaPDF(usuario, contrato, factura);
                    emailService.sendFacturaEmail(usuario, factura);
                }
                else{
                    ConfirmDialog errorDialog = new ConfirmDialog("Ups!", "Ya tiene una tarifa contrada", "Cerrar", event -> {
                        UI.getCurrent().navigate("/contratar");
                    });
                    errorDialog.open();
                }
            }
        }
        catch(Exception e) {
            ConfirmDialog errorDialog = new ConfirmDialog("Error", e.getMessage(), "Reintentar", event -> {
                UI.getCurrent().navigate("/contratar");
            });
            errorDialog.open();
        }
    }

    private Div crearTarjeta(Tarifa tarifa, SeleccionadorDeTarifas seleccionador, List<Div> cards) {
        Div card = new Div();
        card.addClassName("card-contrato");

        card.addClickListener(event -> {
            // Deseleccionar todas las tarjetas del mismo servicio si se selecciona otra
            if(!card.hasClassName("card-contrato-clicked")) {
                for(Div otraCard : cards) {
                    otraCard.removeClassName("card-contrato-clicked");
                }
            }

            Tarifa previaSeleccion = seleccionador.tarifaSeleccionada;
            if(seleccionador.seleccionarTarifa(tarifa)) {
                card.addClassName("card-contrato-clicked");
                actualizarPrecioTotal(tarifa.getPrecio(), previaSeleccion, true);
            } 
            else {
                card.removeClassName("card-contrato-clicked");
                seleccionador.deseleccionarTarifa();
                actualizarPrecioTotal(tarifa.getPrecio(), null, false);
            }
        });

        if(tarifa.getServicio().equals(Servicio.FIJO)) {
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

    private void actualizarPrecioTotal(BigDecimal precio, Tarifa previaSeleccion, boolean suma) {    
        if(suma) 
            if(previaSeleccion != null) {
                precioTotal = precioTotal.add(precio);
                precioTotal = precioTotal.subtract(previaSeleccion.getPrecio());
            }
            else precioTotal = precioTotal.add(precio);
        else precioTotal = precioTotal.subtract(precio);
        
        precioTitle.setText("Precio total: " + String.valueOf(precioTotal) + " €/mes");
    }

    // Clase para manejar la selección de tarifas por servicio
    class SeleccionadorDeTarifas {
        private Tarifa tarifaSeleccionada;

        boolean seleccionarTarifa(Tarifa tarifa) {
            if(tarifaSeleccionada == null || !tarifaSeleccionada.equals(tarifa)) {
                tarifaSeleccionada = tarifa;
                return true; // La tarifa ha sido seleccionada
            }
            return false; // La tarifa ya estaba seleccionada
        }

        void deseleccionarTarifa() { tarifaSeleccionada = null; }
    }
}