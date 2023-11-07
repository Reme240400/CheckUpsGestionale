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
import Models.ModelModifica;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.IntegerStringConverter;

public class Modifica implements Initializable {

    // ----------------- Societa ----------------- //
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
    // ----------------- Societa ----------------- //

    // ----------------- Unita Locale ----------------- //
    @FXML
    private JFXComboBox<String> cercaRecordU;
    
    @FXML
    private TextField textFieldNomeU;

    @FXML
    private TextField textFieldLocalitaU;

    @FXML
    private TextField textFieldIndirizzoU;

    @FXML
    private TextField textFieldProvinciaU;
    // ----------------- Unita Locale ----------------- //

    @FXML
    private DialogPane dialogPane;

    @FXML
    private JFXComboBox<Societa> cercaSocieta;

    private ModelModifica modelModifica;

    List<Societa> listSocieta = ClassHelper.getListSocieta();
    List<UnitaLocale> listUnitaLocale = ClassHelper.getListUnitaLocale();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // * *************** inizializza i campi *************** //
        
        ObservableList<String> sItems = FXCollections.observableArrayList();
        ObservableList<String> uItems = FXCollections.observableArrayList();

        // * *************** popola il combobox *************** //
        for (Societa societa : listSocieta) {
            cercaRecordS.getItems().add(societa.getNome());
            sItems.add(societa.getNome());
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
    }

    // --------------- Riempi i campi con i dati della societa selezionata --------------- //
    public void fillTextFieldS(KeyEvent event){
        if (event.getCode().toString().equals("ENTER")){
            modelModifica.fillTextField( cercaRecordS, textFieldNomeS, textFieldIndirizzo, textFieldLocalita, textFieldProvincia, textFieldTel);
        }
    }

    // --------------- Riempi i campi con i dati dell'unita locale selezionata --------------- //
    public void fillTextFieldU(KeyEvent event){
        if (event.getCode().toString().equals("ENTER")){
            int id = cercaSocieta.getSelectionModel().getSelectedItem().getId();
            modelModifica.fillTextField( cercaRecordU, id, textFieldNomeU, textFieldIndirizzoU, textFieldLocalitaU, textFieldProvinciaU);
        }
    }

    // --------------- Salva le modifiche --------------- //
    public void updateChanges(){

    }

    // --------------- Mostra il dialogPane per filtrare l'Unita Locale --------------- //
    public int showDialogPane() throws IOException {

        // viene triggherato il metodo anche in uscita dalla tab

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/modifica_dialogPane.fxml"));
        DialogPane dialogPane = loader.load();

        DialogPane1 dialogController = loader.getController();

        dialogController.setModel(modelModifica);

        
        return 0;
    }
    
    // ----------------- Setta il model ----------------- //
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
