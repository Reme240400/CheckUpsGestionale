package View.Controllers.Creazione;

import java.net.URL;
import java.util.ResourceBundle;

import Models.ModelCreazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogPaneAddP implements Initializable{ 

    @FXML
    private TextField creaNome;

    @FXML
    private TextArea creaRischio;

    @FXML
    private TextField creaSoggettiEsposti;

    @FXML
    private Spinner<Integer> setStimaD;

    @FXML
    private Spinner<Integer> setStimaP;

    @FXML
    private Spinner<Integer> setStimaR;

    @FXML
    private TextField textFieldOggetto;

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
    public void initialize(URL location, ResourceBundle resources) {
    }

    public String getNome(){
        return creaNome.getText();
    }

    public String getRischio(){
        return creaRischio.getText();
    }

    public String getSoggettiEsposti(){
        return creaSoggettiEsposti.getText();
    }

    public int getStimaD(){
        return setStimaD.getValue();
    }

    public int getStimaP(){
        return setStimaP.getValue();
    }

    public int getStimaR(){
        return setStimaR.getValue();
    }

    public void setModel(ModelCreazione modelCreazione) {
        this.modelCreazione = modelCreazione;
    }

    public void fillTextBox(String nomeS, String nomeU, String nomeR, String nomeT, String nomeO ) {

        textFieldSocieta.setText(nomeS);
        textFieldUnitaLocale.setText(nomeU);
        textFieldReparto.setText(nomeR);
        textFieldTitolo.setText(nomeT);
        textFieldOggetto.setText(nomeO);
    }



}
