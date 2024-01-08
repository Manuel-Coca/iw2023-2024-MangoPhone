package es.uca.iw.aplication.service;

import java.io.File;
import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import es.uca.iw.aplication.tables.Factura;
import es.uca.iw.aplication.tables.Mensaje;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MyEmailService implements EmailService{
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mail;

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    public MyEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private String getServerUrl() {
        String serverUrl = "http://";
        serverUrl += InetAddress.getLoopbackAddress().getHostAddress();
        serverUrl += ":" + serverPort + "/activar";
        return serverUrl;
    }

    @Override
    public boolean sendRegistartionEmail(Usuario usuario, String code) {
        SimpleMailMessage message = new SimpleMailMessage();

        String subject = "Código de activación de MangoPhone";
        String body = "Muchas gracias por confiar en nosotros. \n"
            + "Para activa su cuenta, haga click en el siguiente enlace: " + getServerUrl() + " o vuelva a la página de MangoPhone.\n"
            + "Deberá introducir su correo electrónico y el siguiente código: "
            + code;

        try {
            message.setFrom(mail);
            message.setTo(usuario.getCorreoElectronico());
            message.setSubject(subject);
            message.setText(body);
            this.mailSender.send(message);
        }
        catch(MailException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean sendNewPassEmail(Usuario usuario, String newPass) {
        SimpleMailMessage message = new SimpleMailMessage();

        String subject = "Nueva contraseña de MangoPhone";
        String body = "Esta es la nueva contraseña asignada a su cuenta: " + newPass + "\n"
            + "Inicie sesión con esta nueva contraseña.\n"
            + "Le recomendamos entrar en la pestaña de 'Tu perfil' y volver a cambiar esta contraseña por una más personal y segura.";

        try {
            message.setFrom(mail);
            message.setTo(usuario.getCorreoElectronico());
            message.setSubject(subject);
            message.setText(body);
            this.mailSender.send(message);
        }
        catch(MailException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /*
     * Pre:         Recibe un usuario y un contrato
     * Post:        Crea un mensaje personalizado, junto a la factura asociada al contrato. Despues de enviar el correo elimina el fichero en el sistema
     *              de archivos local
     */
    public boolean sendFacturaEmail(Usuario usuario, Factura factura) {
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setTo(usuario.getCorreoElectronico());
            helper.setSubject("Factura");
            helper.setText("<html>\r\n" + 
                            "<head>\r\n" + 
                            "  <style>\r\n" + 
                            "    body {\r\n" + 
                            "      font-family: 'Comic Sans MS', cursive;\r\n" + 
                            "      line-height: 1.6;\r\n" + 
                            "      margin: 20px;\r\n" + 
                            "    }\r\n" + 
                            "    h1 {\r\n" + 
                            "      color: #ff9900;\r\n" + 
                            "      font-size: 24px;\r\n" + 
                            "    }\r\n" + 
                            "    p {\r\n" + 
                            "      margin-bottom: 15px;\r\n" + 
                            "      font-size: 16px;\r\n" + 
                            "    }\r\n" + 
                            "    strong {\r\n" + 
                            "      color: black;\r\n" + 
                            "      font-size: 16px;\r\n" + 
                            "    }\r\n" + 
                            "  </style>\r\n" + 
                            "</head>\r\n" + 
                            "<body>\r\n" + 
                            "  <h1>Mango<span style=\"color: green;\">Phone</span></h1>\r\n" + //
                            "  <p>Estimado <span style=\"color: #ff9900;\">" + usuario.getNombre() + "</span>, Le agradecemos por elegir MangoPhone. Adjuntamos la factura correspondiente a las tarifas contratadas, con el siguiente desglose.</p>\r\n" + //
                            "</body>\r\n" + 
                            "</html>\r\n" + 
                            "", true);

                String nombreFichero = factura.getfileName();
                String path = "docs_facturas\\" + nombreFichero;
                
                File file = new File(path);
                helper.addAttachment(nombreFichero, file);
                mailSender.send(message);

        }catch(MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void sendResponseMessages(Mensaje mensaje, String respuesta) {
        SimpleMailMessage message = new SimpleMailMessage();

        String subject = "Respuesta a su mensaje";
        String body =  "Tu mensaje:\n" 
            + "Asunto: " + mensaje.getAsunto() + "\n" 
            + "Cuerpo: " + mensaje.getCuerpo() + "\n"
            + "----------------------------------------------------------------------\n"
            + "Respuesta:\n" + respuesta;

        try {
            message.setFrom(mail);
            message.setTo(mensaje.getUsuario().getDuennoCuenta().getCorreoElectronico());
            message.setSubject(subject);
            message.setText(body);
            this.mailSender.send(message);
        }catch(MailException ex) {
            ex.printStackTrace();
        }
    }
}