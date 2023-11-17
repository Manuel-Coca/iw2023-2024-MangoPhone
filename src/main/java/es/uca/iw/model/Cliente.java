package es.uca.iw.model;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;


@Entity
@Table(name="Cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(32)")
    private int id;

    @Column(name = "nombre", length = 32)
    private String nombre;

    @Column(name = "apellidos", length = 64)
    private String apellidos;

    @Column(name = "correoElectronico", length = 128)
    private String correoElectronico;

    @Column(name = "contrasena", length = 128)
    private String contrasena;

    @Column(name = "telefono", length = 9)
    private String telefono;

    /**
     * Constructor Parametrizado de Cliente
     * @param nombre
     * @param apellidos
     * @param correoElectronico
     * @param contrasena
     * @param telefono
     */
    public Cliente(String nombre, String apellidos, String correoElectronico, String contrasena, String telefono){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.telefono = telefono;
    }

    /**
     * Retorna el id del Cliente
     * @return id
     */
    public int getId(){return id;}

    /**
     * Retorna el nombre del Cliente
     * @return nombre
     */
    public String getNombre(){return nombre;}

    /**
     * Retorna el apellidos del Cliente
     * @return apellidos
     */
    public String getApellidos(){return apellidos;}

    /**
     * Retorna el correo electronico del Cliente
     * @return correoElectronico
     */
    public String getCorreoElectronico(){return correoElectronico;}

    /**
     * Retorna el contrasena electronico del Cliente
     * @return contrasena electronico
     */
    //public String getContrasena(){return contrasena;}

    /**
     * Retorna el telefono del Cliente
     * @return telefono
     */
    public String getTelefono(){return telefono;}
}