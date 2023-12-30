package es.uca.iw.endPoints;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataUsageRecord {
    private int megBytes;
    private String date;
}