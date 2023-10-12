package View.Controllers;

import com.jfoenix.controls.JFXButton;

import Models.ModelCreazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class CreazioneReparti implements Initializable{

    @FXML
    JFXButton btnSalva;

    private ModelCreazione modelCreazione;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

    }

    public void salvaReparto(javafx.event.ActionEvent event) {
        // fare la call alla Query

    }

    public void setModel(ModelCreazione modelCreazione) {
        this.modelCreazione = modelCreazione;
    }

    
}
