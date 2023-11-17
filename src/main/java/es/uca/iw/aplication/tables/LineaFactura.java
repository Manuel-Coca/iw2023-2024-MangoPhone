package es.uca.iw.aplication.tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="LineaFactura")
public class LineaFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(32)")
    private int id;
    public int getId() { return id; }

    /*@Column(name = "id", columnDefinition = "int(32)")
    private Factura factura;*/

    @Column(name = "precio")
    private float precio;
    public float getPrecio() { return precio; }
    public void setPrecio(float precio) { this.precio = precio; }

    public LineaFactura(float precio){
        this.precio = precio;
    }
}