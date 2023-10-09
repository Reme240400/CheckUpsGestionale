package View.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class CreazioneSocieta implements Initializable {

    @FXML
    JFXButton btnSalva;

    private Creazione creazione;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void salvaSocieta(javafx.event.ActionEvent event) {

        creazione.setEnableBtnUnitaLocale();

    }

    public void setCreazione(Creazione creazione) {
        this.creazione = creazione;
    }
    
}
