package View.Controllers.Modifiche;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import Controllers.Controller;
import Helpers.ClassHelper;
import Models.Alerts;
import Models.ModelModifica;
import Models.ModelPaths;
import Models.Tables.Oggetto;
import Models.Tables.Titolo;
import View.Controllers.Modifiche.DialogPane.DialogPaneModificaOggetto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ModificaSezioneOggetti implements Initializable{

    @FXML
    private JFXButton btnDel;

    @FXML
    private JFXButton btnModify;

    @FXML
    private JFXButton btnRefresh;

    @FXML
    private TextField filterTable;

    @FXML
    private TableColumn<Oggetto, Integer> idCol;

    @FXML
    private TableColumn<Oggetto, String> nomeCol;

    @FXML
    private TableView<Oggetto> tableOggetti;

    private List<Oggetto> listaOggetti = ClassHelper.getListOggetto();

    private ObservableList<Oggetto> observableList = FXCollections.observableArrayList();

    private ModelModifica modelModifica;
    private ModelPaths modelPaths;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<Oggetto, Integer>("id"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<Oggetto, String>("nome"));

        tableOggetti.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectOggetto(); // Chiama il metodo quando viene selezionato un elemento
            }
        });
    }

    private void selectOggetto() {

        Oggetto oggetto = tableOggetti.getSelectionModel().getSelectedItem();
        modelModifica.setOggetto(oggetto);

    }

    @FXML
    private void delete() {
        if (tableOggetti.getSelectionModel().getSelectedItem() != null) {
            Oggetto oggetto = tableOggetti.getSelectionModel().getSelectedItem();
            Controller.eliminaRecord(oggetto, oggetto.getId());
            tableOggetti.getItems().remove(oggetto);
        }
    }

    @FXML
    private void filterTable() {
        modelModifica.filterTable(filterTable, tableOggetti, observableList);
    }

    @FXML
    private void modify() throws Exception {
        if(modelModifica.getOggettoTmp() != null ){

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/modifica_oggetto_dialogPane.fxml"));
            DialogPane dialogPane = loader.load();

            DialogPaneModificaOggetto dialogController = loader.getController();

            dialogController.setModel(modelModifica);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Modifica Oggetto");

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            // ------------------- Se viene premuto il tasto "Applica" ------------------- //

            if (clickedButton.get() == ButtonType.APPLY) {

                updateChanges(dialogController);
            }
        } else {
            Alerts.errorAllert("Errore", "Selezione del Titolo fallita", "Il titolo selezionato non è valido");
        }
    }

    private void updateChanges(DialogPaneModificaOggetto dialogController) {
        if(modelModifica.getOggettoTmp() != null && 
            modelModifica.getOggettoTmp().getNome() != ""){

            modelModifica.getOggettoTmp().setNome(dialogController.getNome());

            Controller.modificaCampo(modelModifica.getOggettoTmp());
            
        } else {
            Alerts.errorAllert("Errore", "Selezione del Titolo fallita", "L'oggetto selezionato non è valido");
        }
    }

    @FXML
    private void refresh() {
        try {
            modelModifica.resetAllTmp();
            ButtonType clickedButton = modelPaths.showOggettiDialogPane(modelModifica);
        
            if (clickedButton == ButtonType.APPLY) {
                if(modelModifica.getTitoloTmp() != null){
                    modelModifica.setSelectedReparto(false);

                    Parent root = modelPaths.switchToModificaOggetti(modelModifica);
                    Controller.changePane(modelPaths.getStackPaneModificaO(), root);
                } else {
                    Alerts.errorAllert("Errore", "Selezione errata", "Seleziona un'titolo");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setModel(ModelModifica modelModifica, ModelPaths modelPaths) {
        this.modelModifica = modelModifica;
        this.modelPaths = modelPaths;

        observableList = FXCollections.observableArrayList(modelModifica.fillOggettiTable(listaOggetti));
        tableOggetti.setItems(observableList);
    }
}
