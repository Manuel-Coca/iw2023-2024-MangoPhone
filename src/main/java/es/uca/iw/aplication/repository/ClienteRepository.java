package es.uca.iw.aplication.repository;

import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.uca.iw.aplication.tables.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    @Modifying
    @Query(
        value = "INSERT INTO cliente VALUES (:id, :nombre, :apellidos, :dni, :telefono, :correoElectronico, :contrasena)",
            nativeQuery = true
    )
    void insertCliente(@Param("id") UUID id,
                    @Param("nombre") String nombre,
                    @Param("apellidos") String apellidos,
                    @Param("dni") String dni,
                    @Param("telefono") String telefono,
                    @Param("correoElectronico") String correoElectronico,
                    @Param("contrasena") String contrasena
    );

}