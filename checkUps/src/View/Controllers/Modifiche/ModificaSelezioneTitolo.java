package View.Controllers.Modifiche;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Controllers.ClassHelper;
import Models.ModelModifica;
import Models.Tables.Reparto;
import Models.Tables.Titolo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ModificaSelezioneTitolo implements Initializable {

    @FXML
    private TableView<Titolo> tableViewTitoli;

    @FXML
    private TableColumn<Titolo, String> descCol;

    @FXML
    private TextField filterTable;

    @FXML
    private TableColumn<Titolo, Integer> idCol;

    @FXML
    private TableColumn<Titolo, String> nomeColR;


    private ObservableList<Titolo> observableList = FXCollections.observableArrayList();

    private ModelModifica modelModifica;

    private List<Titolo> listaTitoli = ClassHelper.getListTitolo();
    private List<Reparto> listaReparto = ClassHelper.getListReparto();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        idCol.setCellValueFactory(new PropertyValueFactory<Titolo, Integer>("id"));
        nomeColR.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(listaReparto.stream().filter(reparto -> reparto.getId() == cellData.getValue().getIdReparto()).findFirst().get().getNome());
        });

        descCol.setCellValueFactory(new PropertyValueFactory<Titolo, String>("descrizione"));
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

    public void filterTable(){
        
        modelModifica.filterTable(filterTable, tableViewTitoli , observableList);

    }

    public void setModel(ModelModifica modelModifica) {
        this.modelModifica = modelModifica;
        observableList = FXCollections.observableArrayList(modelModifica.fillTitoliTable(listaTitoli));

        tableViewTitoli.setItems(observableList);
    }  
    
}
