package es.uca.iw.model;

import java.util.Date;
import java.util.UUID;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name="Factura")
public class Factura {
    public enum Estado{Pagado,NoPagado}; //Se incluiran mas estados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int uId;

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
    public int getId(){return uId;}

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