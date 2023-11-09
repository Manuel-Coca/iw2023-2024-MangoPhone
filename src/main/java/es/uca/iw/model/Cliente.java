package es.uca.iw.model;

import java.util.UUID;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name="cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID uId = UUID.randomUUID();

    @Column(name = "nombre", length = 32)
    private String sNombre;

    @Column(name = "apellidos", length = 64)
    private String sApellidos;

    @Column(name = "correoElectronico", length = 128)
    private String sCorreoElectronico;

    @Column(name = "contrasena", length = 128)
    private String sContrasena;

    @Column(name = "telefono", length = 9)
    private String sTelefono;

    /**
     * Constructor Parametrizado de Cliente
     * @param nombre
     * @param apellidos
     * @param correoElectronico
     * @param contrasena
     * @param telefono
     */
    public Cliente(String nombre, String apellidos, String correoElectronico, String contrasena, String telefono){
        this.sNombre = nombre;
        this.sApellidos = apellidos;
        this.sCorreoElectronico = correoElectronico;
        this.sContrasena = contrasena;
        this.sTelefono = telefono;
    }

    /**
     * Retorna el id del cliente
     * @return uId
     */
    public UUID getId(){return uId;}

    /**
     * Retorna el nombre del cliente
     * @return sNombre
     */
    public String getNombre(){return sNombre;}

    /**
     * Retorna el apellidos del cliente
     * @return sApellidos
     */
    public String getApellidos(){return sApellidos;}

    /**
     * Retorna el correo electronico del cliente
     * @return sCorreoElectronico
     */
    public String getCorreoElectronico(){return sCorreoElectronico;}

    /**
     * Retorna el contrasena electronico del cliente
     * @return contrasena electronico
     */
    //public String getContrasena(){return contrasena;}

    /**
     * Retorna el telefono del cliente
     * @return sTelefono
     */
    public String getTelefono(){return sTelefono;}
}