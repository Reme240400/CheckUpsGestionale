package View.Controllers.Creazione;

import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.ClassHelper;
import Models.Model;
import Models.ModelCreazione;
import Models.ModelModifica;
import Models.ModelPaths;
import Models.Tables.Oggetto;
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

public class CreazioneOggetto implements Initializable {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDel;

    @FXML
    private JFXButton btnModify;

    @FXML
    private JFXButton btnSalvaAggiungi;

    @FXML
    private JFXComboBox<String> cercaReparto;

    @FXML
    private JFXComboBox<String> cercaSocieta;

    @FXML
    private JFXComboBox<String> cercaTitolo;

    @FXML
    private JFXComboBox<String> cercaUnita;

    @FXML
    private TableView<Oggetto> tableOggetti;

    @FXML
    private TableColumn<Oggetto, Integer> idCol;

    @FXML
    private TableColumn<Oggetto, String> nomeCol;

    @FXML
    private TableColumn<Oggetto, String> nomeColT;

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
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        societaList = ClassHelper.getListSocieta();
        unitaLocaleList = ClassHelper.getListUnitaLocale();
        oggettoList = ClassHelper.getListOggetto();
        repartoList = ClassHelper.getListReparto();
        titoloList = ClassHelper.getListTitolo();

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

    // --------------- triggherato quando si seleziona un' societa --------------- //
    public void selectSocieta() {
        List<UnitaLocale> specificList = null;

        if (cercaSocieta.getValue() != null && !cercaSocieta.getValue().isEmpty()) {

            localSocieta = societaList.stream()
                                    .filter(s -> s.getNome().equals(cercaSocieta.getValue()))
                                    .findFirst().get();
            // textFieldSocieta.setText(modelCreazione.getSocietaTmp().getNome());

            specificList = unitaLocaleList.stream()
                    .filter(u -> u.getIdSocieta() == localSocieta.getId())
                    .toList();

            ObservableList<String> items = FXCollections.observableArrayList();

            // * *************** popola il combobox *************** //

            for (UnitaLocale unita : specificList) {
                items.add(unita.getNome());
            }

            // --------------- filtra il Combobox --------------- //
            FilteredList<String> filteredItems = Model.filterComboBox(cercaUnita, items);

            cercaUnita.setItems(filteredItems);
        }

        cercaUnita.setValue(null);
        cercaReparto.setValue(null);
        cercaTitolo.setValue(null);
        tableOggetti.getItems().clear();
    }

    // --------------- triggherato quando si seleziona un' unita locale --------------- //
    public void selectUnita() {
        List<Reparto> specificList = null;

        if(cercaUnita.getValue() != null && !cercaUnita.getValue().isEmpty()){

            if(localSocieta != null){
                localUnita = unitaLocaleList.stream()
                        .filter(u -> u.getIdSocieta() == localSocieta.getId())
                        .filter(u -> u.getNome().equals(cercaUnita.getValue()))
                        .findFirst().get();

                // textFieldUnita.setText(modelCreazione.getUnitaLocaleTmp().getNome());
                specificList = repartoList.stream()
                    .filter(r -> r.getIdUnitaLocale() == localUnita.getId())
                    .toList();

                ObservableList<String> items = FXCollections.observableArrayList();

                // * *************** popola il combobox *************** //

                for (Reparto reparto : specificList) {
                    items.add(reparto.getNome());
                }

                // --------------- filtra il Combobox --------------- //
                FilteredList<String> filteredItems = Model.filterComboBoxById(cercaReparto, localSocieta.getId(), items);

                cercaReparto.setItems(filteredItems);
            }
        }
        cercaReparto.setValue(null);
        cercaTitolo.setValue(null);
        tableOggetti.getItems().clear();
    }

    // --------------- triggherato quando si seleziona un' reparto --------------- //
    public void selectReparto() {

        List<Titolo> specificList = null;

        if(localUnita != null){
            localReparto = repartoList.stream()
                    .filter(r -> r.getIdUnitaLocale() == localUnita.getId())
                    .filter(r -> r.getNome().equals(cercaReparto.getValue()))
                    .findFirst().get();

            // textFieldReparto.setText(modelCreazione.getRepartoTmp().getNome());

            specificList = titoloList.stream()
                                    .filter(t -> t.getIdReparto() == localReparto.getId())
                                    .toList();

            ObservableList<String> items = FXCollections.observableArrayList();

            // * *************** popola il combobox *************** //

            for (Titolo titolo : specificList) {
                items.add(titolo.getDescrizione());
            }

            // --------------- filtra il Combobox --------------- //
            FilteredList<String> filteredItems = Model.filterComboBoxById(cercaTitolo, localUnita.getId(), items);

            cercaTitolo.setItems(filteredItems);
        }

        cercaTitolo.setValue(null);
        tableOggetti.getItems().clear();

    }

    // --------------- triggherato quando si seleziona un' titolo --------------- //
    public void selectTitolo() {
        
        if(localReparto != null){
            localTitolo = titoloList.stream()
                    .filter(u -> u.getIdReparto() == localReparto.getId())
                    .filter(u -> u.getDescrizione().equals(cercaTitolo.getValue()))
                    .findFirst().get();

            // textFieldTitolo.setText(modelCreazione.getTitoloTmp().getDescrizione());

            fillTableView();
        }
    }

    private void fillTableView() {

        List<Oggetto> specificList = null;

        if (localTitolo != null) {
            oggettoList = ClassHelper.getListOggetto().stream()
                    .filter(o -> o.getIdTitolo() == localTitolo.getId())
                    .toList();
        }

        specificList = oggettoList.stream()
                                    .filter(o -> o.getIdTitolo() == localTitolo.getId())
                                    .toList();

        ObservableList<Oggetto> items = FXCollections.observableArrayList();

        // * *************** popola il combobox *************** //

        for (Oggetto oggetto : specificList) {
            items.add(oggetto);
        }

        tableOggetti.setItems(items);
        
    }

    @FXML
    public void delete() {

    }

    @FXML
    public void modify() {

    }

    @FXML
    public void addOggetto() {

    }

    @FXML
    public void save_addOggetto() {

    }

    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths, ModelModifica modelModifica) {
        this.modelCreazione = modelCreazione;
        this.modelPaths = modelPaths;
        this.modelModifica = modelModifica;

    
    }

}
