package es.uca.iw.model;

import java.util.Date;
import java.util.UUID;
import com.vaadin.flow.component.template.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="factura")
public class Factura {
    public enum Estado{Pagado,NoPagado}; //Se incluiran mas estados
    @Id
    @Column(name = "id")
    private UUID uId = UUID.randomUUID();

    @Column(name = "numero")
    private int iNumero;

    @Column(name = "nombre")
    private float fPrecio;

    @Column(name = "fechaEmision")
    private Date dFechaEmision;

    @Column(name = "estado")
    private Estado eEstado;

    /**
     * Constructor Parametrizado de Factura
     * @param numero
     * @param precio
     * @param fechaEmision
     * @param estado
     */
    public Factura(int numero, float precio, Date fechaEmision, Estado estado){
        this.iNumero = numero;
        this.fPrecio = precio;
        this.dFechaEmision = fechaEmision;
        this.eEstado = estado; 
    }

    /**
     * Retorna el id del Factura
     * @return uId
     */
    public UUID getId(){return uId;}

    /**
     * Retorna el numero del Factura
     * @return iNumero
     */
    public int getNumero(){return iNumero;}

    /**
     * Retorna el precio del Factura
     * @return fPrecio
     */
    public float getPrecio(){return fPrecio;}

    /**
     * Retorna el fecha emision del Factura
     * @return dFechaEmision
     */
    public Date getFechaInicio(){return dFechaEmision;}

    /**
     * Retorna el estado del Factura
     * @return eEstado
     */
    public Estado getEstado(){return eEstado;}
}