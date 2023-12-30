package es.uca.iw.endPoints;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerLine {
    private UUID id;
    private String name;
    private String surname;
    private String carrier;
    private String phoneNumber;
}