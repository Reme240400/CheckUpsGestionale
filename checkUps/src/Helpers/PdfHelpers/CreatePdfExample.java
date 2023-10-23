package Helpers.PdfHelpers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import Controllers.ClassHelper;
import Controllers.ControllerDb;
import Models.Tables.Provvedimento;
import Models.Tables.Societa;

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
            PDType0Font font = PDType0Font.load(document,
                    new File(
                            "C:\\dev\\CheckUps\\CheckUpsGestionale\\checkUps\\src\\resources\\fonts\\Helvetica-Bold-Font.ttf"));

            stampaSocieta(document, font);

            stampaProvvedimenti(document, font);

            // Visualizzazione del PDF
            document.save(nomeFile);
            document.close();

            // Apertura del PDF con l'applicazione predefinita per la visualizzazione
            Desktop.getDesktop().open(new File(nomeFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int calculateMaxRowsPerPage(PDPage page, float margin) {
        PDRectangle mediaBox = page.getMediaBox();
        float rowHeight = 50; // Regola questa altezza a seconda delle tue esigenze
        return (int) ((mediaBox.getHeight() - 2 * margin) / rowHeight);
    }

    private static String wordWrap(String text, int lineLength) {
        String[] words = text.split(" ");
        StringBuilder wrappedText = new StringBuilder();
        int currentLineLength = 0;

        for (String word : words) {
            if (currentLineLength + word.length() + 1 <= lineLength) {
                wrappedText.append(word).append(" ");
                currentLineLength += word.length() + 1;
            } else {
                wrappedText.append("\n").append(word).append(" ");
                currentLineLength = word.length() + 1;
            }
        }

        return wrappedText.toString().trim();
    }

    public static void stampaSocieta(PDDocument document, PDType0Font font) {
        try {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            // Creazione di un nuovo stream di contenuto per la pagina
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Impostazione del font
            contentStream.setFont(font, 8);

            // Aggiunta del contenuto al documento
            ControllerDb.popolaListaSocietaDaDb();

            List<Societa> records = ClassHelper.getListSocieta();
            float yPosition = page.getMediaBox().getHeight() - 50;
            int maxRowsPerPage = calculateMaxRowsPerPage(page, 50);

            for (Societa record : records) {
                // Controllo per creazione di numero giusto di pagine
                if (yPosition < maxRowsPerPage) {
                    contentStream.close();
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.setFont(font, 6);
                    yPosition = page.getMediaBox().getHeight() - 50;
                }
                contentStream.beginText();
                contentStream.newLineAtOffset(15, yPosition);
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

                yPosition -= 50; // Spaziatura tra le righe

            }
            contentStream.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void stampaProvvedimenti(PDDocument document, PDType0Font font) {
        try {
            PDPage page1 = new PDPage(PDRectangle.A4);
            document.addPage(page1);

            // Creazione di un nuovo stream di contenuto per la pagina
            PDPageContentStream contentStream1 = new PDPageContentStream(document,
                    page1);
            // Impostazione del font
            contentStream1.setFont(font, 3);

            ControllerDb.popolaListaProvvedimentiDaDb();

            List<Provvedimento> recordspProvvedimento = ClassHelper.getListProvvedimento();
            int maxRowsPerPage = calculateMaxRowsPerPage(page1, 50);
            float yPosition = page1.getMediaBox().getHeight() - 50;
            yPosition = page1.getMediaBox().getHeight() - 50;

            for (Provvedimento record : recordspProvvedimento) {

                // Controllo per creazione di numero giusto di pagine
                if (yPosition < maxRowsPerPage) {
                    contentStream1.close();
                    page1 = new PDPage(PDRectangle.A4);
                    document.addPage(page1);
                    contentStream1 = new PDPageContentStream(document, page1);
                    contentStream1.setFont(font, 3);
                    yPosition = page1.getMediaBox().getHeight() - 50;
                }
                // costruisco tabella
                // riga superiore
                contentStream1.moveTo(10, yPosition + 5);
                contentStream1.lineTo(575, yPosition + 5);
                contentStream1.stroke();
                // riga inferiore
                contentStream1.moveTo(10, yPosition - 5);
                contentStream1.lineTo(575, yPosition - 5);
                contentStream1.stroke();
                // colonne
                contentStream1.moveTo(10, yPosition + 5);
                contentStream1.lineTo(10, yPosition - 5);
                contentStream1.stroke();

                contentStream1.moveTo(340, yPosition + 5);
                contentStream1.lineTo(340, yPosition - 5);
                contentStream1.stroke();

                contentStream1.moveTo(490, yPosition + 5);
                contentStream1.lineTo(490, yPosition - 5);
                contentStream1.stroke();

                contentStream1.moveTo(575, yPosition + 5);
                contentStream1.lineTo(575, yPosition - 5);
                contentStream1.stroke();

                contentStream1.beginText();
                contentStream1.newLineAtOffset(15, yPosition);
                contentStream1.showText("NOME: " + wordWrap(record.getNome(),
                        10).replace("\n", "").replace("\r", "")); // Suddivide il testo in linee più corte o alemno
            
                contentStream1.endText();
                contentStream1.beginText();
                contentStream1.newLineAtOffset(350, yPosition);
                contentStream1.showText("RISCHIO: " + record.getRischio());
                contentStream1.endText();
                contentStream1.beginText();
                contentStream1.newLineAtOffset(500, yPosition);
                contentStream1.showText("SOGGETTI ESPOSTI: " + record.getSoggettiEsposti());
                contentStream1.endText();
                contentStream1.beginText();
                contentStream1.newLine();
                contentStream1.endText();

                // }else{
                // caso in cui ho più di 200 caratteri
                // faccio la stessa cosa di sopra ma scorro la y solo con il nome e prima di
                // fare rischio e soggetti esposti torno alla y originale

                // }

                yPosition -= 10; // Spaziatura tra le righe
            }

            contentStream1.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

}
