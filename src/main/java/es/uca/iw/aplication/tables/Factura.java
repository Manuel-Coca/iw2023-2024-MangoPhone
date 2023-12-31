package es.uca.iw.aplication.tables;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import es.uca.iw.aplication.tables.tarifas.Tarifa;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @Column(name = "precio")
    private BigDecimal precio;
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = (precio); }
    public void addPrecio(BigDecimal precio) { this.precio = this.precio.add(precio); }

    @Column(name = "fechaEmision")
    private LocalDate fechaEmision;
    public LocalDate getFechaInicio() { return fechaEmision; }
    public void setFechaInicio(LocalDate fecha) { this.fechaEmision = fecha; }

    @Column(name = "estado")
    private Estado estado;
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    // Contrato asociado

    // Binario (PDF)

    @ManyToOne
    private Tarifa tarifa = null;
    public Tarifa getTarifa() { return tarifa; }
    public void setTarifa(Tarifa tarifa) { this.tarifa = tarifa; }

    public Factura(Estado estado, LocalDate fechaEmision, BigDecimal precio, Tarifa tarifaSeleccionada) {
        this.estado = estado;
        this.fechaEmision = fechaEmision;
        this.precio = precio;
        this.tarifa = tarifaSeleccionada;
    }

    public Factura(){}
}