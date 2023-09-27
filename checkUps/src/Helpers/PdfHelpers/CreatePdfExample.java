package Helpers.PdfHelpers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import Controllers.ClassHelper;
import Controllers.DbController;
import Tables.Societa;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CreatePdfExample {
    public static void main(String[] args) {
        // Creazione di un documento PDF
        String nomeFile = "prova.pdf";
        
        try {


            PDDocument document = new PDDocument();
            PDType0Font font = PDType0Font.load(document, new File("src/resources/fonts/Helvetica-Bold-Font.ttf"));
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Creazione di un nuovo stream di contenuto per la pagina
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Impostazione del font
            contentStream.setFont(font, 12);


            // Aggiunta del contenuto al documento
            DbController dbc = new DbController();
            dbc.popolaListaSocieta();
            
            List<Societa> records = ClassHelper.getListSocieta(); // Sostituisci con la tua logica per ottenere i record dalla tabella "societa"
            float yPosition = page.getMediaBox().getHeight() - 50;

            for (Societa record : records) {
                contentStream.beginText();
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText("ID: "+ record.getIdSocieta());
                contentStream.newLine();
                contentStream.showText("Nome: " + record.getEnte());
                contentStream.newLine();
                contentStream.showText("Altro: " + record.getDescrizione());
                contentStream.newLine();
                contentStream.endText();
                yPosition -= 50; // Spaziatura tra le righe
            }

            contentStream.close();

            // Visualizzazione del PDF
            document.save(nomeFile);
            document.close();

            // Apertura del PDF con l'applicazione predefinita per la visualizzazione
            Desktop.getDesktop().open(new File(nomeFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* 
    private static List<Record> getRecordsFromDatabase() {
        // Implementa la logica per ottenere i record dalla tabella "societa" qui
        // Restituisce una lista di record
    }*/
}



