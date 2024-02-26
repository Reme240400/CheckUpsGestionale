package View.Controllers.Creazione;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.Controller;
import Helpers.ClassHelper;
import Interfaces.CreazioneInterface;
import Models.Model;
import Models.ModelCreazione;
import Models.ModelPaths;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

public class CreazioneUnitaLocale implements Initializable, CreazioneInterface {

    @FXML
    private JFXComboBox<String> cercaUnita;

    @FXML
    private JFXButton btnSalva;

    @FXML
    private JFXButton btnAnnulla;

    @FXML
    private JFXButton btnAggiorna;

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

    private Societa localSocieta;
    private List<Societa> listSocieta;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listSocieta = ClassHelper.getListSocieta();
        ObservableList<String> items = FXCollections.observableArrayList();

        // * *************** popola il combobox *************** //

        for (Societa societa : listSocieta) {
            cercaUnita.getItems().add(societa.getNome());
            items.add(societa.getNome());
        }

        // --------------- filtra il Combobox --------------- //
        FilteredList<String> filteredItems = Model.filterComboBox(cercaUnita, items);

        cercaUnita.setItems(filteredItems);

    }

    public void aggiorna() {

        int id = Model.autoSetId(ClassHelper.getListUnitaLocale());

        UnitaLocale unitaLocale = new UnitaLocale(id,
                textFieldUnitaLocale.getText(),
                textFieldIndirizzo.getText(),
                textFieldLocalita.getText(),
                textFieldProvincia.getText(),
                textFieldTel.getText(),
                localSocieta.getId());

        Controller.inserisciNuovoRecord(unitaLocale);

        modelCreazione.resetSocietaTmp();
        pulisciDati();

    }

    public void saveAndGoNext() {

        int id = Model.autoSetId(ClassHelper.getListUnitaLocale());

        UnitaLocale unitaLocale = new UnitaLocale(id,
                textFieldUnitaLocale.getText(),
                textFieldIndirizzo.getText(),
                textFieldLocalita.getText(),
                textFieldProvincia.getText(),
                textFieldTel.getText(),
                localSocieta.getId());

        Controller.inserisciNuovoRecord(unitaLocale);

        modelCreazione.createSocietaTmp(localSocieta);
        modelCreazione.createUnitaLocaleTmp(unitaLocale);
        modelCreazione.setCanGoNext(false);
        modelCreazione.setSaved(false);

        try {
            Parent root = modelPaths.switchToCreazioneReparti(modelCreazione);

            Controller.changePane(modelPaths.getStackPaneCrea(), root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pulisciDati() {

        textFieldIndirizzo.clear();
        textFieldLocalita.clear();
        textFieldProvincia.clear();
        textFieldUnitaLocale.clear();
        textFieldTel.clear();

        modelCreazione.resetUnitaLocaleTmp();
        modelCreazione.setCanGoNext(false);
        modelCreazione.setSaved(false);
        modelCreazione.setDiscard(false);
    }

    public void keyReleasedProperty() {

        modelCreazione.areTextFieldsFilled(textFieldUnitaLocale, textFieldIndirizzo, textFieldLocalita,
                textFieldProvincia);

    }

    public void setSocieta() {

        if (cercaUnita.getValue() != null && !cercaUnita.getValue().equals("")) {
            if (localSocieta != null) {
                if (!cercaUnita.getValue().equals(nomeSocieta.getText())) {
                    modelCreazione.resetAllTmp();
                }
            }

            this.localSocieta = listSocieta.stream()
                    .filter(s -> s.getNome().equals(cercaUnita.getValue()))
                    .findFirst().get();

            this.textFieldIndirizzo.editableProperty().bind(modelCreazione.isEnableProperty());
            this.textFieldLocalita.editableProperty().bind(modelCreazione.isEnableProperty());
            this.textFieldProvincia.editableProperty().bind(modelCreazione.isEnableProperty());
            this.textFieldUnitaLocale.editableProperty().bind(modelCreazione.isEnableProperty());
            nomeSocieta.setText(localSocieta.getNome());

        }
    }

    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths) {

        this.modelCreazione = modelCreazione;
        this.modelPaths = modelPaths;

        this.btnAggiorna.disableProperty().bind(modelCreazione.savedProperty().not());
        this.btnAnnulla.disableProperty().bind(modelCreazione.discardProperty().not());
        this.btnSalva.disableProperty().bind(modelCreazione.savedProperty().not());

        if (modelCreazione.getSocietaTmp() != null) {
        
            this.localSocieta = modelCreazione.getSocietaTmp();
            nomeSocieta.setText(modelCreazione.getSocietaTmp().getNome());

        }
    }
}
