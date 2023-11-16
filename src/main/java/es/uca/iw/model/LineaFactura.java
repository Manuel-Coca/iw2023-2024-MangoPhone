package es.uca.iw.model;

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

    @Column(name = "precio")
    private float precio;

    /**
     * Constructor Parametrizado de LineaFactura
     * @param precio
     */
    public LineaFactura(float precio){
        this.precio = precio;
    }

    /**
     * Retorna el precio del LineaFactura
     * @return precio
     */
    public float getPrecio(){return precio;}
}