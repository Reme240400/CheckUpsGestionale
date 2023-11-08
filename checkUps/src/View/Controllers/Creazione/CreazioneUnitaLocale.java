package View.Controllers.Creazione;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Controllers.ClassHelper;
import Controllers.Controller;
import Models.ModelCreazione;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class CreazioneUnitaLocale implements Initializable {

    @FXML
    private JFXButton btnSalva;

    @FXML
    private JFXButton btnAnnulla;

    @FXML
    private TextField nomeSocieta;

    @FXML
    private TextField textFieldUnitaLocale;

    @FXML
    private TextField textFieldIndirizzo;

    @FXML
    private TextField textFieldLocalita;

    @FXML
    private TextField textFieldProvincia;

    @FXML
    private TextField textFieldTel;

    private ModelCreazione model;
    private Controller controller;

    private String txtUnitaLocale;
    private String txtIndirizzo;
    private String txtLocalita;
    // private String txtTel;
    private String txtProvincia;

    private Societa societaTmp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = new Controller();
    }

    public void salvaUnitaLocale(javafx.event.ActionEvent event) {

        if (societaTmp == null) {

            controller.inserisciNuovoRecord(societaTmp);
        }

            int id = controller.getNewId(ClassHelper.getListUnitaLocale());

            UnitaLocale unitaLocale = new UnitaLocale(  id, 
                                                        textFieldUnitaLocale.getText(),
                                                        textFieldIndirizzo.getText(),
                                                        textFieldLocalita.getText(),
                                                        textFieldProvincia.getText(),
                                                        societaTmp.getId());

            controller.inserisciNuovoRecord(unitaLocale);

            model.setUnitaSaved(true);
            model.setSaved(false);
            model.setDiscard(false);
        

    }

    public void eliminaUnitaLocale() {

        textFieldIndirizzo.clear();
        textFieldLocalita.clear();
        textFieldProvincia.clear();
        textFieldUnitaLocale.clear();
        // textFieldTel.clear();

        model.setSaved(false);
        model.setDiscard(false);
    }

    public void keyReleasedProperty() {

        txtUnitaLocale = textFieldUnitaLocale.getText();
        txtIndirizzo = textFieldIndirizzo.getText();
        txtLocalita = textFieldLocalita.getText();
        txtProvincia = textFieldProvincia.getText();
        // txtTel = textFieldTel.getText();

        boolean areAllDisabled = (txtUnitaLocale.isEmpty() ||
                txtUnitaLocale.trim().isEmpty() ||
                txtIndirizzo.isEmpty() ||
                txtIndirizzo.trim().isEmpty() ||
                txtLocalita.isEmpty() ||
                txtLocalita.trim().isEmpty() ||
                txtProvincia.isEmpty() ||
                txtProvincia.trim().isEmpty()
        /*
         * txtTel.isEmpty() ||
         * txtTel.trim().isEmpty()
         */);

        boolean isDisabled = (txtUnitaLocale.isEmpty() &&
                txtIndirizzo.isEmpty() &&
                txtLocalita.isEmpty() &&
                txtProvincia.isEmpty() /*
                                        * &&
                                        * txtTel.isEmpty()
                                        */);

        model.setSaved(!areAllDisabled);
        model.setDiscard(!isDisabled);

    }

    public void setModel(ModelCreazione model) {
        this.model = model;

        this.btnSalva.disableProperty().bind(model.savedProperty().not());
        this.btnAnnulla.disableProperty().bind(model.discardProperty().not());

    }

    public void setSocieta(Societa societaTmp) {

        this.societaTmp = societaTmp;

        nomeSocieta.setText(societaTmp.getNome());
    }
}
