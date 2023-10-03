package Helpers.PdfHelpers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import Controllers.ClassHelper;
import Controllers.DbController;
import Models.Tables.Societa;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.io.File;

public class CreatePdfExample {
    public static void main(String[] args) {
        // Creazione di un documento PDF
        String nomeFile = "prova.pdf";

        try {

            PDDocument document = new PDDocument();
            PDType0Font font = PDType0Font.load(document,
                    new File("src/resources/fonts/Helvetica-Bold-Font.ttf"));
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Creazione di un nuovo stream di contenuto per la pagina
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Impostazione del font
            contentStream.setFont(font, 12);

            // Aggiunta del contenuto al documento

            DbController dbc = new DbController();

            dbc.popolaListaSocieta();

            List<Societa> records = ClassHelper.getListSocieta(); // Sostituisci con la tua logica per ottenere i record
                                                                  // dalla tabella "societa"
            float yPosition = page.getMediaBox().getHeight() - 50;

            for (Societa record : records) {
                contentStream.beginText();
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText("ID: " + record.getIdSocieta() + "/n");
                contentStream.newLine();
                contentStream.showText("Nome: " + record.getEnte() + "/n");
                contentStream.newLine();
                contentStream.showText("Altro: " + record.getDescrizione() + "n");
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

            // Chiedi all'utente se vuole salvare il PDF
            int userChoice = JOptionPane.showConfirmDialog(null, "Vuoi salvare il PDF?", "Conferma",
                    JOptionPane.YES_NO_OPTION);

            if (userChoice == JOptionPane.YES_OPTION) {
                // Chiedi all'utente di selezionare un percorso per il salvataggio
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("File PDF", "pdf"));
                int returnValue = fileChooser.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {

                    File selectedFile = new File(fileChooser.getSelectedFile().toPath().toString() + ".pdf");             
                    
                    // Salva il PDF nel percorso selezionato
                    Files.copy(new File(nomeFile).toPath(), selectedFile.toPath());

                    JOptionPane.showMessageDialog(null, "Il PDF è stato salvato con successo.");

                    // Apertura del PDF con l'applicazione predefinita per la visualizzazione
                    Desktop.getDesktop().open(selectedFile);

                }
            }
        } catch (IOException e) {
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
