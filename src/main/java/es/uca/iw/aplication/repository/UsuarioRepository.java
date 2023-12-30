package es.uca.iw.aplication.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.uca.iw.aplication.tables.usuarios.CuentaUsuario;
import es.uca.iw.aplication.tables.usuarios.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findById(UUID id);

    Usuario findByNombre(String nombre);

    Usuario findByApellidos(String apellidos);

    Usuario findByDni(String dni);

    Usuario findByCorreoElectronico(String correo);

    Usuario findByTelefono(String telefono);

    Usuario findByCuentaUsuario(CuentaUsuario cuentaUsuario);

    List<Usuario> findByActivoTrue();

    List<Usuario> findByActivoFalse();
}