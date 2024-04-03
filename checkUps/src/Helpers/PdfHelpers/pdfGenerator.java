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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;

public class pdfGenerator {

    // Variabile statica per tenere traccia del numero di pagina corrente
    public static int currentPage = 0;
    public static int pagineTotali = 0;
    public static String urlLogoSocieta;
    public static String urlLogoCheckUps = "C:\\dev\\CheckUps\\CheckUpsGestionale\\checkUps\\src\\View\\images\\logo con sfondo transpartente.png"; // Da
                                                                                                                                                    // sistemare
    public static String revisione;

    // Metodo per generare un documento PDF per la valutazione dei rischi
    public static void stampaValutazioneRischi(Societa societa, UnitaLocale unitaLocale, List<Reparto> reparti,
            String nomeFile) {
        // Crea un nuovo documento con una dimensione personalizzata
        Document document = new Document(new Rectangle(1008, 612));
        try {
            urlLogoSocieta = societa.getLogoUrl();
            // Itera attraverso i reparti per la valutazione dei rischi
            for (Reparto reparto : reparti) {

                // Crea un'istanza di PdfWriter e imposta un piè di pagina personalizzato per il
                // documento
                revisione = reparto.getRevisione();
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nomeFile));
                writer.setPageEvent(new PdfFooter());

                // Apre il documento
                document.open();

                // Creazione pagina iniziale
                PdfPTable tableIniziale = new PdfPTable(3);
                tableIniziale.setWidthPercentage(100);
                PdfPCell societaCellIniziale = createCell("Società: " + societa.getNome(), 2, Font.BOLD, 10);
                societaCellIniziale.setBorderWidthRight(0);
                tableIniziale.addCell(societaCellIniziale);
                PdfPCell unitaLocaleCellIniziale = createCell("Unità locale: " + unitaLocale.getNome(), 1, Font.BOLD,
                        10);
                unitaLocaleCellIniziale.setBorderWidthLeft(0);
                tableIniziale.addCell(unitaLocaleCellIniziale);
                tableIniziale.setSpacingAfter(170f);
                // Aggiunge la tabella iniziale al documento e vado alla seconda pagina
                document.add(tableIniziale);

                PdfPTable tableBody = new PdfPTable(1);
                tableBody.setWidthPercentage(100);

                PdfPCell body = createCell(
                        "- Relazione Tecnica di Valutazione Rischi - \n in materia di sicurezza e salute sul lavoro, ex D.Lgs 81/2008 ",
                        1, Font.BOLD, 20);
                body.setHorizontalAlignment(Element.ALIGN_CENTER);
                body.setVerticalAlignment(Element.ALIGN_MIDDLE);
                body.setBorder(Rectangle.NO_BORDER);
                tableBody.addCell(body);
                tableBody.setSpacingAfter(0f);
                document.add(tableBody);

                PdfPTable tableUnderBody = new PdfPTable(2);
                tableUnderBody.setWidthPercentage(100);
                PdfPCell underBodyLeft = createCell(
                        "Il Datore di Lavoro: \n \n \nIl Medico Competente: ",
                        1, Font.BOLD, 10);

                PdfPCell underBodyRight = createCell(
                        "Il rappresentante dei lavoratori per la sicurezza: \n \n \n Il responsabile del servizio di prevenzione e protezione: \n \n \n in data: ",
                        1, Font.BOLD, 10);
                underBodyLeft.setHorizontalAlignment(Element.ALIGN_CENTER);
                underBodyLeft.setVerticalAlignment(Element.ALIGN_MIDDLE);
                underBodyLeft.setBorder(Rectangle.NO_BORDER);
                underBodyRight.setHorizontalAlignment(Element.ALIGN_CENTER);
                underBodyRight.setVerticalAlignment(Element.ALIGN_MIDDLE);
                underBodyRight.setBorder(Rectangle.NO_BORDER);
                tableUnderBody.addCell(underBodyLeft);
                tableUnderBody.addCell(underBodyRight);
                tableUnderBody.setSpacingAfter(150f);
                document.add(tableUnderBody);

                PdfPTable tableLogo = new PdfPTable(1);
                tableLogo.setWidthPercentage(100);

                PdfPCell logo = createCell(
                        societa.getLogoUrl(),
                        1, Font.BOLD, 20);
                logo.setHorizontalAlignment(Element.ALIGN_CENTER);
                logo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                logo.setBorder(Rectangle.NO_BORDER);
                tableLogo.addCell(logo);
                tableLogo.setSpacingAfter(30f);
                document.add(tableLogo);
                document.newPage();

                // Crea una tabella per visualizzare le informazioni sulla società, la sede ed
                // il reparto
                PdfPTable table = new PdfPTable(3);
                table.setWidthPercentage(100);

                // Popola la tabella con dettagli sulla società, la sede e il reparto
                PdfPCell societaCell = createCell("Società:", 1, Font.BOLD, 10);
                table.addCell(societaCell);
                PdfPCell unitaLocaleCell = createCell("Unità Locale:", 1, Font.BOLD, 10);
                table.addCell(unitaLocaleCell);
                PdfPCell repartiCell = createCell("Reparto:", 1, Font.BOLD, 10);
                table.addCell(repartiCell);
                PdfPCell societaValueCell = createCell(societa.getNome(), 1, Font.BOLD, 10);
                table.addCell(societaValueCell);
                PdfPCell unitaLocaleValueCell = createCell(unitaLocale.getNome(), 1, Font.BOLD, 10);
                table.addCell(unitaLocaleValueCell);
                PdfPCell repartiValueCell = createCell(reparto.getNome(), 1, Font.BOLD, 10);
                table.addCell(repartiValueCell);

                table.setSpacingAfter(10f);

                // Aggiunge la tabella al documento
                document.add(table);

                // Filtra i titoli in base al reparto
                List<Titolo> titoli = ModelListe.filtraTitoliDaReparto(reparti);
                int n = 1;

                // Itera attraverso i titoli
                for (Titolo titolo : titoli) {
                    // Filtra gli oggetti in base al titolo
                    List<Oggetto> oggetti = ModelListe.filtraOggettiDaTitolo(titolo.getId());
                    document.add(Chunk.NEWLINE);

                    int k = 0;

                    // Itera attraverso gli oggetti
                    for (Oggetto oggetto : oggetti) {
                        // Filtra le misure in base all'oggetto
                        List<Provvedimento> provvedimenti = ModelListe.filtraProvvedimentiDaOggetto(oggetto.getId());

                        // Salta se non ci sono misure per l'oggetto
                        if (provvedimenti.size() == 0) {
                            continue;
                        }

                        // Controlla se è la prima iterazione per evitare interruzioni di pagina non
                        // necessarie
                        if (k == 0) {
                            // Aggiunge una nuova pagina prima del titolo se non è la prima iterazione
                            if (n != 1) {
                                document.newPage();
                            }

                            // Crea un paragrafo per visualizzare le informazioni sul titolo
                            Paragraph titoloParagraph = new Paragraph();
                            titoloParagraph.setAlignment(Element.ALIGN_CENTER);
                            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                            Chunk titoloChunk = new Chunk("TITOLO: " + n + " " + titolo.getDescrizione(), boldFont);
                            titoloParagraph.add(titoloChunk);
                            titoloParagraph.setSpacingAfter(10f);

                            // Aggiunge le informazioni sul titolo al documento
                            document.add(titoloParagraph);
                            n++;
                        }
                        k++;

                        // Crea un paragrafo per visualizzare le informazioni sull'oggetto
                        Paragraph oggettoParagraph = new Paragraph();
                        oggettoParagraph.setAlignment(Element.ALIGN_LEFT);
                        Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                        oggettoParagraph.add(new Phrase("OGGETTO: " + oggetto.getNome(), boldFont));
                        oggettoParagraph.setSpacingAfter(10f);

                        // Aggiunge le informazioni sull'oggetto al documento
                        document.add(oggettoParagraph);

                        int j = 0;
                        // Itera attraverso le misure
                        for (Provvedimento provvedimento : provvedimenti) {
                            // Crea una tabella per visualizzare i dettagli della misura
                            PdfPTable provvedimentoTable = new PdfPTable(6);

                            // Visualizza gli header solo nella prima iterazione
                            if (j == 0) {
                                provvedimentoTable.addCell(createCell("RISCHIO ", 1, Font.BOLD, 10));
                                provvedimentoTable.addCell(createCell("STIMA (PxD = R) ", 1, Font.BOLD, 10));
                                provvedimentoTable.addCell(createCell("MISURE DI PREVENZIONE ", 3, Font.BOLD, 10));
                                provvedimentoTable.addCell(createCell("MANSIONI ESPOSTE ", 1, Font.BOLD, 10));
                            }
                            j++;

                            // Imposta le proprietà della tabella
                            provvedimentoTable.setWidthPercentage(100);

                            // Popola la tabella con i dettagli della misura
                            provvedimentoTable.addCell(
                                    createCell(replaceInvalidCharacters(provvedimento.getRischio()), 1, Font.BOLD, 10));
                            String stima = (provvedimento.getStimaP() + " x " + provvedimento.getStimaD() + " = "
                                    + provvedimento.getStimaR());
                            provvedimentoTable.addCell(createCell(stima, 1, Font.BOLD, 10));
                            provvedimentoTable.addCell(
                                    createCell(replaceInvalidCharacters(provvedimento.getNome().replace("\n", ""))
                                            .replace("\r", "").replace("€", " euro"), 3, Font.BOLD, 10));

                            provvedimentoTable
                                    .addCell(createCell(provvedimento.getSoggettiEsposti(), 1, Font.BOLD, 10));

                            // Aggiunge la tabella delle misure al documento
                            document.add(provvedimentoTable);
                        }
                    }
                }
            }
        } catch (DocumentException | FileNotFoundException e) {
            // Gestisce le eccezioni legate al documento
            e.printStackTrace();
        } finally {
            // Chiude il documento se è aperto
            if (document != null && document.isOpen()) {
                document.close();
            }
            stampaValutazioneRischiRender(societa, unitaLocale, reparti, nomeFile, currentPage);
        }

    }

    // METODO CHE FA LA STAMPA FINALE

    public static void stampaValutazioneRischiRender(Societa societa, UnitaLocale unitaLocale, List<Reparto> reparti,
            String nomeFile, int pagine) {
        // Crea un nuovo documento con una dimensione personalizzata
        Document document = new Document(new Rectangle(1008, 612));
        currentPage = 0;
        pagineTotali = pagine;
        try {
            // Itera attraverso i reparti per la valutazione dei rischi
            for (Reparto reparto : reparti) {
                // Crea un'istanza di PdfWriter e imposta un piè di pagina personalizzato per il
                // documento
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nomeFile));
                writer.setPageEvent(new PdfFooter());

                // Apre il documento
                document.open();

                // Creazione pagina iniziale
                PdfPTable tableIniziale = new PdfPTable(3);
                tableIniziale.setWidthPercentage(100);
                PdfPCell societaCellIniziale = createCell("Società: " + societa.getNome(), 2, Font.BOLD, 15);
                societaCellIniziale.setBorderWidthRight(0);
                tableIniziale.addCell(societaCellIniziale);
                PdfPCell unitaLocaleCellIniziale = createCell("Unità locale: " + unitaLocale.getNome(), 1, Font.BOLD,
                        15);
                unitaLocaleCellIniziale.setBorderWidthLeft(0);
                tableIniziale.addCell(unitaLocaleCellIniziale);
                tableIniziale.setSpacingAfter(70f);
                // Aggiunge la tabella iniziale al documento e vado alla seconda pagina
                document.add(tableIniziale);

                // LOGO CHECKUPS
                PdfPTable tableLogoChekups = new PdfPTable(1);
                tableLogoChekups.setWidthPercentage(100);
                Image logoCheckUpsImage = Image.getInstance(urlLogoCheckUps);
                // Imposta le dimensioni dell'immagine
                // logoCheckUpsImage.scaleToFit(1f, 1f);
                PdfPCell logoChekups = createImageCell(logoCheckUpsImage, 1);
                logoChekups.setHorizontalAlignment(Element.ALIGN_CENTER);
                logoChekups.setVerticalAlignment(Element.ALIGN_MIDDLE);
                logoChekups.setBorder(Rectangle.NO_BORDER);
                logoChekups.setFixedHeight(90f);
                tableLogoChekups.addCell(logoChekups);
                tableLogoChekups.setSpacingAfter(80f);
                document.add(tableLogoChekups);

                // SCRITTE SOTTO LOGO
                PdfPTable tableBody = new PdfPTable(1);
                tableBody.setWidthPercentage(100);
                PdfPCell body = createCell(
                        "- Relazione Tecnica di Valutazione Rischi - \n in materia di sicurezza e salute sul lavoro, ex D.Lgs 81/2008 ",
                        1, Font.BOLD, 20);
                body.setHorizontalAlignment(Element.ALIGN_CENTER);
                body.setVerticalAlignment(Element.ALIGN_MIDDLE);
                body.setBorder(Rectangle.NO_BORDER);
                tableBody.addCell(body);
                tableBody.setSpacingAfter(35f);
                document.add(tableBody);

                PdfPTable tableUnderBody = new PdfPTable(2);
                tableUnderBody.setWidthPercentage(100);
                PdfPCell underBodyLeft = createCell(
                        "Il Datore di Lavoro: \n \n \nIl Medico Competente: ",
                        1, Font.BOLD, 15);

                PdfPCell underBodyRight = createCell(
                        "Il rappresentante dei lavoratori per la sicurezza: \n \n \n Il responsabile del servizio di prevenzione e protezione: \n \n \n in data: ",
                        1, Font.BOLD, 15);
                underBodyLeft.setHorizontalAlignment(Element.ALIGN_CENTER);
                underBodyLeft.setVerticalAlignment(Element.ALIGN_MIDDLE);
                underBodyLeft.setBorder(Rectangle.NO_BORDER);
                underBodyRight.setHorizontalAlignment(Element.ALIGN_CENTER);
                underBodyRight.setVerticalAlignment(Element.ALIGN_MIDDLE);
                underBodyRight.setBorder(Rectangle.NO_BORDER);
                tableUnderBody.addCell(underBodyLeft);
                tableUnderBody.addCell(underBodyRight);
                tableUnderBody.setSpacingAfter(40f);
                document.add(tableUnderBody);

                PdfPTable tableLogo = new PdfPTable(1);
                tableLogo.setWidthPercentage(100);

                PdfPCell logo = createCell(
                        societa.getLogoUrl(),
                        1, Font.BOLD, 20);
                logo.setHorizontalAlignment(Element.ALIGN_CENTER);
                logo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                logo.setBorder(Rectangle.NO_BORDER);
                tableLogo.addCell(logo);
                tableLogo.setSpacingAfter(30f);
                document.add(tableLogo);

                document.newPage();

                // Crea una tabella per visualizzare le informazioni sulla società, la sede ed
                // il reparto
                PdfPTable table = new PdfPTable(3);
                table.setWidthPercentage(100);

                // Popola la tabella con dettagli sulla società, la sede e il reparto
                PdfPCell societaCell = createCell("Società:", 1, Font.BOLD, 10);
                table.addCell(societaCell);
                PdfPCell unitaLocaleCell = createCell("Unità Locale:", 1, Font.BOLD, 10);
                table.addCell(unitaLocaleCell);
                PdfPCell repartiCell = createCell("Reparto:", 1, Font.BOLD, 10);
                table.addCell(repartiCell);
                PdfPCell societaValueCell = createCell(societa.getNome(), 1, Font.BOLD, 10);
                table.addCell(societaValueCell);
                PdfPCell unitaLocaleValueCell = createCell(unitaLocale.getNome(), 1, Font.BOLD, 10);
                table.addCell(unitaLocaleValueCell);
                PdfPCell repartiValueCell = createCell(reparto.getNome(), 1, Font.BOLD, 10);
                table.addCell(repartiValueCell);

                table.setSpacingAfter(10f);

                // Aggiunge la tabella al documento
                document.add(table);

                // Filtra i titoli in base al reparto
                List<Titolo> titoli = ModelListe.filtraTitoliDaReparto(reparti);
                int n = 1;

                // Itera attraverso i titoli
                for (Titolo titolo : titoli) {
                    // Filtra gli oggetti in base al titolo
                    List<Oggetto> oggetti = ModelListe.filtraOggettiDaTitolo(titolo.getId());
                    document.add(Chunk.NEWLINE);

                    int k = 0;

                    // Itera attraverso gli oggetti
                    for (Oggetto oggetto : oggetti) {
                        // Filtra le misure in base all'oggetto
                        List<Provvedimento> provvedimenti = ModelListe.filtraProvvedimentiDaOggetto(oggetto.getId());

                        // Salta se non ci sono misure per l'oggetto
                        if (provvedimenti.size() == 0) {
                            continue;
                        }

                        // Controlla se è la prima iterazione per evitare interruzioni di pagina non
                        // necessarie
                        if (k == 0) {
                            // Aggiunge una nuova pagina prima del titolo se non è la prima iterazione
                            if (n != 1) {
                                document.newPage();
                            }

                            // Crea un paragrafo per visualizzare le informazioni sul titolo
                            Paragraph titoloParagraph = new Paragraph();
                            titoloParagraph.setAlignment(Element.ALIGN_CENTER);
                            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                            Chunk titoloChunk = new Chunk("TITOLO: " + n + " " + titolo.getDescrizione(), boldFont);
                            titoloParagraph.add(titoloChunk);
                            titoloParagraph.setSpacingAfter(10f);

                            // Aggiunge le informazioni sul titolo al documento
                            document.add(titoloParagraph);
                            n++;
                        }
                        k++;

                        // Crea un paragrafo per visualizzare le informazioni sull'oggetto
                        Paragraph oggettoParagraph = new Paragraph();
                        oggettoParagraph.setAlignment(Element.ALIGN_LEFT);
                        Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                        oggettoParagraph.add(new Phrase("OGGETTO: " + oggetto.getNome(), boldFont));
                        oggettoParagraph.setSpacingAfter(10f);

                        // Aggiunge le informazioni sull'oggetto al documento
                        document.add(oggettoParagraph);

                        int j = 0;
                        // Itera attraverso le misure
                        for (Provvedimento provvedimento : provvedimenti) {
                            // Crea una tabella per visualizzare i dettagli della misura
                            PdfPTable provvedimentoTable = new PdfPTable(6);

                            // Visualizza gli header solo nella prima iterazione
                            if (j == 0) {
                                provvedimentoTable.addCell(createCell("RISCHIO ", 1, Font.BOLD, 10));
                                provvedimentoTable.addCell(createCell("STIMA (PxD = R) ", 1, Font.BOLD, 10));
                                provvedimentoTable.addCell(createCell("MISURE DI PREVENZIONE ", 3, Font.BOLD, 10));
                                provvedimentoTable.addCell(createCell("MANSIONI ESPOSTE ", 1, Font.BOLD, 10));
                            }
                            j++;

                            // Imposta le proprietà della tabella
                            provvedimentoTable.setWidthPercentage(100);

                            // Popola la tabella con i dettagli della misura
                            provvedimentoTable.addCell(
                                    createCell(replaceInvalidCharacters(provvedimento.getRischio()), 1, Font.BOLD, 10));
                            String stima = (provvedimento.getStimaP() + " x " + provvedimento.getStimaD() + " = "
                                    + provvedimento.getStimaR());
                            provvedimentoTable.addCell(createCell(stima, 1, Font.BOLD, 10));
                            provvedimentoTable.addCell(
                                    createCell(replaceInvalidCharacters(provvedimento.getNome().replace("\n", ""))
                                            .replace("\r", "").replace("€", " euro"), 3, Font.BOLD, 10));
                            String soggettiEsposti = provvedimento.getSoggettiEsposti();
                            if (soggettiEsposti != null) {
                                soggettiEsposti = replaceInvalidCharacters(soggettiEsposti).replace("&lt;", "<")
                                        .replace("&gt;", ">");
                            }
                            provvedimentoTable.addCell(createCell(soggettiEsposti, 1, Font.BOLD, 10));

                            // Aggiunge la tabella delle misure al documento
                            document.add(provvedimentoTable);
                        }
                    }
                }
            }
        } catch (DocumentException | IOException e) {
            // Gestisce le eccezioni legate al documento
            e.printStackTrace();
        } finally {
            // Chiude il documento se è aperto
            if (document != null && document.isOpen()) {
                document.close();
            }
            pagineTotali = 0;
            pagine = 0;
            currentPage = 0;
        }
    }

    // Metodo di supporto per creare una PdfPCell con contenuto, colspan e stile del
    // carattere specificati
    private static PdfPCell createCell(String content, int colspan, int fontStyle, int size) {
        Font font = new Font(Font.FontFamily.HELVETICA, size, fontStyle);
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setColspan(colspan);
        return cell;
    }

    private static PdfPCell createImageCell(Image image, int colspan) {
        PdfPCell cell = new PdfPCell(image, true);
        cell.setColspan(colspan);
        return cell;
    }

    // Classe interna per definire un piè di pagina personalizzato per il documento
    // PDF
    private static class PdfFooter extends PdfPageEventHelper {
        public void onEndPage(PdfWriter writer, Document document) {
            // Crea una tabella per il piè di pagina con tre colonne
            PdfPTable table = new PdfPTable(3);
            currentPage++;
            // Formatta la data corrente
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = dateFormat.format(new Date());

            // Imposta il colore del bordo delle celle su bianco
            table.getDefaultCell().setBorderColor(BaseColor.WHITE);
            table.setTotalWidth(document.right() - document.left());
            table.getDefaultCell().setFixedHeight(20);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            // Aggiunge data, logo e numero di pagina al piè di pagina
            table.addCell(new Phrase("Revisione n. " + revisione + " del:  " + formattedDate));
            table.addCell(urlLogoSocieta);

            table.addCell("Pagina " + (currentPage) + " di " + (pagineTotali));

            // Scrive il piè di pagina nel documento
            table.writeSelectedRows(0, -1, document.left(), document.bottom(), writer.getDirectContent());
        }

    }

    // Metodo di supporto per sostituire caratteri non validi in un testo
    private static String replaceInvalidCharacters(String text) {
        // Sostituisci caratteri non validi con un punto interrogativo
        text = text.replaceAll("&apos;", "' ");

        // Aggiungi ulteriori operazioni di sostituzione se necessario

        return text;
    }
}
