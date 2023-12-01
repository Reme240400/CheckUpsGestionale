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
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class pdfGenerator {

    public static void stampaValutazioneRischi(Societa societa, UnitaLocale unitaLocale, List<Reparto> reparti, String nomeFile) {

        // Creare un nuovo documento
        Document document = new Document(PageSize.A4.rotate());
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

            // Add Societa information
            PdfPTable societaTable = new PdfPTable(1);
            PdfPCell societaCell = createCell("Società:", 1);
            societaCell.setPhrase(new Phrase("Società:", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
            societaTable.addCell(societaCell);
            PdfPCell societaValueCell = createCell(societa.getNome(), 1);
            societaTable.addCell(societaValueCell);
            table.addCell(societaTable);

            // Add Unità Locale information
            PdfPTable unitaLocaleTable = new PdfPTable(1);
            PdfPCell unitaLocaleCell = createCell("Unità Locale:", 1);
            unitaLocaleCell.setPhrase(new Phrase("Unità Locale:", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
            unitaLocaleTable.addCell(unitaLocaleCell);
            PdfPCell unitaLocaleValueCell = createCell(unitaLocale.getNome(), 1);
            unitaLocaleTable.addCell(unitaLocaleValueCell);
            table.addCell(unitaLocaleTable);

            // Add Reparti information
            PdfPTable repartiTable = new PdfPTable(1);
            PdfPCell repartiCell = createCell("Reparti:", 1);
            repartiCell.setPhrase(new Phrase("Reparti:", FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
            repartiTable.addCell(repartiCell);
            PdfPCell repartiValueCell = createCell(reparti.get(0).getNome(), 1);
            repartiTable.addCell(repartiValueCell);
            table.addCell(repartiTable);

            table.setSpacingAfter(10f); // Imposta la spaziatura dopo il titolo a 10 punti

            // Add table to document
            document.add(table);

            // Add Titoli and related content
            List<Titolo> titoli = ModelListe.filtraTitoliDaReparto(reparti);
            int n = 1;
            // Loop sui titoli
            for (Titolo titolo : titoli) {
                List<Oggetto> oggetti = ModelListe.filtraOggettiDaTitolo(titolo.getId());
                document.add(Chunk.NEWLINE);
                if (oggetti.size() == 0) {
                    continue;
                }

                int k = 0;
                // Loop sugli oggetti
                for (Oggetto oggetto : oggetti) {

                    List<Provvedimento> provvedimenti = ModelListe.filtraProvvedimentiDaOggetto(oggetto.getId());
                    // Create a table for each Oggetto
                    if (provvedimenti.size() == 0) {
                        continue;
                    }
                    if (k == 0) {
                        // Add Titolo information
                        Paragraph titoloParagraph = new Paragraph();
                        titoloParagraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                        Chunk titoloChunk = new Chunk("TITOLO: " + n + " " + titolo.getDescrizione(),
                                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
                        titoloParagraph.add(titoloChunk);
                        titoloParagraph.setSpacingAfter(10f);
                        document.add(titoloParagraph);
                        n++;
                    }
                    k++;

                    // Add Oggetto information
                    Paragraph oggettoParagraph = new Paragraph();
                    oggettoParagraph.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
                    oggettoParagraph.setFont(FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
                    oggettoParagraph.add(new Phrase("OGGETTO: " + oggetto.getNome()));
                    oggettoParagraph.setSpacingAfter(10f); // Imposta la spaziatura dopo il titolo a 10 punti
                    document.add(oggettoParagraph);

                    int j = 0;
                    for (Provvedimento provvedimento : provvedimenti) {

                        PdfPTable provvedimentoTable = new PdfPTable(6);
                        if (j == 0) {

                            provvedimentoTable.addCell(createCell("MISURE DI PREVENZIONE ", 3));
                            provvedimentoTable.addCell(createCell("RISCHIO ", 1));
                            provvedimentoTable.addCell(createCell("STIMA (PxD = R) ", 1));
                            provvedimentoTable.addCell(createCell("MANSIONI ESPOSTE ", 1));
                        }
                        j++;

                        provvedimentoTable.setWidthPercentage(100);

                        // Add Misure di Prevenzione

                        provvedimentoTable.addCell(createCell(provvedimento.getNome()
                                .replace("\n", "").replace("\r", "").replace("€", " euro"), 3));

                        // Add Rischio

                        provvedimentoTable.addCell(createCell(provvedimento.getRischio(), 1));

                        // Add Stima
                        String stima = (provvedimento.getStimaP() + " x " + provvedimento.getStimaD() + " = "
                                + provvedimento.getStimaR());
                        provvedimentoTable.addCell(createCell(stima, 1));

                        // Add Mansioni Esposte

                        provvedimentoTable.addCell(createCell(provvedimento.getSoggettiEsposti(), 1));

                        // Add the Provvedimento table to the document
                        document.add(provvedimentoTable);
                    }
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
