package es.uca.iw.model;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name="Contrato")
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(32)")
    private int id;

    @Column(name = "numero")
    private int numero;

    @Column(name = "precio")
    private float precio;

    @Column(name = "fechaInicio")
    private Date fechaInicio;

    /**
     * Constructor Parametrizado de Contrato
     * @param numero
     * @param precio
     * @param fecha
     */
    public Contrato(int numero, float precio, Date fecha){
        this.numero = numero;
        this.precio = precio;
        this.fechaInicio = fecha;
    }

    /**
     * Retorna el id del Contrato
     * @return id
     */
    public int getId(){return id;}

    /**
     * Retorna el numero del Contrato
     * @return numero
     */
    public int getNumero(){return numero;}

    /**
     * Retorna el precio del Contrato
     * @return precio
     */
    public float getPrecio(){return precio;}

    /**
     * Retorna el fecha inicio del Contrato
     * @return fechaInicio
     */
    public Date getFechaInicio(){return fechaInicio;}
}