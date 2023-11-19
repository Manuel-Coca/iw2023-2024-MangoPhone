package es.uca.iw.aplication.tables;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Opcion")
public class Opcion {
    public enum Tipo{Roaming,CompartirDatos,Bloquear};

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id = null;
    public UUID getId() { return id; }
    public void setId(UUID newId) { this.id = newId; }

    @Column(name = "nombre")
    private Tipo nombre;
    public Tipo getTipo() { return nombre; }
    public void setTipo(Tipo nombre) { this.nombre = nombre; }

    @Column(name = "estado")
    private boolean estado;
    public boolean getEstado() { return estado; }
    public void setTipo(boolean estado) { this.estado = estado; }

    public Opcion(Tipo tipo, boolean estado){
        this.nombre = tipo;
        this.estado = estado;
    }    
}