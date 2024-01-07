package es.uca.iw;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.theme.Theme;
/*
import es.uca.iw.endPoints.CallRecord;
import com.github.javaparser.utils.Log;
import java.util.logging.Logger;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
*/
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */

@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@SpringBootApplication
@EnableScheduling
@Theme(value = "mangophone")
public class Application implements AppShellConfigurator {

    //private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void configurePage(AppShellSettings settings) {
        settings.addFavIcon("icon", "/icons/mango-fruit-icon.svg", "192x192");
        settings.addLink("shortcut icon", "/icons/mango-fruit-icon.svg");
    }

    /*@Bean
    public RestTemplate restTemplate(RestTemplate builder) { return builder.build(); }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            CallRecord callRecord = restTemplate.getForObject("http://omr-simulator.us-east-1.elasticbeanstalk.com", callRecord.class);
            log.info(callRecord.toString());
        };
    }*/
}