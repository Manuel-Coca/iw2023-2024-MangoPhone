package es.uca.iw.model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID uId = UUID.randomUUID();

    @Column(name = "nombre")
    private Tipo eTipo;

    @Column(name = "estado")
    private boolean bEstado;

    /**
     * Contructor Parametrizado de Opcion
     * @param tipo
     * @param activo
     */
    public Opcion(Tipo tipo, boolean estado){
        this.eTipo = tipo;
        this.bEstado = estado;
    }

    /**
     * Retorna el tipo del Opcion
     * @return eTipo
     */
    public Tipo getTipo(){return eTipo;}

    /**
     * Retorna el estado del Opcion
     * @return bEstado
     */
    public boolean getEstado(){return bEstado;}
}