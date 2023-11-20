package es.uca.iw.aplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import es.uca.iw.aplication.repository.UsuarioRepository;
import es.uca.iw.aplication.tables.usuarios.Usuario;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository,PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUsuario(Usuario usuario) {       
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));   
        
        usuarioRepository.save(usuario);     
    }

    public boolean validarCredenciales(String correo, String contrasena) {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correo) ;
        
        return usuario != null && passwordEncoder.matches(contrasena, usuario.getContrasena());
    }

    public Usuario buscarEmail(String email) {
        return usuarioRepository.findByCorreoElectronico(email);
    }
}