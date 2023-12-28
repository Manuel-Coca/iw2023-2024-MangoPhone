package es.uca.iw.aplication.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import es.uca.iw.aplication.tables.usuarios.CuentaUsuario;

public interface CuentaUsuarioRepository extends JpaRepository<CuentaUsuario, UUID> {
    List<CuentaUsuario> findAll();
}