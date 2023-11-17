package es.uca.iw.aplication.tables;

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
    @Column(name = "id", columnDefinition = "int(32)")
    private int id;

    @Column(name = "nombre", length = 32)
    private String nombre;
    
    @Column(name = "precio")
    private float precio;

    /**
     * Constructor Parametrizado de Servicio
     * @param nombre
     * @param precio
     */
    public Servicio(String nombre, float precio){
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Retorna el id del Servicio
     * @return id
     */
    public int getId(){return id;}

    /**
     * Retorna el nombre del Servicio
     * @return nombre
     */
    public String getNombre(){return nombre;}

    /**
     * Retorna el precio del Servicio
     * @return precio
     */
    public float getPrecio(){return precio;}
}