package View.Controllers.Creazione;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.Optional;

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
import View.Controllers.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

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
    private List<UnitaLocale> listUnitaLocale;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        UnaryOperator<TextFormatter.Change> filter = change -> {

            // remove any non-digit characters from inserted text:
            if (!change.getText().matches("\\d*")) {
                change.setText(change.getText().replaceAll("[^\\d]", ""));
            }

            if (change.getControlNewText().length() > 15) {
                return null;
            }

            return change;
        };

        TextFormatter<Integer> formatter = new TextFormatter<Integer>(new IntegerStringConverter(), null, filter);
        textFieldTel.setTextFormatter(formatter);

        listUnitaLocale = ClassHelper.getListUnitaLocale();

    }

    public void setOldTextFields() {
        if (modelCreazione.getUnitaLocaleTmp() != null) {
            textFieldUnitaLocale.setText(modelCreazione.getUnitaLocaleTmp().getNome());
            textFieldLocalita.setText(modelCreazione.getUnitaLocaleTmp().getLocalita());
            textFieldIndirizzo.setText(modelCreazione.getUnitaLocaleTmp().getIndirizzo());
            textFieldProvincia.setText(modelCreazione.getUnitaLocaleTmp().getProvincia());
            textFieldTel.setText(String.valueOf(modelCreazione.getUnitaLocaleTmp().getTelefono()));
            cercaUnita.setValue(modelCreazione.getUnitaLocaleTmp().getNome());
        }
    }

    public void selezionaUnita() {
        Optional<UnitaLocale> curUnita = listUnitaLocale.stream()
                .filter(u -> u.getIdSocieta() == localSocieta.getId())
                .filter(s -> s.getNome().equals(cercaUnita.getValue()))
                .findFirst();

        if (curUnita.isEmpty())
            return;

        UnitaLocale unita = curUnita.get();
        textFieldUnitaLocale.setText(unita.getNome());
        textFieldLocalita.setText(unita.getLocalita());
        textFieldProvincia.setText(unita.getProvincia());
        textFieldTel.setText(unita.getTelefono());
        textFieldIndirizzo.setText(unita.getIndirizzo());

        modelCreazione.createUnitaLocaleTmp(unita);
        modelCreazione.setCanGoNext(true);
        modelCreazione.setDiscard(true);
    }

    public void goBack() {
        try {
            modelCreazione.resetUnitaLocaleTmp();
            Parent root = modelPaths.switchToCreazioneSocieta(modelCreazione);

            Controller.changePane(modelPaths.getStackPaneCrea(), root);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        if (cercaUnita.getValue() == null) {
            int id = Model.autoSetId(ClassHelper.getListUnitaLocale());

            UnitaLocale unitaLocale = new UnitaLocale(id,
                    textFieldUnitaLocale.getText(),
                    textFieldIndirizzo.getText(),
                    textFieldLocalita.getText(),
                    textFieldProvincia.getText(),
                    textFieldTel.getText(),
                    localSocieta.getId());

            Controller.inserisciNuovoRecord(unitaLocale);

            modelCreazione.createUnitaLocaleTmp(unitaLocale);
        }

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

        cercaUnita.getSelectionModel().clearSelection();
        modelCreazione.setCanGoNext(false);
        modelCreazione.setSaved(false);
        modelCreazione.setDiscard(false);
    }

    public void keyReleasedProperty() {

        modelCreazione.areTextFieldsFilled(textFieldUnitaLocale, textFieldIndirizzo, textFieldLocalita,
                textFieldProvincia, textFieldTel);

    }

    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths) {

        this.modelCreazione = modelCreazione;
        this.modelPaths = modelPaths;

        this.btnAggiorna.disableProperty().bind(modelCreazione.savedProperty().not());
        this.btnAnnulla.disableProperty().bind(modelCreazione.discardProperty().not());
        this.btnSalva.disableProperty().bind(modelCreazione.canGoNextProperty().not());

        if (modelCreazione.getSocietaTmp() != null) {

            this.localSocieta = modelCreazione.getSocietaTmp();

            ObservableList<String> unitalocali = FXCollections.observableArrayList(listUnitaLocale.stream()
                    .filter(u -> u.getIdSocieta() == localSocieta.getId())
                    .map(UnitaLocale::getNome)
                    .collect(Collectors.toList()));

            FilteredList<String> filteredItems = ViewController.filterComboBox(cercaUnita, unitalocali);
            cercaUnita.setItems(filteredItems);

        }
    }
}
