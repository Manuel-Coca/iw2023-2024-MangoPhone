package es.uca.iw;

/*
import java.util.Optional;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.flow.spring.security.AuthenticationContext;
import es.uca.iw.aplication.repository.UsuarioRepository;
import es.uca.iw.aplication.tables.usuarios.Usuario;

@Component
public class AuthenticatedUser {
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationContext authenticationContext;
    
    public AuthenticatedUser(UsuarioRepository usuarioRepository, AuthenticationContext authenticationContext) { 
        this.usuarioRepository = usuarioRepository; 
        this.authenticationContext = authenticationContext;
    }

    private Optional<Authentication> getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return Optional.ofNullable(context.getAuthentication()).filter(authentication -> !(authentication instanceof AnonymousAuthenticationToken));
    }

    //public Optional<Usuario> get() { return getAuthentication().map(authentication -> usuarioRepository.findByNombre(authentication.getName())); }

    @Transactional
    public Optional<Usuario> get() {
        return authenticationContext.getAuthenticatedUser(Usuario.class).map(userDetails -> usuarioRepository.findByNombre(userDetails.getNombre())/*.get());
    }
}
*/