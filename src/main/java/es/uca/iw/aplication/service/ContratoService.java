package es.uca.iw.aplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.iw.aplication.repository.ContratoRepository;
import es.uca.iw.aplication.tables.Contrato;
import es.uca.iw.aplication.tables.Contrato_Tarifa;
import es.uca.iw.aplication.tables.tarifas.Tarifa;
import es.uca.iw.aplication.tables.usuarios.CuentaUsuario;

@Service
public class ContratoService {
    private final ContratoRepository contratoRepository;
    private final Contrato_TarifaService contratoTarifaService;
    
    @Autowired
    public ContratoService(ContratoRepository contratoRepository, Contrato_TarifaService contratoTarifaService) { 
        this.contratoRepository = contratoRepository; 
        this.contratoTarifaService = contratoTarifaService;
    }

    /*
     * Pre:     Recibe un contrato
     * Post:    Si no existe crea una nueva entrada en la base de datos con los datos del contrato, sino actualiza
     */
    public void save(Contrato contrato){ contratoRepository.save(contrato); }

    /*
     * Pre:     Recibe un contrato y una cuentaUsuario
     * Post:    Enlaza la cuentaUsuario al contrato
     */
    public void asignarCuentaUsuario(Contrato contrato, CuentaUsuario cuentaUsuario) { contrato.setCuentaUsuario(cuentaUsuario); }

    /*
     * Pre:     Recibe un contrato existente en la base de datos
     * Post:    Actualiza el precio, y hace un update en la base de datos
     */
    public void actualizarContrato(Contrato contrato) {
        contrato.setPrecio(contrato.calcularPrecioTotal());
        contratoRepository.save(contrato);
    }

    /*
     * Pre:     Recibe un contrato, y tarifa distintos de null
     * Post:    Añade al objeto actual contrato la tarifa, devuelve true si se ha podido añadir correctamente, y false si no se ha podido
    */
    public boolean addTarifa(Contrato contrato, Tarifa tarifa){
        if(!existeTarifa(contrato, tarifa)) {
            Contrato_Tarifa contratoTarifa = new Contrato_Tarifa(contrato, tarifa);
            contratoTarifaService.save(contratoTarifa);
            contrato.addContratoTarifas(contratoTarifa);
            contrato.setPrecio(contrato.calcularPrecioTotal());
            
            return true;
        } else return false;
    }

    /*
     * Pre:     Recibe un objeto contratoTarifa
     * Post:    Elimina del objeto contrato relacionado, el objeto contratoTarifa
     */
    public void deleteTarifa(Contrato_Tarifa contratoTarifa) {
        if(existeTarifa(contratoTarifa.getContrato(), contratoTarifa.getTarifa())) {
            int index = indexTarifa(contratoTarifa.getContrato(), contratoTarifa);
            contratoTarifa.getContrato().getContratoTarifas().remove(index);

            contratoTarifaService.remove(contratoTarifa.getId());
        }
    }

    /*
     * Pre:     Recibe un contrato y una tarifa distintos de null
     * Post:    Devuelve true si existe la tarifa en el contrato, sino false
     */
    public boolean existeTarifa(Contrato contrato, Tarifa tarifa) {
        if(contrato != null){
            if(contrato.getContratoTarifas() != null)
                for(Contrato_Tarifa contratoTarifa : contrato.getContratoTarifas())
                    if(contratoTarifa.getTarifa().getId().equals(tarifa.getId()))
                        return true;
        }
        return false;
    }

    /*
     * Pre:     Recibe un objeto contrato y contrato_tarifa
     * Post:    Si el contrato tiene entidades contrato_tarifa asociadas, devuelve el indice que ocupa el objeto contrato_tarifa
     */
    public int indexTarifa(Contrato contrato, Contrato_Tarifa contratoTarifa) {
        if(contrato.getContratoTarifas() == null) return 0;
        else return contrato.getContratoTarifas().indexOf(contratoTarifa);
    }

    public Contrato findByCuentaUsuario(CuentaUsuario cuentaUsuario) {
        return contratoRepository.findByCuentaUsuarioId(cuentaUsuario.getId()).get();
    }
}