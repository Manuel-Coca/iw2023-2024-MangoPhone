package es.uca.iw.endPoints;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CallRecord {
    private String destinationPhoneNumber;
    public String getDestinationPhoneNumber() { return destinationPhoneNumber; }
    public void setDestinationPhoneNumber(String destinationPhoneNumber) { this.destinationPhoneNumber = destinationPhoneNumber; }

    private int seconds;
    public int getSeconds() { return seconds; }
    public void setSeconds(int seconds) { this.seconds = seconds; }

    private String dateTime;
    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
}