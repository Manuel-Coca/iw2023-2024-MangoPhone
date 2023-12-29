package es.uca.iw.aplication.service;

import org.springframework.stereotype.Service;

import es.uca.iw.aplication.repository.FacturaRepository;
import es.uca.iw.aplication.tables.Factura;

@Service
public class FacturaService {
    private final FacturaRepository facturaRepository;

    public FacturaService(FacturaRepository facturaRepository) { this.facturaRepository = facturaRepository; }

    public void createFactura(Factura factura){
        facturaRepository.save(factura);
    }
}