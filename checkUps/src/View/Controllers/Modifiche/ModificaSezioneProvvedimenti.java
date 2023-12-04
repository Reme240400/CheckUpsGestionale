package View.Controllers.Modifiche;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import Controllers.Controller;
import Helpers.ClassHelper;
import Models.Alerts;
import Models.ModelModifica;
import Models.ModelPaths;
import Models.Tables.Provvedimento;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ModificaSezioneProvvedimenti implements Initializable{

    @FXML
    private JFXButton btnDel;

    @FXML
    private JFXButton btnModify;

    @FXML
    private JFXButton btnRefresh;

    @FXML
    private TextField filterTable;

    @FXML
    private TableColumn<Provvedimento, Integer> idCol;

    @FXML
    private TableColumn<Provvedimento, String> nomeCol;

    @FXML
    private TableColumn<Provvedimento, String> rischioCol;

    @FXML
    private TableColumn<Provvedimento, String> soggetiCol;

    @FXML
    private TableColumn<Provvedimento, String> stimaCol;

    @FXML
    private TableView<Provvedimento> tableProvvedimenti;

    private ObservableList<Provvedimento> observableList = FXCollections.observableArrayList();
    
    private ModelModifica modelModifica;
    private ModelPaths modelPaths;

    private List<Provvedimento> listaProvvedimenti = ClassHelper.getListProvvedimento();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        idCol.setCellValueFactory(new PropertyValueFactory<Provvedimento, Integer>("id"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<Provvedimento, String>("nome"));
        rischioCol.setCellValueFactory(new PropertyValueFactory<Provvedimento, String>("rischio"));
        soggetiCol.setCellValueFactory(new PropertyValueFactory<Provvedimento, String>("soggetti_esposti"));
        stimaCol.setCellValueFactory(new PropertyValueFactory<Provvedimento, String>("stima"));

        tableProvvedimenti.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectProvvedimento(); // Chiama il metodo quando viene selezionato un elemento
            }
        });
    }

    private void selectProvvedimento() {
            
        Provvedimento provvedimento = tableProvvedimenti.getSelectionModel().getSelectedItem();
        modelModifica.setProvvedimento(provvedimento);
    }

    @FXML
    public void delete( ) {
        if (tableProvvedimenti.getSelectionModel().getSelectedItem() != null) {
            Provvedimento provvedimento = tableProvvedimenti.getSelectionModel().getSelectedItem();
            Controller.eliminaRecord(provvedimento, provvedimento.getId());
            tableProvvedimenti.getItems().remove(provvedimento);
        }
    }

    @FXML
    public void filterTable( ) {
        modelModifica.filterTable(filterTable, tableProvvedimenti, observableList);
    }

    @FXML
    public void modify( ) {


    }

    @FXML
    public void refresh( ) {
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
    
        observableList = FXCollections.observableArrayList(modelModifica.fillProvvedimentiTable(listaProvvedimenti));
        tableProvvedimenti.setItems(observableList);
    }

    
}
