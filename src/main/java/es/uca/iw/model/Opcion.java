package es.uca.iw.model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(32)")
    private int id;

    @Column(name = "nombre")
    private Tipo nombre;

    @Column(name = "estado")
    private boolean estado;

    /**
     * Contructor Parametrizado de Opcion
     * @param tipo
     * @param activo
     */
    public Opcion(Tipo tipo, boolean estado){
        this.nombre = tipo;
        this.estado = estado;
    }

    /**
     * Retorna el tipo del Opcion
     * @return nombre
     */
    public Tipo getTipo(){return nombre;}

    /**
     * Retorna el estado del Opcion
     * @return estado
     */
    public boolean getEstado(){return estado;}
}