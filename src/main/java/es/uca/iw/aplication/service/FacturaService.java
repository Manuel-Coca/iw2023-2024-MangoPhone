package es.uca.iw.aplication.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import es.uca.iw.aplication.repository.FacturaRepository;
import es.uca.iw.aplication.tables.Contrato;
import es.uca.iw.aplication.tables.Contrato_Tarifa;
import es.uca.iw.aplication.tables.Factura;
import es.uca.iw.aplication.tables.Factura.Estado;
import es.uca.iw.aplication.tables.usuarios.Usuario;

@Service
public class FacturaService {
    private final FacturaRepository facturaRepository;
    private final ContratoService contratoService;
    private final MyEmailService mEmailService;
    private final UsuarioService usuarioService;

    @Autowired
    public FacturaService(FacturaRepository facturaRepository, MyEmailService mailService, ContratoService contratoService, UsuarioService usuarioService){ 
        this.facturaRepository = facturaRepository; 
        this.mEmailService = mailService; 
        this.contratoService = contratoService;
        this.usuarioService = usuarioService;
    }
    
    /*************************************************************************** Interfaz Común ************************************************************************************/
    public void save(Factura factura){ facturaRepository.save(factura); }

    public void delete(Factura factura){ facturaRepository.delete(factura); }

    public Factura getFacturaById(UUID id){ return facturaRepository.findById(id).get(); }

    public List<Factura> findByContrato(Contrato contrato) { return facturaRepository.findByContrato(contrato); }
    
    /*************************************************************************** Interfaz Personalizada ************************************************************************************/

    @Scheduled(cron = "0 0 1 * * *")
    public void generarFacturaMensual() {
        List<Usuario> todosUsuarios = usuarioService.findAll();
        for(Usuario usuario : todosUsuarios) {
            if(usuario.getCuentaUsuario().getContrato() != null) {
                if((Period.between(usuario.getCuentaUsuario().getContrato().getFechaInicio(), LocalDate.now()).getMonths() >= 1 &&
                (Period.between(usuario.getCuentaUsuario().getContrato().getFechaInicio(), LocalDate.now()).getDays() == 0))){
                    Factura factura = new Factura(Estado.Pagado, LocalDate.now(), usuario.getCuentaUsuario().getContrato(), generarNombreFactura(usuario));
                    save(factura);
                    contratoService.addFactura(usuario.getCuentaUsuario().getContrato(), factura);
                    contratoService.actualizarContrato(usuario.getCuentaUsuario().getContrato());
                    
                    crearFacturaPDFLocal(usuario.getCuentaUsuario().getContrato(), factura);
                    mEmailService.sendFacturaEmail(usuario, factura);
                    eliminarFacturaPDFLocal(factura);
                    save(factura);
                }
            }
        }
    }

    /*
     * Pre: Recibe un documento
     * Post: Devuelve el documento un vector de bytes
     */
    public byte[] pdfToBinary(Document document) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] pdfAsBytes = baos.toByteArray();
        return pdfAsBytes;
    }

    // A LO MEJOR SOBRA
    public void rescatarFacturaPDF(Factura factura){
        String nombreFichero = factura.getfileName();
        String path = "docs_facturas\\" + nombreFichero;
        FileInputStream fileInputStream = null;
		File file = new File(path);
		byte[] fileArray = new byte[(int) file.length()];

		try {
			// Con este código se obtienen los bytes del archivo.
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(fileArray);
			fileInputStream.close();

			// Con este código se agregan los bytes al archivo.
			FileOutputStream fileOuputStream = new FileOutputStream(path);
			fileOuputStream.write(fileArray);
			fileOuputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public String generarNombreFactura(Usuario usuario) {
        // Version simplificada del estandar de la expedicion de facturas de la Agencia Estatal de Administración Tributaria (AEAT)
        return LocalDateTime.now().getYear() + "_" + LocalDateTime.now().getMonth().getValue() + "_" + usuario.getDNI() + "@" + usuario.getNombre() + ".pdf";
    }

    /*
     * Pre: Recibe un usuario y un contrato
     * Post: Usando la api OpenPDF, genera un documento pdf (Documento usado en sendFacturaEmail)
     */
    public void crearFacturaPDFLocal(Contrato contrato, Factura factura) {
            Document document = new Document();

            String nombreFichero = factura.getfileName();
            String path = "docs_facturas\\" + nombreFichero;

            try {
                PdfWriter.getInstance(document, new FileOutputStream(path));
                
                document.open();
                Paragraph title = new Paragraph();
                title.setAlignment(Element.ALIGN_CENTER);
                title.add("MangoPhone");

                document.add(title);
                document.add(new Paragraph("Fecha de Emisión: " + contrato.getFechaInicio()));
                document.add(new Paragraph("------------------------------------------"));

                for(Contrato_Tarifa contratoTarifa: contrato.getContratoTarifas()){
                    if (contratoTarifa.getTarifa() != null) {
                        document.add(new Paragraph("Tarifa Aplicada: " + contratoTarifa.getTarifa().getServicio()));
                        document.add(new Paragraph("Capacidad: " + contratoTarifa.getTarifa().getCapacidad()));
                        document.add(new Paragraph("Precio: " + contratoTarifa.getTarifa().getPrecio()  + " €"));
                        document.add(new Paragraph("------------------------------------------"));
                    }
                }
                document.add(new Paragraph("Precio Total: " + contrato.getPrecio()  + " €"));
                document.close();

                File file = new File(path);
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }    
    }

    /*  Pre: Recibe una factura
     *  Post: La elimina del sistema de archivos local
     */
    public void eliminarFacturaPDFLocal(Factura factura) {
        File file = new File("docs_facturas\\" + factura.getfileName());
        file.delete();
    }

}