package es.uca.iw;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import es.uca.iw.views.global.LoginView;
import org.springframework.security.core.userdetails.User;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Bean
    public PasswordEncoder passwordEncoder() { return passwordEncoder; }
    
    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
            .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers((new AntPathRequestMatcher("/line-awesome/svg/**"))).permitAll()
            .requestMatchers((new AntPathRequestMatcher("/images/**"))).permitAll()
            .requestMatchers((new AntPathRequestMatcher("/icons/**"))).permitAll()
        );
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception { super.configure(web); }
/*
    @Bean
    public UserDetailsManager userDetailsService() {
        UserDetails admin =
                User.withUsername("admin")
                        .password("{noop}admin")
                        .roles("ADMIN")
                        .build();
        UserDetails user =
                User.withUsername("user")
                        .password("{noop}user")
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(admin, user);
    }*/
    @Bean
    public UserDetailsManager userDetailsService() {
        UserDetails cliente =
                User.withUsername("cliente")
                        .password("{noop}user")
                        .roles("0")
                        .build();
        UserDetails marketing =
                User.withUsername("marketing")
                        .password("{noop}marketing")
                        .roles("1")
                        .build();
        UserDetails sac =
                User.withUsername("sac")
                        .password("{noop}sac")
                        .roles("2")
                        .build();
        UserDetails finanzas =
                User.withUsername("finanzas")
                        .password("{noop}finanzas")
                        .roles("3")
                        .build();
        UserDetails root =
                User.withUsername("root")
                        .password("{noop}root")
                        .roles("4")
                        .build();
        return new InMemoryUserDetailsManager(cliente, marketing, sac, finanzas, root);
    }
}