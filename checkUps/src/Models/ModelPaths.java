package Models;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import View.Controllers.Home;
import View.Controllers.ValutaRischi;
import View.Controllers.ViewController;
import View.Controllers.Creazione.Creazione;
import View.Controllers.Creazione.CreazioneReparto;
import View.Controllers.Creazione.CreazioneSocieta;
import View.Controllers.Creazione.CreazioneUnitaLocale;
import View.Controllers.Modifiche.Modifica;
import View.Controllers.Modifiche.ModificaSelezioneReparti;
import View.Controllers.Modifiche.ModificaSelezioneTitolo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class ModelPaths {

    private String loadedFXMLs = null;

    // * *************** Cambia la scena a modifica *************** //
    public Parent switchToModifica( ModelModifica model) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/modifica.fxml"));

        Parent root = loader.load();
        Modifica modifica = loader.getController();

        modifica.setModel(model);

        return root;

    }

    public Parent switchToModificaReparto(ModelModifica model) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/modifica_selezioneReparto.fxml"));

        Parent root = loader.load();
        ModificaSelezioneReparti modifica = loader.getController();

        modifica.setModel(model);

        return root;

    }

    public Parent switchToModificaTitoli(ModelModifica modelModifica) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/modifica_selezioneTitolo.fxml"));

        Parent root = loader.load();
        ModificaSelezioneTitolo controller = loader.getController();

        controller.setModel(modelModifica);

        return root;
    }

    // * *************** Cambia la scena a valuta rischi *************** //
    public Parent switchToValutaRischi( ModelValutaRischi modelValutaRischi, Societa societa, UnitaLocale unita) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/valuta_rischi.fxml"));

        Parent root = loader.load();
        ValutaRischi rischiController = loader.getController();

        System.out.println("Societa: " + societa.getNome());
        System.out.println("Unita: " + unita.getNome());

        rischiController.setSection(unita, societa);
        rischiController.setModel(modelValutaRischi);

        return root;
    }

    // * *************** Cambia la scena a creazione *************** //
    // public Parent switchToCreazione(ModelCreazione modelCreazione, StackPane stackPaneHome) throws IOException {

    //     System.out.println(stackPaneHome.getChildren().getClass().getClassLoader().getResource("View/fxml/main_creazione.fxml"));
            
    //     FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/main_creazione.fxml"));

    //     System.out.println("Loader: " + loader.getRoot());

    //     if (!stackPaneHome.getChildren().contains(loader.getRoot())) {

    //     Parent root = loader.load();
    //     Creazione creazione = loader.getController();

    //     System.out.println("StackPaneHome : " + stackPaneHome.getChildren());

    //     creazione.setModelCreazione(modelCreazione, stackPaneHome); 

    //     return root;
    //     } else {
    //         return null;
    //     }
    // }

    

    // * *************** Cambia la scena a creazione *************** //
    public Parent switchToCreazione(ModelCreazione modelCreazione, StackPane stackPaneHome) throws IOException {
        URL fxmlURL = getClass().getClassLoader().getResource("View/fxml/main_creazione.fxml");

        // Check if the stackPaneHome already contains the root of the new scene
        if (fxmlURL != null && !isAlreadyLoaded(stackPaneHome, fxmlURL.toString())) {
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();
            Creazione creazione = loader.getController();

            System.out.println("StackPaneHome : " + stackPaneHome.getChildren());

            creazione.setModelCreazione(modelCreazione, stackPaneHome);

            // Add the loaded FXML URL to the set
            loadedFXMLs = fxmlURL.toString();

            return root;
        } else {
            return null;
        }
    }

    // * *************** Cambia la scena a home *************** //
    public Parent switchToHome(ModelHome modelHome, ViewController controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/home.fxml"));
        Parent root = loader.load();
        Home home = loader.getController();

        home.setModel(modelHome);
        home.setController(controller);

        return root;
    }

    public Parent switchToCreazioneUnitaLocale(ModelCreazione modelCreazione) throws IOException{

        FXMLLoader loaderUnitaLocale = new FXMLLoader(getClass().getResource("/View/fxml/creazione_unitalocale.fxml"));

        Parent root = loaderUnitaLocale.load();
        CreazioneUnitaLocale creazioneUnita = loaderUnitaLocale.getController();

        creazioneUnita.setModel(modelCreazione);
        creazioneUnita.setSocieta(modelCreazione.getSocietaTmp());

        return root;

    }

    public Parent switchToCreazioneSocieta(ModelCreazione modelCreazione) throws IOException{

        FXMLLoader loaderSocieta = new FXMLLoader(getClass().getResource("/View/fxml/creazione_societa.fxml"));

        Parent root = loaderSocieta.load();
        CreazioneSocieta creazioneSocieta = loaderSocieta.getController();

        creazioneSocieta.setModel(modelCreazione);

        return root;
    }

    public Parent switchToCreazioneReparti(ModelCreazione modelCreazione, StackPane stackPaneHome, StackPane stackPaneCreazione) throws IOException{

        FXMLLoader loaderReparti = new FXMLLoader(getClass().getResource("/View/fxml/creazione_reparti.fxml"));
        Parent root = loaderReparti.load();

        CreazioneReparto creazioneReparto = loaderReparti.getController();

        System.out.println("StackPane 5: " + stackPaneHome.getChildren());

        creazioneReparto.giveStackPane(stackPaneHome, stackPaneCreazione);
        creazioneReparto.setModel(modelCreazione, this);

        return root;
    }
    
    // ------------------ Verifica che il contenuto dello stackPane sia già stato caricato ------------------ //
    private boolean isAlreadyLoaded(StackPane stackPane, String fxmlURL) {
        return loadedFXMLs.equals(fxmlURL);
    }

}
