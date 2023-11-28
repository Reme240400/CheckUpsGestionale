package Helpers.PdfHelpers;

import Controllers.ClassHelper;
import Controllers.ControllerDb;
import Models.ModelListe;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import javafx.scene.text.Font;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.pdfbox.jbig2.segments.Table;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;


import javafx.scene.text.TextAlignment;

public class pdfGenerator {
    private static final int ROW_HEIGHT = 20;
    private static final float MARGIN = 50f;

    public static void stampaValutazioneRischi(Societa societa, UnitaLocale unitaLocale, List<Reparto> reparti, String nomeFile) {
        try {
            Document document = new Document(PageSize.A4, MARGIN, MARGIN, MARGIN, MARGIN);
            PdfWriter.getInstance(document, new FileOutputStream(nomeFile));

            document.open();

            // Set font and other styling options as needed

            // Add content to the PDF
            addContent(document, societa, unitaLocale, reparti);

            // Close the document
            document.close();

            // Open the PDF with the default application for viewing
            Desktop.getDesktop().open(new File(nomeFile));
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (DocumentException e) {
            System.out.println(e);
        }
    }

    private static void addContent(Document document, Societa societa, UnitaLocale unitaLocale, List<Reparto> reparti) throws DocumentException {
        document.add(new Phrase("Società: " + societa.getNome()));
        document.add(new Phrase("Unità Locale: " + unitaLocale.getNome()));
        document.add(new Phrase("Reparto: " + reparti.get(0).getNome()));

        // Add more content as needed
    }
}