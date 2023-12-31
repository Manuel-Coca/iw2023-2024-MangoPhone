package es.uca.iw.aplication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import es.uca.iw.aplication.repository.Contrato_TarifaRepository;
import es.uca.iw.aplication.tables.Contrato;
import es.uca.iw.aplication.tables.Contrato_Tarifa;
import es.uca.iw.aplication.tables.tarifas.Tarifa;

@Service
public class Contrato_TarifaService {
    private final Contrato_TarifaRepository contratoTarifaRepository;

    public Contrato_TarifaService(Contrato_TarifaRepository contratoTarifaRepository) { this.contratoTarifaRepository = contratoTarifaRepository; }

    public void create(Contrato_Tarifa contrato_Tarifa) {
        contratoTarifaRepository.save(contrato_Tarifa);
    }

    public void remove(UUID contrato_Tarifa) {
        contratoTarifaRepository.deleteById(contrato_Tarifa);
    }

    public List<Contrato_Tarifa> findByContrato(Contrato contrato) {
        List<Contrato_Tarifa> facturas = new ArrayList<>();
        facturas = contratoTarifaRepository.findByContrato(contrato);
        return facturas;
    } 

    public Contrato_Tarifa findByContratoAndTarifa(Contrato contrato, Tarifa tarifa) {
        return contratoTarifaRepository.findByContratoAndTarifa(contrato, tarifa);
    }

}
