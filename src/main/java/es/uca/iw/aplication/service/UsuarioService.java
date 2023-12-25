package es.uca.iw.aplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import es.uca.iw.aplication.repository.UsuarioRepository;
import es.uca.iw.aplication.tables.usuarios.Usuario;

@Service
public class UsuarioService implements UserDetailsService {
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
    /*
    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombre(nombre);
        if(usuario == null) { throw new UsernameNotFoundException("No se encontro el usuario con nombre: " + nombre); 
        }else{
            User usuarioAux = new User();
            usuarioAux.setName(usuario.getNombre());
            usuarioAux.setPassword(usuario.getPassword());
            usuarioAux.setRoles(usuario.getAuthorities());
            return usuarioAux;
        } 
    }*/

    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombre(nombre);
        
        if(usuario == null) throw new UsernameNotFoundException("No se encontro el usuario con nombre: " + nombre); 
        else return new User(usuario.getNombre(), usuario.getContrasena(), usuario.getAuthorities());
    }

    /*private Collection<GrantedAuthority> getAuthorities(List<Rol> roles) {
        return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.toString())).collect(Collectors.toList());
    }*/
}