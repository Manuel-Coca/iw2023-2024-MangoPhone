package es.uca.iw.endPoints;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/CustomerLine")
public class customerLineController {
    private final CustomerLineService customerLineService;

    public customerLineController(CustomerLineService customerLineService) { this.customerLineService = customerLineService; }

    
    @GetMapping("/Phones")
    public List<CustomerLine> getAllLines() { return customerLineService.getAllLines(); }

    //public List<CustomerLine> getAllLines() { return customerLineService.getAllLines(); }

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

    /*
    @GetMapping("/deleteLine")
    public void deleteLine() { 
        String id = "c75343df-bd2e-47c8-8af5-3164e0162818";
        customerLineService.deleteLine(id);
    }
    */

    public void deleteLine(Usuario usuario) { 
        String id = getOneLineByPhoneNumber(usuario).getId();
        customerLineService.deleteLine(id);
    }

    /*@GetMapping("/modifyLine")
    public Mono<CustomerLine> modifyLine() { 
        Usuario usuario = new Usuario();
        usuario.setNombre("Rafael");
        usuario.setApellidos("Leal Pardo");
        usuario.setTelefono("111558899");
        String id = getOneLineByPhoneNumber(usuario).getId();
        System.out.println(id);
        //String id = "180b1a25-e840-4394-8dae-852b31a0679a";
        CustomerLine customerLine = new CustomerLine();
        customerLine.setName(usuario.getNombre());
        customerLine.setSurname(usuario.getApellidos());
        customerLine.setCarrier("MangoPhone");
        customerLine.setPhoneNumber(usuario.getTelefono());
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

    @GetMapping("/dataUsage")
    public List<DataUsageRecord> dataUsageLine() {
        String id = "ce2476ee-cf17-44b8-98ff-0aba52bfcd6f";
        String startDate = ""; 
        String endDate = "";
        return customerLineService.dataUsageCustomer(id, startDate, endDate);
    }

    public int volumenMensualConsumoDatos(Usuario usuario) {
        String id = getOneLineByPhoneNumber(usuario).getId();
        int valor = 0;
        
        LocalDate date = LocalDate.now();
        String startDate = date.getYear() + "-" + date.getMonthValue() + "-01"; 
        String endDate = date.with(TemporalAdjusters.lastDayOfMonth()).toString();
        
        List<DataUsageRecord> volume = customerLineService.dataUsageCustomer(id, startDate, endDate);
        for(DataUsageRecord datos : volume) valor += datos.getMegBytes();

        return valor;
    }

    public List<DataUsageRecord> dataUsageLine(Usuario usuario, String startDate, String endDate) { 
        String id = getOneLineByPhoneNumber(usuario).getId();
        return customerLineService.dataUsageCustomer(id, startDate, endDate);
    }

    @GetMapping("/callRecord")
    public List<CallRecord> callRecordLine() {
        String id = "180b1a25-e840-4394-8dae-852b31a0679a";
        String startDate = ""; 
        String endDate = "";
        return customerLineService.callRecordCustomer(id, startDate, endDate);
    }

    public int volumenMensualLlamadas(Usuario usuario) {
        String id = getOneLineByPhoneNumber(usuario).getId();
        int valor = 0;
        
        LocalDate date = LocalDate.now();
        String startDate = date.getYear() + "-" + date.getMonthValue() + "-01"; 
        String endDate = date.with(TemporalAdjusters.lastDayOfMonth()).toString();
        
        List<CallRecord> volume = customerLineService.callRecordCustomer(id, startDate, endDate);
        for(CallRecord llamada : volume) valor += llamada.getSeconds();

        return valor;
    }

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