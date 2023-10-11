package View.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Models.ModelCreazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class CreazioneSocieta implements Initializable {

    @FXML
    JFXButton btnSalva;

   // private Creazione creazione;
    private ModelCreazione modelCreazione;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void salvaSocieta(javafx.event.ActionEvent event) {

        modelCreazione.setSocietySaved(true);

    }

    
    public void setModel(ModelCreazione modelCreazione) {
        this.modelCreazione = modelCreazione;
    }
}
