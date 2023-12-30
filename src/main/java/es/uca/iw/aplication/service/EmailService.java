package es.uca.iw.aplication.service;

import es.uca.iw.aplication.tables.Factura;
import es.uca.iw.aplication.tables.usuarios.Usuario;

public interface EmailService { 
    
    boolean sendRegistartionEmail(Usuario usuario, String code); 
    
    /*
     * Crea un pdf con la factura determinada y es enviada al correo del usuario "usuario"
     */
    boolean sendFacturaEmail(Usuario usuario, Factura factura, String tipo);
}