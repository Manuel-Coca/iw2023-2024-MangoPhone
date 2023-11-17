package es.uca.iw.model;

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

    @Column(name = "departamento", length = 32)
    private Departamentos departamento;

    /**
     * Constructor Parametrizado de Empleado
     * @param nombre
     * @param apellidos
     * @param correoElectronico
     * @param contrasena
     * @param departamento
     */
    public Empleado(String nombre, String apellidos, String correoElectronico, String contrasena, Departamentos departamento){
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.correoElectronico=correoElectronico;
        this.contrasena=contrasena;
        this.departamento=departamento;
    }

    /**
     * Retorna el id del Empleado
     * @return id
     */
    public int getId(){return id;}

    /**
     * Retorna el nombre del Empleado
     * @return nombre
     */
    public String getNombre(){return nombre;}

    /**
     * Retorna el apellidos del Empleado
     * @return apellidos
     */
    public String getApellidos(){return apellidos;}

    /**
     * Retorna el correo electronico del Empleado
     * @return correoElectronico
     */
    public String getCorreoElectronico(){return correoElectronico;}

    /**
     * Retorna el contrasena electronico del Empleado
     * @return contrasena electronico
     */
    //public String getContrasena(){return contrasena;}

    /**
     * Retorna el departamento del Empleado
     * @return departamento
     */
    public Departamentos getDepartamento(){return departamento;}
}