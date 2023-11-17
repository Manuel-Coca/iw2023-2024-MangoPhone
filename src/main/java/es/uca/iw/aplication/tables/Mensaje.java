package es.uca.iw.aplication.tables;

import java.util.Date;
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
    @Column(name = "id", columnDefinition = "int(32)")
    private int id;

    @Column(name = "tipo")
    private Tipo tipo;

    @Column(name = "fechaEmision")
    private Date fechaEmision;

    /**
     * Contructor Parametrizado de Mensaje
     * @param tipo
     * @param fecha
     */
    public Mensaje(Tipo tipo, Date fecha){
        this.tipo = tipo;
        this.fechaEmision = fecha;
    }

    /**
     * Retorna el tipo del Mensaje
     * @return tipo
     */
    public Tipo getEstado(){return tipo;}

    /**
     * Retorna el fecha emision del Mensaje
     * @return fechaEmision
     */
    public Date getFechaInicio(){return fechaEmision;}
}