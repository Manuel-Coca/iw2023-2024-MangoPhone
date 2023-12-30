package es.uca.iw.endPoints;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CallRecord {
    private String destinationPhoneNumber;
    private int seconds;
    private String dateTime;
}