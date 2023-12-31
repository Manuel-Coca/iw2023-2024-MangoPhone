package es.uca.iw.aplication.tables;

import java.util.UUID;

import es.uca.iw.aplication.tables.tarifas.Tarifa;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// Si no se puede un campo multievaluado
//  Contrato_tarifa

@Entity
@Table(name = "Contrato_Tarifa")
public class Contrato_Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id = null;
    public UUID getId() { return id; }
    public void setId(UUID newId) { this.id = newId; }

    @ManyToOne
    private Contrato contrato = null;
    public Contrato getContrato() { return contrato; }
    public void setContrato(Contrato contrato) { this.contrato = contrato; }

    @ManyToOne
    public Tarifa tarifa = null;
    public Tarifa getTarifa() { return tarifa; }
    public void setTarifa(Tarifa tarifa) { this.tarifa = tarifa; }
    
    public Contrato_Tarifa(Contrato contrato, Tarifa tarifa) {
        this.contrato = contrato;
        this.tarifa = tarifa;
    }

    public Contrato_Tarifa(){}

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Contrato_Tarifa other)) {
            return false; // null or other class
        }

        if (id != null) {
            return id.equals(other.id);
        }
        return super.equals(other);
    }
}
