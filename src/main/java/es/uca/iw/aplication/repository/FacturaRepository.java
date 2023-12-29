package es.uca.iw.aplication.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uca.iw.aplication.tables.Factura;

public interface FacturaRepository extends JpaRepository<Factura, UUID> {
    List<Factura> findAll();
}