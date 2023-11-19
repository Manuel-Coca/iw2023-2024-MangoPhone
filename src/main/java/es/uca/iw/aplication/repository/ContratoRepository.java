package es.uca.iw.aplication.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import es.uca.iw.aplication.tables.Contrato;

public interface ContratoRepository extends JpaRepository<Contrato, UUID> {

    Optional<Contrato> findById(UUID id);
}