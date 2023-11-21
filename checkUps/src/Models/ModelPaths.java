package Models;

import java.io.IOException;
import java.net.URL;

import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import View.Controllers.Home;
import View.Controllers.ValutaRischi;
import View.Controllers.ViewController;
import View.Controllers.Creazione.Creazione;
import View.Controllers.Creazione.CreazioneReparto;
import View.Controllers.Creazione.CreazioneSocieta;
import View.Controllers.Creazione.CreazioneTitolo;
import View.Controllers.Creazione.CreazioneUnitaLocale;
import View.Controllers.Modifiche.Modifica;
import View.Controllers.Modifiche.ModificaSelezioneReparti;
import View.Controllers.Modifiche.ModificaSelezioneTitolo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class ModelPaths {

    private String loadedFXMLs = null;
    private StackPane stackPaneHome = null;
    private StackPane stackPaneModifica = null;

    public void setStackPaneHome(StackPane stackPane) {
        this.stackPaneHome = stackPane;
    }

    public void setStackPaneModifica(StackPane stackPane) {
        this.stackPaneModifica = stackPane;
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

    public Parent switchToModificaReparto(ModelModifica model) throws IOException {

        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/modifica_selezioneReparto.fxml");

        // Check if the stackPaneHome already contains the root of the new scene
        if (fxmlURL != null && !isAlreadyLoaded(stackPaneModifica, fxmlURL.toString())) {
            FXMLLoader loader = new FXMLLoader(fxmlURL);

            Parent root = loader.load();
            ModificaSelezioneReparti modifica = loader.getController();

            modifica.setModel(model);

            loadedFXMLs = fxmlURL.toString();

            return root;
        } else {
            return null;
        }
    }

    public Parent switchToModificaTitoli(ModelModifica modelModifica) throws IOException {

        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/modifica_selezioneTitolo.fxml");

        // Check if the stackPaneHome already contains the root of the new scene
        if (fxmlURL != null && !isAlreadyLoaded(stackPaneModifica, fxmlURL.toString())) {
            FXMLLoader loader = new FXMLLoader(fxmlURL);

            Parent root = loader.load();
            ModificaSelezioneTitolo controller = loader.getController();

            controller.setModel(modelModifica);

            loadedFXMLs = fxmlURL.toString();

            return root;
        } else {
            return null;
        }
    }

    // * *************** Cambia la scena a valuta rischi *************** //
    public Parent switchToValutaRischi(ModelValutaRischi modelValutaRischi, Societa societa, UnitaLocale unita)
            throws IOException {
        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/valuta_rischi.fxml");

        if (fxmlURL != null && !isAlreadyLoaded(stackPaneHome, fxmlURL.toString())) {

            FXMLLoader loader = new FXMLLoader(fxmlURL);

            Parent root = loader.load();
            ValutaRischi rischiController = loader.getController();

            System.out.println("Societa: " + societa.getNome());
            System.out.println("Unita: " + unita.getNome());

            rischiController.setSection(unita, societa);
            rischiController.setModel(modelValutaRischi);

            loadedFXMLs = fxmlURL.toString();

            return root;
        } else {
            return null;
        }
    }

    // * *************** Cambia la scena a creazione *************** //
    // public Parent switchToCreazione(ModelCreazione modelCreazione, StackPane
    // stackPaneHome) throws IOException {

    // System.out.println(stackPaneHome.getChildren().getClass().getClassLoader().getResource("View/fxml/main_creazione.fxml"));

    // FXMLLoader loader = new
    // FXMLLoader(getClass().getResource("/View/fxml/main_creazione.fxml"));

    // System.out.println("Loader: " + loader.getRoot());

    // if (!stackPaneHome.getChildren().contains(loader.getRoot())) {

    // Parent root = loader.load();
    // Creazione creazione = loader.getController();

    // System.out.println("StackPaneHome : " + stackPaneHome.getChildren());

    // creazione.setModelCreazione(modelCreazione, stackPaneHome);

    // return root;
    // } else {
    // return null;
    // }
    // }

    // * *************** Cambia la scena a creazione *************** //
    public Parent switchToCreazione(ModelCreazione modelCreazione, ViewController controller) throws IOException {
        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/main_creazione.fxml");

        // Check if the stackPaneHome already contains the root of the new scene
        if (fxmlURL != null && !isAlreadyLoaded(stackPaneHome, fxmlURL.toString())) {
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();
            Creazione creazione = loader.getController();

            creazione.setModelCreazione(modelCreazione, this, controller);

            // Add the loaded FXML URL to the set
            loadedFXMLs = fxmlURL.toString();

            return root;
        } else {
            return null;
        }
    }

    // * *************** Cambia la scena a home *************** //
    public Parent switchToHome(ModelHome modelHome, ViewController controller) throws IOException {

        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/home.fxml");

        // Check if the stackPaneHome already contains the root of the new scene
        if (fxmlURL != null && !isAlreadyLoaded(stackPaneHome, fxmlURL.toString())) {
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();
            Home home = loader.getController();

            home.setModel(modelHome);
            home.setController(controller);

            loadedFXMLs = fxmlURL.toString();

            return root;
        } else {
            return null;
        }
    }

    public Parent switchToCreazioneUnitaLocale(ModelCreazione modelCreazione) throws IOException {

        // URL fxmlURL =
        // getClass().getClassLoader().getResource("View/fxml/creazione_unitalocale.fxml");

        // Check if the stackPaneHome already contains the root of the new scene
        // if (fxmlURL != null && !isAlreadyLoaded(stackPaneHome, fxmlURL.toString())) {
        FXMLLoader loaderUnitaLocale = new FXMLLoader(getClass().getResource("/View/fxml/creazione_unitalocale.fxml"));

        Parent root = loaderUnitaLocale.load();
        CreazioneUnitaLocale creazioneUnita = loaderUnitaLocale.getController();

        creazioneUnita.setModel(modelCreazione, this);
        creazioneUnita.setSocieta(modelCreazione.getSocietaTmp());

        return root;
        // } else {
        // return null;
        // }

    }

    public Parent switchToCreazioneSocieta(ModelCreazione modelCreazione) throws IOException {

        FXMLLoader loaderSocieta = new FXMLLoader(getClass().getResource("/View/fxml/creazione_societa.fxml"));

        Parent root = loaderSocieta.load();
        CreazioneSocieta creazioneSocieta = loaderSocieta.getController();

        creazioneSocieta.setModel(modelCreazione, this);

        return root;
    }

    public Parent switchToCreazioneReparti(ModelCreazione modelCreazione, ViewController viewController) throws IOException {

        FXMLLoader loaderReparti = new FXMLLoader(getClass().getResource("/View/fxml/creazione_reparti.fxml"));
        Parent root = loaderReparti.load();

        CreazioneReparto creazioneReparto = loaderReparti.getController();

        creazioneReparto.setModel(modelCreazione, this, viewController);

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

    public Parent switchToCreazioneTitolo(ModelCreazione modelCreazione, ViewController viewController) throws IOException {
        
        FXMLLoader loaderTitoli = new FXMLLoader(getClass().getResource("/View/fxml/creazione_titoli.fxml"));
        Parent root = loaderTitoli.load();

        CreazioneTitolo creazioneTitolo = loaderTitoli.getController();

        creazioneTitolo.setModel(modelCreazione, this, viewController);

        return root;
    }

}
