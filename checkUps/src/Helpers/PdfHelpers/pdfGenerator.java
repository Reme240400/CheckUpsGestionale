package Helpers.PdfHelpers;

import Models.ModelListe;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
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
    public static int paginaAttuale;
    public static int pagineTotali;
    public static com.itextpdf.text.Image logoSocieta;
    public static String urlLogoCheckUps = "checkUps\\src\\resources\\logo\\LOGOCheckUp.jpg";
    public static String revisione;

    // Metodo per generare un documento PDF per la valutazione dei rischi
    public static void stampaValutazioneRischi(Societa societa, UnitaLocale unitaLocale, List<Reparto> reparti,
            String nomeFile) {
        FontFactory.register("checkUps\\src\\resources\\fonts\\arial\\ARIAL.TTF",
                "ARIAL");
        // Crea un nuovo documento con una dimensione personalizzata
        Document document = new Document(new Rectangle(1008, 612));
        paginaAttuale = 0;
        pagineTotali = 0;
        revisione = reparti.get(0).getRevisione();
        try {
            // Crea un'istanza di PdfWriter e imposta un piè di pagina personalizzato per il
            // documento
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nomeFile));
            writer.setPageEvent(new PdfFooter());
            // Apre il documento
            document.open();
            // Creazione pagina iniziale
            PdfPTable tableIniziale = new PdfPTable(3);
            tableIniziale.setWidthPercentage(100);
            PdfPCell societaCellIniziale = createCell("Società: " + societa.getNome(), 2,
                    FontFactory.getFont("ARIAL", 20));
            societaCellIniziale.setBorderWidthRight(0);
            societaCellIniziale.setBorderWidthLeft(0);
            societaCellIniziale.setBorderWidthBottom(0);
            societaCellIniziale.setBorderWidthTop(0);
            tableIniziale.addCell(societaCellIniziale);
            PdfPCell unitaLocaleCellIniziale = createCell("Unità locale: " + unitaLocale.getNome(), 1,
                    FontFactory.getFont("ARIAL", 20));
            unitaLocaleCellIniziale.setBorderWidthLeft(0);
            unitaLocaleCellIniziale.setBorderWidthRight(0);
            unitaLocaleCellIniziale.setBorderWidthBottom(0);
            unitaLocaleCellIniziale.setBorderWidthTop(0);
            tableIniziale.addCell(unitaLocaleCellIniziale);
            tableIniziale.setSpacingAfter(70f);
            // Aggiunge la tabella iniziale al documento e vado alla seconda pagina
            document.add(tableIniziale);

            // LOGO CHECKUPS
            PdfPTable tableLogoChekups = new PdfPTable(1);
            tableLogoChekups.setWidthPercentage(100);
            Image logoCheckUpsImage = Image.getInstance(urlLogoCheckUps);
            // Imposta le dimensioni dell'immagine
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
                    1, FontFactory.getFont("ARIAL", 20));
            body.setHorizontalAlignment(Element.ALIGN_CENTER);
            body.setVerticalAlignment(Element.ALIGN_MIDDLE);
            body.setBorder(Rectangle.NO_BORDER);
            tableBody.addCell(body);
            tableBody.setSpacingAfter(35f);
            document.add(tableBody);

            PdfPTable tableUnderBody = new PdfPTable(2);
            tableUnderBody.setWidthPercentage(100);
            PdfPCell underBodyLeft = createCell(
                    "Il Datore di Lavoro: _________________\n \n \nIl Medico Competente: _________________",
                    1, FontFactory.getFont("ARIAL", 10));

            PdfPCell underBodyRight = createCell(
                    "Il rappresentante dei lavoratori per la sicurezza: _________________\n \n \n Il responsabile del servizio di prevenzione e protezione: _________________\n \n \n in data: ___________",
                    1, FontFactory.getFont("ARIAL", 10));
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

            if (societa.hasImage()) {
                logoSocieta = javafxImageToPdfImage(societa.getLogoImage());
            }
            document.newPage();
            int i = 0;
            // Itera attraverso i reparti per la valutazione dei rischi
            for (Reparto reparto : reparti) {
                revisione = reparto.getRevisione();
                if (i != 0) {
                    document.newPage();
                }
                i++;
                // Crea una tabella per visualizzare le informazioni sulla società, la sede ed
                // il reparto
                PdfPTable table = new PdfPTable(3);
                table.setWidthPercentage(100);

                // Popola la tabella con dettagli sulla società, la sede e il reparto
                PdfPCell societaCell = createCell("Società: " + societa.getNome(), 1, FontFactory.getFont("ARIAL", 15));
                societaCell.setPaddingBottom(5); // Imposta lo spazio inferiore della cella
                table.addCell(societaCell);
                PdfPCell unitaLocaleCell = createCell("Unità Locale: " + unitaLocale.getNome(), 1,
                        FontFactory.getFont("ARIAL", 15));
                table.addCell(unitaLocaleCell);
                PdfPCell repartiCell = createCell("Reparto: " + reparto.getNome(), 1, FontFactory.getFont("ARIAL", 15));
                table.addCell(repartiCell);

                table.setSpacingAfter(10f);

                // Aggiunge la tabella al documento
                document.add(table);

                // Filtra i titoli in base al reparto
                List<Titolo> titoli = ModelListe.filtraTitoliDaReparto(reparti);
                int n = 1;

                // Itera attraverso i titoli
                for (Titolo titolo : titoli) {
                    // All'inizio del ciclo per gli oggetti e i titoli, registra la posizione
                    // corrente nella pagina
                    float currentPosition = writer.getVerticalPosition(false);
                    currentPosition = writer.getVerticalPosition(false);
                    if (currentPosition < 120) {
                        document.newPage();
                    }
                    // Filtra gli oggetti in base al titolo
                    List<Oggetto> oggetti = ModelListe.filtraOggettiDaTitolo(titolo.getId());
                    document.add(Chunk.NEWLINE);

                    int k = 0;
                    // Itera attraverso gli oggetti
                    for (Oggetto oggetto : oggetti) {
                        currentPosition = writer.getVerticalPosition(false);
                        if (currentPosition < 120 && oggetti.size() != k) {
                            document.newPage();
                        }
                        // Filtra le misure in base all'oggetto
                        List<Provvedimento> provvedimenti = ModelListe.filtraProvvedimentiDaOggetto(oggetto.getId());
                        // Salta se non ci sono misure per l'oggetto
                        if (provvedimenti.size() == 0) {
                            continue;
                        }
                        // Controlla se è la prima iterazione per evitare interruzioni di pagina non
                        // necessarie
                        if (k == 0) {
                            // Crea un paragrafo per visualizzare le informazioni sul titolo
                            Paragraph titoloParagraph = new Paragraph();
                            titoloParagraph.setAlignment(Element.ALIGN_CENTER);
                            Font font = FontFactory.getFont("ARIAL", 15);
                            Chunk titoloChunk = new Chunk("TITOLO: " + n + " " + titolo.getDescrizione(), font);
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
                        Font font = FontFactory.getFont("ARIAL", 15);
                        oggettoParagraph.add(new Phrase("OGGETTO: " + oggetto.getNome(), font));
                        oggettoParagraph.setSpacingAfter(10f);
                        // Aggiunge le informazioni sull'oggetto al documento
                        document.add(oggettoParagraph);

                        int j = 0;
                        // Itera attraverso le misure
                        for (Provvedimento provvedimento : provvedimenti) {
                            // Crea una tabella per visualizzare i dettagli della misura
                            PdfPTable provvedimentoTable = new PdfPTable(10);

                            // Visualizza gli header solo nella prima iterazione
                            if (j == 0) {
                                PdfPCell rischioCell = createCell("RISCHIO ", 2, FontFactory.getFont("ARIAL", 10));
                                rischioCell.setBorderWidth(2f);
                                provvedimentoTable.addCell(rischioCell);

                                PdfPCell stimaCell = createCell("STIMA (PxD = R) ", 1,
                                        FontFactory.getFont("ARIAL", 10));
                                stimaCell.setBorderWidth(2f);
                                provvedimentoTable.addCell(stimaCell);

                                PdfPCell misureCell = createCell("MISURE DI PREVENZIONE ", 5,
                                        FontFactory.getFont("ARIAL", 10));
                                misureCell.setBorderWidth(2f);
                                provvedimentoTable.addCell(misureCell);

                                PdfPCell mansioniCell = createCell("MANSIONI ESPOSTE ", 2,
                                        FontFactory.getFont("ARIAL", 10));
                                mansioniCell.setBorderWidth(2f);
                                provvedimentoTable.addCell(mansioniCell);

                            }
                            j++;

                            // Imposta le proprietà della tabella
                            provvedimentoTable.setWidthPercentage(100);

                            // Popola la tabella con i dettagli della misura
                            provvedimentoTable.addCell(
                                    createCell(replaceInvalidCharacters(provvedimento.getRischio()), 2,
                                            FontFactory.getFont("ARIAL", 10)));
                            String stima = (provvedimento.getStimaP() + " x " + provvedimento.getStimaD() + " = "
                                    + provvedimento.getStimaR());
                            provvedimentoTable.addCell(createCell(stima, 1, FontFactory.getFont("ARIAL", 10)));
                            provvedimentoTable.addCell(
                                    createCell(replaceInvalidCharacters(provvedimento.getNome().replace("\n", ""))
                                            .replace("\r", "").replace("€", " euro"), 5,
                                            FontFactory.getFont("ARIAL", 10)));
                            String soggettiEsposti = provvedimento.getSoggettiEsposti();
                            if (soggettiEsposti != null) {
                                soggettiEsposti = replaceInvalidCharacters(soggettiEsposti).replace("&lt;", "<")
                                        .replace("&gt;", ">");
                            }
                            provvedimentoTable
                                    .addCell(createCell(soggettiEsposti, 2, FontFactory.getFont("ARIAL", 10)));

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
            pagineTotali = paginaAttuale;
            paginaAttuale = 0;
            stampaValutazioneRischiRender(societa, unitaLocale, reparti, nomeFile);
        }

    }

    // METODO CHE FA LA STAMPA FINALE
    public static void stampaValutazioneRischiRender(Societa societa, UnitaLocale unitaLocale, List<Reparto> reparti,
            String nomeFile) {
        // Crea un nuovo documento con una dimensione personalizzata
        Document document = new Document(new Rectangle(1008, 612));
        paginaAttuale = 0;
        revisione = reparti.get(0).getRevisione();
        try {
            // Crea un'istanza di PdfWriter e imposta un piè di pagina personalizzato per il
            // documento
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nomeFile));
            writer.setPageEvent(new PdfFooter());
            // Apre il documento
            document.open();
            // Creazione pagina iniziale
            PdfPTable tableIniziale = new PdfPTable(3);
            tableIniziale.setWidthPercentage(100);
            PdfPCell societaCellIniziale = createCell("Società: " + societa.getNome(), 2,
                    FontFactory.getFont("ARIAL", 20));
            societaCellIniziale.setBorderWidthRight(0);
            societaCellIniziale.setBorderWidthLeft(0);
            societaCellIniziale.setBorderWidthBottom(0);
            societaCellIniziale.setBorderWidthTop(0);
            tableIniziale.addCell(societaCellIniziale);
            PdfPCell unitaLocaleCellIniziale = createCell("Unità locale: " + unitaLocale.getNome(), 1,
                    FontFactory.getFont("ARIAL", 20));
            unitaLocaleCellIniziale.setBorderWidthLeft(0);
            unitaLocaleCellIniziale.setBorderWidthRight(0);
            unitaLocaleCellIniziale.setBorderWidthBottom(0);
            unitaLocaleCellIniziale.setBorderWidthTop(0);
            tableIniziale.addCell(unitaLocaleCellIniziale);
            tableIniziale.setSpacingAfter(70f);
            // Aggiunge la tabella iniziale al documento e vado alla seconda pagina
            document.add(tableIniziale);

            // LOGO CHECKUPS
            PdfPTable tableLogoChekups = new PdfPTable(1);
            tableLogoChekups.setWidthPercentage(100);
            Image logoCheckUpsImage = Image.getInstance(urlLogoCheckUps);
            // Imposta le dimensioni dell'immagine
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
                    1, FontFactory.getFont("ARIAL", 20));
            body.setHorizontalAlignment(Element.ALIGN_CENTER);
            body.setVerticalAlignment(Element.ALIGN_MIDDLE);
            body.setBorder(Rectangle.NO_BORDER);
            tableBody.addCell(body);
            tableBody.setSpacingAfter(35f);
            document.add(tableBody);

            PdfPTable tableUnderBody = new PdfPTable(2);
            tableUnderBody.setWidthPercentage(100);
            PdfPCell underBodyLeft = createCell(
                    "Il Datore di Lavoro: _________________\n \n \nIl Medico Competente: _________________",
                    1, FontFactory.getFont("ARIAL", 10));

            PdfPCell underBodyRight = createCell(
                    "Il rappresentante dei lavoratori per la sicurezza: _________________\n \n \n Il responsabile del servizio di prevenzione e protezione: _________________\n \n \n in data: ___________",
                    1, FontFactory.getFont("ARIAL", 10));
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

            if (societa.hasImage()) {
                logoSocieta = javafxImageToPdfImage(societa.getLogoImage());
            }
            document.newPage();
            int i = 0;
            // Itera attraverso i reparti per la valutazione dei rischi
            for (Reparto reparto : reparti) {
                revisione = reparto.getRevisione();
                if (i != 0) {
                    document.newPage();
                }
                i++;
                // Crea una tabella per visualizzare le informazioni sulla società, la sede ed
                // il reparto
                PdfPTable table = new PdfPTable(3);
                table.setWidthPercentage(100);

                // Popola la tabella con dettagli sulla società, la sede e il reparto
                PdfPCell societaCell = createCell("Società: " + societa.getNome(), 1, FontFactory.getFont("ARIAL", 15));
                societaCell.setPaddingBottom(5); // Imposta lo spazio inferiore della cella
                table.addCell(societaCell);
                PdfPCell unitaLocaleCell = createCell("Unità Locale: " + unitaLocale.getNome(), 1,
                        FontFactory.getFont("ARIAL", 15));
                table.addCell(unitaLocaleCell);
                PdfPCell repartiCell = createCell("Reparto: " + reparto.getNome(), 1, FontFactory.getFont("ARIAL", 15));
                table.addCell(repartiCell);

                table.setSpacingAfter(10f);

                // Aggiunge la tabella al documento
                document.add(table);

                // Filtra i titoli in base al reparto
                List<Titolo> titoli = ModelListe.filtraTitoliDaReparto(reparti);
                int n = 1;

                // Itera attraverso i titoli
                for (Titolo titolo : titoli) {
                    // All'inizio del ciclo per gli oggetti e i titoli, registra la posizione
                    // corrente nella pagina
                    float currentPosition = writer.getVerticalPosition(false);
                    currentPosition = writer.getVerticalPosition(false);
                    if (currentPosition < 120) {
                        document.newPage();
                    }
                    // Filtra gli oggetti in base al titolo
                    List<Oggetto> oggetti = ModelListe.filtraOggettiDaTitolo(titolo.getId());
                    document.add(Chunk.NEWLINE);

                    int k = 0;
                    // Itera attraverso gli oggetti
                    for (Oggetto oggetto : oggetti) {
                        currentPosition = writer.getVerticalPosition(false);
                        if (currentPosition < 120 && oggetti.size() != k) {
                            document.newPage();
                        }
                        // Filtra le misure in base all'oggetto
                        List<Provvedimento> provvedimenti = ModelListe.filtraProvvedimentiDaOggetto(oggetto.getId());
                        // Salta se non ci sono misure per l'oggetto
                        if (provvedimenti.size() == 0) {
                            continue;
                        }
                        // Controlla se è la prima iterazione per evitare interruzioni di pagina non
                        // necessarie
                        if (k == 0) {
                            // Crea un paragrafo per visualizzare le informazioni sul titolo
                            Paragraph titoloParagraph = new Paragraph();
                            titoloParagraph.setAlignment(Element.ALIGN_CENTER);
                            Font font = FontFactory.getFont("ARIAL", 15);
                            Chunk titoloChunk = new Chunk("TITOLO: " + n + " " + titolo.getDescrizione(), font);
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
                        Font font = FontFactory.getFont("ARIAL", 15);
                        oggettoParagraph.add(new Phrase("OGGETTO: " + oggetto.getNome(), font));
                        oggettoParagraph.setSpacingAfter(10f);
                        // Aggiunge le informazioni sull'oggetto al documento
                        document.add(oggettoParagraph);

                        int j = 0;
                        // Itera attraverso le misure
                        for (Provvedimento provvedimento : provvedimenti) {
                            // Crea una tabella per visualizzare i dettagli della misura
                            PdfPTable provvedimentoTable = new PdfPTable(10);

                            // Visualizza gli header solo nella prima iterazione
                            if (j == 0) {
                                PdfPCell rischioCell = createCell("RISCHIO ", 2, FontFactory.getFont("ARIAL", 10));
                                rischioCell.setBorderWidth(2f);
                                provvedimentoTable.addCell(rischioCell);

                                PdfPCell stimaCell = createCell("STIMA (PxD = R) ", 1,
                                        FontFactory.getFont("ARIAL", 10));
                                stimaCell.setBorderWidth(2f);
                                provvedimentoTable.addCell(stimaCell);

                                PdfPCell misureCell = createCell("MISURE DI PREVENZIONE ", 5,
                                        FontFactory.getFont("ARIAL", 10));
                                misureCell.setBorderWidth(2f);
                                provvedimentoTable.addCell(misureCell);

                                PdfPCell mansioniCell = createCell("MANSIONI ESPOSTE ", 2,
                                        FontFactory.getFont("ARIAL", 10));
                                mansioniCell.setBorderWidth(2f);
                                provvedimentoTable.addCell(mansioniCell);

                            }
                            j++;

                            // Imposta le proprietà della tabella
                            provvedimentoTable.setWidthPercentage(100);

                            // Popola la tabella con i dettagli della misura
                            provvedimentoTable.addCell(
                                    createCell(replaceInvalidCharacters(provvedimento.getRischio()), 2,
                                            FontFactory.getFont("ARIAL", 10)));
                            String stima = (provvedimento.getStimaP() + " x " + provvedimento.getStimaD() + " = "
                                    + provvedimento.getStimaR());
                            provvedimentoTable.addCell(createCell(stima, 1, FontFactory.getFont("ARIAL", 10)));
                            provvedimentoTable.addCell(
                                    createCell(replaceInvalidCharacters(provvedimento.getNome().replace("\n", ""))
                                            .replace("\r", "").replace("€", " euro"), 5,
                                            FontFactory.getFont("ARIAL", 10)));
                            String soggettiEsposti = provvedimento.getSoggettiEsposti();
                            if (soggettiEsposti != null) {
                                soggettiEsposti = replaceInvalidCharacters(soggettiEsposti).replace("&lt;", "<")
                                        .replace("&gt;", ">");
                            }
                            provvedimentoTable
                                    .addCell(createCell(soggettiEsposti, 2, FontFactory.getFont("ARIAL", 10)));

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
            paginaAttuale = 0;
            pagineTotali = 0;
        }
    }

    // Metodo di supporto per creare una PdfPCell con contenuto, colspan e stile del
    // carattere specificati
    private static PdfPCell createCell(String content, int colspan, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setColspan(colspan);
        return cell;
    }

    private static PdfPCell createImageCell(Image image, int colspan) {
        PdfPCell cell = new PdfPCell(image, true);
        cell.setColspan(colspan);
        return cell;
    }

    // Metodo per convertire un'immagine JavaFX in un'immagine iTextPDF
    private static Image javafxImageToPdfImage(javafx.scene.image.Image javafxImage) {
        // Converti l'immagine JavaFX in un'immagine AWT
        BufferedImage awtImage = SwingFXUtils.fromFXImage(javafxImage, null);

        // Converti l'immagine AWT in un'immagine iTextPDF
        Image pdfImage = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(awtImage, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            pdfImage = Image.getInstance(imageInByte);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfImage;
    }

    // Classe interna per definire un piè di pagina personalizzato per il documento
    // PDF
    private static class PdfFooter extends PdfPageEventHelper {
        public void onStartPage(PdfWriter writer, Document document) {
            // Crea una tabella per il piè di pagina con tre colonne
            PdfPTable table = new PdfPTable(3);
            paginaAttuale++;
            // Formatta la data corrente
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = dateFormat.format(new Date());
            // Imposta il colore del bordo delle celle su bianco
            table.getDefaultCell().setBorderColor(BaseColor.WHITE);
            table.setTotalWidth(document.right() - document.left());
            table.getDefaultCell().setFixedHeight(41);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            if (paginaAttuale != 1) {
                 // Aggiunge data, logo e numero di pagina al piè di pagina
                PdfPCell cellRevisione = new PdfPCell(new Phrase("Revisione n. " + revisione + " del:  " + formattedDate));
                cellRevisione.setBorderColor(BaseColor.WHITE);
                cellRevisione.setVerticalAlignment(Element.ALIGN_BOTTOM);
                cellRevisione.setPaddingBottom(10f);
                table.addCell(cellRevisione);
                if(logoSocieta!=null){
                    table.addCell(logoSocieta);
                }else{
                    table.addCell("");
                }
            } else {
                table.addCell("");
                table.addCell("");
            }
            PdfPCell cellPagine = new PdfPCell(new Phrase("Pagina " + (paginaAttuale) + " di " + (pagineTotali)));
            cellPagine.setVerticalAlignment(Element.ALIGN_BOTTOM); // Imposta l'allineamento verticale in basso
            cellPagine.setBorderColor(BaseColor.WHITE);
            cellPagine.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellPagine.setPaddingBottom(10f);
            table.addCell(cellPagine);
            // Scrive il piè di pagina nel documento
            table.writeSelectedRows(0, -1, document.left(), document.bottom()+4, writer.getDirectContent());
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
