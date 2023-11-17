package es.uca.iw.aplication.service;

import org.springframework.beans.factory.annotation.Autowired;

import es.uca.iw.aplication.repository.ClienteRepository;
import es.uca.iw.aplication.tables.Cliente;
import jakarta.transaction.Transactional;

public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public void saveCliente(Cliente cliente) {         
        clienteRepository.insertCliente(cliente.getId(), cliente.getNombre(), cliente.getApellidos(), cliente.getDNI(),cliente.getTelefono(), cliente.getCorreoElectronico(), cliente.getContrasena());     
    }
}