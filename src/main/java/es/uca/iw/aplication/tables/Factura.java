package es.uca.iw.aplication.tables;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name="Factura")
public class Factura {
    public enum Estado{Pagado,NoPagado}; //Se incluiran mas estados

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id = null;
    public UUID getId() { return id; }
    public void setId(UUID newId) { this.id = newId; }

    @Column(name = "numero")
    private int numero;
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    @Column(name = "precio")
    private float precio;
    public float getPrecio() { return precio; }
    public void setPrecio(int precio) { this.precio = precio; }

    @Column(name = "fechaEmision")
    private LocalDate fechaEmision;
    public LocalDate getFechaInicio() { return fechaEmision; }
    public void setFechaInicio(LocalDate fecha) { this.fechaEmision = fecha; }

    @Column(name = "estado")
    private Estado estado;
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    @OneToOne
    private Contrato contrato = null;
    public Contrato getContrato() { return contrato; }
    public void setContrato(Contrato contrato) { this.contrato = contrato; }

}