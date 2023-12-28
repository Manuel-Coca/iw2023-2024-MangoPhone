package es.uca.iw.aplication.service;

import org.springframework.stereotype.Service;

import es.uca.iw.aplication.repository.CuentaUsuarioRepository;
import es.uca.iw.aplication.tables.usuarios.CuentaUsuario;

@Service
public class CuentaUsuarioService {
    private final CuentaUsuarioRepository cuentaUsuarioRepository;

    public CuentaUsuarioService(CuentaUsuarioRepository cuentaUsuarioRepository) { this.cuentaUsuarioRepository = cuentaUsuarioRepository; }

    public void createCuentaUsuario(CuentaUsuario cuentaUsuario){
        cuentaUsuarioRepository.save(cuentaUsuario);
    }
}