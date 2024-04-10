package View.Controllers.Creazione;

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
import Models.Model;
import Models.ModelCreazione;
import Models.ModelPaths;
import Models.Tables.UnitaLocale;
import Models.others.CreazioneBase;
import Models.others.TipoCreazionePagina;
import View.Controllers.ViewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.LongStringConverter;

public class CreazioneUnitaLocale extends CreazioneBase implements Initializable {

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

    @FXML
    private TextField textFieldEmail;

    private List<UnitaLocale> listUnitaLocale;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listUnitaLocale = ClassHelper.getListUnitaLocale();

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*") && change.getControlNewText().length() <= 15) {
                return change;
            }

            return null;
        };

        TextFormatter<Long> formatter = new TextFormatter<Long>(new LongStringConverter(), null, filter);
        textFieldTel.setTextFormatter(formatter);

    }

    public void setOldTextFields() {
        if (modelCreazione.getUnitaLocaleTmp() == null)
            return;

        textFieldUnitaLocale.setText(modelCreazione.getUnitaLocaleTmp().getNome());
        textFieldLocalita.setText(modelCreazione.getUnitaLocaleTmp().getLocalita());
        textFieldIndirizzo.setText(modelCreazione.getUnitaLocaleTmp().getIndirizzo());
        textFieldProvincia.setText(modelCreazione.getUnitaLocaleTmp().getProvincia());
        textFieldTel.setText(String.valueOf(modelCreazione.getUnitaLocaleTmp().getTelefono()));
        textFieldEmail.setText(modelCreazione.getUnitaLocaleTmp().getEmail());
        cercaUnita.setValue(modelCreazione.getUnitaLocaleTmp().getNome());

        modelCreazione.setCanGoNext(true);
    }

    public void selezionaUnita() {
        // Reset dei bottoni
        modelCreazione.setSaved(false);
        modelCreazione.setCanGoNext(false);

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
        textFieldIndirizzo.setText(unita.getIndirizzo());
        textFieldTel.setText(unita.getTelefono());
        textFieldEmail.setText(unita.getEmail());

        modelCreazione.setCanGoNext(true);
        modelCreazione.createUnitaLocaleTmp(unita);
    }

    @FXML
    public void onAggiorna() {
        UnitaLocale unitaLocale = new UnitaLocale(modelCreazione.getUnitaLocaleTmp().getId(),
                localSocieta.getId(),
                textFieldUnitaLocale.getText(),
                textFieldIndirizzo.getText(),
                textFieldLocalita.getText(),
                textFieldProvincia.getText(),
                textFieldTel.getText(),
                textFieldEmail.getText());

        Controller.modificaCampo(unitaLocale);
        modelCreazione.createUnitaLocaleTmp(unitaLocale);
        modelCreazione.setSaved(false);
    }

    public void pulisciDati() {
        textFieldIndirizzo.clear();
        textFieldLocalita.clear();
        textFieldProvincia.clear();
        textFieldUnitaLocale.clear();
        textFieldTel.clear();
        textFieldEmail.clear();

        cercaUnita.getSelectionModel().clearSelection();

        modelCreazione.resetUnitaLocaleTmp();
        modelCreazione.setSaved(false);
        modelCreazione.setCanGoNext(false);
    }

    @FXML
    public void keyReleasedProperty() {
        boolean neededFieldsValid = this.checkNeededTextFields(textFieldUnitaLocale, textFieldIndirizzo,
                textFieldProvincia);

        // sto creando una società nuova
        if (cercaUnita.getValue() == null) {
            modelCreazione.setSaved(neededFieldsValid);
            modelCreazione.setCanGoNext(neededFieldsValid);
        } else {
            var dataChanged = this.isFormDataChanged();
            var set = neededFieldsValid && dataChanged;

            modelCreazione.setCanGoNext(neededFieldsValid);
            modelCreazione.setSaved(set);
        }
    }

    @FXML
    public void onSaveAndGoNext() {
        if (cercaUnita.getValue() == null) {
            int id = Model.autoSetId(ClassHelper.getListUnitaLocale());

            UnitaLocale unitaLocale = new UnitaLocale(id,
                    localSocieta.getId(),
                    textFieldUnitaLocale.getText(),
                    textFieldIndirizzo.getText(),
                    textFieldLocalita.getText(),
                    textFieldProvincia.getText(),
                    textFieldTel.getText(),
                    textFieldEmail.getText());

            Controller.inserisciNuovoRecord(unitaLocale);
            modelCreazione.createUnitaLocaleTmp(unitaLocale);
        } else if (this.isFormDataChanged())
            this.onAggiorna();

        super.changePage(TipoCreazionePagina.REPARTO, true);
    }

    public void onActionBack() {
        modelCreazione.resetUnitaLocaleTmp();
        super.changePage(TipoCreazionePagina.SOCIETA, false);
    }

    @Override
    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths) {
        super.setModel(modelCreazione, modelPaths);

        this.btnAggiorna.disableProperty().bind(modelCreazione.savedProperty().not());
        this.btnSalva.disableProperty().bind(modelCreazione.canGoNextProperty().not());

        ObservableList<String> unitalocali = FXCollections.observableArrayList(listUnitaLocale.stream()
                .filter(u -> u.getIdSocieta() == this.localSocieta.getId())
                .map(UnitaLocale::getNome)
                .collect(Collectors.toList()));

        FilteredList<String> filteredItems = ViewController.filterComboBox(cercaUnita, unitalocali);
        cercaUnita.setItems(filteredItems);
    }

    public boolean isFormDataChanged() {
        var uLocale = modelCreazione.getUnitaLocaleTmp();

        return isSingleFieldChanged(uLocale.getNome(), textFieldUnitaLocale) ||
                isSingleFieldChanged(uLocale.getIndirizzo(), textFieldIndirizzo) ||
                isSingleFieldChanged(uLocale.getLocalita(), textFieldLocalita) ||
                isSingleFieldChanged(uLocale.getProvincia(), textFieldProvincia) ||
                isSingleFieldChanged(uLocale.getProvincia(), textFieldProvincia) ||
                isSingleFieldChanged(uLocale.getEmail(), textFieldEmail);
    }
}
