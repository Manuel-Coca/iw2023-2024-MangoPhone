package es.uca.iw.endPoints;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataUsageRecord {
    private int megBytes;
    public int getMegBytes() { return megBytes; }
    public void setMegBytes(int megBytes) { this.megBytes = megBytes; }

    private String date;
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}