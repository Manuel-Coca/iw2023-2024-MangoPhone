package es.uca.iw.aplication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import es.uca.iw.aplication.repository.Contrato_TarifaRepository;
import es.uca.iw.aplication.tables.Contrato;
import es.uca.iw.aplication.tables.Contrato_Tarifa;
import es.uca.iw.aplication.tables.tarifas.Tarifa;

@Service
public class Contrato_TarifaService {
    private final Contrato_TarifaRepository contratoTarifaRepository;

    public Contrato_TarifaService(Contrato_TarifaRepository contratoTarifaRepository) { this.contratoTarifaRepository = contratoTarifaRepository; }


    /*************************************************************************** Interfaz Común ************************************************************************************/
    /*
     * Pre:     Recibe una instancia de Contrato_Tarifa
     * Post:    Si no existe una tabla de dicha entidad se crea, sino se actualiza en la base de datos
     */
    public void save(Contrato_Tarifa contrato_Tarifa) { contratoTarifaRepository.save(contrato_Tarifa); }

    /*
     * Pre:     Recibe el UUID de un objeto contrato_tarifa
     * Post:    Si existe la entrada contrato_tarifa en la base de datos, la borra
     */
    public void delete(UUID contrato_Tarifa) { contratoTarifaRepository.deleteById(contrato_Tarifa); }

    public Contrato_Tarifa getContrato_TarifaById(UUID contrato_tarifa) { return contratoTarifaRepository.findById(contrato_tarifa).get(); }

    /*************************************************************************** Interfaz Personalizada ************************************************************************************/
    /*
     * Pre:     Recibe un contrato 
     * Post:    Si existe en la tabla Contrato_Tarifa, devuelve todos los objetos Contrato_Tarifa asociados
     */
    public List<Contrato_Tarifa> findByContrato(Contrato contrato) {
        List<Contrato_Tarifa> facturas = new ArrayList<>();
        facturas = contratoTarifaRepository.findByContrato(contrato);
        return facturas;
    } 

    /*
     * Pre:     Recibe una instancia de Contrato y Tarifa
     * Post:    Devuelve la instancia de Contrato_Tarifa donde contrato_id y tarifa_id, coinciden con los de los objetos pasados a la funcion
     */
    public Contrato_Tarifa findByContratoAndTarifa(Contrato contrato, Tarifa tarifa) {
        return contratoTarifaRepository.findByContratoAndTarifa(contrato, tarifa);
    }
}
