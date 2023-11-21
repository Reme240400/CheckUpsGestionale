package View.Controllers.Creazione;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.ClassHelper;
import Controllers.Controller;
import Models.Model;
import Models.ModelCreazione;
import Models.ModelPaths;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import View.Controllers.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class CreazioneUnitaLocale extends Controller implements Initializable {

    @FXML
    private JFXComboBox<String> cercaRecord;

    @FXML
    private JFXButton btnSalvaU;

    @FXML
    private JFXButton btnAnnullaU;

    @FXML
    private JFXButton btnSalvaAggiungiU;

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

    private ModelCreazione modelCreazione;
    private ModelPaths modelPaths;

    private String txtUnitaLocale;
    private String txtIndirizzo;
    private String txtLocalita;
    // private String txtTel;
    private String txtProvincia;

    private Societa societaTmp;
    private List<Societa> listSocieta;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listSocieta = ClassHelper.getListSocieta();
        ObservableList<String> items = FXCollections.observableArrayList();

        // * *************** popola il combobox *************** //

        for (Societa societa : listSocieta) {
            cercaRecord.getItems().add(societa.getNome());
            items.add(societa.getNome());
        }

        // --------------- filtra il Combobox --------------- //
        FilteredList<String> filteredItems = ViewController.filterComboBoxSocieta(cercaRecord, items);

        cercaRecord.setItems(filteredItems);
        
    }

    public void setSocietaId(){

        if (cercaRecord.getValue() != null && !cercaRecord.getValue().equals("")) {
            modelCreazione.createSocietaTmp(listSocieta.stream().filter(s -> s.getNome().equals(cercaRecord.getValue())).findFirst().get());
            setSocieta(modelCreazione.getSocietaTmp());
        }
            
    }

    public void salvaUnitaLocale(javafx.event.ActionEvent event) {

        int id = Model.autoSetId(ClassHelper.getListUnitaLocale());

        UnitaLocale unitaLocale = new UnitaLocale(  id, 
                                                    textFieldUnitaLocale.getText(),
                                                    textFieldIndirizzo.getText(),
                                                    textFieldLocalita.getText(),
                                                    textFieldProvincia.getText(),
                                                    societaTmp.getId());

        inserisciNuovoRecord(unitaLocale);

        modelCreazione.resetSocietaTmp();
        eliminaUnitaLocale();
        
    }

    public void save_addUnitaLocale(){

        int id = Model.autoSetId(ClassHelper.getListUnitaLocale());

        UnitaLocale unitaLocale = new UnitaLocale(  id, 
                                                    textFieldUnitaLocale.getText(),
                                                    textFieldIndirizzo.getText(),
                                                    textFieldLocalita.getText(),
                                                    textFieldProvincia.getText(),
                                                    societaTmp.getId());
        
        inserisciNuovoRecord(unitaLocale);

        //modelCreazione.resetSocietaTmp();
        modelCreazione.createUnitaLocaleTmp(unitaLocale);
        modelCreazione.setSaved(false);
    }

    public void eliminaUnitaLocale() {

        textFieldIndirizzo.clear();
        textFieldLocalita.clear();
        textFieldProvincia.clear();
        textFieldUnitaLocale.clear();
        // textFieldTel.clear();

        modelCreazione.setSaved(false);
        modelCreazione.setDiscard(false);
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

        modelCreazione.setSaved(!areAllDisabled);
        modelCreazione.setDiscard(!isDisabled);

    }

    public void setModel(ModelCreazione model, ModelPaths modelPaths) {
        this.modelCreazione = model;
        this.modelPaths = modelPaths;

        this.textFieldIndirizzo.editableProperty().bind(modelCreazione.isEnableProperty().not());
        this.textFieldLocalita.editableProperty().bind(modelCreazione.isEnableProperty().not());
        this.textFieldProvincia.editableProperty().bind(modelCreazione.isEnableProperty().not());
        this.textFieldUnitaLocale.editableProperty().bind(modelCreazione.isEnableProperty().not());
        this.textFieldTel.editableProperty().bind(modelCreazione.isEnableProperty().not());

        this.btnSalvaU.disableProperty().bind(model.savedProperty().not());
        this.btnAnnullaU.disableProperty().bind(model.discardProperty().not());
        this.btnSalvaAggiungiU.disableProperty().bind(model.savedProperty().not());

    }

    public void setSocieta(Societa societaTmp) {

        if (societaTmp != null) {
            this.societaTmp = societaTmp;
            nomeSocieta.setText(societaTmp.getNome());

            this.textFieldIndirizzo.editableProperty().bind(modelCreazione.isEnableProperty());
            this.textFieldLocalita.editableProperty().bind(modelCreazione.isEnableProperty());
            this.textFieldProvincia.editableProperty().bind(modelCreazione.isEnableProperty());
            this.textFieldUnitaLocale.editableProperty().bind(modelCreazione.isEnableProperty());
            this.textFieldTel.editableProperty().bind(modelCreazione.isEnableProperty());
        } 
    }
}
