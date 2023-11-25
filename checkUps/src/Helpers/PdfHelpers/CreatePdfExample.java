package Helpers.PdfHelpers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import Controllers.ClassHelper;
import Controllers.ControllerDb;
import Models.ModelListe;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import javafx.scene.text.Font;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CreatePdfExample {

    private static int calculateMaxRowsPerPage(PDPage page, float margin) {
        PDRectangle mediaBox = page.getMediaBox();
        float rowHeight = 50; // Regola questa altezza a seconda delle tue esigenze
        return (int) ((mediaBox.getHeight() - 2 * margin) / rowHeight);
    }

    public static void stampaValutazioneRischi(Societa societa, UnitaLocale unitaLocale, List<Reparto> reparti, String nomeFile) {

        // le filtro
        // Societa societa = ClassHelper.getListSocieta().stream().filter(s -> s.getId() == idSocieta).findFirst().get();
        // UnitaLocale unitaLocale = ClassHelper.getListUnitaLocale().stream().filter(s -> s.getId() == idUnitaLocale)
        //         .findFirst().get();
        // reparti reparti = ClassHelper.getListreparti().stream().filter(r -> r.getId() == idreparti).findFirst().get();

        List<Titolo> titoli = ModelListe.filtraTitoliDaReparto(reparti);

        System.out.println("ID_SOCIETA: " + societa.getId());
        System.out.println("ID UNITA_LOCALE: " + unitaLocale.getId());
        System.out.println("ID reparti: " + reparti.get(0).getId());
        try {
            PDDocument document = new PDDocument();
            PDType0Font font = PDType0Font.load(document,
                    new File(
                            "C:\\dev\\ProgettoCheckUp\\CheckUpsGestionale\\checkUps\\src\\resources\\fonts\\Helvetica-Bold-Font.ttf"));
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Creazione di un nuovo stream di contenuto per la pagina
            PDPageContentStream contentStream = new PDPageContentStream(document,
                    page);
            // Impostazione del font
            contentStream.setFont(font, 8);
            int maxRowsPerPage = calculateMaxRowsPerPage(page, 50);
            float yPosition = page.getMediaBox().getHeight() - 50;
            yPosition = page.getMediaBox().getHeight() - 50;

            yPosition = yPosition + 10;
            // costruisco le prime due righe della tabella
            // queste celle conterranno società, unità locale e reparti
            // riga superiore
            contentStream.moveTo(10, yPosition + 5);
            contentStream.lineTo(575, yPosition + 5);
            contentStream.stroke();
            // riga inferiore
            contentStream.moveTo(10, yPosition - 10);
            contentStream.lineTo(575, yPosition - 10);
            contentStream.stroke();
            // colonne
            contentStream.moveTo(10, yPosition + 5);
            contentStream.lineTo(10, yPosition - 10);
            contentStream.stroke();

            contentStream.beginText();
            contentStream.newLineAtOffset(15, yPosition - 5);
            contentStream.showText("Società:  " + societa.getNome());
            contentStream.endText();

            contentStream.moveTo(190, yPosition + 5);
            contentStream.lineTo(190, yPosition - 10);
            contentStream.stroke();

            contentStream.beginText();
            contentStream.newLineAtOffset(195, yPosition - 5);
            contentStream.showText("Unità Locale:  " + unitaLocale.getNome());
            contentStream.endText();

            contentStream.moveTo(380, yPosition + 5);
            contentStream.lineTo(380, yPosition - 10);
            contentStream.stroke();

            contentStream.beginText();
            contentStream.newLineAtOffset(385, yPosition - 5);
            contentStream.showText("reparti:  " + reparti.get(0).getNome());
            contentStream.endText();

            contentStream.moveTo(575, yPosition + 5);
            contentStream.lineTo(575, yPosition - 10);
            contentStream.stroke();

            contentStream.setFont(font, 6);

            int n = 1;
            // ciclo titoli
            for (Titolo titolo : titoli) {
                // stampo il titolo
                yPosition = yPosition - 20;
                contentStream.setFont(font, 8);
                contentStream.beginText();
                contentStream.newLineAtOffset(220, yPosition - 5);
                contentStream.showText("TITOLO: " + n + " " + titolo.getDescrizione());
                contentStream.endText();

                yPosition = yPosition - 20;
                n++;

                List<Oggetto> oggetti = ModelListe.filtraOggettiDaTitolo(titolo.getId());
                // ciclo oggetti del titolo
                for (Oggetto oggetto : oggetti) {
                    contentStream.setFont(font, 8);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(20, yPosition - 5);
                    contentStream.showText("OGGETTO: " + oggetto.getNome());
                    contentStream.endText();
                    contentStream.beginText();
                    contentStream.newLineAtOffset(405, yPosition - 5);
                    contentStream.setFont(font, 5);
                    contentStream.showText("STIMA (PxD = R)");
                    contentStream.endText();
                    contentStream.setFont(font, 8);
                    yPosition = yPosition - 15;

                    List<Provvedimento> provvedimenti = ModelListe.filtraProvvedimentiDaOggetto(oggetto.getId());
                    for (Provvedimento provvedimento : provvedimenti) {
                        contentStream.setFont(font, 3);
                        // ciclo provvedimenti dell'oggetto
                        if (provvedimento.getNome().length() > 155) {
                            // Controllo per creazione di numero giusto di pagine
                            if (yPosition < maxRowsPerPage) {
                                contentStream.close();
                                page = new PDPage(PDRectangle.A4);
                                document.addPage(page);
                                contentStream = new PDPageContentStream(document, page);
                                contentStream.setFont(font, 3);
                                yPosition = page.getMediaBox().getHeight() - 50;
                            }
                            // costruisco tabella
                            // riga superiore
                            contentStream.moveTo(10, yPosition + 5);
                            contentStream.lineTo(575, yPosition + 5);
                            contentStream.stroke();
                            // riga inferiore
                            contentStream.moveTo(10, yPosition - 10);
                            contentStream.lineTo(575, yPosition - 10);
                            contentStream.stroke();
                            // colonne
                            contentStream.moveTo(10, yPosition + 5);
                            contentStream.lineTo(10, yPosition - 10);
                            contentStream.stroke();

                            contentStream.moveTo(340, yPosition + 5);
                            contentStream.lineTo(340, yPosition - 10);
                            contentStream.stroke();

                            contentStream.moveTo(400, yPosition + 5);
                            contentStream.lineTo(400, yPosition - 10);
                            contentStream.stroke();

                            contentStream.moveTo(450, yPosition + 5);
                            contentStream.lineTo(450, yPosition - 10);
                            contentStream.stroke();

                            contentStream.moveTo(575, yPosition + 5);
                            contentStream.lineTo(575, yPosition - 10);
                            contentStream.stroke();

                            String nome = provvedimento.getNome();
                            String firstPart = "";
                            String secondPart = "";

                            if (nome.length() < 165) {
                                firstPart = nome;
                            } else {
                                int spaceIndex = nome.indexOf(" ", 155);
                                if (spaceIndex != -1) {
                                    firstPart = nome.substring(0, spaceIndex);
                                    secondPart = nome.substring(spaceIndex + 1);
                                } else {
                                    firstPart = nome.substring(0, 155);
                                    secondPart = nome.substring(155);
                                }
                            }

                            contentStream.beginText();
                            contentStream.newLineAtOffset(15, yPosition);
                            contentStream.showText("MISURE DI PREVENZIONE E DI PROTEZIONE: "
                                    + firstPart.replace("\n", "").replace("\r", ""));
                            contentStream.endText();

                            contentStream.beginText();
                            contentStream.newLineAtOffset(15, yPosition - 5);
                            contentStream.showText(secondPart.replace("\n", "").replace("\r", ""));
                            contentStream.endText();

                            contentStream.beginText();
                            contentStream.newLineAtOffset(345, yPosition);
                            contentStream.showText("RISCHIO: " + provvedimento.getRischio());
                            contentStream.endText();
                            contentStream.beginText();
                            contentStream.newLineAtOffset(405, yPosition);
                            contentStream.showText(provvedimento.getStimaP() + " x " + provvedimento.getStimaD() + " = "
                                    + provvedimento.getStimaR());
                            contentStream.endText();

                            contentStream.beginText();
                            contentStream.newLineAtOffset(455, yPosition);
                            contentStream.showText("MANSIONI ESPOSTE: " + provvedimento.getSoggettiEsposti());
                            contentStream.endText();
                            contentStream.beginText();
                            contentStream.newLine();
                            contentStream.endText();

                            yPosition -= 15; // Spaziatura tra le righe
                            // caso in cui devo andare a capo
                        } else {
                            // Controllo per creazione di numero giusto di pagine
                            if (yPosition < maxRowsPerPage) {
                                contentStream.close();
                                page = new PDPage(PDRectangle.A4);
                                document.addPage(page);
                                contentStream = new PDPageContentStream(document, page);
                                contentStream.setFont(font, 3);
                                yPosition = page.getMediaBox().getHeight() - 50;
                            }
                            // costruisco tabella
                            // riga superiore
                            contentStream.moveTo(10, yPosition + 5);
                            contentStream.lineTo(575, yPosition + 5);
                            contentStream.stroke();
                            // riga inferiore
                            contentStream.moveTo(10, yPosition - 5);
                            contentStream.lineTo(575, yPosition - 5);
                            contentStream.stroke();
                            // colonne
                            contentStream.moveTo(10, yPosition + 5);
                            contentStream.lineTo(10, yPosition - 5);
                            contentStream.stroke();

                            contentStream.moveTo(340, yPosition + 5);
                            contentStream.lineTo(340, yPosition - 5);
                            contentStream.stroke();

                            contentStream.moveTo(400, yPosition + 5);
                            contentStream.lineTo(400, yPosition - 5);
                            contentStream.stroke();

                            contentStream.moveTo(450, yPosition + 5);
                            contentStream.lineTo(450, yPosition - 5);
                            contentStream.stroke();

                            contentStream.moveTo(575, yPosition + 5);
                            contentStream.lineTo(575, yPosition - 5);
                            contentStream.stroke();

                            contentStream.beginText();
                            contentStream.newLineAtOffset(15, yPosition);
                            contentStream.showText(
                                    "MISURE DI PREVENZIONE E DI PROTEZIONE: " + provvedimento.getNome()
                                            .replace("\n", "").replace("\r", "").replace("€", " euro"));

                            contentStream.endText();
                            contentStream.beginText();
                            contentStream.newLineAtOffset(345, yPosition);
                            contentStream.showText("RISCHIO: " + provvedimento.getRischio());
                            contentStream.endText();
                            contentStream.beginText();
                            contentStream.newLineAtOffset(405, yPosition);
                            contentStream.showText(provvedimento.getStimaP() + " x " + provvedimento.getStimaD() + " = "
                                    + provvedimento.getStimaR());
                            contentStream.endText();
                            contentStream.beginText();
                            contentStream.newLineAtOffset(455, yPosition);
                            contentStream.showText("MANSIONI ESPOSTE: " + provvedimento.getSoggettiEsposti());
                            contentStream.endText();
                            contentStream.beginText();
                            contentStream.newLine();
                            contentStream.endText();

                            yPosition -= 10; // Spaziatura tra le righe

                        }

                    }

                }

            }
            
            contentStream.close();
            // Visualizzazione del PDF
            document.save(nomeFile);
            document.close();
            // Apertura del PDF con l'applicazione predefinita per la visualizzazione
            Desktop.getDesktop().open(new File(nomeFile));
        } catch (IOException e) {
            System.out.println(e);
        }
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
                contentStream.showText("ID: " + record.getId());
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

            List<Provvedimento> recordsProvvedimento = ClassHelper.getListProvvedimento();
            int maxRowsPerPage = calculateMaxRowsPerPage(page1, 50);
            float yPosition = page1.getMediaBox().getHeight() - 50;
            yPosition = page1.getMediaBox().getHeight() - 50;

            for (Provvedimento record : recordsProvvedimento) {

                if (record.getNome().length() > 225) {
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
                    contentStream1.moveTo(10, yPosition - 10);
                    contentStream1.lineTo(575, yPosition - 10);
                    contentStream1.stroke();
                    // colonne
                    contentStream1.moveTo(10, yPosition + 5);
                    contentStream1.lineTo(10, yPosition - 10);
                    contentStream1.stroke();

                    contentStream1.moveTo(340, yPosition + 5);
                    contentStream1.lineTo(340, yPosition - 10);
                    contentStream1.stroke();

                    contentStream1.moveTo(490, yPosition + 5);
                    contentStream1.lineTo(490, yPosition - 10);
                    contentStream1.stroke();

                    contentStream1.moveTo(575, yPosition + 5);
                    contentStream1.lineTo(575, yPosition - 10);
                    contentStream1.stroke();

                    String nome = record.getNome();
                    String firstPart = "";
                    String secondPart = "";

                    if (nome.length() <= 215) {
                        firstPart = nome;
                    } else {
                        int spaceIndex = nome.indexOf(" ", 215);
                        if (spaceIndex != -1) {
                            firstPart = nome.substring(0, spaceIndex);
                            secondPart = nome.substring(spaceIndex + 1);
                        } else {
                            firstPart = nome.substring(0, 215);
                            secondPart = nome.substring(215);
                        }
                    }

                    contentStream1.beginText();
                    contentStream1.newLineAtOffset(15, yPosition);
                    contentStream1.showText("NOME: " + firstPart.replace("\n", "").replace("\r", ""));
                    contentStream1.endText();

                    contentStream1.beginText();
                    contentStream1.newLineAtOffset(26, yPosition - 5);
                    contentStream1.showText(secondPart.replace("\n", "").replace("\r", ""));
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

                    yPosition -= 15; // Spaziatura tra le righe
                    // caso in cui devo andare a capo
                } else {
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
                    contentStream1.showText(
                            "NOME: " + record.getNome().replace("\n", "").replace("\r", "").replace("€", " euro"));

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

                    yPosition -= 10; // Spaziatura tra le righe

                }
            }

            contentStream1.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

}
