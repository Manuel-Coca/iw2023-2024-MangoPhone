package es.uca.iw.aplication.service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
import es.uca.iw.aplication.tables.usuarios.Usuario;
@Service
public class FacturaService {
    private final FacturaRepository facturaRepository;

    @Autowired
    public FacturaService(FacturaRepository facturaRepository) { this.facturaRepository = facturaRepository; }
    
    /*************************************************************************** Interfaz Común ************************************************************************************/
    public void save(Factura factura){ facturaRepository.save(factura); }

    public void delete(Factura factura){ facturaRepository.delete(factura); }

    public Factura getFacturaById(UUID id){ return facturaRepository.findById(id).get(); }

    /*************************************************************************** Interfaz Personalizada ************************************************************************************/

    public List<Factura> findByContrato(Contrato contrato) { return facturaRepository.findByContrato(contrato); }

    /*
     * Pre: Recibe un documento
     * Post: Devuelve el documento un vector de bytes
     */
    public byte[] pdfToBinary(Document document) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] pdfAsBytes = baos.toByteArray();
        return pdfAsBytes;
    }

    /*
     * Pre: Recibe un usuario y un contrato
     * Post: Usando la api OpenPDF, genera un documento pdf (Documento usado en sendFacturaEmail)
     */
    public Document generarFacturaPDF(Usuario usuario, Contrato contrato, Factura factura) {
        
            Document document = new Document();

            String nombreFichero = factura.getfileName();
            String path = "doc\\recibo-facturas" + nombreFichero;

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

            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        return document;                           
    }

}