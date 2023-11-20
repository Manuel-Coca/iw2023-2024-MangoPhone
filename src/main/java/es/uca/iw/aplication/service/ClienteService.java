package es.uca.iw.aplication.service;

import java.util.Optional;
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

    public boolean validarCredenciales(String correo, String contrasena) {
        Optional<Cliente> cliente = clienteRepository.findByCorreoElectronico(correo) ;
        
        return cliente != null && passwordEncoder.matches(contrasena, cliente.get().getContrasena());
    }

    public Optional<Cliente> findByCorreoElectronico(String email) {
        return clienteRepository.findByCorreoElectronico(email);
    }
}