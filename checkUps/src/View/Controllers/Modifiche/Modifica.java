package View.Controllers.Modifiche;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;

import Controllers.ClassHelper;
import Controllers.ControllerDb;
import Models.ModelModifica;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.IntegerStringConverter;

public class Modifica implements Initializable {

    @FXML
    private JFXButton btnSaveS;

    @FXML
    private JFXComboBox<String> cercaRecordS;

    @FXML
    private TextField textFieldIndirizzo;

    @FXML
    private TextField textFieldLocalita;

    @FXML
    private TextField textFieldProvincia;

    @FXML
    private TextField textFieldTel;

    @FXML
    private TextField textFieldNomeS;

    @FXML
    private DialogPane dialogPane; // Inject your dialog pane

    private ModelModifica modelModifica;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // * *************** inizializza i campi *************** //
        ControllerDb.popolaListaSocietaDaDb();
        List<Societa> listSocieta = ClassHelper.getListSocieta();
        List<UnitaLocale> listUnitaLocale = ClassHelper.getListUnitaLocale();
        ObservableList<String> sItems = FXCollections.observableArrayList();
        ObservableList<String> uItems = FXCollections.observableArrayList();

        /*
         * FilteredList<String> filteredItems =
         * ViewController.filterComboBoxSocieta(cercaRecordS, sItems);
         * 
         * cercaRecordS.setItems(filteredItems);
         * 
         * FilteredList<String> filteredItems2 =
         * ViewController.filterComboBoxById(cercaRecordU, uItems);
         * 
         * cercaRecordU.setItems(filteredItems2);
         */

        // * *************** popola il combobox *************** //
        for (Societa societa : listSocieta) {
            cercaRecordS.getItems().add(societa.getNome());
            sItems.add(societa.getNome());
        }

        // * filtra il Combobox

        // * ************************************************ //

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
    }

    public void fillTextField(KeyEvent event){
        if (event.getCode().toString().equals("ENTER")){
            modelModifica.fillTextField( cercaRecordS, textFieldNomeS, textFieldIndirizzo, textFieldLocalita, textFieldProvincia, textFieldTel);
        }
    }

    public void updateChanges(){

    }

    public int showDialogPane() throws IOException {

        // viene triggherato il metodo anche in uscita dalla tab

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/modifica_dialogPane.fxml"));
        DialogPane dialogPane = loader.load();

        DialogPane1 dialogController = loader.getController();

        dialogController.setModel(modelModifica);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);

        dialog.showAndWait();

        return 0;
    }
    
    public void setModel(ModelModifica modelModifica) {
        this.modelModifica = modelModifica;

        this.btnSaveS.disableProperty().bind(modelModifica.savedProperty().not());

        this.textFieldIndirizzo.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldLocalita.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldProvincia.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldNomeS.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldTel.editableProperty().bind(modelModifica.isEnableProperty());

    }

}
