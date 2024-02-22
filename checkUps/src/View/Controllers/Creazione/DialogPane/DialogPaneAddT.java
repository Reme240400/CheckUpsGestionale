package View.Controllers.Creazione.DialogPane;

import java.net.URL;
import java.util.ResourceBundle;

import Models.ModelCreazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class DialogPaneAddT implements Initializable{

    @FXML
    private TextField creaNome;

    @FXML
    private TextField textFieldSocieta;

    @FXML
    private TextField textFieldUnitaLocale;

    @FXML
    private TextField textFieldReparto;

    private ModelCreazione modelCreazione;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public String getNome(){
    
        return creaNome.getText();
    
    }

    public void fillTextBox(String nomeSocieta, String nomeUnita, String nomeReparto ){
        textFieldSocieta.setText(nomeSocieta);
        textFieldUnitaLocale.setText(nomeUnita);
        textFieldReparto.setText(nomeReparto);
    }

    public void setModel(ModelCreazione modelCreazione){
        this.modelCreazione = modelCreazione;
    }
    
}