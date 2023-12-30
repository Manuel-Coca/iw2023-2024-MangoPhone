package es.uca.iw.aplication.service;

import org.springframework.stereotype.Service;

import es.uca.iw.aplication.repository.Contrato_FacturaRepository;
import es.uca.iw.aplication.tables.Contrato_Factura;

@Service
public class Contrato_FacturaService {
    private final Contrato_FacturaRepository contratoFacturaRepository;

    public Contrato_FacturaService(Contrato_FacturaRepository contratoFacturaRepository) { this.contratoFacturaRepository = contratoFacturaRepository; }

    public void create(Contrato_Factura contratoFactura) {
        contratoFacturaRepository.save(contratoFactura);
    }
}
