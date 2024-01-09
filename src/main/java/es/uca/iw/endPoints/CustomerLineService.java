package es.uca.iw.endPoints;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class CustomerLineService {
    private static final String url = "http://omr-simulator.us-east-1.elasticbeanstalk.com";
    private static final String carrier = "MangoPhone";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WebClient webClient;

    /**
     * Metodo que Obtiene la operación GET /
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
     * Metodo que Obtiene la operación GET /{id}
     * con toda la información de la linea
     * de teléfono.
     * @param id
     * @return
     */
    public CustomerLine getOneLine(String id) {
        String urlFinal = url + "/" + id + "?carrier=" + carrier;
        CustomerLine customerLines = restTemplate.getForObject(urlFinal, CustomerLine.class);
        return customerLines;
    }

    /**
     * Metodo que realiza la operación DELETE /{id}
     * con toda la información de la linea
     * de teléfono.
     * @param id
     */
    public void deleteLine(String id) {
        String urlFinal = url + "/" + id + "?carrier=" + carrier;

        RequestCallback requestCallback = restTemplate.httpEntityCallback(null, String.class);
        ResponseExtractor<ResponseEntity<Void>> responseExtractor = restTemplate.responseEntityExtractor(Void.class);

        restTemplate.execute(urlFinal, HttpMethod.DELETE, requestCallback, responseExtractor);
    }

    /**
     * Metodo que realiza la operación PATCH /{id}
     * con toda la información de la linea
     * de teléfono.
     * @param customerLine
     * @param id
     */
    public CustomerLine patchLine(String id, CustomerLine customerLine) {
        String urlFinal = url + "/" + id;
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/hal+json");
        headers.set("Content-Type", "application/json");

        HttpEntity<CustomerLine> resqEntity = new HttpEntity<>(customerLine, headers);
        System.out.println("\n" +  "\n");
        CustomerLine customerLine2 = restTemplate.exchange(urlFinal, HttpMethod.PATCH, resqEntity, CustomerLine.class).getBody();
        return customerLine2;
    }

    public Mono<CustomerLine> realizarSolicitudPatch(String id, CustomerLine customerLine) {
        String urlFinal = url + "/" + id;
        return webClient.patch()
                .uri(urlFinal)
                .body(BodyInserters.fromValue(customerLine))
                .retrieve()
                .bodyToMono(CustomerLine.class);
    }

    private boolean validarFechaYFormato(String fecha, String formato) {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
            LocalDate.parse(fecha, formatter);
            return true;
        }catch(DateTimeParseException e){return false;}
    }

    /**
     * Metodo que realiza la operación GET /{id}/datausagerecords
     * con toda la información de la linea
     * de teléfono sobre sus datos usados.
     * @param startDate
     * @param endDate
     * @return List<DataUsageRecord>
     */
    public List<DataUsageRecord> dataUsageCustomer(String id, String startDate, String endDate) {
        String urlFinal = url + "/" + id + "/datausagerecords?carrier=" + carrier;

        if (validarFechaYFormato(startDate, "yyyy-MM-dd")) urlFinal = urlFinal + "&startDate=" + startDate;
        if (validarFechaYFormato(endDate, "yyyy-MM-dd")) urlFinal = urlFinal + "&endDate=" + endDate;

        DataUsageRecord[] dataUsageRecord = restTemplate.getForObject(urlFinal, DataUsageRecord[].class);
        
        return Arrays.asList(dataUsageRecord);
    }

    /**
     * Metodo que realiza la operación GET /{id}/callrecords
     * con toda la información de la linea
     * de teléfono sobre sus llamadas.
     * @param startDate
     * @param endDate
     * @return List<CallRecord>
     */
    public List<CallRecord> callRecordCustomer(String id, String startDate, String endDate) {
        String urlFinal = url + "/" + id + "/callrecords?carrier=" + carrier;

        if (validarFechaYFormato(startDate, "yyyy-MM-dd")) urlFinal = urlFinal + "&startDate=" + startDate;
        if (validarFechaYFormato(endDate, "yyyy-MM-dd")) urlFinal = urlFinal + "&endDate=" + endDate;
        
        CallRecord[] callRecord = restTemplate.getForObject(urlFinal, CallRecord[].class);
        
        return Arrays.asList(callRecord);
    }

    /**
     * Metodo que realiza la operación GET search/phoneNumber/{phoneNumber}
     * con toda la información de la linea
     * de teléfono sobre sus llamadas.
     * @param phoneNumber
     * @return CustomerLine
     */
    public CustomerLine getOneLineByPhoneNumber(String phoneNumber) {
        String urlFinal = url + "/search/phoneNumber/" + phoneNumber + "?carrier=" + carrier;
        CustomerLine customerLines = restTemplate.getForObject(urlFinal, CustomerLine.class);
        return customerLines;
    }
}