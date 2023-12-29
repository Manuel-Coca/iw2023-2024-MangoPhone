package es.uca.iw.aplication.service;

import org.springframework.stereotype.Service;

import es.uca.iw.aplication.repository.ContratoRepository;
import es.uca.iw.aplication.tables.Contrato;

@Service
public class ContratoService {
    private final ContratoRepository contratoRepository;

    public ContratoService(ContratoRepository contratoRepository) { this.contratoRepository = contratoRepository; }

    public void createContrato(Contrato contrato){
        contratoRepository.save(contrato);
    }
}