package es.uca.iw.aplication.tables;

import java.util.Date;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "Mensaje")
public class Mensaje {
    public enum Tipo { Online, Offline, Reclamacion, Consulta };
    public enum Estado { Abierto, Cerrado };
    public enum Emisor { Trabajador, Cliente };

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id = null;
    public UUID getId() { return id; }
    public void setId(UUID newId) { this.id = newId; }

    @Column(name = "tipo")
    private Tipo tipo;
    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    @Column(name = "estado")
    private Estado estado;
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    @Column(name = "emisor")
    private Emisor emisor;
    public Emisor getEmisor() { return emisor; }
    public void setEmisor(Emisor emisor) { this.emisor = emisor; }

    @Lob
    @Column(name = "cuerpo")
    private String cuerpo;
    public String getCuerpo() { return cuerpo; }
    public void setCuerpo(String cuerpo) { this.cuerpo = cuerpo; }

    @Column(name = "fechaEmision")
    private Date fechaEmision;
    public Date getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(Date fecha) { this.fechaEmision = fecha; }

    public Mensaje(Tipo tipo, Date fecha, String cuerpo){
        this.tipo = tipo;
        this.fechaEmision = fecha;
        this.cuerpo = cuerpo;
    }
}