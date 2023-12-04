package Helpers.PdfHelpers;

import Models.ModelListe;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class pdfGenerator {

    public static void stampaValutazioneRischi(Societa societa, UnitaLocale unitaLocale, List<Reparto> reparti,
            String nomeFile) {
        Document document = new Document(PageSize.A4.rotate());

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nomeFile));
            writer.setPageEvent(new PdfFooter());

            document.open();

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);

            PdfPCell societaCell = createCell("Società:", 1, Font.BOLD);
            table.addCell(societaCell);
            PdfPCell unitaLocaleCell = createCell("Unità Locale:", 1, Font.BOLD);
            table.addCell(unitaLocaleCell);
            PdfPCell repartiCell = createCell("Reparto:", 1, Font.BOLD);
            table.addCell(repartiCell);
            PdfPCell societaValueCell = createCell(societa.getNome(), 1, Font.BOLD);
            table.addCell(societaValueCell);

            
            PdfPCell unitaLocaleValueCell = createCell(unitaLocale.getNome(), 1, Font.BOLD);
            table.addCell(unitaLocaleValueCell);

            
            PdfPCell repartiValueCell = createCell(reparti.get(0).getNome(), 1, Font.BOLD);
            table.addCell(repartiValueCell);

            table.setSpacingAfter(10f);

            document.add(table);

            List<Titolo> titoli = ModelListe.filtraTitoliDaReparto(reparti);
            int n = 1;

            for (Titolo titolo : titoli) {
                List<Oggetto> oggetti = ModelListe.filtraOggettiDaTitolo(titolo.getId());
                document.add(Chunk.NEWLINE);

                int k = 0;

                for (Oggetto oggetto : oggetti) {
                    List<Provvedimento> provvedimenti = ModelListe.filtraProvvedimentiDaOggetto(oggetto.getId());

                    if (provvedimenti.size() == 0) {
                        continue;
                    }

                    if (k == 0) {
                        Paragraph titoloParagraph = new Paragraph();
                        titoloParagraph.setAlignment(Element.ALIGN_CENTER);
                        Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                        Chunk titoloChunk = new Chunk("TITOLO: " + n + " " + titolo.getDescrizione(), boldFont);
                        titoloParagraph.add(titoloChunk);
                        titoloParagraph.setSpacingAfter(10f);
                        document.add(titoloParagraph);
                        n++;
                    }
                    k++;

                    Paragraph oggettoParagraph = new Paragraph();
                    oggettoParagraph.setAlignment(Element.ALIGN_LEFT);
                    Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                    oggettoParagraph.add(new Phrase("OGGETTO: " + oggetto.getNome(), boldFont));
                    oggettoParagraph.setSpacingAfter(10f);
                    document.add(oggettoParagraph);

                    int j = 0;
                    for (Provvedimento provvedimento : provvedimenti) {
                        PdfPTable provvedimentoTable = new PdfPTable(6);

                        if (j == 0) {
                            provvedimentoTable.addCell(createCell("MISURE DI PREVENZIONE ", 3, Font.BOLD));
                            provvedimentoTable.addCell(createCell("RISCHIO ", 1, Font.BOLD));
                            provvedimentoTable.addCell(createCell("STIMA (PxD = R) ", 1, Font.BOLD));
                            provvedimentoTable.addCell(createCell("MANSIONI ESPOSTE ", 1, Font.BOLD));
                        }
                        j++;

                        provvedimentoTable.setWidthPercentage(100);

                        provvedimentoTable.addCell(createCell(provvedimento.getNome().replace("\n", "")
                                .replace("\r", "").replace("€", " euro"), 3, Font.BOLD));
                        provvedimentoTable.addCell(createCell(provvedimento.getRischio(), 1, Font.BOLD));
                        String stima = (provvedimento.getStimaP() + " x " + provvedimento.getStimaD() + " = "
                                + provvedimento.getStimaR());
                        provvedimentoTable.addCell(createCell(stima, 1, Font.BOLD));
                        provvedimentoTable.addCell(createCell(provvedimento.getSoggettiEsposti(), 1, Font.BOLD));

                        float remainingHeight = document.top() - document.bottom() - document.bottomMargin();
                        float provvedimentoTableHeight = provvedimentoTable.calculateHeights();
                        
                        if (remainingHeight < provvedimentoTableHeight) {
                            document.newPage();
                        }

                        document.add(provvedimentoTable);
                    }
                }
            }
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
    }

    private static PdfPCell createCell(String content, int colspan, int fontStyle) {
        Font font = new Font(Font.FontFamily.HELVETICA, 10, fontStyle);
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setColspan(colspan);
        return cell;
    }

    private static class PdfFooter extends PdfPageEventHelper {
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable table = new PdfPTable(1);
            // Crea una data formattata
SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
String formattedDate = dateFormat.format(new Date());
            table.setTotalWidth(document.right() - document.left());
            table.getDefaultCell().setFixedHeight(20);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(new Phrase("DATA "+formattedDate));
            table.writeSelectedRows(0, -1, document.left(), document.bottom(), writer.getDirectContent());
        }
    }
}