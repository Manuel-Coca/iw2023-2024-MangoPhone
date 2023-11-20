package es.uca.iw.aplication.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import es.uca.iw.aplication.tables.usuarios.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findById(UUID id);

    Usuario findByNombre(String nombre);

    Usuario findByApellidos(String apellidos);

    Usuario findByDni(String dni);

    Usuario findByCorreoElectronico(String correo);

    Usuario findByTelefono(String telefono);

    List<Usuario> findByActivoTrue();

    List<Usuario> findByActivoFalse();
}