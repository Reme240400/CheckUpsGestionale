package View.Controllers.Modifiche;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Controllers.ClassHelper;
import Models.ModelModifica;
import Models.Tables.Reparto;
import Models.Tables.UnitaLocale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ModificaSelezioneReparti implements Initializable{

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


    private List<Reparto> listaReparto = ClassHelper.getListReparto();
    private List<UnitaLocale> listUnitaLocale = ClassHelper.getListUnitaLocale();

    private ObservableList<Reparto> observableList = FXCollections.observableArrayList();

    private ModelModifica modelModifica;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
            
        // --------------- inizializzo le colonne della tabella --------------- //
        idCol.setCellValueFactory(new PropertyValueFactory<Reparto, Integer>("id"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<Reparto, String>("nome"));
        descCol.setCellValueFactory(new PropertyValueFactory<Reparto, String>("descrizione"));

    }

    // --------------- filtra la tabella in tempo reale, in base al nome --------------- //
    public void filterTable(){
        modelModifica.filterTable(filterTable, tableViewReparti, observableList);
    }

    public void fillRepartiTable(){

        List<Reparto> specificList = null;
        
        if (modelModifica.getIdUnitaLocaleTmp() != -1 ) {
            specificList = modelModifica.fillRepartiTable(listaReparto);

            observableList = FXCollections.observableArrayList(specificList);
            tableViewReparti.setItems(observableList);
            
        } else if(modelModifica.getIdSocietaTmp() != -1){
            specificList = modelModifica.fillAllRepartiTable(listaReparto, listUnitaLocale);

            observableList = FXCollections.observableArrayList(specificList);
            tableViewReparti.setItems(observableList);            
        }
    }

    @FXML
    private void updateChanges(){

    }

    @FXML
    private void modify(){

    }

    @FXML
    private void delete(){

    }

    public void setModel(ModelModifica modelModifica) {
        this.modelModifica = modelModifica;

        fillRepartiTable();  

    }
    
}