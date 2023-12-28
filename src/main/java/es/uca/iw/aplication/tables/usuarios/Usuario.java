package es.uca.iw.aplication.tables.usuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import es.uca.iw.aplication.tables.enumerados.Rol;
import jakarta.persistence.*;

@Entity
public class Usuario implements UserDetails {
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

    @Column(name = "dni", length = 9, unique = true)
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

    @Column(name = "rol")
    private Rol rol = null;
    public Rol getRol() { return this.rol; }
    public void setRol(Rol rol) { this.rol = rol; }
    public List<Rol> getAllRoles() {
        List<Rol> roles = new ArrayList<>();
        for(Rol rol : Rol.values()) { roles.add(rol);}
        return roles;
    }

    @OneToOne
    private CuentaUsuario cuentaUsuario;
    public CuentaUsuario getCuentaUsuario() { return cuentaUsuario; }
    public void setCuentaUsuario(CuentaUsuario cuentaUsuario) { this.cuentaUsuario = cuentaUsuario; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { 
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + getRol().toString()));
        return authorities;
    }

    @Override
    public String getPassword() { return getContrasena(); }

    @Override
    public String getUsername() { return getNombre(); }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return getActivo(); }

}