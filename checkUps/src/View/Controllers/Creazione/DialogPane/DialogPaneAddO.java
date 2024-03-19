package View.Controllers.Creazione.dialogPane;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class DialogPaneAddO implements Initializable {

    @FXML
    private TextField creaNomeOggetto;

    @FXML
    private TextField textFieldReparto;

    @FXML
    private TextField textFieldSocieta;

    @FXML
    private TextField textFieldTitolo;

    @FXML
    private TextField textFieldUnitaLocale;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public String getNome() {
        return creaNomeOggetto.getText();
    }

    public void fillTextBox(String nomeS, String nomeO, String nomeR, String nomeT) {
        textFieldSocieta.setText(nomeS);
        textFieldUnitaLocale.setText(nomeO);
        textFieldReparto.setText(nomeR);
        textFieldTitolo.setText(nomeT);
    }

}
