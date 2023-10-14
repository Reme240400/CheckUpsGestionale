package View.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Models.ModelCreazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


public class CreazioneUnitaLocale implements Initializable{
    
    @FXML
    JFXButton btnSalva;

    @FXML
    JFXButton btnAnnulla;

    private ModelCreazione model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public void salvaUnitaLocale(javafx.event.ActionEvent event) {

        // fare la call alla Query
        model.setUnitaSaved(true);

    }

    public void setModel(ModelCreazione model) {
        this.model = model;

        this.btnSalva.disableProperty().bind(model.savedProperty().not());
        this.btnAnnulla.disableProperty().bind(model.discardProperty().not());

    }
}
