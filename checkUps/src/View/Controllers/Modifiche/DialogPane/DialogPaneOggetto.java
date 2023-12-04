package View.Controllers.Modifiche.DialogPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import Helpers.ClassHelper;
import Models.Model;
import Models.ModelModifica;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DialogPaneOggetto implements Initializable{

    @FXML
    private JFXComboBox<String> cercaReparto;

    @FXML
    private JFXComboBox<String> cercaSocieta;

    @FXML
    private JFXComboBox<String> cercaUnitaLocale;

    @FXML
    private TableView<Titolo> tableTitolo;

    @FXML
    private TableColumn<Titolo, Integer> idCol;

    @FXML
    private TableColumn<Titolo, String> nomeCol;

    private ModelModifica modelModifica;

    private List<Societa> listSocieta = ClassHelper.getListSocieta();
    private List<UnitaLocale> listUnitaLocale = ClassHelper.getListUnitaLocale();
    private List<Reparto> listReparto = ClassHelper.getListReparto();
    private List<Titolo> listTitolo = ClassHelper.getListTitolo();

    private Societa localSocieta;
    private UnitaLocale localUnitaLocale;
    private Reparto localReparto;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        idCol.setCellValueFactory(new PropertyValueFactory<Titolo, Integer>("id"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<Titolo, String>("descrizione"));

        // * *************** inizializza i campi *************** //

        ObservableList<String> societies = FXCollections.observableArrayList();

        // * *************** popola i combobox *************** //

        for (Societa societa : listSocieta) {
            cercaSocieta.getItems().add(societa.getNome());
            societies.add(societa.getNome());
        }
        // * **************************************** //

        // * filtra i Combobox
        FilteredList<String> filteredItems = Model.filterComboBox(cercaSocieta, societies);

        cercaSocieta.setItems(filteredItems);

        // * ************************************************ //

        tableTitolo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectTitolo(); // Chiama il metodo quando viene selezionato un elemento
            }
        });
    }

    @FXML
    public void onSelectedSocieta() {
            
        if(cercaSocieta.getSelectionModel().getSelectedItem() != null){
            
            localSocieta = listSocieta.stream()
                                        .filter(s -> s.getNome().equals(cercaSocieta.getSelectionModel().getSelectedItem()))
                                        .findFirst().get();

            ObservableList<String> unitaLocali = FXCollections.observableArrayList();

            for (UnitaLocale unitaLocale : listUnitaLocale) {
                if (unitaLocale.getIdSocieta() == localSocieta.getId()) {
                    unitaLocali.add(unitaLocale.getNome());
                }
            }

            // * filtra i Combobox
            FilteredList<String> filteredItems = Model.filterComboBox(cercaUnitaLocale, unitaLocali);

            cercaUnitaLocale.setItems(filteredItems);
        }
    }

    @FXML
    public void onSelectedUnita() {
            
        if(cercaUnitaLocale.getSelectionModel().getSelectedItem() != null){
            
            localUnitaLocale = listUnitaLocale.stream()
                                                    .filter(u -> u.getIdSocieta() == localSocieta.getId())
                                                    .filter(u -> u.getNome().equals(cercaUnitaLocale.getSelectionModel().getSelectedItem()))
                                                    .findFirst().get();

            ObservableList<String> reparti = FXCollections.observableArrayList();

            for (Reparto reparto : listReparto) {
                if (reparto.getIdUnitaLocale() == localUnitaLocale.getId()) {
                    reparti.add(reparto.getNome());
                }
            }

            // * filtra i Combobox
            FilteredList<String> filteredItems = Model.filterComboBox(cercaReparto, reparti);

            cercaReparto.setItems(filteredItems);
        }
    } 
    
    @FXML
    public void onSelectedReparto() {
        
        if (cercaReparto.getSelectionModel().getSelectedItem() != null) {
            
            localReparto = listReparto.stream()
                                        .filter(r -> r.getIdUnitaLocale() == localUnitaLocale.getId())
                                        .filter(r -> r.getNome().equals(cercaReparto.getSelectionModel().getSelectedItem()))
                                        .findFirst().get();

            ObservableList<Titolo> titoli = FXCollections.observableArrayList();

            for (Titolo titolo : listTitolo) {
                if (titolo.getIdReparto() == localReparto.getId()) {
                    titoli.add(titolo);
                }
            }

            tableTitolo.setItems(titoli); 
            
        }
    }

    private void selectTitolo() {
        
        Titolo titolo = tableTitolo.getSelectionModel().getSelectedItem();
        modelModifica.setReparto(localReparto);
        modelModifica.setTitolo(titolo);
    }

    public void setModel( ModelModifica modelModifica) {
        this.modelModifica = modelModifica;

    }

    
}
