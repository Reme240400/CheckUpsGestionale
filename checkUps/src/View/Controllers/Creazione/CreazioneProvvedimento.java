package View.Controllers.Creazione;

import java.net.URL;
import java.util.List;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.Controller;
import Helpers.ClassHelper;
import Models.Alerts;
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
import View.Controllers.Creazione.dialogPane.DialogPaneAddP;
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

public class CreazioneProvvedimento implements Initializable {

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
    private List<Provvedimento> provList = ClassHelper.getListProvvedimento();

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

        idColP.setCellValueFactory(new PropertyValueFactory<Provvedimento, Integer>("id"));
        nomeColP.setCellValueFactory(new PropertyValueFactory<Provvedimento, String>("nome"));
        rischioCol.setCellValueFactory(new PropertyValueFactory<Provvedimento, String>("rischio"));
        soggetiCol.setCellValueFactory(new PropertyValueFactory<Provvedimento, String>("soggettiEsposti"));
        stimaCol.setCellValueFactory(new PropertyValueFactory<Provvedimento, Integer>("stimaR"));

        ObservableList<String> items = FXCollections.observableArrayList();

        // * *************** popola il combobox *************** //

        for (Societa societa : societaList) {
            cercaSocieta.getItems().add(societa.getNome());
            items.add(societa.getNome());
        }

        // --------------- filtra il Combobox --------------- //
        FilteredList<String> filteredItems = Model.filterComboBox(cercaSocieta, items);

        cercaSocieta.setItems(filteredItems);

