package es.uca.iw.aplication.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import es.uca.iw.aplication.tables.enumerados.Rol;
import es.uca.iw.aplication.tables.usuarios.CuentaUsuario;
import es.uca.iw.aplication.tables.usuarios.Token;

@Service
public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private CuentaUsuarioService cuentaUsuarioService;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository,PasswordEncoder passwordEncoder, 
        TokenRepository tokenRepository, CuentaUsuarioService cuentaUsuarioService){
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.cuentaUsuarioService = cuentaUsuarioService;
    }

    public void createUsuario(Usuario usuario) {       
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usuarioRepository.save(usuario);
    }

    public void updateUsuarioOnlyPass(Usuario usuario) throws Exception { 
        if(usuarioRepository.existsById(usuario.getId())) {
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            usuarioRepository.save(usuario);
        }
        else throw new Exception("El usuario no existe");
    }

    public void updateUsuarioRegularData(Usuario usuario) throws Exception { 
        if(usuarioRepository.existsById(usuario.getId())) usuarioRepository.save(usuario);
        else throw new Exception("El usuario no existe");
    }

    public boolean validarCredenciales(String correo, String contrasena) {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correo);
        return usuario != null && passwordEncoder.matches(contrasena, usuario.getContrasena());
    }

    public boolean compararContrasena(String inputPass, String actualPass) {
        return passwordEncoder.matches(inputPass, actualPass);
    }

    public String encriptarPass(String pass) { return passwordEncoder.encode(pass); }

    public Usuario buscarEmail(String email) { return usuarioRepository.findByCorreoElectronico(email); }
    public Usuario findById(Usuario usuario) { return usuarioRepository.findById(usuario.getId()).get(); }
    public Usuario findById(UUID id) { return usuarioRepository.findById(id).get(); }
    public List<Usuario> findAll() { return usuarioRepository.findAll(); }
    public List<Usuario> findByRol(Rol rol) { return usuarioRepository.findByRol(rol); }

    public Token buscarToken(Usuario usuario) { return tokenRepository.findByUsuario(usuario); }

    public boolean activarUsuario(String correo, String registerCode) {
        Usuario usuario = buscarEmail(correo);
        Token token = buscarToken(usuario);
        Date fechaActual = new Date();

        if(usuario != null && token != null && token.getUsuario().getId().equals(usuario.getId()) && fechaActual.before(token.getDate()) && registerCode.equals(token.getToken())) {
            usuario.setActivo(true);
            CuentaUsuario cuentaUsuario = new CuentaUsuario();
            cuentaUsuario.setDuennoCuenta(usuario);
            cuentaUsuarioService.save(cuentaUsuario);
            usuario.setCuentaUsuario(cuentaUsuario);
            usuarioRepository.save(usuario);
            // Incluir Eliminación de token (En el caso que se quiera eliminar)
            return true;
        }
        else return false;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombre(nombre);
        
        if(usuario == null) throw new UsernameNotFoundException("No se encontro el usuario con nombre: " + nombre); 
        else return new User(usuario.getNombre(), usuario.getContrasena(), usuario.getAuthorities());
    }
    
}