package es.uca.iw.aplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import es.uca.iw.aplication.repository.ClienteRepository;
import es.uca.iw.aplication.tables.Cliente;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository,PasswordEncoder passwordEncoder){
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createCliente(Cliente cliente) {       
        cliente.setContrasena(passwordEncoder.encode(cliente.getContrasena()));   
        
        clienteRepository.save(cliente);     
    }
}