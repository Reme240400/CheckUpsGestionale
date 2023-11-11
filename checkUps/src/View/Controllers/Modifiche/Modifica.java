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
import Models.ModelModifica;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import View.Controllers.ViewController;

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

    private List<Societa> listSocieta = ClassHelper.getListSocieta();
    private List<UnitaLocale> listUnitaLocale = ClassHelper.getListUnitaLocale();

    private ObservableList<String> uItems = FXCollections.observableArrayList();
    private ObservableList<String> sItems = FXCollections.observableArrayList();

    private int idSocieta = -1;
    //private int idUnitaLocale = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // ---------------- popola il combobox ---------------- //
        for (Societa societa : listSocieta) {
            cercaRecordS.getItems().add(societa.getNome());
            sItems.add(societa.getNome());
        }

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

        // --------------- filtra il Combobox --------------- //
        FilteredList<String> filteredItems = ViewController.filterComboBoxSocieta(cercaRecordS, sItems);

        cercaRecordS.setItems(filteredItems);
    }

    // --------------- Riempi i campi con i dati della societa selezionata --------------- //
    public void fillTextFieldS(){
        if (cercaRecordS.getValue() != null && !cercaRecordS.getValue().equals("")){
            modelModifica.fillTextField( cercaRecordS, textFieldNomeS, textFieldIndirizzo, textFieldLocalita, textFieldProvincia, textFieldTel);
        }
    }

    // --------------- Riempi i campi con i dati dell'unita locale selezionata --------------- //
    public void fillTextFieldU(){
        if (cercaRecordU.getValue() != null && !cercaRecordU.getValue().equals("")){
            int id = modelModifica.getIdSocietaTmp();
            modelModifica.fillTextField( cercaRecordU, id, textFieldNomeU, textFieldIndirizzoU, textFieldLocalitaU, textFieldProvinciaU);
        }
    }    

    // --------------- Salva le modifiche --------------- //
    public void updateChanges(){

    }

    // --------------- Mostra il dialogPane per filtrare l'Unita Locale --------------- //
    public void showUnitaPane() throws IOException {

        System.out.println(tabUnitaLocale.isSelected());

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

            // ------------------- Se viene premuto il tasto "Applica" ------------------- //
            if(clickedButton.get() == ButtonType.APPLY){
                if (modelModifica.getIdSocietaTmp() != -1) {
                    // prende l'id della societa selezionata //
                    this.idSocieta = modelModifica.getIdSocietaTmp();

                    for (UnitaLocale unitaLocale : listUnitaLocale) {
                        if (unitaLocale.getIdSocieta() == idSocieta) {
                            cercaRecordU.getItems().add(unitaLocale.getNome());
                            uItems.add(unitaLocale.getNome());
                        }
                    }

                    cercaRecordU.setItems(ViewController.filterComboBoxUnitaLocale(cercaRecordU, idSocieta, uItems));

                } else{
                    tabPane.getSelectionModel().select(0);
                }          
            } else{
                tabPane.getSelectionModel().select(0);
            }
        }
    }

    public void showRepartiTitoliDialogPane() throws IOException{
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/modifica_reparto_dialogPane.fxml"));
            DialogPane dialogPane = loader.load();

            DialogPane2 dialogController = loader.getController();

            dialogController.setModel(modelModifica);
            
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Scegli la Unità Locale");
            
            Optional<ButtonType> clickedButton = dialog.showAndWait();

            // ------------------- Se viene premuto il tasto "Applica" ------------------- //

            if(clickedButton.get() == ButtonType.APPLY){
                showRepartoPane();
            }else{
                tabPane.getSelectionModel().select(0);
            }

    }

    // ------------------- Mostra il dialogPane dei Reparti ------------------- //
    public void showRepartoPane() throws IOException{
                    
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/View/fxml/modifica_selezioneReparto.fxml"));

        Parent root = loader2.load();
        ModificaSelezioneReparti controller = loader2.getController();

        controller.setModel(modelModifica);

        titoli_repartiStackPane.getChildren().removeAll();
        titoli_repartiStackPane.getChildren().setAll(root);       
        
    }

    // ------------------- Mostra il dialogPane dei Titoli ------------------- //
    public void showTitoliPane() throws IOException{
    
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/View/fxml/modifica_selezioneTitolo.fxml"));

        Parent root = loader3.load();
        ModificaSelezioneTitolo controller = loader3.getController();

        controller.setModel(modelModifica);

        titoli_repartiStackPane.getChildren().removeAll();
        titoli_repartiStackPane.getChildren().setAll(root);

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

        this.textFieldIndirizzoU.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldLocalitaU.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldProvinciaU.editableProperty().bind(modelModifica.isEnableProperty());
        this.textFieldNomeU.editableProperty().bind(modelModifica.isEnableProperty());
        this.btnSaveU.disableProperty().bind(modelModifica.savedProperty().not());

        this.idSocieta = modelModifica.getIdSocietaTmp();
        //this.idUnitaLocale = modelModifica.getIdUnitaLocaleTmp();
        
    }

}
