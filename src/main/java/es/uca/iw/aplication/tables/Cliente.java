package es.uca.iw.aplication.tables;

import jakarta.persistence.Id;

import java.util.UUID;

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
    private UUID id;
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

    @Column(name = "dni", length = 9)
    private String dni;
    public String getDNI() { return dni; }
    public void setDNI(String dni) { this.dni = dni; }

    @Column(name = "correoElectronico", length = 128)
    private String correoElectronico;
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    @Column(name = "contrasena", length = 128)
    private String contrasena;
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public boolean comprobarContrasena(String contrasena) { return this.contrasena.equals(contrasena); }

    @Column(name = "telefono", length = 9)
    private String telefono;
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /*
    public Cliente(String nombre, String apellidos, String correoElectronico, String contrasena, String telefono){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.telefono = telefono;
    }
    */
}