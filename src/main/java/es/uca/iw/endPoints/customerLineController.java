package es.uca.iw.endPoints;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import reactor.core.publisher.Mono;

//
@RestController
//@RequestMapping("/CustomerLine")
public class customerLineController {
    private final CustomerLineService customerLineService;

    public customerLineController(CustomerLineService customerLineService) { this.customerLineService = customerLineService; }

    
    /*@GetMapping("/Phones")
    
    public List<CustomerLine> getAllLines() { return customerLineService.getAllLines(); }*/

    public List<CustomerLine> getAllLines() { return customerLineService.getAllLines(); }

    /*@GetMapping("/post")
    
    public void lineReg() { 
        CustomerLine customerLine = new CustomerLine();
        customerLine.setName("Juan");
        customerLine.setSurname("GarciaCandom");
        customerLine.setCarrier("MangoPhone");
        customerLine.setPhoneNumber("444223344");
        customerLineService.lineRegister(customerLine);
    }*/

    public void lineReg(Usuario usuario) { 
        CustomerLine customerLine = new CustomerLine();
        customerLine.setName(usuario.getNombre());
        customerLine.setSurname(usuario.getApellidos());
        customerLine.setCarrier("MangoPhone");
        customerLine.setPhoneNumber(usuario.getTelefono());
        customerLineService.lineRegister(customerLine);
    }

    /*@GetMapping("/OnePhone")
    
    public CustomerLine getOneLine() { 
        String id = "aa636a3e-a0ae-4485-8053-8012ee2e7975";
        return customerLineService.getOneLine(id);
    }*/

    public CustomerLine getOneLine(Usuario usuario) { 
        String id = getOneLineByPhoneNumber(usuario).getId();
        return customerLineService.getOneLine(id);
    }

    /*@GetMapping("/deleteLine")
    
    public void deleteLine() { 
        String id = "aa636a3e-a0ae-4485-8053-8012ee2e7975";
        customerLineService.deleteLine(id);
    }*/

    public void deleteLine(Usuario usuario) { 
        String id = getOneLineByPhoneNumber(usuario).getId();
        customerLineService.deleteLine(id);
    }

    /*@GetMapping("/modifyLine")
    
    public Mono<CustomerLine> modifyLine() { 
        String id = "aa636a3e-a0ae-4485-8053-8012ee2e7975";
        CustomerLine customerLine = new CustomerLine();
        customerLine.setName("ManuYJuan");
        customerLine.setSurname("CocaGarcia");
        customerLine.setCarrier("MangoPhone");
        customerLine.setPhoneNumber("111223344");
        return customerLineService.realizarSolicitudPatch(id, customerLine);
    }*/

    public Mono<CustomerLine> modifyLine(Usuario usuario) { 
        String id = getOneLineByPhoneNumber(usuario).getId();
        CustomerLine customerLine = new CustomerLine();
        customerLine.setName(usuario.getNombre());
        customerLine.setSurname(usuario.getApellidos());
        customerLine.setCarrier("MangoPhone");
        customerLine.setPhoneNumber(usuario.getTelefono());
        return customerLineService.realizarSolicitudPatch(id, customerLine);
    }

    /*@PatchMapping("/dataUsage")
    
    public List<DataUsageRecord> dataUsageLine() {
        String id = "ce2476ee-cf17-44b8-98ff-0aba52bfcd6f";
        String startDate = ""; 
        String endDate = "";
        return customerLineService.dataUsageCustomer(id, startDate, endDate);
    }*/

    public List<DataUsageRecord> dataUsageLine(Usuario usuario, String startDate, String endDate) { 
        String id = getOneLineByPhoneNumber(usuario).getId();
        return customerLineService.dataUsageCustomer(id, startDate, endDate);
    }

    /*@GetMapping("/callRecord")
    
    public List<CallRecord> callRecordLine() {
        String id = "aa636a3e-a0ae-4485-8053-8012ee2e7975";
        String startDate = "2024-01-02"; 
        String endDate = "2024-01-03";
        return customerLineService.callRecordCustomer(id, startDate, endDate);
    }*/

    public List<CallRecord> callRecordLine(Usuario usuario, String startDate, String endDate) { 
        String id = getOneLineByPhoneNumber(usuario).getId();
        return customerLineService.callRecordCustomer(id, startDate, endDate);
    }

    /*@GetMapping("/PhoneLine")
    
    public CustomerLine getOneLineByPhoneNumber() {
        String phoneNumber = "111223344"; 
        return customerLineService.getOneLineByPhoneNumber(phoneNumber);
    }*/

    public CustomerLine getOneLineByPhoneNumber(Usuario usuario) {
        String phoneNumber = usuario.getTelefono(); 
        return customerLineService.getOneLineByPhoneNumber(phoneNumber);
    }
}