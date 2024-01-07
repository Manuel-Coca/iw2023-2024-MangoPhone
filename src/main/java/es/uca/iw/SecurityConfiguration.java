package es.uca.iw;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
//import es.uca.iw.aplication.service.UsuarioService;
import es.uca.iw.views.global.autenticacion.LoginView;

@Configuration
//@EnableWebSecurity
public class SecurityConfiguration {//extends VaadinWebSecurity {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //@Autowired
    //public SecurityConfiguration (UsuarioService usuarioService) { this.usuarioService = usuarioService; }

    @Bean
    public PasswordEncoder passwordEncoder() { return passwordEncoder; }
    
    /*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf(csrf -> csrf.disable())
                //.httpBasic(Customizer.withDefaults())
                //.formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers((new AntPathRequestMatcher("/line-awesome/svg/**"))).permitAll()
                .requestMatchers((new AntPathRequestMatcher("/images/**"))).permitAll()
                .requestMatchers((new AntPathRequestMatcher("/icons/**"))).permitAll()
                .requestMatchers((new AntPathRequestMatcher("/CustomerLine"))).permitAll()
                .requestMatchers((new AntPathRequestMatcher("/CustomerLine/**"))).permitAll());
        super.configure(http);
        setLoginView(http, "home");
    }

    @Override
    public void configure(WebSecurity web) throws Exception { super.configure(web); }

    /*@Bean
    public UserDetailsService users() {
        UserDetails cliente = User.builder().
                        username("cliente")
                        .password("{noop}user")
                        .roles("CLIENTE")
                        .build();
        UserDetails marketing = User.builder()
                        .username("marketing")
                        .password("{noop}marketing")
                        .roles("MARKETING")
                        .build();
        UserDetails sac = User.builder()
                        .username("sac")
                        .password("{noop}sac")
                        .roles("SAC")
                        .build();
        UserDetails finanzas = User.builder()
                        .username("finanzas")
                        .password("{noop}finanzas")
                        .roles("FINANZAS")
                        .build();
        UserDetails root = User.builder()
                        .username("root")
                        .password("{noop}root")
                        .roles("ROOT")
                        .build();
        return new InMemoryUserDetailsManager(cliente, marketing, sac, finanzas, root);
    }
    */
}