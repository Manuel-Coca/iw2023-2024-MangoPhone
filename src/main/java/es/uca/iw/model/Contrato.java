package es.uca.iw.model;

import java.util.UUID;
import com.vaadin.flow.component.template.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name="contrato")
public class Contrato {
    @Id
    @Column(name = "id")
    private UUID uId = UUID.randomUUID();

    @Column(name = "numero")
    private int iNumero;

    @Column(name = "precio")
    private float fPrecio;

    @Column(name = "fechaInicio")
    private Date dFechaInicio;

    /**
     * Constructor Parametrizado de Contrato
     * @param numero
     * @param precio
     * @param fecha
     */
    public Contrato(int numero, float precio, Date fecha){
        this.iNumero = numero;
        this.fPrecio = precio;
        this.dFechaInicio = fecha;
    }

    /**
     * Retorna el id del Contrato
     * @return uId
     */
    public UUID getId(){return uId;}

    /**
     * Retorna el numero del Contrato
     * @return iNumero
     */
    public int getNumero(){return iNumero;}

    /**
     * Retorna el precio del Contrato
     * @return fPrecio
     */
    public float getPrecio(){return fPrecio;}

    /**
     * Retorna el fecha inicio del Contrato
     * @return dFechaInicio
     */
    public Date getFechaInicio(){return dFechaInicio;}
}