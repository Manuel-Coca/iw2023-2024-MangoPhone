package es.uca.iw.aplication.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.uca.iw.aplication.repository.UsuarioRepository;
import es.uca.iw.aplication.repository.TokenRepository;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import es.uca.iw.aplication.tables.usuarios.Token;

@Service
public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository,PasswordEncoder passwordEncoder, TokenRepository tokenRepository){
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
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

    public Usuario buscarEmail(String email) { return usuarioRepository.findByCorreoElectronico(email); }

    public Token buscarToken(Usuario usuario) { return tokenRepository.findByUsuario(usuario); }

    public boolean activarUsuario(String correo, String registerCode) {
        Usuario usuario = buscarEmail(correo);
        Token token = buscarToken(usuario);
        Date fechaActual = new Date();

        if(usuario != null && token != null && token.getUsuario().getId().equals(usuario.getId()) && fechaActual.before(token.getDate())) {
            usuario.setActivo(true);
            usuarioRepository.save(usuario);
            // Incluir Eliminación de token (En el caso que se quiera eliminar)
            return true;
        }else return false;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombre(nombre);
        
        if(usuario == null) throw new UsernameNotFoundException("No se encontro el usuario con nombre: " + nombre); 
        else return new User(usuario.getNombre(), usuario.getContrasena(), usuario.getAuthorities());
    }

    /*private Collection<GrantedAuthority> getAuthorities(List<Rol> roles) {
        return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.toString())).collect(Collectors.toList());
    }*/
}