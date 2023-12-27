package es.uca.iw.aplication.service;

import java.net.InetAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import es.uca.iw.aplication.tables.usuarios.Usuario;

@Service
public class MyEmailService implements EmailService{
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mail;

    @Value("${server.port}")
    private int serverPort;

    public MyEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private String getServerUrl() {
        String serverUrl = "http://";
        serverUrl += InetAddress.getLoopbackAddress().getHostAddress();
        serverUrl += ":" + serverPort + "/useractivation";
        return serverUrl;
    }

    @Override
    public boolean sendRegistartionEmail(Usuario usuario, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        /*MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");*/

        String subject = "Buenas";
        String body = "Debes activar tu cuenta. \n"
            + "ve a " + getServerUrl() + " activación de usuario "
            + "e introduce tu email y el siguiente codigo: "
            + code;

        try {
            message.setFrom(mail);
            message.setTo(usuario.getCorreoElectronico());
            message.setSubject(subject);
            message.setText(body);
            this.mailSender.send(message);
        }catch(MailException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}