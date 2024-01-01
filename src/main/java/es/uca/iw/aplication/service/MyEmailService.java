package es.uca.iw.aplication.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.time.LocalDate;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import es.uca.iw.aplication.tables.Contrato;
import es.uca.iw.aplication.tables.usuarios.Usuario;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MyEmailService implements EmailService{
    private final JavaMailSender mailSender;
    private final FacturaService facturaService;

    @Value("${spring.mail.username}")
    private String mail;

    @Value("${server.port}")
    private int serverPort;

    public MyEmailService(JavaMailSender mailSender, FacturaService facturaService) {
        this.mailSender = mailSender;
        this.facturaService = facturaService;
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

    public boolean sendFacturaEmail(Usuario usuario, Contrato contrato) {
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

                String nombreFichero = "Factura-" + "-" + usuario.getNombre() + "-" + LocalDate.now() + ".pdf";
                String path = "doc\\recibo-facturas" + nombreFichero;
                File file = new File(path);
                helper.addAttachment(nombreFichero, file);
                mailSender.send(message);
                file.delete();
        }catch(MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}