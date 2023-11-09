package es.uca.iw.model;

import java.util.UUID;
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
    @Column(name = "id")
    private UUID uId = UUID.randomUUID();

    @Column(name = "precio")
    private float fPrecio;

    /**
     * Constructor Parametrizado de LineaFactura
     * @param precio
     */
    public LineaFactura(float precio){
        this.fPrecio = precio;
    }

    /**
     * Retorna el precio del LineaFactura
     * @return fPrecio
     */
    public float getPrecio(){return fPrecio;}
}