package es.uca.iw.aplication.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.uca.iw.aplication.repository.ServicioRepository;
import es.uca.iw.aplication.tables.Servicio;

@Service
public class ServicioService {
    private final ServicioRepository servicioRepository;
    
    @Autowired
    public ServicioService(ServicioRepository servicioRepository) { this.servicioRepository = servicioRepository; }

    public void createServicio(Servicio servicio) { servicioRepository.save(servicio); }

    public List<Servicio> getAllServicios() { return servicioRepository.findAll(); }
}