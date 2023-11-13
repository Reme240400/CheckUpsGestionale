package Models;

import java.io.IOException;

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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ModelPaths {

    // * *************** Cambia la scena a modifica *************** //
    public Parent switchToModifica( ModelModifica model) throws IOException{
        

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/modifica.fxml"));

        Parent root = loader.load();
        Modifica modifica = loader.getController();

        modifica.setModel(model);

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
    public Parent switchToCreazione(ModelCreazione modelCreazione) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/main_creazione.fxml"));

        Parent root = loader.load();
        Creazione creazione = loader.getController();

        creazione.setModelCreazione(modelCreazione);

        return root;
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

    public Parent switchToCreazioneReparti(ModelCreazione modelCreazione) throws IOException{

        FXMLLoader loaderReparti = new FXMLLoader(getClass().getResource("/View/fxml/creazione_reparti.fxml"));
        Parent root = loaderReparti.load();

        CreazioneReparto creazioneReparto = loaderReparti.getController();

        creazioneReparto.setModel(modelCreazione);

        return root;
    }
}
