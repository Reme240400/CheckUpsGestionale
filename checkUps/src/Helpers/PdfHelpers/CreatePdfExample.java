package Helpers.PdfHelpers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import Controllers.ClassHelper;
import sql.ControllerDb;
import Models.Tables.Provvedimento;
import Models.Tables.Societa;
import sql.ControllerDb;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.util.List;

public class CreatePdfExample {
    public static void main(String[] args) {
        // Creazione di un documento PDF
        String nomeFile = "prova.pdf";

        try {

            PDDocument document = new PDDocument();
            PDType0Font font = PDType0Font.load(document,
                    new File("C:\\dev\\CheckUpsGestionale\\checkUps\\src\\resources\\fonts\\Helvetica-Bold-Font.ttf"));
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

           // Creazione di un nuovo stream di contenuto per la pagina
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Impostazione del font
            contentStream.setFont(font, 12);

            /*/
            // Aggiunta del contenuto al documento

            System.out.println("Lista società prima" + ClassHelper.getListSocieta());
            ControllerDb.popolaListaSocietaDaDb();

            List<Societa> records = ClassHelper.getListSocieta();
            float yPosition = page.getMediaBox().getHeight() - 50;

            for (Societa record : records) {
                contentStream.beginText();
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText("ID: " + record.getIdSocieta());
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(150, yPosition);
                contentStream.showText("Nome: " + record.getNome());
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(350, yPosition);
                contentStream.showText("Indirizzo: " + record.getIndirizzo());
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLine();
                contentStream.endText();
                yPosition -= 50; // Spaziatura tra le righe
            }
            */
             PDPage page1 = new PDPage(PDRectangle.A4);
            document.addPage(page1);

            // Creazione di un nuovo stream di contenuto per la pagina
            PDPageContentStream contentStream1 = new PDPageContentStream(document, page1);
            // Impostazione del font
            contentStream1.setFont(font, 12);

            
            // Aggiunta del contenuto al documento

            
            ControllerDb.popolaListaProvvedimentiDaDb();
            System.out.println("Lista provvedimenti: " + ClassHelper.getListProvvedimento());

            List<Provvedimento> records = ClassHelper.getListProvvedimento();
            float yPosition = page1.getMediaBox().getHeight() - 50;

            for (Provvedimento record : records) {
                contentStream1.beginText();
                contentStream1.newLineAtOffset(50, yPosition);
                contentStream1.showText("NOME: " + record.getNome());
                contentStream1.endText();
                contentStream1.beginText();
                contentStream1.newLineAtOffset(150, yPosition);
                contentStream1.showText("RISCHIO: " + record.getRischio());
                contentStream1.endText();
                contentStream1.beginText();
                contentStream1.newLineAtOffset(350, yPosition);
                contentStream1.showText("SOGGETTI ESPOSTI: " + record.getSoggettiEsposti());
                contentStream1.endText();
                contentStream1.beginText();
                contentStream1.newLine();
                contentStream1.endText();
                yPosition -= 50; // Spaziatura tra le righe
            }


            contentStream1.close();

            // Visualizzazione del PDF
            document.save(nomeFile);
            document.close();

            // Apertura del PDF con l'applicazione predefinita per la visualizzazione
            Desktop.getDesktop().open(new File(nomeFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * private static List<Record> getRecordsFromDatabase() {
     * // Implementa la logica per ottenere i record dalla tabella "societa" qui
     * // Restituisce una lista di record
     * }
     */
}