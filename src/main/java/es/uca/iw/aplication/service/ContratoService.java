package es.uca.iw.aplication.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import java.util.List;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;

import es.uca.iw.aplication.repository.ContratoRepository;
import es.uca.iw.aplication.tables.Contrato;
import es.uca.iw.aplication.tables.Contrato_Factura;
import es.uca.iw.aplication.tables.Factura;
import es.uca.iw.aplication.tables.Factura.Estado;
import es.uca.iw.aplication.tables.tarifas.Tarifa;
import es.uca.iw.aplication.tables.usuarios.CuentaUsuario;
import es.uca.iw.aplication.tables.usuarios.Usuario;

@Service
public class ContratoService {
    private final ContratoRepository contratoRepository;
    
    private final FacturaService facturaService;
    private final EmailService emailService;
    private final Contrato_FacturaService contrato_FacturaService;
    
    public ContratoService(ContratoRepository contratoRepository, FacturaService facturaService, EmailService emailService, Contrato_FacturaService contrato_FacturaService) { 
        this.contratoRepository = contratoRepository; 
        this.facturaService = facturaService;
        this.emailService = emailService;
        this.contrato_FacturaService = contrato_FacturaService;
    }

    public void createContrato(Contrato contrato){
        contrato.setPrecio(contrato.calcularPrecioTotal());
        contratoRepository.save(contrato);
    }

    /*
     * A partir de una tarifa, crea una factura de manera automatica, la asigna al contrato actual y la manda por correo
    */
    public void addTarifa(Contrato contrato, Usuario usuario, Tarifa tarifa, String tipo){
        boolean existe = false;
        if(contrato != null){
            if(contrato.getContratoFacturas() != null){
                for(Contrato_Factura contratoFactura :  contrato.getContratoFacturas()){
                    Factura factura = contratoFactura.getFactura();
                    if(factura.getTarifa().getId().equals(tarifa.getId()))
                    existe = true;
                }
            }
        }
            
        if(!existe) {
            Factura factura = new Factura(Estado.NoPagado, LocalDate.now(), tarifa.getPrecio(), tarifa);
            facturaService.createFactura(factura);
            Contrato_Factura contratoFactura = new Contrato_Factura(contrato, factura);
            contrato.addContratoFactura(contratoFactura);
            contrato_FacturaService.create(contratoFactura);
            emailService.sendFacturaEmail(usuario, factura, tipo);
            ConfirmDialog errorDialog = new ConfirmDialog("Bienvenido", "Se le ha añadido su nueva tarifa", "Cerrar", event -> {
                UI.getCurrent().navigate("/contratar");
            });
            errorDialog.open();
        } else {
            ConfirmDialog errorDialog = new ConfirmDialog("Ups!", "Parece que ya tienes esta tarifa contratada", "Cerrar", event -> {
                UI.getCurrent().navigate("/contratar");
            });
            errorDialog.open();
        }
    }

    public void removeTarifa(Contrato contrato, Usuario usuario, Tarifa tarifa, String tipo) {
        boolean existe = false;
        Factura factura = new Factura();
        Contrato_Factura contratoFactura = new Contrato_Factura();
        for(Contrato_Factura it : usuario.getCuentaUsuario().getContrato().getContratoFacturas()) 
            if(it.getFactura().getTarifa().getId() == tarifa.getId() && !existe){
                existe = true;
                factura = it.getFactura();
                contratoFactura = it;
            }
        
        if(existe) {
            List<Contrato_Factura> contratoFacturas = contrato.getContratoFacturas();
            contratoFacturas.remove(contratoFacturas.indexOf(contratoFactura));
            contrato.setContratoFacturas(contratoFacturas);
            contrato_FacturaService.remove(contratoFactura);
            facturaService.removeFactura(factura);
            ConfirmDialog errorDialog = new ConfirmDialog("Eliminado", "Se le ha dado de baja de la tarifa seleccionada", "Cerrar", event -> {
                UI.getCurrent().navigate("/contratar");
            });
            errorDialog.open();
        } else {
            ConfirmDialog errorDialog = new ConfirmDialog("Ups!", "Parece que no tienes esta tarifa contratada", "Cerrar", event -> {
                UI.getCurrent().navigate("/contratar");
            });
            errorDialog.open();
        }
    }

    public void asignarCuentaUsuario(Contrato contrato, CuentaUsuario cuentaUsuario) {
        contrato.setCuentaUsuario(cuentaUsuario);
    }

    public void actualizarContrato(Contrato contrato) {
        contrato.setPrecio(contrato.calcularPrecioTotal());
        contratoRepository.save(contrato);
    }

    public Contrato findByCuentaUsuario(CuentaUsuario cuentaUsuario) {
        return contratoRepository.findByCuentaUsuarioId(cuentaUsuario.getId()).get();
    }

}