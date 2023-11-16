package es.uca.iw.model;

import java.util.Date;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Mensaje")
public class Mensaje {
    public enum Tipo{Online,Offline};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int uId;

    @Column(name = "tipo")
    private Tipo eTipo;

    @Column(name = "fechaEmision")
    private Date dFechaEmision;

    /**
     * Contructor Parametrizado de Mensaje
     * @param tipo
     * @param fecha
     */
    public Mensaje(Tipo tipo, Date fecha){
        this.eTipo = tipo;
        this.dFechaEmision = fecha;
    }

    /**
     * Retorna el tipo del Mensaje
     * @return eTipo
     */
    public Tipo getEstado(){return eTipo;}

    /**
     * Retorna el fecha emision del Mensaje
     * @return dFechaEmision
     */
    public Date getFechaInicio(){return dFechaEmision;}
}