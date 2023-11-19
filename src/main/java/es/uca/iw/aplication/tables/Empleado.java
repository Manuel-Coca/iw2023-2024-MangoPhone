package es.uca.iw.aplication.tables;

import jakarta.persistence.Id;
import java.util.UUID;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id = null;
    public UUID getId() { return id; }
    public void setId(UUID newId) { this.id = newId; }

    @Column(name = "nombre", length = 32)
    private String nombre;
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Column(name = "apellidos", length = 64)
    private String apellidos;
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    @Column(name = "correoElectronico", length = 128)
    private String correoElectronico;
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correo) { this.correoElectronico = correo; }

    @Column(name = "contrasena", length = 128)
    private String contrasena;
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public boolean comprobarContrasena(String contrasena) { return this.contrasena.equals(contrasena); }

    @Column(name = "departamento", length = 32)
    private Departamentos departamento;
    public Departamentos getDepartamento() { return departamento; }
    public void setDepartamento(Departamentos departamento) { this.departamento = departamento; }

    public Empleado(String nombre, String apellidos, String correoElectronico, String contrasena, Departamentos departamento){
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.correoElectronico=correoElectronico;
        this.contrasena=contrasena;
        this.departamento=departamento;
    }
}