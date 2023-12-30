package es.uca.iw.aplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import es.uca.iw.aplication.tables.Contrato;
import es.uca.iw.aplication.tables.Contrato_Factura;

@Repository
public interface Contrato_FacturaRepository extends  JpaRepository<Contrato_Factura, UUID> {
    Optional<Contrato_Factura> findById(UUID id);
    List<Contrato_Factura>  findByContrato(Contrato contrato);
}
