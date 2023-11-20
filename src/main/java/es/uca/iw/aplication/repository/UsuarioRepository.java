package es.uca.iw.aplication.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import es.uca.iw.aplication.tables.usuarios.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findById(UUID id);

    Optional<Usuario> findByNombre(String nombre);

    Optional<Usuario> findByApellidos(String apellidos);

    Optional<Usuario> findByDni(String dni);

    Optional<Usuario> findByCorreoElectronico(String correo);

    Optional<Usuario> findByTelefono(String telefono);

    List<Usuario> findByActivoTrue();

    List<Usuario> findByActivoFalse();
}