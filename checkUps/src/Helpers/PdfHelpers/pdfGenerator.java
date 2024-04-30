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
    public static String urlLogoCheckUpsPiccolo = "checkUps\\src\\resources\\logo\\LOGOCheckUpPiccolo.jpg";
    public static String revisione;

    // Metodo per generare un documento PDF per la valutazione dei rischi
    public static void stampaValutazioneRischi(Societa societa, UnitaLocale unitaLocale, List<Reparto> reparti,
            String nomeFile) {
        // Importazione dei font custom
        FontFactory.register("checkUps\\src\\resources\\fonts\\arial\\ARIAL.TTF",
                "ARIAL");
        FontFactory.register("checkUps\\src\\resources\\fonts\\arial\\ARIALBD.TTF",
                "ARIAL_BOLD");
        FontFactory.register("checkUps\\src\\resources\\fonts\\arial\\ARIALBLACKITALIC.TTF",
                "ARIAL_ITALIC");
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
            PdfPTable tableIniziale = new PdfPTable(8);
            tableIniziale.setWidthPercentage(100);
            PdfPCell societaCellIniziale = createCell("Società/Ente: ", 1,
                    FontFactory.getFont("ARIAL", 16));
            tableIniziale.addCell(societaCellIniziale);
            PdfPCell nomeSocietaCellIniziale = createCell(societa.getNome(), 3,
                    FontFactory.getFont("ARIAL_BOLD", 16));
            tableIniziale.addCell(nomeSocietaCellIniziale);
            PdfPCell unitaLocaleCellIniziale = createCell("Un. Produttiva: ", 1,
                    FontFactory.getFont("ARIAL", 16));
            tableIniziale.addCell(unitaLocaleCellIniziale);
            PdfPCell nomeUnitaLocaleCellIniziale = createCell(unitaLocale.getNome(), 3,
                    FontFactory.getFont("ARIAL_BOLD", 16));
            tableIniziale.addCell(nomeUnitaLocaleCellIniziale);
            tableIniziale.setSpacingAfter(60f);
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
            logoChekups.setFixedHeight(100f);
            tableLogoChekups.addCell(logoChekups);
            tableLogoChekups.setSpacingAfter(60f);
            document.add(tableLogoChekups);
            // SCRITTE SOTTO LOGO
            PdfPTable tableBody = new PdfPTable(1);
            tableBody.setWidthPercentage(100);
            PdfPCell body = createCell(
                    "Relazione Tecnica di Valutazione Rischi - \n in materia di salute e sicurezza sul lavoro D.Lgs. n. 81/2008 e s.m. e i. \n \n Firmano in data: ________ ",
                    1, FontFactory.getFont("ARIAL", 16));
            body.setHorizontalAlignment(Element.ALIGN_CENTER);
            body.setVerticalAlignment(Element.ALIGN_MIDDLE);
            body.setBorder(Rectangle.NO_BORDER);
            tableBody.addCell(body);
            tableBody.setSpacingAfter(35f);
            document.add(tableBody);
            // Tabella contenente le firme
            PdfPTable tableUnderBody = new PdfPTable(2);
            PdfPCell underBodyLeft = createCell(
                    "Il Datore di Lavoro \n \n \n \n \n \n",
                    1, FontFactory.getFont("ARIAL_ITALIC", 10));
            PdfPCell underBodyRight = createCell(
                    "Il Rappresentante dei lavoratori per la sicurezza (RLS) \n \n \n \n \n \n",
                    1, FontFactory.getFont("ARIAL_ITALIC", 10));
            underBodyLeft.setHorizontalAlignment(Element.ALIGN_CENTER);
            underBodyLeft.setVerticalAlignment(Element.ALIGN_MIDDLE);
            underBodyRight.setHorizontalAlignment(Element.ALIGN_CENTER);
            underBodyRight.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableUnderBody.addCell(underBodyLeft);
            tableUnderBody.addCell(underBodyRight);
            tableUnderBody.setWidthPercentage(65);
            underBodyLeft = createCell(
                    "Il Responsabile del servizio di Prevenzione e Protezione \n \n \n \n \n \n",
                    1, FontFactory.getFont("ARIAL_ITALIC", 10));
            underBodyRight = createCell(
                    "Il Medico Competente \n \n \n \n \n \n",
                    1, FontFactory.getFont("ARIAL_ITALIC", 10));
            underBodyLeft.setHorizontalAlignment(Element.ALIGN_CENTER);
            underBodyLeft.setVerticalAlignment(Element.ALIGN_MIDDLE);
            underBodyRight.setHorizontalAlignment(Element.ALIGN_CENTER);
            underBodyRight.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableUnderBody.addCell(underBodyLeft);
            tableUnderBody.addCell(underBodyRight);
            tableUnderBody.setSpacingAfter(40f);
            document.add(tableUnderBody);

            if (societa.hasImage()) {
                logoSocieta = javafxImageToPdfImage(societa.getLogoImage());
            }
            document.newPage();
            int i = 0;
            // Itera attraverso i reparti per la valutazione dei rischi
            // Itera attraverso i reparti per la valutazione dei rischi
            for (Reparto reparto : reparti) {
                revisione = reparto.getRevisione();
                if (i != 0) {
                    document.newPage();
                }
                i++;
                // Crea una tabella per visualizzare le informazioni sulla società, la sede ed
                // il reparto
                PdfPTable table = new PdfPTable(21);
                table.setWidthPercentage(100);

                // Popola la tabella con dettagli sulla società, la sede e il reparto
                PdfPCell societaCell = createCell("Società:", 1, FontFactory.getFont("ARIAL", 10));
                societaCell.setBorder(Rectangle.NO_BORDER); 
                table.addCell(societaCell);
                societaCell = createCell(societa.getNome(), 5, FontFactory.getFont("ARIAL_BOLD", 10));
                societaCell.setBorderWidthTop(0);
                societaCell.setBorderWidthLeft(0);
                societaCell.setBorderWidthRight(0);
                table.addCell(societaCell);
                PdfPCell unitaLocaleCell = createCell("    Un. Produttiva:", 2,
                        FontFactory.getFont("ARIAL", 10));
                unitaLocaleCell.setBorder(Rectangle.NO_BORDER);
                table.addCell(unitaLocaleCell);
                unitaLocaleCell = createCell(unitaLocale.getNome(), 6,
                        FontFactory.getFont("ARIAL_BOLD", 10));
                unitaLocaleCell.setBorderWidthTop(0);
                unitaLocaleCell.setBorderWidthLeft(0);
                unitaLocaleCell.setBorderWidthRight(0);
                table.addCell(unitaLocaleCell);
                PdfPCell repartiCell = createCell("    Reparto:", 2, FontFactory.getFont("ARIAL", 10));
                repartiCell.setBorder(Rectangle.NO_BORDER);
                table.addCell(repartiCell);
                repartiCell = createCell(reparto.getNome(), 5, FontFactory.getFont("ARIAL_BOLD", 10));
                repartiCell.setBorderWidthTop(0);
                repartiCell.setBorderWidthLeft(0);
                repartiCell.setBorderWidthRight(0);
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
                    if (currentPosition < 430) {
                        document.newPage();
                    }
                    // Filtra gli oggetti in base al titolo
                    List<Oggetto> oggetti = ModelListe.filtraOggettiDaTitolo(titolo.getId());
                    document.add(Chunk.NEWLINE);

                    int k = 0;
                    // Itera attraverso gli oggetti
                    for (Oggetto oggetto : oggetti) {
                        currentPosition = writer.getVerticalPosition(false);
                        if (currentPosition < 150 && oggetti.size() != k) {
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
                            //Titolo
                            PdfPTable tableTitolo = new PdfPTable(1);
                            // Creazione della cella per il titolo
                            PdfPCell cellaTitolo = new PdfPCell();
                            // Creazione del testo per la prima parte (Arial Bold)
                            Chunk parte1 = new Chunk("Titolo: ", FontFactory.getFont("ARIAL", 15));
                            // Creazione del testo per la seconda parte (Arial)
                            Chunk parte2 = new Chunk(n + " " + titolo.getDescrizione(), FontFactory.getFont("ARIAL_BOLD", 15));
                            // Imposta il testo completo nella cella
                            Phrase titoloPhrase = new Phrase();
                            // Aggiunta delle due parti alla cella
                            titoloPhrase.add(parte1);
                            titoloPhrase.add(parte2);
                            cellaTitolo.setPhrase(titoloPhrase);                          
                            cellaTitolo.setVerticalAlignment(Element.ALIGN_CENTER);
                            cellaTitolo.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cellaTitolo.setPaddingBottom(5);
                            tableTitolo.setSpacingAfter(10f);
                            tableTitolo.addCell(cellaTitolo);
                            tableTitolo.setWidthPercentage(30);
                            document.add(tableTitolo);
                            n++;
                        }
                        k++;
                        // Crea un paragrafo per visualizzare le informazioni sull'oggetto
                        Paragraph oggettoParagraph = new Paragraph();
                        oggettoParagraph.setAlignment(Element.ALIGN_LEFT);
                        Font font = FontFactory.getFont("ARIAL", 15);
                        oggettoParagraph.add(new Phrase("Oggetto: " + oggetto.getNome(), font));
                        oggettoParagraph.setSpacingAfter(10f);
                        // Aggiunge le informazioni sull'oggetto al documento
                        document.add(oggettoParagraph);

                        int j = 0;
                        // Itera attraverso le misure
                        for (Provvedimento provvedimento : provvedimenti) {
                            // Crea una tabella per visualizzare i dettagli della misura
                            PdfPTable provvedimentoTable = new PdfPTable(10);
                            currentPosition = writer.getVerticalPosition(false);
                            if (currentPosition < 90) {
                                document.newPage();
                            }
                           
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
            PdfPTable tableIniziale = new PdfPTable(8);
            tableIniziale.setWidthPercentage(100);
            PdfPCell societaCellIniziale = createCell("Società/Ente: ", 1,
                    FontFactory.getFont("ARIAL", 16));
            tableIniziale.addCell(societaCellIniziale);
            PdfPCell nomeSocietaCellIniziale = createCell(societa.getNome(), 3,
                    FontFactory.getFont("ARIAL_BOLD", 16));
            tableIniziale.addCell(nomeSocietaCellIniziale);
            PdfPCell unitaLocaleCellIniziale = createCell("Un. Produttiva: ", 1,
                    FontFactory.getFont("ARIAL", 16));
            tableIniziale.addCell(unitaLocaleCellIniziale);
            PdfPCell nomeUnitaLocaleCellIniziale = createCell(unitaLocale.getNome(), 3,
                    FontFactory.getFont("ARIAL_BOLD", 16));
            tableIniziale.addCell(nomeUnitaLocaleCellIniziale);
            tableIniziale.setSpacingAfter(60f);
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
            logoChekups.setFixedHeight(100f);
            tableLogoChekups.addCell(logoChekups);
            tableLogoChekups.setSpacingAfter(60f);
            document.add(tableLogoChekups);
            // SCRITTE SOTTO LOGO
            PdfPTable tableBody = new PdfPTable(1);
            tableBody.setWidthPercentage(100);
            PdfPCell body = createCell(
                    "Relazione Tecnica di Valutazione Rischi - \n in materia di salute e sicurezza sul lavoro D.Lgs. n. 81/2008 e s.m. e i. \n \n Firmano in data: ________ ",
                    1, FontFactory.getFont("ARIAL", 16));
            body.setHorizontalAlignment(Element.ALIGN_CENTER);
            body.setVerticalAlignment(Element.ALIGN_MIDDLE);
            body.setBorder(Rectangle.NO_BORDER);
            tableBody.addCell(body);
            tableBody.setSpacingAfter(35f);
            document.add(tableBody);
            // Tabella contenente le firme
            PdfPTable tableUnderBody = new PdfPTable(2);
            PdfPCell underBodyLeft = createCell(
                    "Il Datore di Lavoro \n \n \n \n \n \n",
                    1, FontFactory.getFont("ARIAL_ITALIC", 10));
            PdfPCell underBodyRight = createCell(
                    "Il Rappresentante dei lavoratori per la sicurezza (RLS) \n \n \n \n \n \n",
                    1, FontFactory.getFont("ARIAL_ITALIC", 10));
            underBodyLeft.setHorizontalAlignment(Element.ALIGN_CENTER);
            underBodyLeft.setVerticalAlignment(Element.ALIGN_MIDDLE);
            underBodyRight.setHorizontalAlignment(Element.ALIGN_CENTER);
            underBodyRight.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableUnderBody.addCell(underBodyLeft);
            tableUnderBody.addCell(underBodyRight);
            tableUnderBody.setWidthPercentage(65);
            underBodyLeft = createCell(
                    "Il Responsabile del servizio di Prevenzione e Protezione \n \n \n \n \n \n",
                    1, FontFactory.getFont("ARIAL_ITALIC", 10));
            underBodyRight = createCell(
                    "Il Medico Competente \n \n \n \n \n \n",
                    1, FontFactory.getFont("ARIAL_ITALIC", 10));
            underBodyLeft.setHorizontalAlignment(Element.ALIGN_CENTER);
            underBodyLeft.setVerticalAlignment(Element.ALIGN_MIDDLE);
            underBodyRight.setHorizontalAlignment(Element.ALIGN_CENTER);
            underBodyRight.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableUnderBody.addCell(underBodyLeft);
            tableUnderBody.addCell(underBodyRight);
            tableUnderBody.setSpacingAfter(40f);
            document.add(tableUnderBody);

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
                PdfPTable table = new PdfPTable(21);
                table.setWidthPercentage(100);

                // Popola la tabella con dettagli sulla società, la sede e il reparto
                PdfPCell societaCell = createCell("Società:", 1, FontFactory.getFont("ARIAL", 10));
                societaCell.setBorder(Rectangle.NO_BORDER); 
                table.addCell(societaCell);
                societaCell = createCell(societa.getNome(), 5, FontFactory.getFont("ARIAL_BOLD", 10));
                societaCell.setBorderWidthTop(0);
                societaCell.setBorderWidthLeft(0);
                societaCell.setBorderWidthRight(0);
                table.addCell(societaCell);
                PdfPCell unitaLocaleCell = createCell("    Un. Produttiva:", 2,
                        FontFactory.getFont("ARIAL", 10));
                unitaLocaleCell.setBorder(Rectangle.NO_BORDER);
                table.addCell(unitaLocaleCell);
                unitaLocaleCell = createCell(unitaLocale.getNome(), 6,
                        FontFactory.getFont("ARIAL_BOLD", 10));
                unitaLocaleCell.setBorderWidthTop(0);
                unitaLocaleCell.setBorderWidthLeft(0);
                unitaLocaleCell.setBorderWidthRight(0);
                table.addCell(unitaLocaleCell);
                PdfPCell repartiCell = createCell("    Reparto:", 2, FontFactory.getFont("ARIAL", 10));
                repartiCell.setBorder(Rectangle.NO_BORDER);
                table.addCell(repartiCell);
                repartiCell = createCell(reparto.getNome(), 5, FontFactory.getFont("ARIAL_BOLD", 10));
                repartiCell.setBorderWidthTop(0);
                repartiCell.setBorderWidthLeft(0);
                repartiCell.setBorderWidthRight(0);
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
                    if (currentPosition < 430) {
                        document.newPage();
                    }
                    // Filtra gli oggetti in base al titolo
                    List<Oggetto> oggetti = ModelListe.filtraOggettiDaTitolo(titolo.getId());
                    document.add(Chunk.NEWLINE);

                    int k = 0;
                    // Itera attraverso gli oggetti
                    for (Oggetto oggetto : oggetti) {
                        currentPosition = writer.getVerticalPosition(false);
                        if (currentPosition < 150 && oggetti.size() != k) {
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
                            //Titolo
                            PdfPTable tableTitolo = new PdfPTable(1);
                            // Creazione della cella per il titolo
                            PdfPCell cellaTitolo = new PdfPCell();
                            // Creazione del testo per la prima parte (Arial Bold)
                            Chunk parte1 = new Chunk("Titolo: ", FontFactory.getFont("ARIAL", 15));
                            // Creazione del testo per la seconda parte (Arial)
                            Chunk parte2 = new Chunk(n + " " + titolo.getDescrizione(), FontFactory.getFont("ARIAL_BOLD", 15));
                            // Imposta il testo completo nella cella
                            Phrase titoloPhrase = new Phrase();
                            // Aggiunta delle due parti alla cella
                            titoloPhrase.add(parte1);
                            titoloPhrase.add(parte2);
                            cellaTitolo.setPhrase(titoloPhrase);                          
                            cellaTitolo.setVerticalAlignment(Element.ALIGN_CENTER);
                            cellaTitolo.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cellaTitolo.setPaddingBottom(5);
                            tableTitolo.setSpacingAfter(10f);
                            tableTitolo.addCell(cellaTitolo);
                            tableTitolo.setWidthPercentage(30);
                            document.add(tableTitolo);
                            n++;
                        }
                        k++;
                        // Crea un paragrafo per visualizzare le informazioni sull'oggetto
                        Paragraph oggettoParagraph = new Paragraph();
                        oggettoParagraph.setAlignment(Element.ALIGN_LEFT);
                        Font font = FontFactory.getFont("ARIAL", 15);
                        oggettoParagraph.add(new Phrase("Oggetto: " + oggetto.getNome(), font));
                        oggettoParagraph.setSpacingAfter(10f);
                        // Aggiunge le informazioni sull'oggetto al documento
                        document.add(oggettoParagraph);

                        int j = 0;
                        // Itera attraverso le misure
                        for (Provvedimento provvedimento : provvedimenti) {
                            // Crea una tabella per visualizzare i dettagli della misura
                            PdfPTable provvedimentoTable = new PdfPTable(10);
                            currentPosition = writer.getVerticalPosition(false);
                            if (currentPosition < 90) {
                                document.newPage();
                            }
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

            // Aggiunge data, logo e numero di pagina al piè di pagina
            Phrase phrase = new Phrase();
            phrase.add(new Chunk("Redatto a cura di: ")); // Aggiunge il testo
            try {
                phrase.add(new Chunk(Image.getInstance(urlLogoCheckUps), 0, -10)); // Aggiunge l'immagine
            } catch (Exception e) {
                System.out.println("Errore nel caricamento del logo piccolo al piè di pagina");
            }
            PdfPCell cellLogoCheckUps = new PdfPCell(phrase);
            cellLogoCheckUps.setBorderColor(BaseColor.WHITE);
            cellLogoCheckUps.setVerticalAlignment(Element.ALIGN_CENTER);
            cellLogoCheckUps.setPaddingBottom(10f);
            table.addCell(cellLogoCheckUps);
            if (logoSocieta != null) {
                // logoSocieta.scaleToFit(50,50);
                table.addCell(logoSocieta);
            } else {
                table.addCell("");
            }
            PdfPCell cellPagineRev = new PdfPCell(new Phrase("Rev N. " + revisione + " del: " + formattedDate
                    + " - Pag. " + (paginaAttuale) + " di " + (pagineTotali)));
            cellPagineRev.setVerticalAlignment(Element.ALIGN_BOTTOM); // Imposta l'allineamento verticale in basso
            cellPagineRev.setBorderColor(BaseColor.WHITE);
            cellPagineRev.setVerticalAlignment(Element.ALIGN_CENTER);
            cellPagineRev.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellPagineRev.setPaddingBottom(10f);
            table.addCell(cellPagineRev);
            // Scrive il piè di pagina nel documento
            table.writeSelectedRows(0, -1, document.left(), document.bottom() + 4, writer.getDirectContent());
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
