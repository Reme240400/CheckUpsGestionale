package View.Controllers.Modifiche;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXButton;

import Controllers.Controller;
import Helpers.ClassHelper;
import Models.Alerts;
import Models.Model;
import Models.ModelModifica;
import Models.ModelPaths;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
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

    @FXML
    private JFXButton btnOggetti;

    @FXML
    private JFXButton btnProvvedimenti;    

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

    @FXML
    private JFXButton btnDelS;
    
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

    @FXML
    private JFXButton btnDelU;

    // ----------------- Unita Locale ----------------- //

    // ----------------- Reparti / Titoli ----------------- //

    @FXML
    private Tab tabReparti_Titoli;

    @FXML
    private StackPane titoli_repartiStackPane;

    // ----------------- Reparti / Titoli ----------------- //

    // ----------------- Oggetti / Provvedimenti ----------------- //

    @FXML
    private Tab tabOggetti_Provvedimenti;

    @FXML
    private StackPane oggetti_provvedimentiStackPane;


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
        FilteredList<String> filteredItems = Model.filterComboBox(cercaRecordS, sItems);

        cercaRecordS.setItems(filteredItems);
    }

    private void popolaComboBoxU() {
        // ---------------- popola il combobox ---------------- //
        for (UnitaLocale unitaLocale : listUnitaLocale) {
            if (unitaLocale.getIdSocieta() == idSocieta) {
                uItems.add(unitaLocale.getNome());
            }
        }

        cercaRecordU.setItems(Model.filterComboBoxById(cercaRecordU, idSocieta, uItems));

        // --------------- filtra il Combobox --------------- //
        FilteredList<String> filteredItems = Model.filterComboBoxById(cercaRecordU, idSocieta, uItems);

        cercaRecordU.setItems(filteredItems);
    }

    // --------------- Riempi i campi con i dati della societa selezionata --------------- //
    public void fillTextFieldS() {
        if (cercaRecordS.getValue() != null && !cercaRecordS.getValue().equals("")) {
            modelModifica.fillTextField(cercaRecordS, textFieldNomeS, textFieldIndirizzo, textFieldLocalita,
                    textFieldProvincia, textFieldTel);

            Societa societaTmp = listSocieta.stream().filter(s -> s.getNome().equals(cercaRecordS.getValue()))
                    .findFirst().get();
            modelModifica.setSocieta(societaTmp);
        }
    }

    // --------------- Riempi i campi con i dati dell'unita locale selezionata --------------- //
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

    // --------------- Elimina la societa --------------- //
    public void deleteS() {

        if (modelModifica.getSocietaTmp() != null) {
            Controller.eliminaRecord(modelModifica.getSocietaTmp(), modelModifica.getSocietaTmp().getId());
            modelModifica.resetSocietaTmp();
            popolaComboBoxS();
        }
    }

    // --------------- Elimina l'unita locale --------------- //
    public void deleteU() {

        if (modelModifica.getUnitaLocaleTmp() != null) {
            Controller.eliminaRecord(modelModifica.getUnitaLocaleTmp(), modelModifica.getUnitaLocaleTmp().getId());
            modelModifica.resetUnitaLocaleTmp();
            popolaComboBoxU();
        }
    }

    // --------------- Mostra il dialogPane per filtrare l'Unita Locale --------------- //
    public void showUnitaPane() throws IOException {

        // ------------------- Mostra il dialogPane dell'Unita Locale ------------------- //
        if (tabUnitaLocale.isSelected()) {

            ButtonType clickedButton = modelPaths.showUnitaDialogPane(modelModifica);
            // ------------------- Se viene premuto il tasto "Applica" ------------------- //
            if (clickedButton == ButtonType.APPLY) {
                if (modelModifica.getSocietaTmp() != null) {
                    // prende l'id della societa selezionata //
                    this.idSocieta = modelModifica.getSocietaTmp().getId();

                    popolaComboBoxU();

                } else {
                    Alerts.errorAllert("Errore", "Selezione errata", "Seleziona una società");
                    tabPane.getSelectionModel().select(0);
                }
            } else {
                tabPane.getSelectionModel().select(0);
            }
        }
    }

    // ------------------- Mostra il dialogPane per filtrare i Reparti ------------------- //
    public void showRepartiTitoliDialogPane() throws IOException {

        if (tabReparti_Titoli.isSelected()) {
        
            ButtonType clickedButton = modelPaths.showRepartiTitoliDialogPane(modelModifica);

            // ------------------- Se viene premuto il tasto "Applica" ------------------- //

            if (clickedButton == ButtonType.APPLY) {
                if(modelModifica.getUnitaLocaleTmp() != null){
                    showRepartoPane();
                } else {
                    Alerts.errorAllert("Errore", "Selezione errata", "Seleziona un'unità locale");
                    tabPane.getSelectionModel().select(0);
                }
            } else {
                tabPane.getSelectionModel().select(0);
            }
        }
    }

    // ------------------- Mostra il pannello dei Reparti ------------------- //
    public void showRepartoPane() throws IOException {

        Parent root = modelPaths.switchToModificaReparto(modelModifica);

        if (root != null) {
            titoli_repartiStackPane.getChildren().removeAll();
            titoli_repartiStackPane.getChildren().setAll(root);
        }
    }

    // ------------------- Mostra il pannello dei Titoli ------------------- //
    public void showTitoliPane() throws IOException {

        Parent root = modelPaths.switchToModificaTitoli(modelModifica);

        if (root != null) {
            titoli_repartiStackPane.getChildren().removeAll();
            titoli_repartiStackPane.getChildren().setAll(root);
        }
    }

    // ------------------- Mostra il dialogPane per filtrare gli Oggetti ------------------- //
    public void showOggettiDialogPane() throws IOException{
        
        if (tabOggetti_Provvedimenti.isSelected()) {
            
            ButtonType clickedButton = modelPaths.showOggettiDialogPane(modelModifica);

            // ------------------- Se viene premuto il tasto "Applica" ------------------- //

            if (clickedButton == ButtonType.APPLY) {
                if(modelModifica.getTitoloTmp() != null){
                    showOggettiPane();
                } else {
                    Alerts.errorAllert("Errore", "Selezione errata", "Seleziona un Titolo");
                    tabPane.getSelectionModel().select(0);
                }
            } else {
                tabPane.getSelectionModel().select(0);
            }
        }
    }

    // ------------------- Mostra il pannello degli Oggetti ------------------- //
    public void showOggettiPane() throws IOException{
        Parent root = modelPaths.switchToModificaOggetti(modelModifica);

        if (root != null) {
            oggetti_provvedimentiStackPane.getChildren().removeAll();
            oggetti_provvedimentiStackPane.getChildren().setAll(root);
        }
    }

    // ------------------- Mostra il dialogPane per filtrare i Provvedimenti ------------------- //
    public void showProvvedimentiPane() throws IOException{
        Parent root = modelPaths.switchToModificaProvvedimenti(modelModifica);

        if (root != null) {
            oggetti_provvedimentiStackPane.getChildren().removeAll();
            oggetti_provvedimentiStackPane.getChildren().setAll(root);
        }
    }

    // ----------------- Setta il model ----------------- //
    public void setModel(ModelModifica modelModifica, ModelPaths modelPaths) {
        this.modelModifica = modelModifica;
        this.modelPaths = modelPaths;

        this.btnSaveS.disableProperty().bind(modelModifica.savedProperty().not());
        this.btnSaveU.disableProperty().bind(modelModifica.savedProperty().not());
        this.btnTitoli.disableProperty().bind(modelModifica.selectedRepartoProperty().not());
        this.btnProvvedimenti.disableProperty().bind(modelModifica.selectedOggettoProperty().not());

        this.textFieldIndirizzo.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldLocalita.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldProvincia.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldNomeS.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldTel.editableProperty().bind(modelModifica.isEnableProperty());

        this.textFieldIndirizzoU.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldLocalitaU.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldProvinciaU.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldNomeU.editableProperty().bind(modelModifica.isEnableProperty());

        modelModifica.resetAllTmp();

        modelPaths.setStackPaneModificaR(titoli_repartiStackPane);
        modelPaths.setStackPaneModificaO(oggetti_provvedimentiStackPane);
        
        
    }

}
