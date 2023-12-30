package es.uca.iw.aplication.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

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
        Factura factura = new Factura(Estado.NoPagado, LocalDate.now(), tarifa.getPrecio(), tarifa);
        facturaService.createFactura(factura);
        Contrato_Factura contratoFactura = new Contrato_Factura(contrato, factura);
        contrato.addContratoFactura(contratoFactura);
        contrato_FacturaService.create(contratoFactura);
        emailService.sendFacturaEmail(usuario, factura, tipo);
    }

    public void asignarCuentaUsuario(Contrato contrato, CuentaUsuario cuentaUsuario) {
        contrato.setCuentaUsuario(cuentaUsuario);
    }

    public void actualizarContrato(Contrato contrato) {
        contratoRepository.save(contrato);
    }
}