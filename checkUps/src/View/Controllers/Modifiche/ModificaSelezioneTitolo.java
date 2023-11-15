package View.Controllers.Modifiche;

import java.net.URL;
import java.util.ResourceBundle;

import Models.ModelModifica;
import Models.Tables.Reparto;
import Models.Tables.Titolo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ModificaSelezioneTitolo implements Initializable {

    @FXML
    private TableView<Titolo> tableViewTitoli;

    @FXML
    private TextField filterTable;

    private ObservableList<Titolo> observableList = FXCollections.observableArrayList();

    private ModelModifica modelModifica;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
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
        
        modelModifica.filterTable(filterTable, tableViewTitoli, observableList);
    }

    public void setModel(ModelModifica modelModifica) {
        this.modelModifica = modelModifica;
    }



    
    
}
