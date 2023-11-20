package es.uca.iw.aplication.test;

import es.uca.iw.aplication.repository.ClienteRepository;
import es.uca.iw.aplication.service.ClienteService;
import es.uca.iw.aplication.tables.Cliente;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RepositoryClienteTest {

    private final ClienteRepository clienteRepository;

    public static Cliente createTestCliente(String username, String DNI, int phoneNumber) {
        Cliente testCliente = new Cliente();

        testCliente.setDNI(DNI);
        testCliente.setNombre(username);
        testCliente.setApellidos(username);
        testCliente.setCorreoElectronico(username + "@uca.es");
        testCliente.setTelefono(phoneNumber);
        testCliente.setContrasena("password");

        return testCliente;
    }

    @Test
    public void testSaveCliente() throws AssertionError {
        Cliente testCliente = AuthenticationTest.createTestCliente("john Doe", "12345678X" , 000000000);
        clienteService.save(testCliente);

        List<Cliente> clientesBD = clienteRepository.findAll();
        assertTrue(clientesBD.contains(testCliente), "El cliente no se ha almacenado correctamente en la Base de datos");

        clienteService.delete(testCliente);
        assertFalse(clientesBD.contains(testCliente), "El cliente no se ha eliminado correctamente de la Base de datos");
    }
}
