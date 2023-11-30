package Models;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import View.Controllers.ValutaRischi;
import View.Controllers.Creazione.Creazione;
import View.Controllers.Creazione.CreazioneOggetto;
import View.Controllers.Creazione.CreazioneReparto;
import View.Controllers.Creazione.CreazioneSocieta;
import View.Controllers.Creazione.CreazioneTitolo;
import View.Controllers.Creazione.CreazioneUnitaLocale;
import View.Controllers.Modifiche.DialogPaneReparto;
import View.Controllers.Modifiche.DialogPaneUnita;
import View.Controllers.Modifiche.Modifica;
import View.Controllers.Modifiche.ModificaSelezioneReparti;
import View.Controllers.Modifiche.ModificaSelezioneTitolo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.StackPane;

public class ModelPaths {

    private ModelModifica modelModifica = new ModelModifica();

    private String loadedFXMLs = null;
    private StackPane stackPaneHome = null;
    private StackPane stackPaneModifica = null;
    private StackPane stackPaneCrea = null;

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

    public void setStackPaneModifica(StackPane stackPane) {
        this.stackPaneModifica = stackPane;
    }

    public StackPane getStackPaneModifica(){
        return this.stackPaneModifica;
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
    public Parent switchToModificaReparto(ModelModifica model) throws IOException {

        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/modifica_selezioneReparto.fxml");

        // Check if the stackPaneHome already contains the root of the new scene
        if (fxmlURL != null && !isAlreadyLoaded(stackPaneModifica, fxmlURL.toString())) {
            FXMLLoader loader = new FXMLLoader(fxmlURL);

            Parent root = loader.load();
            ModificaSelezioneReparti modifica = loader.getController();

            modifica.setModel(model, this);

            loadedFXMLs = fxmlURL.toString();

            return root;
        } else {
            return null;
        }
    }

    // * *************** Cambia la scena a modifica titoli *************** //
    public Parent switchToModificaTitoli(ModelModifica modelModifica) throws IOException {

        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/modifica_selezioneTitolo.fxml");

        // Check if the stackPaneHome already contains the root of the new scene
        if (fxmlURL != null && !isAlreadyLoaded(stackPaneModifica, fxmlURL.toString())) {
            FXMLLoader loader = new FXMLLoader(fxmlURL);

            Parent root = loader.load();
            ModificaSelezioneTitolo controller = loader.getController();

            controller.setModel(modelModifica, this);

            loadedFXMLs = fxmlURL.toString();

            return root;
        } else {
            return null;
        }
    }

    // * *************** Cambia la scena a valuta rischi *************** //
    public Parent switchToValutaRischi(ModelValutaRischi modelValutaRischi, Societa societa, UnitaLocale unita) throws IOException {
        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/valuta_rischi.fxml");

        //if (!isAlreadyLoaded(stackPaneHome, fxmlURL.toString())) {

            FXMLLoader loader = new FXMLLoader(fxmlURL);

            Parent root = loader.load();
            ValutaRischi rischiController = loader.getController();

            System.out.println("Societa: " + societa.getNome());
            System.out.println("Unita: " + unita.getNome());

            rischiController.setSection(unita, societa);
            rischiController.setModel(modelValutaRischi);

            loadedFXMLs = fxmlURL.toString();

            return root;
        //} else {
          //  return null;
        //}
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
    public Parent switchToHome(ModelHome modelHome) throws IOException {

        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/home.fxml");

        // Check if the stackPaneHome already contains the root of the new scene
        if (fxmlURL != null && !isAlreadyLoaded(stackPaneHome, fxmlURL.toString())) {
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();
            //Home home = loader.getController();

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

    public ButtonType showUnitaDialogPane(ModelModifica modelModifica) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/modifica_dialogPane.fxml"));
        DialogPane dialogPane = loader.load();

        DialogPaneUnita dialogController = loader.getController();

        dialogController.setModel(modelModifica);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.setTitle("Scegli la Società");

        Optional<ButtonType> clickedButton = dialog.showAndWait();

        return clickedButton.get();
    }

    public ButtonType showRepartiTitoliDialogPane(ModelModifica modelModifica) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/switch_reparto_dialogPane.fxml"));
        DialogPane dialogPane = loader.load();

        DialogPaneReparto dialogController = loader.getController();

        dialogController.setModel(modelModifica);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.setTitle("Scegli la Unità Locale");

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
