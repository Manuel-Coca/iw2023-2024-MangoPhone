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
import es.uca.iw.aplication.tables.usuarios.CuentaUsuario;
import es.uca.iw.aplication.tables.usuarios.Token;

@Service
public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
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

    public void updateUsuario(Usuario usuario) throws Exception{ 
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

    public Token buscarToken(Usuario usuario) { return tokenRepository.findByUsuario(usuario); }

    public boolean activarUsuario(String correo, String registerCode) {
        Usuario usuario = buscarEmail(correo);
        Token token = buscarToken(usuario);
        Date fechaActual = new Date();

        if(usuario != null && token != null && token.getUsuario().getId().equals(usuario.getId()) && fechaActual.before(token.getDate()) && registerCode.equals(token.getToken())) {
            usuario.setActivo(true);
            CuentaUsuario cuentaUsuario = new CuentaUsuario();
            cuentaUsuario.setDuennoCuenta(usuario);
            cuentaUsuarioService.createCuentaUsuario(cuentaUsuario);
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
    
    /*public Usuario loadUsuario(Usuario usuario){
        List<Contrato_Tarifa> contratoTarifas = new ArrayList<Contrato_Tarifa>();
        Contrato contrato = new Contrato();
        contrato = contratoService.findByCuentaUsuario(usuario.getCuentaUsuario());
        contratoTarifas =  contratoTarifaService.findByContrato(contrato);
        contrato.setContratoTarifas(contratoTarifas);
        usuario.getCuentaUsuario().setContrato(contrato);

        System.out.println(usuario.getCuentaUsuario().getContrato().getContratoTarifas().toString());
        return usuario;
    }*/

    /*private Collection<GrantedAuthority> getAuthorities(List<Rol> roles) {
        return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.toString())).collect(Collectors.toList());
    }*/
}