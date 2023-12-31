package es.uca.iw.aplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import es.uca.iw.aplication.tables.Contrato;
import es.uca.iw.aplication.tables.Contrato_Tarifa;

@Repository
public interface Contrato_TarifaRepository extends  JpaRepository<Contrato_Tarifa, UUID> {
    Optional<Contrato_Tarifa> findById(UUID id);
    List<Contrato_Tarifa>  findByContrato(Contrato contrato);
}
