package es.uca.iw.aplication.tables;

import java.time.LocalDate;
import java.util.UUID;

import es.uca.iw.aplication.tables.usuarios.CuentaUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Mensaje")
public class Mensaje {
    public enum Tipo { Reclamacion, Consulta };
    public enum Estado { Abierto, Cerrado, Proceso };
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

    @ManyToOne
    private CuentaUsuario cuentaUsuario;
    public CuentaUsuario getUsuario() { return cuentaUsuario; }
    public void setUsuario(CuentaUsuario cuentaUsuario) { this.cuentaUsuario = cuentaUsuario; }

    @Column(name = "asunto")
    private String asunto;
    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }
    
    @Lob
    @Column(name = "cuerpo")
    private String cuerpo;
    public String getCuerpo() { return cuerpo; }
    public void setCuerpo(String cuerpo) { this.cuerpo = cuerpo; }

    @Column(name = "fechaEmision")
    private LocalDate fechaEmision;
    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fecha) { this.fechaEmision = fecha; }
}