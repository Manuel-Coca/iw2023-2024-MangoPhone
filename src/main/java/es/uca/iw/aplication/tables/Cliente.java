package es.uca.iw.aplication.tables;

import jakarta.persistence.Id;
import java.time.LocalDate;
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

    @Column(name = "dni", length = 9)
    private String dni;
    public String getDNI() { return dni; }
    public void setDNI(String dni) { this.dni = dni; }

    @Column(name = "correoElectronico", length = 128)
    private String correoElectronico;
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    @Column(name = "fechaNacimiento")
    private LocalDate fechaNacimiento;
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    @Column(name = "contrasena", length = 128)
    private String contrasena;
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public boolean comprobarContrasena(String contrasena) { return this.contrasena.equals(contrasena); }

    @Column(name = "telefono", length = 9)
    private String telefono;
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Column(name = "activo")
    private boolean activo = false;
    public boolean getActivo() { return this.activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

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