package es.uca.iw.aplication.tables;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "Factura")
public class Factura {
    public enum Estado{Pagado,NoPagado}; //Se incluiran mas estados

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id = null;
    public UUID getId() { return id; }
    public void setId(UUID newId) { this.id = newId; }

    @Column(name = "fechaEmision")
    private LocalDate fechaEmision;
    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fecha) { this.fechaEmision = fecha; }

    @Column(name = "estado")
    private Estado estado;
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    @Column(name = "nombreDocumento")
    private String fileName = null;
    public String getfileName() { return fileName; }
    public void setfileName(String fileName) { this.fileName = fileName; }

    @Column(name = "documentoData")
    @Lob
    private byte[] data = null;
    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }

    @ManyToOne
    private Contrato contrato = null;
    public Contrato getContrato() { return contrato; }
    public void setContrato(Contrato contrato) { this.contrato = contrato; }

    public Factura(Estado estado, LocalDate fechaEmision, Contrato contrato, String fileName) {
        this.estado = estado;
        this.fechaEmision = fechaEmision;
        this.contrato = contrato;
        this.fileName = fileName;
    }

    public Factura(){}
}