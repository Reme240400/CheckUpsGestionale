package View.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Models.CreazioneModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


public class CreazioneUnitaLocale implements Initializable{
    
    @FXML
    JFXButton btnSalva;

    private CreazioneModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public void salvaUnitaLocale(javafx.event.ActionEvent event) {

        model.setUnitaSaved(true);

    }

    public void setModel(CreazioneModel model) {
        this.model = model;
    }
}
