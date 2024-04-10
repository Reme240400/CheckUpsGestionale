package Models;

import java.io.IOException;
import java.net.URL;

import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import Models.others.TipoCreazionePagina;
import View.Controllers.Home;
import View.Controllers.ValutaRischi;
import View.Controllers.Creazione.Creazione;
import View.Controllers.Creazione.CreazioneOggetto;
import View.Controllers.Creazione.CreazioneProvvedimento;
import View.Controllers.Creazione.CreazioneReparto;
import View.Controllers.Creazione.CreazioneSocieta;
import View.Controllers.Creazione.CreazioneTitolo;
import View.Controllers.Creazione.CreazioneUnitaLocale;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class ModelPaths {

    private ModelValutaRischi modelValutaRischi = new ModelValutaRischi();

    private String loadedFXMLs = null;
    private StackPane stackPane = null;
    private StackPane stackPaneHome = null;
    private StackPane stackPaneModificaR = null;
    private StackPane stackPaneModificaO = null;
    private StackPane stackPaneCrea = null;

    public void setMainStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public StackPane getMainStackPane() {
        return this.stackPane;
    }

    public void setStackPaneHome(StackPane stackPane) {
        this.stackPaneHome = stackPane;
    }

    public StackPane getStackPaneHome() {
        return this.stackPaneHome;
    }

    public void setStackPaneCrea(StackPane stackPane) {
        this.stackPaneCrea = stackPane;
    }

    public StackPane getStackPaneCrea() {
        return this.stackPaneCrea;
    }

    public void setStackPaneModificaR(StackPane stackPane) {
        this.stackPaneModificaR = stackPane;
    }

    public StackPane getStackPaneModificaR() {
        return this.stackPaneModificaR;
    }

    public void setStackPaneModificaO(StackPane stackPane) {
        this.stackPaneModificaO = stackPane;
    }

    public StackPane getStackPaneModificaO() {
        return stackPaneModificaO;
    }

    // *************** Cambia la scena a valuta rischi *************** //
    public Parent switchToValutaRischi(ModelValutaRischi modelValutaRischi, Societa societa, UnitaLocale unita)
            throws IOException {
        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/valuta_rischi.fxml");

        FXMLLoader loader = new FXMLLoader(fxmlURL);
        Parent root = loader.load();

        ValutaRischi rischiController = loader.getController();

        rischiController.setSection(unita, societa);
        rischiController.setModel(modelValutaRischi);

        loadedFXMLs = fxmlURL.toString();

        return root;

    }

    // * *************** Cambia la scena a creazione *************** //
    public Parent switchToCreazione(ModelCreazione modelCreazione) throws IOException {
        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/main_creazione.fxml");

        // Check if the stackPaneHome already contains the root of the new scene
        if (fxmlURL == null || isAlreadyLoaded(stackPaneHome, fxmlURL.toString()))
            return null;

        FXMLLoader loader = new FXMLLoader(fxmlURL);
        Parent root = loader.load();
        Creazione creazione = loader.getController();

        creazione.setModelCreazione(modelCreazione, this);

        // Add the loaded FXML URL to the set
        loadedFXMLs = fxmlURL.toString();
        return root;
    }

    public Parent switchToCreazioneByTipo(TipoCreazionePagina tipo, ModelCreazione modelCreazione) {
        try {
            switch (tipo) {
                case SOCIETA:
                    return this.switchToCreazioneSocieta(modelCreazione);
                case UNITA_LOCALE:
                    return this.switchToCreazioneUnitaLocale(modelCreazione);
                case REPARTO:
                    return this.switchToCreazioneReparti(modelCreazione);
                case TITOLO:
                    return this.switchToCreazioneTitolo(modelCreazione);
                case OGGETTO:
                    return this.switchToCreazioneOggetto(modelCreazione);
                case PROVVEDIMENTO:
                    return this.switchToCreazioneProvvedimento(modelCreazione);
                default:
                    break;
            }
        } catch (IOException e) {
            System.out.println("Errore durante il cambio di pagina (Pagina: " + tipo + ")\n");
            e.printStackTrace();
        }

        return null;
    }

    // * *************** Cambia la scena a home *************** //
    public Parent switchToHome() {
        try {
            URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/home.fxml");

            // Check if the stackPaneHome already contains the root of the new scene
            if (fxmlURL != null && !isAlreadyLoaded(stackPaneHome, fxmlURL.toString())) {
                FXMLLoader loader = new FXMLLoader(fxmlURL);
                Parent root = loader.load();

                Home home = loader.getController();

                home.setModel(modelValutaRischi, this);

                loadedFXMLs = fxmlURL.toString();

                return root;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // * *************** Cambia la scena a Creazione Unita Locale *************** //
    public Parent switchToCreazioneUnitaLocale(ModelCreazione modelCreazione) throws IOException {

        FXMLLoader loaderUnitaLocale = new FXMLLoader(
                getClass().getResource("/View/fxml/creazione/creazione_unitalocale.fxml"));

        Parent root = loaderUnitaLocale.load();
        CreazioneUnitaLocale creazioneUnita = loaderUnitaLocale.getController();

        creazioneUnita.setModel(modelCreazione, this);
        creazioneUnita.setOldTextFields();

        // creazioneUnita.setSocieta();

        return root;

    }

    // ------------------ Cambia la scena a Creazione Societa ------------------ //
    public Parent switchToCreazioneSocieta(ModelCreazione modelCreazione) throws IOException {
        FXMLLoader loaderSocieta = new FXMLLoader(
                getClass().getResource("/View/fxml/creazione/creazione_societa.fxml"));
        Parent root = loaderSocieta.load();

        CreazioneSocieta creazioneSocieta = loaderSocieta.getController();
        creazioneSocieta.setModel(modelCreazione, this);
        creazioneSocieta.setOldTextFields();

        return root;
    }

    // ------------------ Cambia la scena a Creazione Reparti ------------------ //
    public Parent switchToCreazioneReparti(ModelCreazione modelCreazione) throws IOException {

        FXMLLoader loaderReparti = new FXMLLoader(
                getClass().getResource("/View/fxml/creazione/creazione_reparti.fxml"));
        Parent root = loaderReparti.load();

        CreazioneReparto creazioneReparto = loaderReparti.getController();

        creazioneReparto.setModel(modelCreazione, this);

        return root;
    }

    // ------------------ Cambia la scena a Creazione Titoli ------------------ //
    public Parent switchToCreazioneTitolo(ModelCreazione modelCreazione) throws IOException {

        FXMLLoader loaderTitoli = new FXMLLoader(getClass().getResource("/View/fxml/creazione/creazione_titoli.fxml"));
        Parent root = loaderTitoli.load();

        CreazioneTitolo creazioneTitolo = loaderTitoli.getController();

        creazioneTitolo.setModel(modelCreazione, this);

        return root;
    }

    // ------------------ Cambia la scena a Creazione Oggetto ------------------ //
    public Parent switchToCreazioneOggetto(ModelCreazione modelCreazione) throws IOException {

        FXMLLoader loaderOggetto = new FXMLLoader(
                getClass().getResource("/View/fxml/creazione/creazione_oggetti.fxml"));
        Parent root = loaderOggetto.load();

        CreazioneOggetto creazioneOggetto = loaderOggetto.getController();

        creazioneOggetto.setModel(modelCreazione, this);

        return root;
    }

    // ------------------ Cambia la scena a Creazione Provvedimento
    // ------------------ //
    public Parent switchToCreazioneProvvedimento(ModelCreazione modelCreazione) throws IOException {

        FXMLLoader loaderProvvedimento = new FXMLLoader(
                getClass().getResource("/View/fxml/creazione/creazione_provvedimenti.fxml"));
        Parent root = loaderProvvedimento.load();

        CreazioneProvvedimento creazioneProvvedimento = loaderProvvedimento.getController();

        creazioneProvvedimento.setModel(modelCreazione, this);

        return root;
    }

    // ------------------ Verifica che il contenuto dello stackPane sia già stato
    // caricato ------------------ //
    private boolean isAlreadyLoaded(StackPane stackPane, String fxmlURL) {
        if (loadedFXMLs != null) {
            return loadedFXMLs.equals(fxmlURL);
        }
        return false;
    }

}
