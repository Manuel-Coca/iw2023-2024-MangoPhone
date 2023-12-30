package es.uca.iw.aplication.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import es.uca.iw.aplication.tables.Factura;
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

    public boolean sendFacturaEmail(Usuario usuario, Factura factura, String tipo) {
  
            Document document = new Document();
            String nombreFichero = "Factura-" + tipo + "-" + usuario.getNombre() + "-" + LocalDate.now() + ".pdf";
            String path = "src\\main\\resources\\recibo-facturas\\" + nombreFichero;

            try {
                PdfWriter.getInstance(document, new FileOutputStream(path));
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            document.open();

            document.add(new Paragraph("FACTURA"));
            document.add(new Paragraph("------------------------------------------"));
            document.add(new Paragraph("Fecha de Emisión: " + factura.getFechaInicio()));
            document.add(new Paragraph("Estado: " + factura.getEstado()));

            if (factura.getTarifa() != null) {
                document.add(new Paragraph("Tarifa Aplicada: " + factura.getTarifa().getServicio()));
                document.add(new Paragraph("Capacidad: " + factura.getTarifa().getCapacidad()));
            }
            
            document.add(new Paragraph("Precio: " + factura.getPrecio()));
            document.close();
            
            MimeMessage message = mailSender.createMimeMessage();
            try{
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
                helper.setTo(usuario.getCorreoElectronico());
                helper.setSubject("Asunto del correo");
                helper.setText("<html><body><h1>Factura adjunta</h1><body></html>", true);

                File file = new File(path);
                helper.addAttachment(nombreFichero, file);

                mailSender.send(message);
            }catch(MessagingException e) {
                e.printStackTrace();
                return false;
            }
        
        return true;
    }
}