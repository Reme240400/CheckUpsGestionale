package View.Controllers.Creazione.DialogPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Models.ModelCreazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogPaneAddR implements Initializable{

    @FXML
    private TextArea creaDescReparto;

    @FXML
    private TextField creaNomeReparto;

    @FXML
    private TextField creaRevisioneReparto;

    @FXML
    private DatePicker creaDataReparto;

    @FXML
    private TextField textFieldSocieta;

    @FXML
    private TextField textFieldUnitaLocale;

    private ModelCreazione modelCreazione;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public String getNome(){
        return creaNomeReparto.getText();
    }

    public String getRevisione(){
        return creaRevisioneReparto.getText();
    }

    public LocalDate getData(){
        return creaDataReparto.getValue();    
    }

    public String getDesc(){
        return creaDescReparto.getText();
    }

    public void fillTextBox(String nomeSocieta, String nomeUnita){
        textFieldSocieta.setText(nomeSocieta);
        textFieldUnitaLocale.setText(nomeUnita);
    }

    public void setModel(ModelCreazione modelCreazione){
        this.modelCreazione = modelCreazione;
    }
    
}
