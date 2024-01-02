package es.uca.iw.aplication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.iw.aplication.repository.Contrato_FacturaRepository;
import es.uca.iw.aplication.tables.Contrato;
import es.uca.iw.aplication.tables.Contrato_Factura;
import es.uca.iw.aplication.tables.Factura;

@Service
public class Contrato_FacturaService {
    private final Contrato_FacturaRepository contrato_FacturaRepository;

    @Autowired
    public Contrato_FacturaService(Contrato_FacturaRepository contrato_FacturaRepository) {
        this.contrato_FacturaRepository = contrato_FacturaRepository;
    }

    /*************************************************************************** Interfaz Común ************************************************************************************/
    /*
     * Pre:     Recibe una instancia de Contrato_Factura
     * Post:    Si no existe una tabla de dicha entidad se crea, sino se actualiza en la base de datos
     */
    public void save(Contrato_Factura contrato_factura) { contrato_FacturaRepository.save(contrato_factura); }

    /*
     * Pre:     Recibe el UUID de un objeto Contrato_Factura
     * Post:    Si existe la entrada Contrato_Factura en la base de datos, la borra
     */
    public void delete(UUID contrato_factura) { contrato_FacturaRepository.deleteById(contrato_factura); }

    public Contrato_Factura getContrato_FacturaById(UUID contrato_factura) { return contrato_FacturaRepository.findById(contrato_factura).get(); }

    /*************************************************************************** Interfaz Personalizada ************************************************************************************/
    /*
     * Pre:     Recibe un contrato 
     * Post:    Si existe en la tabla Contrato_Tarifa, devuelve todos los objetos Contrato_Tarifa asociados
     */
    public List<Contrato_Factura> findByContrato(Contrato contrato) {
        List<Contrato_Factura> facturas = new ArrayList<>();
        facturas = contrato_FacturaRepository.findByContrato(contrato);
        return facturas;
    } 

    /*
     * Pre:     Recibe una instancia de Contrato y Tarifa
     * Post:    Devuelve la instancia de Contrato_Tarifa donde contrato_id y tarifa_id, coinciden con los de los objetos pasados a la funcion
     */
    public Contrato_Factura findByContratoAndFactura(Contrato contrato, Factura factura) {
        return contrato_FacturaRepository.findByContratoAndFactura(contrato, factura);
    }
}
