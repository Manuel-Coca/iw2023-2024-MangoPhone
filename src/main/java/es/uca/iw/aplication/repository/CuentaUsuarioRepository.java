package es.uca.iw.aplication.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.uca.iw.aplication.tables.usuarios.CuentaUsuario;
import es.uca.iw.aplication.tables.usuarios.Usuario;

@Repository
public interface CuentaUsuarioRepository extends JpaRepository<CuentaUsuario, UUID> {
    List<CuentaUsuario> findAll();
    Optional<CuentaUsuario> findByDuennoCuentaId(UUID id);
}