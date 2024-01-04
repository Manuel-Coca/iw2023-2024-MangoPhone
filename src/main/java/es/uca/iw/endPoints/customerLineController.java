package es.uca.iw.endPoints;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import reactor.core.publisher.Mono;

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
        customerLine.setName("Juan");
        customerLine.setSurname("GarciaCandom");
        customerLine.setCarrier("MangoPhone");
        customerLine.setPhoneNumber("444223344");
        customerLineService.lineRegister(customerLine);
    }

    @GetMapping("/OnePhone")
    @AnonymousAllowed
    public CustomerLine getOneLine() { 
        String id = "aa636a3e-a0ae-4485-8053-8012ee2e7975";
        return customerLineService.getOneLine(id);
    }

    @GetMapping("/deleteLine")
    @AnonymousAllowed
    public void deleteLine() { 
        String id = "aa636a3e-a0ae-4485-8053-8012ee2e7975";
        customerLineService.deleteLine(id);
    }

    @GetMapping("/modifyLine")
    @AnonymousAllowed
    public Mono<CustomerLine> modifyLine() { 
        String id = "aa636a3e-a0ae-4485-8053-8012ee2e7975";
        CustomerLine customerLine = new CustomerLine();
        customerLine.setName("ManuYJuan");
        customerLine.setSurname("CocaGarcia");
        customerLine.setCarrier("MangoPhone");
        customerLine.setPhoneNumber("111223344");
        return customerLineService.realizarSolicitudPatch(id, customerLine);
    }

    @PatchMapping("/dataUsage")
    @AnonymousAllowed
    public List<DataUsageRecord> dataUsageLine() {
        String id = "ce2476ee-cf17-44b8-98ff-0aba52bfcd6f";
        String startDate = ""; 
        String endDate = "";
        return customerLineService.dataUsageCustomer(id, startDate, endDate);
    }

    @GetMapping("/callRecord")
    @AnonymousAllowed
    public List<CallRecord> callRecordLine() {
        String id = "aa636a3e-a0ae-4485-8053-8012ee2e7975";
        String startDate = "2024-01-02"; 
        String endDate = "2024-01-03";
        return customerLineService.callRecordCustomer(id, startDate, endDate);
    }

    @GetMapping("/PhoneLine")
    @AnonymousAllowed
    public CustomerLine getOneLineByPhoneNumber() {
        String phoneNumber = "111223344"; 
        return customerLineService.getOneLineByPhoneNumber(phoneNumber);
    }
}