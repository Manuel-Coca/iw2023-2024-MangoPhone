package es.uca.iw.aplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.uca.iw.aplication.repository.CuentaUsuarioRepository;
import es.uca.iw.aplication.tables.Contrato;
import es.uca.iw.aplication.tables.usuarios.CuentaUsuario;
import es.uca.iw.aplication.tables.usuarios.Usuario;

@Service
public class CuentaUsuarioService {
    private final CuentaUsuarioRepository cuentaUsuarioRepository;

    @Autowired
    public CuentaUsuarioService(CuentaUsuarioRepository cuentaUsuarioRepository) { 
        this.cuentaUsuarioRepository = cuentaUsuarioRepository; 
    }

     /*
     * Pre:     Recibe una cuenta de usuario
     * Post:    Si no existe crea una nueva entrada en la base de datos con los datos de la cuenta de usuario, sino actualiza
     */
    public void save(CuentaUsuario cuentaUsuario){ cuentaUsuarioRepository.save(cuentaUsuario); }

    /*
     * Pre: Recibe una cuenta de usuario y un contrato
     * Post: Asigna a la cuenta de usuario el contrato
     */
    public void asignarContrato(CuentaUsuario cuentaUsuario, Contrato contrato) { cuentaUsuario.setContrato(contrato); }

    public CuentaUsuario findByDuennoCuenta(Usuario usuario){
        return cuentaUsuarioRepository.findByDuennoCuentaId(usuario.getId()).get();
    }
}