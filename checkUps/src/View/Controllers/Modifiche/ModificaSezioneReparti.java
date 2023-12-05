package View.Controllers.Modifiche;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Controllers.Controller;
import Helpers.ClassHelper;
import Models.Alerts;
import Models.ModelModifica;
import Models.ModelPaths;
import Models.Tables.Reparto;
import Models.Tables.UnitaLocale;
import View.Controllers.Modifiche.DialogPane.DialogPaneModificaReparto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ModificaSezioneReparti implements Initializable{

    @FXML
    private TableView<Reparto> tableViewReparti;

    @FXML
    private TableColumn<Reparto, Integer> idCol;

    @FXML
    private TableColumn<Reparto, String> nomeCol;

    @FXML
    private TableColumn<Reparto, String> descCol;

    @FXML
    private TextField filterTable;

    @FXML
    private JFXButton btnRefresh;

    private List<Reparto> listaReparto = ClassHelper.getListReparto();
    private List<UnitaLocale> listUnitaLocale = ClassHelper.getListUnitaLocale();

    private ObservableList<Reparto> observableList = FXCollections.observableArrayList();

    private ModelModifica modelModifica;
    private ModelPaths modelPaths;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
            
        // --------------- inizializzo le colonne della tabella --------------- //
        idCol.setCellValueFactory(new PropertyValueFactory<Reparto, Integer>("id"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<Reparto, String>("nome"));
        descCol.setCellValueFactory(new PropertyValueFactory<Reparto, String>("descrizione"));

        tableViewReparti.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectReparto(); // Chiama il metodo quando viene selezionato un elemento
            }
        });

    }

    // --------------- filtra la tabella in tempo reale, in base al nome --------------- //
    public void filterTable(){
        modelModifica.filterTable(filterTable, tableViewReparti, observableList);
    }

    public void fillRepartiTable(){

        List<Reparto> specificList = null;
        
        if (modelModifica.getUnitaLocaleTmp() != null) {
            specificList = modelModifica.fillRepartiTable(listaReparto);

            observableList = FXCollections.observableArrayList(specificList);
            tableViewReparti.setItems(observableList);
            
        } else if(modelModifica.getSocietaTmp() != null){
            specificList = modelModifica.fillAllRepartiTable(listaReparto, listUnitaLocale);

            observableList = FXCollections.observableArrayList(specificList);
            tableViewReparti.setItems(observableList);            
        }
    }

    private void updateChanges(DialogPaneModificaReparto dialogController) throws IOException{

        if(modelModifica.getRepartoTmp() != null && 
            modelModifica.getRepartoTmp().getNome() != "" && 
            modelModifica.getRepartoTmp().getDescrizione() != ""){

            modelModifica.getRepartoTmp().setNome(dialogController.getNomeReparto());
            modelModifica.getRepartoTmp().setDescrizione(dialogController.getDescReparto());

            Controller.modificaCampo(modelModifica.getRepartoTmp());
            
        } else {
            Alerts.errorAllert("Errore", "Selezione del Reparto fallita", "Il reparto selezionato non è valido");
        }
    }

    @FXML
    private void modify() throws IOException{

        if(modelModifica.getRepartoTmp() != null ){

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/modifica_reparto_dialogPane.fxml"));
            DialogPane dialogPane = loader.load();

            DialogPaneModificaReparto dialogController = loader.getController();

            dialogController.setModel(modelModifica);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Modifica Reparto");

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            // ------------------- Se viene premuto il tasto "Applica" ------------------- //

            if (clickedButton.get() == ButtonType.APPLY) {

                updateChanges(dialogController);
            }
        } else {
            Alerts.errorAllert("Errore", "Selezione del Reparto fallita", "Il reparto selezionato non è valido");
        }
    }

    @FXML
    private void delete(){
        
        if (tableViewReparti.getSelectionModel().getSelectedItem() != null) {
            Reparto reparto = tableViewReparti.getSelectionModel().getSelectedItem();
            Controller.eliminaRecord(reparto, reparto.getId());
            tableViewReparti.getItems().remove(reparto);
        }
    }

    @FXML
    private void refresh(){
        try {
            modelModifica.resetAllTmp();
            ButtonType clickedButton = modelPaths.showRepartiTitoliDialogPane(modelModifica);

            if (clickedButton == ButtonType.APPLY) {
                if(modelModifica.getUnitaLocaleTmp() != null){
                    fillRepartiTable();
                } else {
                    Alerts.errorAllert("Errore", "Selezione errata", "Seleziona un'unità locale");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectReparto(){
        Reparto reparto = tableViewReparti.getSelectionModel().getSelectedItem();
        modelModifica.setReparto(reparto);
        modelModifica.setSelectedReparto(true);
        
    }

    public void setModel(ModelModifica modelModifica, ModelPaths modelPaths) {
        this.modelModifica = modelModifica;
        this.modelPaths = modelPaths;

        fillRepartiTable();  

    }
    
}
