package Models;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import View.Controllers.Home;
import View.Controllers.ValutaRischi;
import View.Controllers.Creazione.Creazione;
import View.Controllers.Creazione.CreazioneOggetto;
import View.Controllers.Creazione.CreazioneProvvedimento;
import View.Controllers.Creazione.CreazioneReparto;
import View.Controllers.Creazione.CreazioneSocieta;
import View.Controllers.Creazione.CreazioneTitolo;
import View.Controllers.Creazione.CreazioneUnitaLocale;
import View.Controllers.Modifiche.Modifica;
import View.Controllers.Modifiche.ModificaSezioneOggetti;
import View.Controllers.Modifiche.ModificaSezioneProvvedimenti;
import View.Controllers.Modifiche.ModificaSezioneReparti;
import View.Controllers.Modifiche.ModificaSezioneTitolo;
import View.Controllers.Modifiche.DialogPane.DialogPaneOggetto;
import View.Controllers.Modifiche.DialogPane.DialogPaneReparto;
import View.Controllers.Modifiche.DialogPane.DialogPaneUnita;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class ModelPaths {

    private ModelModifica modelModifica = new ModelModifica();
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

    public StackPane getStackPaneHome(){
        return this.stackPaneHome;
    }

    public void setStackPaneCrea(StackPane stackPane) {
        this.stackPaneCrea = stackPane;
    }

    public StackPane getStackPaneCrea(){
        return this.stackPaneCrea;
    }

    public void setStackPaneModificaR(StackPane stackPane) {
        this.stackPaneModificaR = stackPane;
    }

    public StackPane getStackPaneModificaR(){
        return this.stackPaneModificaR;
    }

    public void setStackPaneModificaO(StackPane stackPane) {
        this.stackPaneModificaO = stackPane;
    }

    public StackPane getStackPaneModificaO() {
        return stackPaneModificaO;
    }

    // * *************** Cambia la scena a modifica *************** //
    public Parent switchToModifica(ModelModifica model) throws IOException {

        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/modifica.fxml");

        if (fxmlURL != null && !isAlreadyLoaded(stackPaneHome, fxmlURL.toString())) {
            FXMLLoader loader = new FXMLLoader(fxmlURL);

            Parent root = loader.load();
            Modifica modifica = loader.getController();

            modifica.setModel(model, this);

            loadedFXMLs = fxmlURL.toString();

            return root;
        } else {
            return null;
        }

    }

    // * *************** Cambia la scena a modifica reparti *************** //
    public Parent switchToModificaReparto(ModelModifica modelModifica) throws IOException {

        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/modifica_sezioneReparto.fxml");

        // Check if the stackPaneHome already contains the root of the new scene
        if (fxmlURL != null && !isAlreadyLoaded(stackPaneModificaR, fxmlURL.toString())) {
            FXMLLoader loader = new FXMLLoader(fxmlURL);

            Parent root = loader.load();
            ModificaSezioneReparti modifica = loader.getController();

            modifica.setModel(modelModifica, this);

            loadedFXMLs = fxmlURL.toString();

            return root;
        } else {
            return null;
        }
    }

    // * *************** Cambia la scena a modifica titoli *************** //
    public Parent switchToModificaTitoli(ModelModifica modelModifica) throws IOException {

        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/modifica_sezioneTitolo.fxml");

        // Check if the stackPaneHome already contains the root of the new scene
        if (fxmlURL != null && !isAlreadyLoaded(stackPaneModificaR, fxmlURL.toString())) {
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();
        
            ModificaSezioneTitolo controller = loader.getController();

            controller.setModel(modelModifica, this);

            loadedFXMLs = fxmlURL.toString();

            return root;
        } else {
            return null;
        }
    }

    // *************** Cambia la scena a Modifica Oggetti *************** //
    public Parent switchToModificaOggetti(ModelModifica modelModifica) throws IOException{

        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/modifica_sezioneOggetto.fxml");

        if (fxmlURL != null && !isAlreadyLoaded(stackPaneModificaO, fxmlURL.toString())) {
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();

            ModificaSezioneOggetti controller = loader.getController();

            controller.setModel(modelModifica, this);

            loadedFXMLs = fxmlURL.toString();

            return root;
        } else {
            return null;
        }
    }

    // *************** Cambia la scena a Modifica Provvedimenti *************** //
    public Parent switchToModificaProvvedimenti(ModelModifica modelModifica) throws IOException{
        
        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/modifica_sezioneProvvedimento.fxml");

        if (fxmlURL != null && !isAlreadyLoaded(stackPaneModificaO, fxmlURL.toString())) {
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();

            ModificaSezioneProvvedimenti controller = loader.getController();

            controller.setModel(modelModifica, this);

            loadedFXMLs = fxmlURL.toString();

            return root;
        } else {
            return null;
        }
        
    }

    // *************** Cambia la scena a valuta rischi *************** //
    public Parent switchToValutaRischi(ModelValutaRischi modelValutaRischi, Societa societa, UnitaLocale unita) throws IOException {
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
        if (fxmlURL != null && !isAlreadyLoaded(stackPaneHome, fxmlURL.toString())) {
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();
            Creazione creazione = loader.getController();

            creazione.setModelCreazione(modelCreazione, this);

            // Add the loaded FXML URL to the set
            loadedFXMLs = fxmlURL.toString();

            return root;
        } else {
            return null;
        }
    }

    // * *************** Cambia la scena a home *************** //
    public Parent switchToHome(ModelHome modelHome, Label title) throws IOException {

        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/home.fxml");

        // Check if the stackPaneHome already contains the root of the new scene
        if (fxmlURL != null && !isAlreadyLoaded(stackPaneHome, fxmlURL.toString())) {
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();

            Home home = loader.getController();

            home.setModel(modelHome, modelValutaRischi, this);
            home.setTitleValuta(title);

            loadedFXMLs = fxmlURL.toString();

            return root;
        } else {
            return null;
        }
    }

    // * *************** Cambia la scena a Creazione Unita Locale *************** //
    public Parent switchToCreazioneUnitaLocale(ModelCreazione modelCreazione) throws IOException {

        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/creazione_unitalocale.fxml");

        // Check if the stackPaneHome already contains the root of the new scene
        if (fxmlURL != null && !isAlreadyLoaded(stackPaneHome, fxmlURL.toString())) {
            FXMLLoader loaderUnitaLocale = new FXMLLoader(getClass().getResource("/View/fxml/creazione_unitalocale.fxml"));

            Parent root = loaderUnitaLocale.load();
            CreazioneUnitaLocale creazioneUnita = loaderUnitaLocale.getController();

            creazioneUnita.setModel(modelCreazione, this);

            creazioneUnita.setTextFields();
            creazioneUnita.setSocieta(modelCreazione.getSocietaTmp());

            return root;
        } else {
            return null;
        }
    }

    // ------------------ Cambia la scena a Creazione Societa ------------------ //
    public Parent switchToCreazioneSocieta(ModelCreazione modelCreazione) throws IOException {

        FXMLLoader loaderSocieta = new FXMLLoader(getClass().getResource("/View/fxml/creazione_societa.fxml"));

        Parent root = loaderSocieta.load();
        CreazioneSocieta creazioneSocieta = loaderSocieta.getController();

        creazioneSocieta.setModel( modelCreazione, this);
        creazioneSocieta.setTextFields();
    
        return root;
    }

    // ------------------ Cambia la scena a Creazione Reparti ------------------ //
    public Parent switchToCreazioneReparti(ModelCreazione modelCreazione) throws IOException {

        FXMLLoader loaderReparti = new FXMLLoader(getClass().getResource("/View/fxml/creazione_reparti.fxml"));
        Parent root = loaderReparti.load();

        CreazioneReparto creazioneReparto = loaderReparti.getController();

        creazioneReparto.setModel( modelCreazione, this, modelModifica);

        return root;
    }

    // ------------------ Cambia la scena a Creazione Titoli ------------------ //
    public Parent switchToCreazioneTitolo(ModelCreazione modelCreazione) throws IOException {
        
        FXMLLoader loaderTitoli = new FXMLLoader(getClass().getResource("/View/fxml/creazione_titoli.fxml"));
        Parent root = loaderTitoli.load();

        CreazioneTitolo creazioneTitolo = loaderTitoli.getController();

        creazioneTitolo.setModel( modelCreazione, this, modelModifica);

        return root;
    }
    
    // ------------------ Cambia la scena a Creazione Oggetto ------------------ //
    public Parent switchToCreazioneOggetto(ModelCreazione modelCreazione) throws IOException{
        
        FXMLLoader loaderOggetto = new FXMLLoader(getClass().getResource("/View/fxml/creazione_oggetti.fxml"));
        Parent root = loaderOggetto.load();
        
        CreazioneOggetto creazioneOggetto = loaderOggetto.getController();

        creazioneOggetto.setModel( modelCreazione, this, modelModifica);

        return root;
    }

    // ------------------ Cambia la scena a Creazione Provvedimento ------------------ //
    public Parent switchToCreazioneProvvedimento(ModelCreazione modelCreazione) throws IOException{
            
            FXMLLoader loaderProvvedimento = new FXMLLoader(getClass().getResource("/View/fxml/creazione_provvedimenti.fxml"));
            Parent root = loaderProvvedimento.load();
            
            CreazioneProvvedimento creazioneProvvedimento = loaderProvvedimento.getController();
    
            creazioneProvvedimento.setModel( modelCreazione, this, modelModifica);
    
            return root;
    }

    // ------------------  ------------------ //
    public ButtonType showUnitaDialogPane(ModelModifica modelModifica) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/switch_unitaLocale_dialogPane.fxml"));
        DialogPane dialogPane = loader.load();

        DialogPaneUnita dialogController = loader.getController();

        dialogController.setModel(modelModifica);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.setTitle("Scegli la Società");

        Optional<ButtonType> clickedButton = dialog.showAndWait();

        return clickedButton.get();
    }

    // ------------------ Cambia la scena a Creazione Reparti ------------------ //
    public ButtonType showRepartiTitoliDialogPane(ModelModifica modelModifica) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/switch_reparto_dialogPane.fxml"));
        DialogPane dialogPane = loader.load();

        DialogPaneReparto dialogController = loader.getController();

        dialogController.setModel(modelModifica);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.setTitle("");

        Optional<ButtonType> clickedButton = dialog.showAndWait();

        return clickedButton.get();
    }
    
    public ButtonType showOggettiDialogPane(ModelModifica modelModifica) throws IOException{
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/switch_oggetto_dialogPane.fxml"));
        DialogPane dialogPane = loader.load();

        DialogPaneOggetto dialogController = loader.getController();

        dialogController.setModel(modelModifica);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.setTitle("");

        Optional<ButtonType> clickedButton = dialog.showAndWait();

        return clickedButton.get();
    }

    // ------------------ Verifica che il contenuto dello stackPane sia già stato caricato ------------------ //
    private boolean isAlreadyLoaded(StackPane stackPane, String fxmlURL) {
        if (loadedFXMLs != null) {
            return loadedFXMLs.equals(fxmlURL);
        }
        return false;
    }

    

    
}
