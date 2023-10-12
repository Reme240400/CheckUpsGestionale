package View.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Models.ModelCreazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class CreazioneSocieta implements Initializable {

    @FXML
    private JFXButton btnAnnulla;

    @FXML
    private JFXButton btnSalva;

    @FXML
    private TextField textFieldSocieta;

    @FXML
    private TextField textFieldIndirizzo;

    @FXML
    private TextField textFieldLocalita;

    @FXML
    private TextField textFieldProvincia;

    @FXML
    private TextField textFieldTel;

    @FXML
    private TextField textFieldDesc;

    // private Creazione creazione;
    private ModelCreazione model;
    private String txtSocieta;
    private String txtIndirizzo;
    private String txtLocalita;
    private String txtTel;
    private String txtProvincia;
    // private String txtDesc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // String txtDesc = textFieldDesc.getText();

    }

    public void salvaSocieta(javafx.event.ActionEvent event) {

        // fare la call alla Query
        model.setSocietySaved(true);

    }

    public void eliminaSocieta() {

    }

    public void keyReleasedProperty() {
        
        txtSocieta = textFieldSocieta.getText();
        txtIndirizzo = textFieldIndirizzo.getText();
        txtLocalita = textFieldLocalita.getText();
        txtProvincia = textFieldProvincia.getText();
        txtTel = textFieldTel.getText();

        boolean areAllDisabled = (txtSocieta.isEmpty() ||
                txtSocieta.trim().isEmpty() ||
                txtIndirizzo.isEmpty() ||
                txtIndirizzo.trim().isEmpty() ||
                txtLocalita.isEmpty() ||
                txtLocalita.trim().isEmpty() ||
                txtProvincia.isEmpty() ||
                txtProvincia.trim().isEmpty() ||
                txtTel.isEmpty() ||
                txtTel.trim().isEmpty());

        boolean isDisabled = (txtSocieta.isEmpty() &&
                txtIndirizzo.isEmpty() &&
                txtLocalita.isEmpty() &&
                txtProvincia.isEmpty() &&
                txtTel.isEmpty());

        model.setSaved(!areAllDisabled);
        model.setDiscard(!isDisabled);

    }

    public void setModel(ModelCreazione model) {
        this.model = model;

        this.btnSalva.disableProperty().bind(model.savedProperty().not());
        this.btnAnnulla.disableProperty().bind(model.discardProperty().not());
    }
}
