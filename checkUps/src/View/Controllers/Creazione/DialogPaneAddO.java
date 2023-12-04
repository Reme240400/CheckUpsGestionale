package View.Controllers.Creazione;

import java.net.URL;
import java.util.ResourceBundle;

import Models.ModelCreazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class DialogPaneAddO implements Initializable{

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

    private ModelCreazione modelCreazione;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public String getNome(){
        return creaNomeOggetto.getText();
    }

    public void setModel(ModelCreazione modelCreazione) {
        this.modelCreazione = modelCreazione;
    }

    public void fillTextBox(String nomeS, String nomeO, String nomeR, String nomeT) {
        textFieldSocieta.setText(nomeS);
        textFieldUnitaLocale.setText(nomeO);
        textFieldReparto.setText(nomeR);
        textFieldTitolo.setText(nomeT);
    }

}
