package es.uca.iw.model;

import java.util.UUID;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name="Empleado")
public class Empleado {
    public enum Departamentos{Marketing,Finanzas,SAC};
    
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

    @Column(name = "departamento", length = 32)
    private Departamentos eDepartamento;

    /**
     * Constructor Parametrizado de Empleado
     * @param nombre
     * @param apellidos
     * @param correoElectronico
     * @param contrasena
     * @param departamento
     */
    public Empleado(String nombre, String apellidos, String correoElectronico, String contrasena, Departamentos departamento){
        this.sNombre=nombre;
        this.sApellidos=apellidos;
        this.sCorreoElectronico=correoElectronico;
        this.sContrasena=contrasena;
        this.eDepartamento=departamento;
    }

    /**
     * Retorna el id del Empleado
     * @return uId
     */
    public UUID getId(){return uId;}

    /**
     * Retorna el nombre del Empleado
     * @return sNombre
     */
    public String getNombre(){return sNombre;}

    /**
     * Retorna el apellidos del Empleado
     * @return sApellidos
     */
    public String getApellidos(){return sApellidos;}

    /**
     * Retorna el correo electronico del Empleado
     * @return sCorreoElectronico
     */
    public String getCorreoElectronico(){return sCorreoElectronico;}

    /**
     * Retorna el contrasena electronico del Empleado
     * @return contrasena electronico
     */
    //public String getContrasena(){return contrasena;}

    /**
     * Retorna el departamento del Empleado
     * @return eDepartamento
     */
    public Departamentos getDepartamento(){return eDepartamento;}
}