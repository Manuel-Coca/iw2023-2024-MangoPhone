package es.uca.iw.aplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.uca.iw.aplication.repository.TokenRepository;
import es.uca.iw.aplication.tables.usuarios.Token;
import es.uca.iw.aplication.tables.usuarios.Usuario;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) { this.tokenRepository = tokenRepository; }

    public void createToken(Token token) { tokenRepository.save(token); }

    public Token buscarToken(Usuario usuario) { return tokenRepository.findByUsuario(usuario); }
}