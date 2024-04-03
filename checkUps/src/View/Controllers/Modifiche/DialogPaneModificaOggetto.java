package View.Controllers.Modifiche;

import java.net.URL;
import java.util.ResourceBundle;

import Models.ModelCreazione;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class DialogPaneModificaOggetto implements Initializable {

    @FXML
    private TextField modificaNomeOggetto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public String getNome() {
        return modificaNomeOggetto.getText();
    }

    public void setModel(ModelCreazione model) {
        modificaNomeOggetto.setText(model.getOggettoTmp().getNome());
    }

}
