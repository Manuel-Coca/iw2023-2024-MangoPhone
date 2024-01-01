package es.uca.iw.endPoints;

/*
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;*/
import es.uca.iw.endPoints.CustomerLine;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerLineService {
    private static final String url = "http://omr-simulator.us-east-1.elasticbeanstalk.com";
    private static final String carrier = "MangoPhone";

    @Autowired
    private RestTemplate restTemplate;

    public List<CustomerLine> getAllLines() {
        /*HttpHeaders headers = new HttpHeaders();
        headers.set(url, carrier);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);*/
        String urlFinal = url + "/?carrier=" + carrier;
        CustomerLine[] customerLines = restTemplate.getForObject(urlFinal, CustomerLine[].class);
        
        return Arrays.asList(customerLines);
    }
}