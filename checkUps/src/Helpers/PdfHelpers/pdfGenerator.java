package Helpers.PdfHelpers;

import Models.ModelListe;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class pdfGenerator {

    public static void stampaValutazioneRischi(Societa societa, UnitaLocale unitaLocale, List<Reparto> reparti,
            String nomeFile) {

        // Creare un nuovo documento
        Document document = new Document();
        // Imposta l'altezza massima della pagina

        // Specificare il percorso del file PDF che si desidera creare
        try {
            PdfWriter.getInstance(document, new FileOutputStream(nomeFile));
        } catch (DocumentException | FileNotFoundException e) {
            System.out.println("Errore durante creazione pdf");
        }
        try {
            document.open();
            // Add content to the document
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);

            // Add Societa, Unità Locale, and Reparti information
            addTableCell(table, "Società:", societa.getNome());
            addTableCell(table, "Unità Locale:", unitaLocale.getNome());
            addTableCell(table, "Reparti:", reparti.get(0).getNome());

            // Add table to document
            document.add(table);

            // Add Titoli and related content
            List<Titolo> titoli = ModelListe.filtraTitoliDaReparto(reparti);
            int n = 1;
            // Loop sui titoli
            for (Titolo titolo : titoli) {
                document.add(Chunk.NEWLINE);

                // Add Titolo information
                Paragraph titoloParagraph = new Paragraph();
                titoloParagraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                titoloParagraph.add(new Phrase("TITOLO: " + n + " " + titolo.getDescrizione()));
                document.add(titoloParagraph);
                n++;

                List<Oggetto> oggetti = ModelListe.filtraOggettiDaTitolo(titolo.getId());

                // Loop sugli oggetti
                for (Oggetto oggetto : oggetti) {
                    // Loop sui provvedimenti
                    List<Provvedimento> provvedimenti = ModelListe.filtraProvvedimentiDaOggetto(oggetto.getId());
                    // Create a table for each Oggetto
                    if(provvedimenti.size()==0){
                        continue;
                    }
                    PdfPTable oggettoTable = new PdfPTable(1);
                    oggettoTable.setWidthPercentage(100);

                    oggettoTable.addCell(createCell("OGGETTO: " + oggetto.getNome(), 1));

                    
                    for (Provvedimento provvedimento : provvedimenti) {
                        PdfPTable provvedimentoTable = new PdfPTable(2);
                        provvedimentoTable.setWidthPercentage(100);

                        // Add Misure di Prevenzione
                        provvedimentoTable.addCell(createCell("Misure di Prevenzione", 1));
                        provvedimentoTable.addCell(createCell(provvedimento.getNome()
                                .replace("\n", "").replace("\r", "").replace("€", " euro"), 1));

                        // Add Rischio
                        provvedimentoTable.addCell(createCell("Rischio", 1));
                        provvedimentoTable.addCell(createCell(provvedimento.getRischio(), 1));

                        // Add Mansioni Esposte
                        provvedimentoTable.addCell(createCell("Mansioni Esposte", 1));
                        provvedimentoTable.addCell(createCell(provvedimento.getSoggettiEsposti(), 1));

                        // Add the Provvedimento table to the Oggetto table
                        oggettoTable.addCell(createCell("", 1)); // Empty cell for spacing
                        oggettoTable.addCell(provvedimentoTable);
                    }

                    // Add the Oggetto table to the document
                    document.add(oggettoTable);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            // Chiudere il documento (il salvataggio è implicito)
            if (document != null && document.isOpen()) {
                document.close();
            }
            // apriFilePDF(nomeFile);
        }
    }

    private static PdfPCell createCell(String content, int colspan) {
        PdfPCell cell = new PdfPCell(new Phrase(content));
        cell.setColspan(colspan);
        return cell;
    }

    private static void addTableCell(PdfPTable table, String header, String content) {
        PdfPCell cell1 = new PdfPCell(new Phrase(header));
        PdfPCell cell2 = new PdfPCell(new Phrase(content));

        cell1.setBorder(Rectangle.OUT_BOTTOM);
        cell2.setBorder(Rectangle.OUT_BOTTOM);

        table.addCell(cell1);
        table.addCell(cell2);
    }

    private static void apriFilePDF(String percorsoFilePDF) {
        try {
            File filePDF = new File(percorsoFilePDF);

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                Desktop desktop = Desktop.getDesktop();

                if (filePDF.exists()) {
                    desktop.open(filePDF);
                } else {
                    System.out.println("Il file non esiste: " + percorsoFilePDF);
                }
            } else {
                System.out.println(
                        "Apertura del desktop non supportata. Prova ad aprire il file manualmente: " + percorsoFilePDF);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
