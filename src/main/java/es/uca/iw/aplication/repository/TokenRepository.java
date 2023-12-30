package es.uca.iw.aplication.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.uca.iw.aplication.tables.usuarios.Token;
import es.uca.iw.aplication.tables.usuarios.Usuario;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
    Token findByUsuario(Usuario usuario);
}