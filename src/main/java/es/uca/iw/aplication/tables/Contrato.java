package es.uca.iw.aplication.tables;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import es.uca.iw.aplication.tables.usuarios.CuentaUsuario;

@Entity
@Table(name="Contrato")
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id = null;
    public UUID getId() { return id; }
    public void setId(UUID newId) { this.id = newId; }

    @Column(name = "precio")
    private BigDecimal precio = BigDecimal.ZERO;
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = this.precio.add(precio); }
    public void addPrecio(BigDecimal precio) { this.precio = this.precio.add(precio); }

    @Column(name = "fechaInicio")
    private LocalDate fechaInicio;
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    @OneToOne
    private CuentaUsuario cuentaUsuario = null;
    public void setCuentaUsuario(CuentaUsuario cuentaUsuario) { this.cuentaUsuario = cuentaUsuario; }
    public CuentaUsuario getUsuario() { return cuentaUsuario; }

    @OneToOne
    private Factura factura = null;
    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "contrato")
    private List<Contrato_Tarifa> contratoTarifas = new ArrayList<Contrato_Tarifa>();
    public List<Contrato_Tarifa> getContratoTarifas() { return contratoTarifas; }
    public void setContratoTarifas(List<Contrato_Tarifa> contratoTarifas) { this.contratoTarifas = contratoTarifas; }
    public void addContratoTarifas(Contrato_Tarifa contratoTarifa) {this.contratoTarifas.add(contratoTarifa); }

    public BigDecimal calcularPrecioTotal() {
        BigDecimal precioTotal = BigDecimal.ZERO;
        for (Contrato_Tarifa contratoTarifa : contratoTarifas) 
            this.addPrecio(contratoTarifa.getTarifa().getPrecio());
        
        return precioTotal;
    }
    
}