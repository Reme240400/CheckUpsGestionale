package Helpers.PdfHelpers;

import Controllers.ClassHelper;
import Models.ModelListe;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;

import java.io.IOException;
import java.util.List;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class pdfGenerator {
    // private static final int ROW_HEIGHT = 20;
    // private static final float MARGIN = 50f;
    private static final String FONT_PATH = "C:\\dev\\ProgettoCheckUp\\CheckUpsGestionale\\checkUps\\src\\resources\\fonts\\Helvetica-Bold-Font.ttf";
    private static List<Titolo> titoli = ClassHelper.getListTitolo();

    private static int calculateMaxRowsPerPage(PageSize pageSize, float margin) {
        float rowHeight = 50; // Adjust this height based on your needs
        return (int) ((pageSize.getHeight() - 2 * margin) / rowHeight);
    }

    public static void stampaValutazioneRischi(Societa societa, UnitaLocale unitaLocale, List<Reparto> reparti, String nomeFile) {
        try {
            PdfWriter writer = new PdfWriter(nomeFile);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);

            document.add(new Paragraph("ID_SOCIETA: " + societa.getId()));
            document.add(new Paragraph("ID UNITA_LOCALE: " + unitaLocale.getId()));
            document.add(new Paragraph("ID reparti: " + reparti.get(0).getId()));

            float yPosition = PageSize.A4.getHeight() - 50;
            yPosition += 10;

            document.add(new Paragraph().setMarginTop(yPosition).setFixedPosition(10, yPosition + 5, 575));
            document.add(new Paragraph().setMarginTop(yPosition - 15).setFixedPosition(10, yPosition - 10,  575));
            document.add(new Paragraph().setMarginTop(yPosition + 5).setFixedPosition(10, yPosition + 5, 575));

            document.add(new Paragraph("Società:  " + societa.getNome()).setMarginTop(yPosition - 5));
            document.add(new Paragraph().setMarginTop(yPosition - 5).setFixedPosition(190, yPosition + 5, 575));
            document.add(new Paragraph("Unità Locale:  " + unitaLocale.getNome()).setMarginTop(yPosition - 5));
            document.add(new Paragraph().setMarginTop(yPosition - 5).setFixedPosition(380, yPosition + 5, 575));
            document.add(new Paragraph("reparti:  " + reparti.get(0).getNome()).setMarginTop(yPosition - 5));
            document.add(new Paragraph().setMarginTop(yPosition - 5).setFixedPosition(575, yPosition + 5, 575));

            document.setFontSize(6);

            int n = 1;

            for (Titolo titolo : titoli) {
                yPosition -= 20;
                document.add(new Paragraph("TITOLO: " + n + ") " + titolo.getDescrizione()).setMarginTop(yPosition - 5));
                yPosition -= 20;
                n++;

                List<Oggetto> oggetti = ModelListe.filtraOggettiDaTitolo(titolo.getId());

                for (Oggetto oggetto : oggetti) {
                    document.add(new Paragraph("OGGETTO: " + oggetto.getNome()).setMarginTop(yPosition - 5));
                    document.add(new Paragraph("STIMA (PxD = R)").setMarginTop(yPosition - 5));
                    yPosition -= 15;

                    List<Provvedimento> provvedimenti = ModelListe.filtraProvvedimentiDaOggetto(oggetto.getId());

                    for (Provvedimento provvedimento : provvedimenti) {
                        if (provvedimento.getNome().length() > 155) {
                            // Code for handling line breaks and wrapping
                        } else {
                            // Code for handling line breaks and wrapping
                        }
                    }
                }
            }

            document.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    // private static void addContent(Document document, Societa societa, UnitaLocale unitaLocale, List<Reparto> reparti) throws DocumentException {
    //     document.add(new Phrase("Società: " + societa.getNome()));
    //     document.add(new Phrase("Unità Locale: " + unitaLocale.getNome()));
    //     document.add(new Phrase("Reparto: " + reparti.get(0).getNome()));

    //     // Add more content as needed
    // }
}