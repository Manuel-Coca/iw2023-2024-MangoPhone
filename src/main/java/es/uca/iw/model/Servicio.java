package es.uca.iw.model;

import java.util.UUID;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="servicio")
public class Servicio {
    @Id
    @Column(name = "id")
    private UUID uId = UUID.randomUUID();

    @Column(name = "nombre", length = 32)
    private String sNombre;
    
    @Column(name = "precio")
    private float fPrecio;

    /**
     * Constructor Parametrizado de Servicio
     * @param nombre
     * @param precio
     */
    public Servicio(String nombre, float precio){
        this.sNombre = nombre;
        this.fPrecio = precio;
    }

    /**
     * Retorna el id del servicio
     * @return uId
     */
    public UUID getId(){return uId;}

    /**
     * Retorna el nombre del servicio
     * @return sNombre
     */
    public String getNombre(){return sNombre;}

    /**
     * Retorna el precio del servicio
     * @return fPrecio
     */
    public float getPrecio(){return fPrecio;}
}