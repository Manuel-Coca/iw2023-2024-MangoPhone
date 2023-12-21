package es.uca.iw.aplication.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import es.uca.iw.aplication.tables.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, UUID> {

    List<Servicio> findAll();

    Servicio findByNombre(String nombre);

}