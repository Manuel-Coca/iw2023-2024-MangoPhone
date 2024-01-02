package es.uca.iw.aplication.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.uca.iw.aplication.tables.Contrato;
import es.uca.iw.aplication.tables.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, UUID> {
    List<Factura> findAll();
    List<Factura> findByContrato(Contrato contrato);
}