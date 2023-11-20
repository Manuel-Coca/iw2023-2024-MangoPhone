package es.uca.iw.aplication.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import es.uca.iw.aplication.tables.usuarios.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findById(UUID id);

    Optional<Cliente> findByNombre(String nombre);

    Optional<Cliente> findByApellidos(String apellidos);

    Optional<Cliente> findByDni(String dni);

    Optional<Cliente> findByCorreoElectronico(String correo);

    Optional<Cliente> findByTelefono(String telefono);

    List<Cliente> findByActivoTrue();

    List<Cliente> findByActivoFalse();
}