package es.uca.iw.aplication.tables.tarifas;

import jakarta.persistence.Id;
import java.math.BigInteger;
import java.util.UUID;
import es.uca.iw.aplication.tables.enumerados.Servicio;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name="Tarifa")
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id = null;
    public UUID getId() { return id; }
    public void setId(UUID newId) { this.id = newId; }

    @Column(name = "servicio", length = 32)
    private Servicio servicio;
    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }
    
    @Column(name = "capacidad")
    private int capacidad;
    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    @Column(name = "precio")
    private BigInteger precio;
    public BigInteger getPrecio() { return precio; }
    public void setPrecio(BigInteger precio) { this.precio = precio; }
}