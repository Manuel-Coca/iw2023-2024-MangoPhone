package es.uca.iw.endPoints;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import es.uca.iw.endPoints.CustomerLine;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerLineService {
    private static final String url = "http://omr-simulator.us-east-1.elasticbeanstalk.com";
    private static final String carrier = "MangoPhone";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Metodo que Obtiene la operación Get /
     * con toda la información de las lineas
     * de teléfono activas.
     * @return List<CustomerLine>
    */
    public List<CustomerLine> getAllLines() {
        String urlFinal = url + "/?carrier=" + carrier;
        CustomerLine[] customerLines = restTemplate.getForObject(urlFinal, CustomerLine[].class);
        
        return Arrays.asList(customerLines);
    }

    /**
     * Metodo de la operación POST /
     * refistra una nueva linea de teléfono
    */
    public void lineRegister(CustomerLine customerLine){        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept","application/hal+json");

        HttpEntity<CustomerLine> resqEntity = new HttpEntity<>(customerLine,headers);

        restTemplate.postForObject(url + "/", resqEntity, Void.class);
    }

    /**
     * Metodo que Obtiene la operación Get /{id}
     * con toda la información de la linea
     * de teléfono.
     * @return CustomerLine
    */
    public CustomerLine getOneLine(String id) {
        String urlFinal = url + "/" + id + "?carrier=" + carrier;
        CustomerLine customerLines = restTemplate.getForObject(urlFinal, CustomerLine.class);
        return customerLines;
    }

    public void deleteLine(String id) {
        String urlFinal = url + "/{id}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("carrier",carrier);

        RequestCallback requestCallback = restTemplate.httpEntityCallback(null, String.class);
        ResponseExtractor<ResponseEntity<Void>> responseExtractor = restTemplate.responseEntityExtractor(Void.class);

        restTemplate.execute(urlFinal, HttpMethod.DELETE, requestCallback, responseExtractor, id, headers);
    }

    public void patchLine(CustomerLine customerLine) {
        String urlFinal = url + "/{id}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept","application/hal+json");

        HttpEntity<CustomerLine> resqEntity = new HttpEntity<>(customerLine,headers);

        restTemplate.patchForObject(urlFinal, resqEntity, Void.class, customerLine.getId());
    }

    public List<DataUsageRecord> dataUsageCustomer(CustomerLine customerLine, String startDate, String endDate) {
        String urlFinal;
        if(startDate == null && endDate == null) urlFinal = url + "/" + customerLine.getId() + "/?carrier=" + carrier;
        else if(startDate != null) urlFinal = url + "/" + customerLine.getId() + "/?carrier=" + carrier + "&startDate=" + startDate;
        else if(endDate != null) urlFinal = url + "/" + customerLine.getId() + "/?carrier=" + carrier + "&endDate=" + endDate;
        else urlFinal = url + "/" + customerLine.getId() + "/?carrier=" + carrier + "&startDate=" + startDate + "&endDate=" + endDate;
        
        DataUsageRecord[] dataUsageRecord = restTemplate.getForObject(urlFinal, DataUsageRecord[].class);
        
        return Arrays.asList(dataUsageRecord);
    }

    public List<CallRecord> callRecordCustomer(CustomerLine customerLine, String startDate, String endDate) {
        String urlFinal;
        if(startDate == null && endDate == null) urlFinal = url + "/" + customerLine.getId() + "/?carrier=" + carrier;
        else if(startDate != null) urlFinal = url + "/" + customerLine.getId() + "/?carrier=" + carrier + "&startDate=" + startDate;
        else if(endDate != null) urlFinal = url + "/" + customerLine.getId() + "/?carrier=" + carrier + "&endDate=" + endDate;
        else urlFinal = url + "/" + customerLine.getId() + "/?carrier=" + carrier + "&startDate=" + startDate + "&endDate=" + endDate;
        
        CallRecord[] callRecord = restTemplate.getForObject(urlFinal, CallRecord[].class);
        
        return Arrays.asList(callRecord);
    }

    public CustomerLine getOneLineByPhoneNumber(String phoneNumber) {
        String urlFinal = url + "/search/phoneNumber/" + phoneNumber + "?carrier=" + carrier;
        CustomerLine customerLines = restTemplate.getForObject(urlFinal, CustomerLine.class);
        return customerLines;
    }
}