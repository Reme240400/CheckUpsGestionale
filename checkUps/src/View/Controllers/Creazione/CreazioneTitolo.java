package View.Controllers.Creazione;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.ClassHelper;
import Controllers.Controller;
import Models.Alerts;
import Models.ModelCreazione;
import Models.ModelModifica;
import Models.ModelPaths;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;

import View.Controllers.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CreazioneTitolo extends Controller implements Initializable {

    @FXML
    private JFXComboBox<String> cercaSocieta;

    @FXML
    private JFXComboBox<String> cercaUnita;

    @FXML
    private TableView<Reparto> tableReparti;

    @FXML
    private TableView<Titolo> tableTitoli;

    @FXML
    private TableColumn<Reparto, Integer> idColR;

    @FXML
    private TableColumn<Reparto, String> nomeColR;

    @FXML
    private TableColumn<Reparto, String> descColR;

    @FXML
    private TableColumn<Titolo, Integer> idColT;

    @FXML
    private TableColumn<Titolo, String> descColT;

    @FXML
    private JFXButton btnAnnulla;

    @FXML
    private JFXButton btnSalva;

    private ModelCreazione modelCreazione;
    private ModelPaths modelPaths;
    private ModelModifica modelModifica;

    private List<Societa> listSocieta;
    private List<UnitaLocale> listUnitaLocale;
    private List<Reparto> listaReparto;
    private List<Titolo> listaTitolo;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        listSocieta = ClassHelper.getListSocieta();
        listaReparto = ClassHelper.getListReparto();
        listUnitaLocale = ClassHelper.getListUnitaLocale();
        listaTitolo = ClassHelper.getListTitolo();

        idColR.setCellValueFactory(new PropertyValueFactory<Reparto, Integer>("id"));
        nomeColR.setCellValueFactory(new PropertyValueFactory<Reparto, String>("nome"));
        descColR.setCellValueFactory(new PropertyValueFactory<Reparto, String>("descrizione"));

        idColT.setCellValueFactory(new PropertyValueFactory<Titolo, Integer>("id"));
        descColT.setCellValueFactory(new PropertyValueFactory<Titolo, String>("descrizione"));

        ObservableList<String> items = FXCollections.observableArrayList();

        // * *************** popola il combobox *************** //

        for (Societa societa : listSocieta) {
            cercaSocieta.getItems().add(societa.getNome());
            items.add(societa.getNome());
        }

        // --------------- filtra il Combobox --------------- //
        FilteredList<String> filteredItems = ViewController.filterComboBoxSocieta(cercaSocieta, items);

        cercaSocieta.setItems(filteredItems);

        tableReparti.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectReparto(); // Chiama il metodo quando viene selezionato un elemento
            }
        });
    }

    // --------------- triggherato quando si seleziona una societa --------------- //
    public void selectSocieta() {
        List<UnitaLocale> specificList = null;
        
        modelCreazione.resetUnitaLocaleTmp();
        modelCreazione.resetRepartoTmp();
        modelCreazione.resetTitoloTmp();

        if (cercaSocieta.getValue() != null && !cercaSocieta.getValue().isEmpty()) {

            modelCreazione.createSocietaTmp(
                    listSocieta.stream().filter(s -> s.getNome().equals(cercaSocieta.getValue())).findFirst().get());
            // textFieldSocieta.setText(modelCreazione.getSocietaTmp().getNome());

            fillTableViewReparti();

            specificList = listUnitaLocale.stream()
                    .filter(u -> u.getIdSocieta() == modelCreazione.getSocietaTmp().getId())
                    .toList();

            ObservableList<String> items = FXCollections.observableArrayList();

            // * *************** popola il combobox *************** //

            for (UnitaLocale unita : specificList) {
                items.add(unita.getNome());
            }

            // --------------- filtra il Combobox --------------- //
            FilteredList<String> filteredItems = ViewController.filterComboBoxSocieta(cercaUnita, items);

            cercaUnita.setItems(filteredItems);
        }
    }

    // --------------- triggherato quando si seleziona un' unita locale --------------- // 

    public void selectUnita() {
        if (modelCreazione.getSocietaTmp() != null && cercaUnita.getValue() != null
                && !cercaUnita.getValue().isEmpty()) {
            modelCreazione.createUnitaLocaleTmp(
                    listUnitaLocale.stream().filter(u -> u.getNome().equals(cercaUnita.getValue())).findFirst().get());
            // textFieldUnita.setText(modelCreazione.getUnitaLocaleTmp().getNome());

            fillTableViewReparti();
        }
    }

    public void selectReparto() {
        if (tableReparti.getSelectionModel().getSelectedItem() != null) {
            modelCreazione.createRepartoTmp(tableReparti.getSelectionModel().getSelectedItem());
            fillTableViewTitoli();
        }
    }

    // --------------- popola la tabella dei reparti --------------- //
    private void fillTableViewReparti() {
        List<Reparto> specificList = null;
        ObservableList<Reparto> observableList = null;

        if (modelCreazione.getSocietaTmp().getId() != -1 && modelCreazione.getUnitaLocaleTmp() == null) {
            specificList = modelCreazione.fillAllRepartiTable(listaReparto, listUnitaLocale);

            observableList = FXCollections.observableArrayList(specificList);
            tableReparti.setItems(observableList);

        } else {
            specificList = modelCreazione.fillRepartiTable(listaReparto);

            observableList = FXCollections.observableArrayList(specificList);
            tableReparti.setItems(observableList);
        }
    }

    // --------------- popola la tabella dei titoli --------------- //
    private void fillTableViewTitoli() {
        List<Titolo> specificList = null;
        ObservableList<Titolo> observableList = null;

        if (modelCreazione.getRepartoTmp() != null) {
            specificList = modelCreazione.fillTitoliTable(listaTitolo, listaReparto);

            observableList = FXCollections.observableArrayList(specificList);
            tableTitoli.setItems(observableList);
        }
    }

    @FXML
    public void modify() throws IOException {

        Parent root = new Parent() {
        };
        modelModifica = new ModelModifica();

        root = modelPaths.switchToModificaTitoli(modelModifica);

        Controller.changePane(modelPaths.getStackPaneHome(), root);
    }

    @FXML
    public void delete() {
        if (tableTitoli.getSelectionModel().getSelectedItem() != null) {
            Titolo titolo = tableTitoli.getSelectionModel().getSelectedItem();
            eliminaRecord(titolo, titolo.getId());
            tableTitoli.getItems().remove(titolo);
        }
    }

    @FXML
    public void addTitolo() throws IOException {
        if (modelCreazione.getSocietaTmp() != null && modelCreazione.getUnitaLocaleTmp() != null
                && modelCreazione.getRepartoTmp() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/creaTitolo_dialogPane.fxml"));
            DialogPane dialogPane = loader.load();

            DialogPaneAddT dialogController = loader.getController();

            dialogController.setModel(modelCreazione);
            dialogController.fillTextBox(modelCreazione.getSocietaTmp().getNome(),
                    modelCreazione.getUnitaLocaleTmp().getNome(), modelCreazione.getRepartoTmp().getNome());

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Crea Reparto");

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            // ------------------- Se viene premuto il tasto "Applica" ------------------- //

            if(clickedButton.get() == ButtonType.APPLY){
                if( dialogController.getNome() != null && !dialogController.getNome().equals("")){
                    int id = getNewId(listaTitolo);
                    Titolo newTitolo = new Titolo(id,
                            modelCreazione.getRepartoTmp().getId(),
                            dialogController.getNome());
                    modelCreazione.createTitoloTmp(newTitolo);
                    inserisciNuovoRecord(newTitolo);

                    tableTitoli.getItems().add(newTitolo);

                    tableTitoli.refresh();
                } else {
                    Alerts.errorAllert();
                }
            }
        }
    }

    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths, ModelModifica modelModifica) {

        this.modelCreazione = modelCreazione;
        this.modelPaths = modelPaths;
        this.modelModifica = modelModifica;

    }

}
