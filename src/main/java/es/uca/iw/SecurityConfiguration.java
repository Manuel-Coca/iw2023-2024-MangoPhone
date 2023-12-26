package es.uca.iw;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import es.uca.iw.views.global.LoginView;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends VaadinWebSecurity {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Bean
    public PasswordEncoder passwordEncoder() { return passwordEncoder; }
    
    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .build();
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers((new AntPathRequestMatcher("/line-awesome/svg/**"))).permitAll()
                .requestMatchers((new AntPathRequestMatcher("/images/**"))).permitAll()
                .requestMatchers((new AntPathRequestMatcher("/icons/**"))).permitAll());
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception { super.configure(web); }

    /*@Bean
    public UserDetailsManager userDetailsService() {
        UserDetails cliente =
                User.withUsername("cliente")
                        .password("{noop}user")
                        .roles("CLIENTE")
                        .build();
        UserDetails marketing =
                User.withUsername("marketing")
                        .password("{noop}marketing")
                        .roles("MARKETING")
                        .build();
        UserDetails sac =
                User.withUsername("sac")
                        .password("{noop}sac")
                        .roles("SAC")
                        .build();
        UserDetails finanzas =
                User.withUsername("finanzas")
                        .password("{noop}finanzas")
                        .roles("FINANZAS")
                        .build();
        UserDetails root =
                User.withUsername("root")
                        .password("{noop}root")
                        .roles("ROOT")
                        .build();
        return new InMemoryUserDetailsManager(cliente, marketing, sac, finanzas, root);
    }*/
}