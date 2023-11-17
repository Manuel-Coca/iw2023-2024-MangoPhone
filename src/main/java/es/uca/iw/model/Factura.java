package es.uca.iw.model;

import java.util.Date;
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
    @Column(name = "id", columnDefinition = "int(32)")
    private int id;

    @Column(name = "numero")
    private int numero;

    @Column(name = "precio")
    private float precio;

    @Column(name = "fechaEmision")
    private Date fechaEmision;

    @Column(name = "estado")
    private Estado estado;

    /**
     * Constructor Parametrizado de Factura
     * @param numero
     * @param precio
     * @param fechaEmision
     * @param estado
     */
    public Factura(int numero, float precio, Date fechaEmision, Estado estado){
        this.numero = numero;
        this.precio = precio;
        this.fechaEmision = fechaEmision;
        this.estado = estado; 
    }

    /**
     * Retorna el id del Factura
     * @return id
     */
    public int getId(){return id;}

    /**
     * Retorna el numero del Factura
     * @return numero
     */
    public int getNumero(){return numero;}

    /**
     * Retorna el precio del Factura
     * @return precio
     */
    public float getPrecio(){return precio;}

    /**
     * Retorna el fecha emision del Factura
     * @return fechaEmision
     */
    public Date getFechaInicio(){return fechaEmision;}

    /**
     * Retorna el estado del Factura
     * @return estado
     */
    public Estado getEstado(){return estado;}
}