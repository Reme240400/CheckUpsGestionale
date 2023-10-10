package View.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Models.CreazioneModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class CreazioneSocieta implements Initializable {

    @FXML
    JFXButton btnSalva;

   // private Creazione creazione;
    private CreazioneModel creazioneModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void salvaSocieta(javafx.event.ActionEvent event) {

        creazioneModel.setSocietySaved(true);

    }

    
    public void setModel(CreazioneModel creazioneModel) {
        this.creazioneModel = creazioneModel;
    }
}
