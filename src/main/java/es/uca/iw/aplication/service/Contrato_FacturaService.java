package es.uca.iw.aplication.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.uca.iw.aplication.repository.Contrato_FacturaRepository;
import es.uca.iw.aplication.tables.Contrato;
import es.uca.iw.aplication.tables.Contrato_Factura;

@Service
public class Contrato_FacturaService {
    private final Contrato_FacturaRepository contratoFacturaRepository;

    public Contrato_FacturaService(Contrato_FacturaRepository contratoFacturaRepository) { this.contratoFacturaRepository = contratoFacturaRepository; }

    public void create(Contrato_Factura contratoFactura) {
        contratoFacturaRepository.save(contratoFactura);
    }

     public void remove(Contrato_Factura contratoFactura) {
        contratoFacturaRepository.delete(contratoFactura);
    }

    public List<Contrato_Factura> findByContrato(Contrato contrato) {
        List<Contrato_Factura> facturas = new ArrayList<>();
        facturas = contratoFacturaRepository.findByContrato(contrato);
        return facturas;
    } 
}
