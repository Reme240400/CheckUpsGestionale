package View.Controllers.Creazione;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Models.ModelCreazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;


public class CreazioneUnitaLocale implements Initializable{
    
    @FXML
    JFXButton btnSalva;

    @FXML
    JFXButton btnAnnulla;

    @FXML
    TextField textFieldUnitaLocale;

    @FXML
    TextField textFieldIndirizzo;

    @FXML
    TextField textFieldLocalita;

    @FXML
    TextField textFieldProvincia;

    @FXML
    TextField textFieldTel;

    private ModelCreazione model;

    private String txtUnitaLocale;
    private String txtIndirizzo;
    private String txtLocalita;
   // private String txtTel;
    private String txtProvincia;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public void salvaUnitaLocale(javafx.event.ActionEvent event) {

        // fare la call alla Query
        model.setUnitaSaved(true);
        model.setSaved(false);
        model.setDiscard(false);

    }

    public void eliminaUnitaLocale() {

        textFieldIndirizzo.clear();
        textFieldLocalita.clear();
        textFieldProvincia.clear();
        textFieldUnitaLocale.clear();
        //textFieldTel.clear();

        model.setSaved(false);
        model.setDiscard(false);
    }

    public void keyReleasedProperty() {
        
        txtUnitaLocale = textFieldUnitaLocale.getText();
        txtIndirizzo = textFieldIndirizzo.getText();
        txtLocalita = textFieldLocalita.getText();
        txtProvincia = textFieldProvincia.getText();
        //txtTel = textFieldTel.getText();

        boolean areAllDisabled = (txtUnitaLocale.isEmpty() ||
                txtUnitaLocale.trim().isEmpty() ||
                txtIndirizzo.isEmpty() ||
                txtIndirizzo.trim().isEmpty() ||
                txtLocalita.isEmpty() ||
                txtLocalita.trim().isEmpty() ||
                txtProvincia.isEmpty() ||
                txtProvincia.trim().isEmpty() 
                /*txtTel.isEmpty() ||
                txtTel.trim().isEmpty()*/);

        boolean isDisabled = (txtUnitaLocale.isEmpty() &&
                txtIndirizzo.isEmpty() &&
                txtLocalita.isEmpty() &&
                txtProvincia.isEmpty() /*&&
                txtTel.isEmpty()*/);

        model.setSaved(!areAllDisabled);
        model.setDiscard(!isDisabled);

    }

    public void setModel(ModelCreazione model) {
        this.model = model;

        this.btnSalva.disableProperty().bind(model.savedProperty().not());
        this.btnAnnulla.disableProperty().bind(model.discardProperty().not());

    }
}
