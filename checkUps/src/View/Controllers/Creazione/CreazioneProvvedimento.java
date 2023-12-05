package View.Controllers.Creazione;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Helpers.ClassHelper;
import Models.Model;
import Models.ModelCreazione;
import Models.ModelModifica;
import Models.ModelPaths;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
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

public class CreazioneProvvedimento implements Initializable{

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDel;

    @FXML
    private JFXButton btnModify;

    @FXML
    private JFXComboBox<String> cercaReparto;

    @FXML
    private JFXComboBox<String> cercaSocieta;

    @FXML
    private JFXComboBox<String> cercaTitolo;

    @FXML
    private JFXComboBox<String> cercaUnita;

    @FXML
    private TableColumn<Oggetto, Integer> idColO;

    @FXML
    private TableColumn<Provvedimento, Integer> idColP;

    @FXML
    private TableColumn<Oggetto, String> nomeColO;

    @FXML
    private TableColumn<Provvedimento, String> nomeColP;

    @FXML
    private TableColumn<Provvedimento, String> rischioCol;

    @FXML
    private TableColumn<Provvedimento, String> soggetiCol;

    @FXML
    private TableColumn<Provvedimento, Integer> stimaCol;

    @FXML
    private TableView<Oggetto> tableOggetti;

    @FXML
    private TableView<Provvedimento> tableProvvedimenti;

    private ModelCreazione modelCreazione;
    private ModelPaths modelPaths;
    private ModelModifica modelModifica;

    private List<Societa> societaList = ClassHelper.getListSocieta();
    private List<UnitaLocale> unitaLocaleList = ClassHelper.getListUnitaLocale();
    private List<Oggetto> oggettoList = ClassHelper.getListOggetto();
    private List<Reparto> repartoList = ClassHelper.getListReparto();
    private List<Titolo> titoloList = ClassHelper.getListTitolo();

    private Societa localSocieta;
    private UnitaLocale localUnita;
    private Reparto localReparto;
    private Titolo localTitolo;
    private Oggetto localOggetto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        societaList = ClassHelper.getListSocieta();
        unitaLocaleList = ClassHelper.getListUnitaLocale();
        oggettoList = ClassHelper.getListOggetto();
        repartoList = ClassHelper.getListReparto();
        titoloList = ClassHelper.getListTitolo();

        idColO.setCellValueFactory(new PropertyValueFactory<Oggetto, Integer>("id"));
        nomeColO.setCellValueFactory(new PropertyValueFactory<Oggetto, String>("nome"));


        ObservableList<String> items = FXCollections.observableArrayList();

        // * *************** popola il combobox *************** //

        for (Societa societa : societaList) {
            cercaSocieta.getItems().add(societa.getNome());
            items.add(societa.getNome());
        }

        // --------------- filtra il Combobox --------------- //
        FilteredList<String> filteredItems = Model.filterComboBox(cercaSocieta, items);

        cercaSocieta.setItems(filteredItems);

    }

    

    @FXML
    public void selectReparto() {

    }

    @FXML
    public void selectSocieta() {

    }

    @FXML
    public void selectTitolo() {

    }

    @FXML
    public void selectUnita() {

    }

    @FXML
    public void addProv() {

    }

    @FXML
    public void delete() {

    }

    @FXML
    public void modify() {

    }
    
    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths, ModelModifica modelModifica) {
        this.modelCreazione = modelCreazione;
        this.modelPaths = modelPaths;
        this.modelModifica = modelModifica;
    }

}
