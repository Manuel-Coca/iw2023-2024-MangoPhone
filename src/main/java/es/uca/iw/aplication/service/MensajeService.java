package es.uca.iw.aplication.service;

import es.uca.iw.aplication.repository.MensajeRepository;
import es.uca.iw.aplication.tables.Mensaje;
import es.uca.iw.aplication.tables.usuarios.CuentaUsuario;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class MensajeService {
    private final MensajeRepository mensajeRepository;

    public MensajeService(MensajeRepository mensajeRepository) { this.mensajeRepository = mensajeRepository; }
    public List<Mensaje> findByCuentaUsuario(CuentaUsuario cuentaUsuario) { return mensajeRepository.findByCuentaUsuario(cuentaUsuario); }

    public List<Mensaje> getAll() { return mensajeRepository.findAll(); }
    public void save(Mensaje mensaje) { mensajeRepository.save(mensaje); }
    public void delete(Mensaje mensaje) { mensajeRepository.delete(mensaje); }
}
