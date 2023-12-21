package es.uca.iw.aplication.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import es.uca.iw.aplication.tables.enumerados.Servicio;
import es.uca.iw.aplication.tables.tarifas.Tarifa;

public interface TarifaRepository extends JpaRepository<Tarifa, UUID> {

    List<Tarifa> findAll();

    List<Tarifa> findByServicio(Servicio servicio);

}