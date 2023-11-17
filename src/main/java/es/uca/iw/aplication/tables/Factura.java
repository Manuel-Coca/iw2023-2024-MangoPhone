package es.uca.iw.aplication.tables;

import java.util.Date;
import jakarta.persistence.Id;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(32)")
    private int id;
    public int getId() { return id; }

    @Column(name = "numero")
    private int numero;
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    @Column(name = "precio")
    private float precio;
    public float getPrecio() { return precio; }
    public void setPrecio(int precio) { this.precio = precio; }

    @Column(name = "fechaEmision")
    private Date fechaEmision;
    public Date getFechaInicio() { return fechaEmision; }
    public void setFechaInicio(Date fecha) { this.fechaEmision = fecha; }

    @Column(name = "estado")
    private Estado estado;
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Factura(int numero, float precio, Date fechaEmision, Estado estado){
        this.numero = numero;
        this.precio = precio;
        this.fechaEmision = fechaEmision;
        this.estado = estado; 
    }
}