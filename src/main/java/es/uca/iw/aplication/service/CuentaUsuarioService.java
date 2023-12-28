package es.uca.iw.aplication.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import es.uca.iw.aplication.repository.CuentaUsuarioRepository;
import es.uca.iw.aplication.tables.usuarios.CuentaUsuario;

public class CuentaUsuarioService implements UserDetailsService {
    private final CuentaUsuarioRepository cuentaUsuarioRepository;

    public CuentaUsuarioService(CuentaUsuarioRepository cuentaUsuarioRepository) { this.cuentaUsuarioRepository = cuentaUsuarioRepository; }

    public void createCuentaUsuario(CuentaUsuario cuentaUsuario){
        cuentaUsuarioRepository.save(cuentaUsuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }
}
