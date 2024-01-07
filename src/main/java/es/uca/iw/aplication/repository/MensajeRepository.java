package es.uca.iw.aplication.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import es.uca.iw.aplication.tables.Mensaje;
import es.uca.iw.aplication.tables.usuarios.CuentaUsuario;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, UUID> {
    List<Mensaje> findAll();
    List<Mensaje> findByCuentaUsuario(CuentaUsuario cuentaUsuario);
}