        tableOggetti.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectOggetto(); // Chiama il metodo quando viene selezionato un elemento
            }
        });

        tableProvvedimenti.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectProv(); // Chiama il metodo quando viene selezionato un elemento
            }
        });
    }

    // --------------- triggherato quando si seleziona un' societa ---------------
    // //
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

    // --------------- triggherato quando si seleziona un' unita locale
    // --------------- //
    public void selectUnita() {
        List<Reparto> specificList = null;

        if (cercaUnita.getValue() != null && !cercaUnita.getValue().isEmpty()) {

            if (localSocieta != null) {
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
                FilteredList<String> filteredItems = Model.filterComboBoxById(cercaReparto, localSocieta.getId(),
                        items);

                cercaReparto.setItems(filteredItems);
            }
        }

        cercaReparto.setValue(null);
        cercaTitolo.setValue(null);
        tableOggetti.getItems().clear();
    }

    // --------------- triggherato quando si seleziona un' reparto ---------------
    // //
    public void selectReparto() {

        List<Titolo> specificList = null;

        if (localUnita != null && cercaReparto.getValue() != null && !cercaReparto.getValue().isEmpty()) {
            localReparto = repartoList.stream()
                    .filter(r -> r.getIdUnitaLocale() == localUnita.getId())
                    .filter(r -> r.getNome().equals(cercaReparto.getValue()))
                    .findFirst().get();

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

        if (localReparto != null && cercaTitolo.getValue() != null && !cercaTitolo.getValue().isEmpty()) {
            localTitolo = titoloList.stream()
                    .filter(u -> u.getIdReparto() == localReparto.getId())
                    .filter(u -> u.getDescrizione().equals(cercaTitolo.getValue()))
                    .findFirst().get();

            // textFieldTitolo.setText(modelCreazione.getTitoloTmp().getDescrizione());

            fillTableViewO();
        }
    }

    // --------------- triggherato quando si seleziona un' oggetto ---------------
    // //
    public void selectOggetto() {

        if (localTitolo != null && tableOggetti.getSelectionModel().getSelectedItem() != null) {
            localOggetto = tableOggetti.getSelectionModel().getSelectedItem();

            fillTableViewP();
        }
    }

    public void selectProv() {
        if (tableProvvedimenti.getSelectionModel().getSelectedItem() != null) {
            Provvedimento provvedimento = tableProvvedimenti.getSelectionModel().getSelectedItem();
            modelCreazione.createProvvedimentoTmp(provvedimento);
        }
    }

    private void fillTableViewO() {

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

    private void fillTableViewP() {

        List<Provvedimento> specificList = null;

        if (localOggetto != null) {
            specificList = ClassHelper.getListProvvedimento().stream()
                    .filter(p -> p.getIdOggetto() == localOggetto.getId())
                    .toList();
        }

        ObservableList<Provvedimento> items = FXCollections.observableArrayList();

        // * *************** popola il combobox *************** //

        for (Provvedimento provvedimento : specificList) {
            items.add(provvedimento);
        }

        tableProvvedimenti.setItems(items);

    }

    @FXML
    public void addProv() throws IOException {
        if (modelCreazione.getSocietaTmp() != null &&
                modelCreazione.getUnitaLocaleTmp() != null &&
                modelCreazione.getRepartoTmp() != null &&
                modelCreazione.getTitoloTmp() != null &&
                modelCreazione.getOggettoTmp() != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/creaProv_dialogPane.fxml"));
            DialogPane dialogPane = loader.load();

            DialogPaneAddP dialogController = loader.getController();

            dialogController.setModel(modelCreazione);
            dialogController.fillTextBox(modelCreazione.getSocietaTmp().getNome(),
                    modelCreazione.getUnitaLocaleTmp().getNome(),
                    modelCreazione.getRepartoTmp().getNome(),
                    modelCreazione.getTitoloTmp().getDescrizione(),
                    modelCreazione.getOggettoTmp().getNome());

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Crea provvedimento");

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            // ------------------- Se viene premuto il tasto "Applica" -------------------
            // //

            if (clickedButton.get() == ButtonType.APPLY) {
                if (dialogController.getNome() != null
                        && !dialogController.getNome().equals("")
                        && dialogController.getRischio() != null
                        && !dialogController.getRischio().equals("")
                        && dialogController.getSoggettiEsposti() != null
                        && !dialogController.getSoggettiEsposti().equals("")
                        && dialogController.getStimaR() != 0
                        && dialogController.getStimaD() != 0
                        && dialogController.getStimaP() != 0) {

                    int id = Controller.getNewId(provList);
                    LocalDate data = LocalDate.now();
                    Provvedimento newProvvedimento = new Provvedimento(id,
                            localOggetto.getId(),
                            dialogController.getNome(),
                            dialogController.getRischio(),
                            dialogController.getSoggettiEsposti(),
                            dialogController.getStimaR(),
                            dialogController.getStimaD(),
                            dialogController.getStimaP(),
                            dialogController.getEmail(),
                            Optional.of(data),
                            Optional.of(dialogController.getDataFine()));

                    modelCreazione.createProvvedimentoTmp(newProvvedimento);
                    Controller.inserisciNuovoRecord(newProvvedimento);

                    tableProvvedimenti.getItems().add(newProvvedimento);

                    tableProvvedimenti.refresh();

                } else {
                    Alerts.errorAllert("Errore", "Errore nell'inserimento",
                            "Qualcosa non è stato inserito correttamente");
                }
            }
        }

        if (localSocieta != null &&
                localUnita != null &&
                localReparto != null &&
                localTitolo != null &&
                localOggetto != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/creaProv_dialogPane.fxml"));
            DialogPane dialogPane = loader.load();

            DialogPaneAddP dialogController = loader.getController();

            dialogController.setModel(modelCreazione);
            dialogController.fillTextBox(localSocieta.getNome(),
                    localUnita.getNome(),
                    localReparto.getNome(),
                    localTitolo.getDescrizione(),
                    localOggetto.getNome());

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Crea provvedimento");

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            // ------------------- Se viene premuto il tasto "Applica" -------------------
            // //

            if (clickedButton.get() == ButtonType.APPLY) {
                if (dialogController.getNome() != null
                        && !dialogController.getNome().equals("")
                        && dialogController.getRischio() != null
                        && !dialogController.getRischio().equals("")
                        && dialogController.getSoggettiEsposti() != null
                        && !dialogController.getSoggettiEsposti().equals("")
                        && dialogController.getStimaR() != 0
                        && dialogController.getStimaD() != 0
                        && dialogController.getStimaP() != 0) {

                    int id = Controller.getNewId(provList);
                    LocalDate data = LocalDate.now();
                    Provvedimento newProvvedimento = new Provvedimento(id,
                            localOggetto.getId(),
                            dialogController.getNome(),
                            dialogController.getRischio(),
                            dialogController.getSoggettiEsposti(),
                            dialogController.getStimaR(),
                            dialogController.getStimaD(),
                            dialogController.getStimaP(),
                            dialogController.getEmail(),
                            Optional.of(data),
                            Optional.of(dialogController.getDataFine()));

                    modelCreazione.createProvvedimentoTmp(newProvvedimento);
                    Controller.inserisciNuovoRecord(newProvvedimento);

                    tableProvvedimenti.getItems().add(newProvvedimento);

                    tableProvvedimenti.refresh();
                } else {
                    Alerts.errorAllert("Errore", "Errore nell'inserimento",
                            "Qualcosa non è stato inserito correttamente");
                }
            }
        }
    }

    @FXML
    public void delete() {
        if (tableProvvedimenti.getSelectionModel().getSelectedItem() != null) {
            Provvedimento provvedimento = tableProvvedimenti.getSelectionModel().getSelectedItem();
            Controller.eliminaRecord(provvedimento, provvedimento.getId());
            tableProvvedimenti.getItems().remove(provvedimento);
        }
    }

    @FXML
    public void modify() {
        // if (tableProvvedimenti.getSelectionModel().getSelectedItem() != null) {

        // Parent root = new Parent() {};
        // modelModifica = new ModelModifica();

        // if(modelCreazione.getOggettoTmp() != null)
        // modelModifica.setOggetto(modelCreazione.getOggettoTmp());
        // else if(localOggetto != null)
        // modelModifica.setOggetto(localOggetto);

        // try {
        // root = modelPaths.switchToModificaProvvedimenti(modelModifica);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        // Controller.changePane(modelPaths.getStackPaneHome(), root);
        // }
    }

    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths, ModelModifica modelModifica) {
        this.modelCreazione = modelCreazione;
        this.modelPaths = modelPaths;
        this.modelModifica = modelModifica;

        if (modelCreazione.getSocietaTmp() != null) {
            localSocieta = modelCreazione.getSocietaTmp();
            cercaSocieta.setValue(localSocieta.getNome());
        }

        if (modelCreazione.getUnitaLocaleTmp() != null) {
            localUnita = modelCreazione.getUnitaLocaleTmp();
            cercaUnita.setValue(localUnita.getNome());
        }

        if (modelCreazione.getRepartoTmp() != null) {
            localReparto = modelCreazione.getRepartoTmp();
            cercaReparto.setValue(localReparto.getNome());
        }

        if (modelCreazione.getTitoloTmp() != null) {
            localTitolo = modelCreazione.getTitoloTmp();
            cercaTitolo.setValue(localTitolo.getDescrizione());
        }

        if (modelCreazione.getOggettoTmp() != null) {
            localOggetto = modelCreazione.getOggettoTmp();
            fillTableViewO();
        }

    }

}
