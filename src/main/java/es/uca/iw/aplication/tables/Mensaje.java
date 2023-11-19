package es.uca.iw.aplication.tables;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id = null;
    public UUID getId() { return id; }
    public void setId(UUID newId) { this.id = newId; }

    @Column(name = "tipo")
    private Tipo tipo;
    public Tipo getEstado() { return tipo; }
    void setTipo(Tipo tipo) { this.tipo = tipo; }

    @Column(name = "fechaEmision")
    private Date fechaEmision;
    public Date getFechaInicio() { return fechaEmision; }
    void setFechaInicio(Date fehca) { this.fechaEmision = fehca; }

    public Mensaje(Tipo tipo, Date fecha){
        this.tipo = tipo;
        this.fechaEmision = fecha;
    }
}