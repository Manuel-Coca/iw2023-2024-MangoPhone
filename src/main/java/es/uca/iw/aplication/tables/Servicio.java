package es.uca.iw.aplication.tables;

import jakarta.persistence.Id;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name="Servicio")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id = null;
    public UUID getId() { return id; }
    public void setId(UUID newId) { this.id = newId; }

    @Column(name = "nombre", length = 32)
    private String nombre;
    public String getNombre() { return nombre; }
    public void SetNombre(String nombre) { this.nombre = nombre; }
    
    @Column(name = "precio")
    private float precio;
    public float getPrecio() { return precio; }
    public void SetPrecio(float precio) { this.precio = precio; }

    public Servicio(String nombre, float precio){
        this.nombre = nombre;
        this.precio = precio;
    }
}