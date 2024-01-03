package es.uca.iw.views.global.perfil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import es.uca.iw.aplication.service.ContratoService;
import es.uca.iw.aplication.service.FacturaService;
import es.uca.iw.aplication.tables.Contrato;
import es.uca.iw.aplication.tables.Factura;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.views.templates.MainLayout;

@PageTitle("Tus facturas")
@Route(value = "profile/facturas", layout = MainLayout.class)
@RouteAlias(value = "profile/facturas", layout = MainLayout.class)
@AnonymousAllowed
public class PerfilFacturasView extends Div {
    
    @Autowired
    private FacturaService facturaService;

    @Autowired
    private ContratoService contratoService;

    private VaadinSession session = VaadinSession.getCurrent();
    private Factura selectedFactura;

    public PerfilFacturasView(FacturaService facturaService, ContratoService contratoService) {
        this.facturaService = facturaService;
        this.contratoService = contratoService;

        VerticalLayout listaLayout = new VerticalLayout();
        HorizontalLayout botonesLayout = new HorizontalLayout();

        Button descargarButton = new Button("Seleccionar factura");
        descargarButton.addClassName("boton-verde-secondary");
        descargarButton.addClickListener(event -> {
            ConfirmDialog confirmDialog = new ConfirmDialog("Confirmar Descarga",
                    "¿Estás seguro de que quieres descargar el archivo?",
                    "Sí", e -> {
                        // Si el usuario hace clic en "Sí", procede con la descarga
                        downloadFile();
                    },
                    "Cancelar", e -> {
                        // Si el usuario hace clic en "Cancelar", no hagas nada
                    });
        
            confirmDialog.open();
        });
        
        
        Button atrasButton = new Button("Volver");
        atrasButton.addClassName("boton-naranja-primary");
        atrasButton.addClickListener(event -> { 
            UI.getCurrent().navigate("profile");
        });

        botonesLayout.add(descargarButton, atrasButton);

        listaLayout.add(gridFactura(), botonesLayout);

        add(listaLayout);
    }

    private Grid<Factura> gridFactura() {
        Grid<Factura> gridFactura = new Grid<Factura>(Factura.class, false);
        gridFactura.addColumn(Factura::getFechaInicio).setHeader("Fecha de emisión");
        gridFactura.addColumn(Factura::getEstado).setHeader("Estado");
        gridFactura.addColumn(Factura::getfileName).setHeader("Documento");
        
        gridFactura.setItems(getFacturas(contratoService.findByCuentaUsuario(((Usuario)session.getAttribute("loggedUser")).getCuentaUsuario())));

        gridFactura.addSelectionListener(selection -> {
            Optional<Factura> factura = selection.getFirstSelectedItem();
            if(factura.isPresent()) {
                selectedFactura = factura.get();
            }
        });
        
        return gridFactura;
    }
    
    private List<Factura> getFacturas(Contrato contrato) {
        List<Factura> facturas = new ArrayList<Factura>();
        facturas = facturaService.findByContrato(contrato);
        return facturas;
    }

    // Función para descargar el archivo
        private void downloadFile() {
            Anchor anchor = new Anchor(new StreamResource(selectedFactura.getfileName(), new InputStreamFactory() {
                @Override
                public InputStream createInputStream() {
                    File file = new File("doc\\recibo-facturas\\" + selectedFactura.getfileName());
                    try {
                        return new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        // TODO: Manejar FileNotFoundException de alguna manera
                        throw new RuntimeException(e);
                    }
                }
            }), "");
            anchor.getElement().setAttribute("download", true);

            // JavaScript para abrir en nueva pestaña
            anchor.getElement().executeJs("this.target='_blank'");

            anchor.add(new Button(selectedFactura.getfileName()));
            add(anchor);

            // Simula el clic en el enlace para iniciar la descarga automáticamente
            anchor.getElement().executeJs("this.click()");
        }
}
