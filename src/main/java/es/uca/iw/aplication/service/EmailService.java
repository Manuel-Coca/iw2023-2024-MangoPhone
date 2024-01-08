package es.uca.iw.aplication.service;
import es.uca.iw.aplication.tables.Factura;
import es.uca.iw.aplication.tables.usuarios.Usuario;

public interface EmailService { 
    
    boolean sendRegistartionEmail(Usuario usuario, String code); 
    boolean sendNewPassEmail(Usuario usuario, String newPass);
    boolean sendNewToken(Usuario usuario, String token);
    
    /*
     * Pre:     Recibe un usuario y un contrato
     * Post:    A partir de la factura del contrato, genera un nuevo pdf y lo envia por correo
     */
    boolean sendFacturaEmail(Usuario usuario, Factura factura);
}