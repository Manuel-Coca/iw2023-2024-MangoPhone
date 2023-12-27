package es.uca.iw.aplication.tables;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import es.uca.iw.aplication.tables.tarifas.Tarifa;
import es.uca.iw.aplication.tables.usuarios.Usuario;

@Entity
@Table(name="Contrato")
public class Contrato {
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
    public void setPrecio(float precio) { this.precio = precio; }

    @Column(name = "fechaInicio")
    private Date fechaInicio;
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    @OneToOne
    private Usuario usuario = null;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "Contrato_Tarifa",
        joinColumns = @JoinColumn(name = "contrato_id"), 
        inverseJoinColumns = @JoinColumn(name = "tarifa_id")
    )
    private List<Tarifa> tarifas = new ArrayList<Tarifa>();

    public Contrato(int numero, float precio, Date fecha){
        this.numero = numero;
        this.precio = precio;
        this.fechaInicio = fecha;
    }
}