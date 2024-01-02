package es.uca.iw.endPoints;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@RestController
@RequestMapping("/CustomerLine")
public class customerLineController {
    private final CustomerLineService customerLineService;

    public customerLineController(CustomerLineService customerLineService) { this.customerLineService = customerLineService; }

    @GetMapping("/Phones")
    @AnonymousAllowed
    public List<CustomerLine> getAllLines() { return customerLineService.getAllLines(); }

    @GetMapping("/post")
    @AnonymousAllowed
    public void lineReg() { 
        CustomerLine customerLine = new CustomerLine();
        customerLine.setName("Rafael");
        customerLine.setSurname("LealPardo");
        customerLine.setCarrier("MangoPhone");
        customerLine.setPhoneNumber("111223344");
        customerLineService.lineRegister(customerLine);
    }

    @GetMapping("/OnePhone")
    @AnonymousAllowed
    public CustomerLine getOneLine() { 
        String id = "0b333275-839a-4d65-bb63-4635aa056537";
        return customerLineService.getOneLine(id);
    }
}