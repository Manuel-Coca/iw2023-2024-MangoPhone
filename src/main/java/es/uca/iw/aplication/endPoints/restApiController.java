package es.uca.iw.aplication.endPoints;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class restApiController {
    @Value("${API.url}")
    private String urlAPI;

    @Value("${API.carrier}")
    private String carrier;

    @RequestMapping("/")
    public String prueba() {
        return "prueba";
    }

    @GetMapping(value = "/")
    public List<Object> getAllTelephones() {
        String url = urlAPI + "/";
        RestTemplate restTemplate = new RestTemplate();

        Object[] telephones = restTemplate.getForObject(url, Object[].class);

        return Arrays.asList(telephones);
    }
}