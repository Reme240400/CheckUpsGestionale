package Helpers.PdfHelpers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

public class CreatePdfExample {
    public static void main(String[] args) {
        // Creazione di un documento PDF
        String nomeFile = "prova.pdf";

        Document doc = null;
        // Aggiunta del contenuto al documento
        try {
            PdfWriter pdfWriter = new PdfWriter(nomeFile);
            PdfDocument pdfDoc = new PdfDocument(pdfWriter);
            doc = new Document(pdfDoc);
            // Aggiunta di un paragrafo
            Paragraph paragraph = new Paragraph("Questo è un paragrafo esistente.");
            doc.add(paragraph); 
             

        } catch (Exception e) {
          
            System.out.println("DIO LUPOOOOOOOOOOOOOOOOOOOOOOOOOOOOO"+e);
        } finally {
            // Chiusura del documento
           

        }
        
        doc.close();

    }
}
