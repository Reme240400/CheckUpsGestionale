package Models;

import java.io.IOException;

import View.Controllers.Home;
import View.Controllers.ValutaRischi;
import View.Controllers.Creazione.Creazione;
import View.Controllers.Modifiche.Modifica;
import javafx.event.ActionEvent;
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
    public Parent switchToValutaRischi() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/valuta_rischi.fxml"));

        Parent root = loader.load();
        //loader.setController();
        ValutaRischi rischiController = loader.getController();

        

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
    public Parent switchToHome(ModelHome modelHome) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/home.fxml"));
        Parent root = loader.load();
        Home home = loader.getController();

        home.setModel(modelHome);

        return root;
    }
}
