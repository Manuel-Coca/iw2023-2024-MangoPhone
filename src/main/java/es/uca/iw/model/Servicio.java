package es.uca.iw.model;

import java.util.UUID;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name="Servicio")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int uId;

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
     * Retorna el id del Servicio
     * @return uId
     */
    public int getId(){return uId;}

    /**
     * Retorna el nombre del Servicio
     * @return sNombre
     */
    public String getNombre(){return sNombre;}

    /**
     * Retorna el precio del Servicio
     * @return fPrecio
     */
    public float getPrecio(){return fPrecio;}
}