package View.Controllers;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class CreazioneReparti implements Initializable{

    @FXML
    JFXButton btnSalva;

    private Creazione creazione;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

    }

    public void salvaReparto(javafx.event.ActionEvent event) {

        creazione.finalReport();

    }

    public void setCreazione(Creazione creazione) {
        this.creazione = creazione;
    }
    
}
