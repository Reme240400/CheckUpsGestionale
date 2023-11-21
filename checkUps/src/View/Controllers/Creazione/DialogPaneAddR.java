package View.Controllers.Creazione;

import java.net.URL;
import java.util.ResourceBundle;

import Models.ModelCreazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class DialogPaneAddR implements Initializable{

    @FXML
    private DatePicker creaDescReparto;

    @FXML
    private TextField creaNomeReparto;

    @FXML
    private TextField textFieldSocieta;

    @FXML
    private TextField textFieldUnitaLocale;

    private ModelCreazione modelCreazione;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public String getNome(){
        if(!creaNomeReparto.getText().isEmpty())
            return creaNomeReparto.getText();
        else
            return null;
    }

    public String getData(){
        if(!creaDescReparto.getPromptText().isEmpty())
            return creaDescReparto.getPromptText();
        else
            return null;
    }

    public void fillTextBox(String nomeSocieta, String nomeUnita){
        textFieldSocieta.setText(nomeSocieta);
        textFieldUnitaLocale.setText(nomeUnita);
    }

    public void setModel(ModelCreazione modelCreazione){
        this.modelCreazione = modelCreazione;
    }
    
}
