package View.Controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.ClassHelper;
import Models.Model;
import Models.ModelCreazione;
import Models.Tables.Societa;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;
import sql.ControllerDb;

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
    private ModelCreazione model;

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
        FilteredList<String> filteredItems = new FilteredList<String>(items, p -> true);

        // Add a listener to the textProperty of the combobox editor. The
        // listener will simply filter the list every time the input is changed
        // as long as the user hasn't selected an item in the list.
        cercaRecord.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = cercaRecord.getEditor();
            final String selected = cercaRecord.getSelectionModel().getSelectedItem();

            // This needs run on the GUI thread to avoid the error described
            // here: https://bugs.openjdk.java.net/browse/JDK-8081700.
            Platform.runLater(() -> {
                // If the no item in the list is selected or the selected item
                // isn't equal to the current input, we refilter the list.
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredItems.setPredicate(item -> {
                        // We return true for any items that starts with the
                        // same letters as the input. We use toUpperCase to
                        // avoid case sensitivity.
                        if (item.toUpperCase().startsWith(newValue.toUpperCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                }
            });
        });

        cercaRecord.setItems(filteredItems);
        // * ************************************************ //

    }

    // * salva la societa
    public void salvaSocieta(javafx.event.ActionEvent event) {

        Societa societaTmp = new Societa(txtSocieta, txtIndirizzo, txtLocalita, txtProvincia, txtTel,
                "");

        // fare la call alla Query
        model.setSocietaTmp(societaTmp);

        Model.inserisciRecordInLista(societaTmp);
        model.setSocietySaved(true);
        model.setSaved(false);
        model.setDiscard(false);

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

        model.setSaved(false);
        model.setDiscard(false);
    }

    // * controlla se i campi sono vuoti
    public void keyReleasedProperty() {
        this.txtSocieta = textFieldSocieta.getText();
        this.txtIndirizzo = textFieldIndirizzo.getText();
        this.txtLocalita = textFieldLocalita.getText();
        this.txtProvincia = textFieldProvincia.getText();
        this.txtTel = textFieldTel.getText();

        model.isTextFilled(textFieldSocieta, textFieldIndirizzo, textFieldLocalita,
                textFieldProvincia, textFieldTel);

    }

    // * ************ setta il modello ************ //
    public void setModel(ModelCreazione model) {
        this.model = model;

        this.btnSalva.disableProperty().bind(model.savedProperty().not());
        this.btnAnnulla.disableProperty().bind(model.discardProperty().not());

        // * ************ setta i campi come sono stati salvati ************ //
        model.setOldTextFields(textFieldSocieta, textFieldIndirizzo, textFieldLocalita, textFieldProvincia,
                textFieldTel);
        // * ************************************************ //
    }
}
