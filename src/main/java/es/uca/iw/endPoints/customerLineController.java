package es.uca.iw.endPoints;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@RestController
@RequestMapping("/CustomerLine")
public class customerLineController {
    private final CustomerLineService customerLineService;

    public customerLineController(CustomerLineService customerLineService) { this.customerLineService = customerLineService; }

    @GetMapping("/Phones")
    @AnonymousAllowed
    public List<CustomerLine> callEndpoint() {
        return customerLineService.getAllLines();
    }
}