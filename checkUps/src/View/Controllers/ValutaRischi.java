package View.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ClassHelper;

import java.util.List;

import Models.ModelValutaRischi;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ValutaRischi implements Initializable{

    @FXML
    private TableColumn<Reparto, String> descriptionCol;

    @FXML
    private TableColumn<Reparto, Integer> idCol;

    @FXML
    private TableColumn<Reparto, String> nameCol;

    @FXML
    private TableView<Reparto> tableView;

    @FXML
    private TextField nomeSocieta;

    @FXML
    private TextField nomeUnitaLocale;

    @FXML
    private TextField filterTextField;

    private List<Reparto> reparti = ClassHelper.getListReparto();

    private ObservableList<Reparto> observableList;
    private ModelValutaRischi modelValutaRischi;

    private UnitaLocale unitaLocale;
    // private Societa societa;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idCol.setCellValueFactory(new PropertyValueFactory<Reparto, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Reparto, String>("nome"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Reparto, String>("descrizione"));

    }

    public void fillTable(){

        List<Reparto>  specificList = reparti.stream().filter(reparto -> reparto.getIdUnitaLocale() == unitaLocale.getId()).toList();

        observableList = FXCollections.observableArrayList(specificList);
        tableView.setItems(observableList);
        
    }

    public void filterTable(){
        modelValutaRischi.filterTable(filterTextField, tableView, observableList);
    }

    public void setSection(UnitaLocale unitaLocale, Societa societa) {
        // this.societa = societa;
        this.unitaLocale = unitaLocale;

        nomeSocieta.setText(societa.getNome());
        nomeUnitaLocale.setText(unitaLocale.getNome());

        fillTable();
    }

    public void setModel(ModelValutaRischi modelValutaRischi) {
        this.modelValutaRischi = modelValutaRischi;

    }


    
}
