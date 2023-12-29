package es.uca.iw.aplication.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.uca.iw.aplication.repository.TarifaRepository;
import es.uca.iw.aplication.tables.enumerados.Servicio;
import es.uca.iw.aplication.tables.tarifas.Tarifa;

@Service
public class TarifaService {

    private final TarifaRepository tarifaRepository;
    
    @Autowired
    public TarifaService(TarifaRepository tarifaRepository) { this.tarifaRepository = tarifaRepository; }

    public Tarifa getTarifaById(UUID id) {return tarifaRepository.getReferenceById(id); }

    public Tarifa createTarifa(Tarifa tarifa) { return tarifaRepository.save(tarifa); }

    public List<Tarifa> getAllTarifas() { return tarifaRepository.findAll(); }

    public List<Tarifa> getTarifaByServicio(Servicio servicio) { return tarifaRepository.findByServicio(servicio); }

    public void deleteTarifa(Tarifa tarifa) { tarifaRepository.deleteById(tarifa.getId()); }

    public Tarifa updateTarifa(Tarifa tarifa) { 
        if(tarifaRepository.existsById(tarifa.getId())) return tarifaRepository.save(tarifa);
        else return null; 
    }
}