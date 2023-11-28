package View.Controllers.Modifiche;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXButton;

import Controllers.ClassHelper;
import Controllers.Controller;
import Models.ModelModifica;
import Models.ModelPaths;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import View.Controllers.ViewController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.StackPane;
import javafx.util.converter.IntegerStringConverter;

public class Modifica implements Initializable {

    @FXML
    private TabPane tabPane;

    @FXML
    private TextField filterTable;

    @FXML
    private JFXButton btnTitoli;

    @FXML
    private JFXButton btnReparti;

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
    private Tab tabUnitaLocale;

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

    @FXML
    private JFXButton btnSaveU;

    // ----------------- Unita Locale ----------------- //

    @FXML
    private Tab tabReparti_Titoli;

    @FXML
    private StackPane titoli_repartiStackPane;

    @FXML
    private DialogPane dialogPane;

    private ModelModifica modelModifica;
    private ModelPaths modelPaths;

    private List<Societa> listSocieta = ClassHelper.getListSocieta();
    private List<UnitaLocale> listUnitaLocale = ClassHelper.getListUnitaLocale();

    private ObservableList<String> uItems = FXCollections.observableArrayList();
    private ObservableList<String> sItems = FXCollections.observableArrayList();

    private int idSocieta = -1;
    // private int idUnitaLocale = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // --------------- controlla se vengono inseriti solo numeri --------------- //
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
        // ------------------------------------------------------------------------- //
        popolaComboBoxS();
    }

    private void popolaComboBoxS() {
        // ---------------- popola il combobox ---------------- //
        for (Societa societa : listSocieta) {
            sItems.add(societa.getNome());
        }

        cercaRecordS.setItems(sItems);

        // --------------- filtra il Combobox --------------- //
        FilteredList<String> filteredItems = ViewController.filterComboBoxSocieta(cercaRecordS, sItems);

        cercaRecordS.setItems(filteredItems);
    }

    private void popolaComboBoxU() {
        // ---------------- popola il combobox ---------------- //
        for (UnitaLocale unitaLocale : listUnitaLocale) {
            if (unitaLocale.getIdSocieta() == idSocieta) {
                uItems.add(unitaLocale.getNome());
            }
        }

        cercaRecordU.setItems(ViewController.filterComboBoxUnitaLocale(cercaRecordU, idSocieta, uItems));

        // --------------- filtra il Combobox --------------- //
        FilteredList<String> filteredItems = ViewController.filterComboBoxUnitaLocale(cercaRecordU, idSocieta, uItems);

        cercaRecordU.setItems(filteredItems);
    }

    // --------------- Riempi i campi con i dati della societa selezionata
    // --------------- //
    public void fillTextFieldS() {
        if (cercaRecordS.getValue() != null && !cercaRecordS.getValue().equals("")) {
            modelModifica.fillTextField(cercaRecordS, textFieldNomeS, textFieldIndirizzo, textFieldLocalita,
                    textFieldProvincia, textFieldTel);

            Societa societaTmp = listSocieta.stream().filter(s -> s.getNome().equals(cercaRecordS.getValue()))
                    .findFirst().get();
            modelModifica.setSocieta(societaTmp);
        }
    }

    // --------------- Riempi i campi con i dati dell'unita locale selezionata
    // --------------- //
    public void fillTextFieldU() {
        if (cercaRecordU.getValue() != null && !cercaRecordU.getValue().equals("")) {
            int id = modelModifica.getSocietaTmp().getId();
            modelModifica.fillTextField(cercaRecordU, id, textFieldNomeU, textFieldIndirizzoU, textFieldLocalitaU,
                    textFieldProvinciaU);

            UnitaLocale unitaLocaleTmp = listUnitaLocale.stream()
                    .filter(u -> u.getNome().equals(cercaRecordU.getValue())).findFirst().get();
            modelModifica.setUnitaLocale(unitaLocaleTmp);
        }
    }

    // --------------- Salva le modifiche --------------- //
    public void updateChanges() {

        if (tabPane.getSelectionModel().getSelectedIndex() == 0 && modelModifica.getSocietaTmp() != null) {
            modelModifica.getSocietaTmp().setNome(textFieldNomeS.getText());
            modelModifica.getSocietaTmp().setIndirizzo(textFieldIndirizzo.getText());
            modelModifica.getSocietaTmp().setLocalita(textFieldLocalita.getText());
            modelModifica.getSocietaTmp().setProvincia(textFieldProvincia.getText());
            modelModifica.getSocietaTmp().setTelefono(textFieldTel.getText());

            Controller.modificaCampo(modelModifica.getSocietaTmp());
            popolaComboBoxS();

        } else if (tabPane.getSelectionModel().getSelectedIndex() == 1 && modelModifica.getUnitaLocaleTmp() != null) {
            modelModifica.getUnitaLocaleTmp().setNome(textFieldNomeU.getText());
            modelModifica.getUnitaLocaleTmp().setIndirizzo(textFieldIndirizzoU.getText());
            modelModifica.getUnitaLocaleTmp().setLocalita(textFieldLocalitaU.getText());
            modelModifica.getUnitaLocaleTmp().setProvincia(textFieldProvinciaU.getText());
            
            Controller.modificaCampo(modelModifica.getUnitaLocaleTmp());
            popolaComboBoxU();
        }
    }

    // --------------- Mostra il dialogPane per filtrare l'Unita Locale --------------- //
    public void showUnitaPane() throws IOException {

        // ------------------- Mostra il dialogPane dell'Unita Locale ------------------- //
        if (tabUnitaLocale.isSelected()) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/modifica_dialogPane.fxml"));
            DialogPane dialogPane = loader.load();

            DialogPane1 dialogController = loader.getController();

            dialogController.setModel(modelModifica);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Scegli la Società");

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            // ------------------- Se viene premuto il tasto "Applica" -------------------
            // //
            if (clickedButton.get() == ButtonType.APPLY) {
                if (modelModifica.getSocietaTmp() != null) {
                    // prende l'id della societa selezionata //
                    this.idSocieta = modelModifica.getSocietaTmp().getId();

                    popolaComboBoxU();

                } else {
                    tabPane.getSelectionModel().select(0);
                }
            } else {
                tabPane.getSelectionModel().select(0);
            }
        }
    }

    public void showRepartiTitoliDialogPane() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/modifica_reparto_dialogPane.fxml"));
        DialogPane dialogPane = loader.load();

        DialogPane2 dialogController = loader.getController();

        dialogController.setModel(modelModifica);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.setTitle("Scegli la Unità Locale");

        Optional<ButtonType> clickedButton = dialog.showAndWait();

        // ------------------- Se viene premuto il tasto "Applica" -------------------
        // //

        if (clickedButton.get() == ButtonType.APPLY) {
            showRepartoPane();
        } else {
            tabPane.getSelectionModel().select(0);
        }

    }

    // ------------------- Mostra il dialogPane dei Reparti ------------------- //
    public void showRepartoPane() throws IOException {

        Parent root = modelPaths.switchToModificaReparto(modelModifica);

        if (root != null) {
            titoli_repartiStackPane.getChildren().removeAll();
            titoli_repartiStackPane.getChildren().setAll(root);
        }
    }

    // ------------------- Mostra il dialogPane dei Titoli ------------------- //
    public void showTitoliPane() throws IOException {

        Parent root = modelPaths.switchToModificaTitoli(modelModifica);

        if (root != null) {
            titoli_repartiStackPane.getChildren().removeAll();
            titoli_repartiStackPane.getChildren().setAll(root);
        }
    }

    // ----------------- Setta il model ----------------- //
    public void setModel(ModelModifica modelModifica, ModelPaths modelPaths) {
        this.modelModifica = modelModifica;
        this.modelPaths = modelPaths;

        this.btnSaveS.disableProperty().bind(modelModifica.savedProperty().not());
        this.btnSaveU.disableProperty().bind(modelModifica.savedProperty().not());
        this.btnTitoli.disableProperty().bind(modelModifica.savedProperty().not());

        this.textFieldIndirizzo.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldLocalita.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldProvincia.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldNomeS.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldTel.editableProperty().bind(modelModifica.isEnableProperty());

        this.textFieldIndirizzoU.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldLocalitaU.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldProvinciaU.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldNomeU.editableProperty().bind(modelModifica.isEnableProperty());
        
    }

}
