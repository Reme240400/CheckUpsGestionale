package View.Controllers.Creazione;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.ClassHelper;
import Controllers.ControllerDb;
import Models.ModelListe;
import Models.ModelCreazione;
import Models.Tables.Societa;
import View.Controllers.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

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

    // @FXML
    // private TextField textFieldDesc;

    @FXML
    private JFXComboBox<String> cercaRecord;

    // private Creazione creazione;
    private ModelCreazione modelCreazione;

    private String txtSocieta;
    private String txtIndirizzo;
    private String txtLocalita;
    private String txtTel;
    private String txtProvincia;
    Societa societaTmp = null;
    // private String txtDesc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // * *************** inizializza i campi *************** //
        ControllerDb.popolaListaSocietaDaDb();
        List<Societa> listSocieta = ClassHelper.getListSocieta();
        ObservableList<String> items = FXCollections.observableArrayList();

        // * *************** popola il combobox *************** //
        cercaRecord.getItems().add("Nuovo");
        for (Societa societa : listSocieta) {
            cercaRecord.getItems().add(societa.getNome());
            items.add(societa.getNome());
        }

        // * controlla se vengono inseriti solo numeri
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

        // * **************************************** //

        // * filtra il Combobox
        FilteredList<String> filteredItems = ViewController.filterComboBoxSocieta(cercaRecord, items);

        cercaRecord.setItems(filteredItems);
        // * ************************************************ //

    }

    // * salva la societa
    public void salvaSocieta(javafx.event.ActionEvent event) {

        Societa societaTmp = new Societa(txtSocieta, txtIndirizzo, txtLocalita, txtProvincia, txtTel,
                "");

        // fare la call alla Query
        modelCreazione.setSocietaTmp(societaTmp);

        ModelListe.inserisciRecordInLista(societaTmp);
        modelCreazione.setSocietySaved(true);
        modelCreazione.setSaved(false);
        modelCreazione.setDiscard(false);

    }

    // * elimina la societa
    public void eliminaSocieta() {

        // textFieldDesc.clear();
        textFieldIndirizzo.clear();
        textFieldLocalita.clear();
        textFieldProvincia.clear();
        textFieldSocieta.clear();
        textFieldTel.clear();
        cercaRecord.getSelectionModel().clearSelection();

        modelCreazione.setSaved(false);
        modelCreazione.setDiscard(false);
    }

    // * controlla se i campi sono vuoti
    public void keyReleasedProperty() {
        this.txtSocieta = textFieldSocieta.getText();
        this.txtIndirizzo = textFieldIndirizzo.getText();
        this.txtLocalita = textFieldLocalita.getText();
        this.txtProvincia = textFieldProvincia.getText();
        this.txtTel = textFieldTel.getText();

        modelCreazione.isTextFilled(textFieldSocieta, textFieldIndirizzo, textFieldLocalita,
                textFieldProvincia, textFieldTel);

    }

    // * ************ setta il modelCreazionelo ************ //
    public void setModel(ModelCreazione modelCreazione) {
        this.modelCreazione = modelCreazione;

        this.btnSalva.disableProperty().bind(modelCreazione.savedProperty().not());
        this.btnAnnulla.disableProperty().bind(modelCreazione.discardProperty().not());

        // * ************ setta i campi come sono stati salvati ************ //
        modelCreazione.setOldTextFields(textFieldSocieta, textFieldIndirizzo, textFieldLocalita, textFieldProvincia,
                textFieldTel);
        // * ************************************************ //
    }
}
