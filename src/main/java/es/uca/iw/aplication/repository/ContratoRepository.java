package es.uca.iw.aplication.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.uca.iw.aplication.tables.Contrato;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, UUID> {

    Optional<Contrato> findById(UUID id);
    Optional<Contrato> findByCuentaUsuarioId(UUID id);

}