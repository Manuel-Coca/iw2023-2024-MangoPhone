package es.uca.iw.aplication.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.uca.iw.aplication.repository.Contrato_TarifaRepository;
import es.uca.iw.aplication.tables.Contrato;
import es.uca.iw.aplication.tables.Contrato_Tarifa;

@Service
public class Contrato_TarifaService {
    private final Contrato_TarifaRepository contratoTarifaRepository;

    public Contrato_TarifaService(Contrato_TarifaRepository contratoTarifaRepository) { this.contratoTarifaRepository = contratoTarifaRepository; }

    public void create(Contrato_Tarifa contratoFactura) {
        contratoTarifaRepository.save(contratoFactura);
    }

     public void remove(Contrato_Tarifa contratoFactura) {
        contratoTarifaRepository.delete(contratoFactura);
    }

    public List<Contrato_Tarifa> findByContrato(Contrato contrato) {
        List<Contrato_Tarifa> facturas = new ArrayList<>();
        facturas = contratoTarifaRepository.findByContrato(contrato);
        return facturas;
    } 
}
