package es.uca.iw.aplication.service;

import org.springframework.stereotype.Service;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;

import es.uca.iw.aplication.repository.ContratoRepository;
import es.uca.iw.aplication.tables.Contrato;
import es.uca.iw.aplication.tables.Contrato_Tarifa;
import es.uca.iw.aplication.tables.tarifas.Tarifa;
import es.uca.iw.aplication.tables.usuarios.CuentaUsuario;

@Service
public class ContratoService {
    private final ContratoRepository contratoRepository;
    private final Contrato_TarifaService contratoTarifaService;
    
    public ContratoService(ContratoRepository contratoRepository, Contrato_TarifaService contratoTarifaService) { 
        this.contratoRepository = contratoRepository; 
        this.contratoTarifaService = contratoTarifaService;
    }

    public void createContrato(Contrato contrato){
        contratoRepository.save(contrato);
    }

    /*
     * A partir de una tarifa, crea una factura de manera automatica, la asigna al contrato actual y la manda por correo
    */
    public boolean addTarifa(Contrato contrato, Tarifa tarifa){
        if(!existeTarifa(contrato, tarifa)) {
            Contrato_Tarifa contratoTarifa = new Contrato_Tarifa(contrato, tarifa);
            contratoTarifaService.create(contratoTarifa);
            contrato.addContratoTarifas(contratoTarifa);
            contrato.setPrecio(contrato.calcularPrecioTotal());

            return true;
        } else return false;
    }

    public boolean existeTarifa(Contrato contrato, Tarifa tarifa) {
        if(contrato != null){
            if(contrato.getContratoTarifas() != null)
            for(Contrato_Tarifa contratoTarifa : contrato.getContratoTarifas())
                if(contratoTarifa.getTarifa().getId().equals(tarifa.getId()))
                    return true;
        }
        return false;
    }

    public void asignarCuentaUsuario(Contrato contrato, CuentaUsuario cuentaUsuario) {
        contrato.setCuentaUsuario(cuentaUsuario);
    }

    public void actualizarContrato(Contrato contrato) {
        contrato.setPrecio(contrato.calcularPrecioTotal());
        contratoRepository.save(contrato);
    }

    public Contrato findByCuentaUsuario(CuentaUsuario cuentaUsuario) {
        return contratoRepository.findByCuentaUsuarioId(cuentaUsuario.getId()).get();
    }

}