package View.Controllers.Creazione;

import java.util.List;
import java.io.IOException;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.Controller;
import Helpers.ClassHelper;
import Interfaces.CreazioneTInterface;
import Models.Alerts;
import Models.Model;
import Models.ModelCreazione;
import Models.ModelModifica;
import Models.ModelPaths;
import Models.Tables.Oggetto;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import View.Controllers.Creazione.DialogPane.DialogPaneAddO;

import javafx.beans.property.SimpleStringProperty;
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

public class CreazioneOggetto implements Initializable, CreazioneTInterface {

    @FXML
    private JFXButton btnAggiungi;

    // @FXML
    // private JFXButton btnDel;

    @FXML
    private JFXButton btnModifica;

    @FXML
    private JFXButton btnNext;

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

        idCol.setCellValueFactory(new PropertyValueFactory<Oggetto, Integer>("id"));
        nomeColT.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(titoloList.stream()
                    .filter(reparto -> reparto.getId() == cellData.getValue().getIdTitolo())
                    .findFirst().get()
                    .getDescrizione());
        });

        nomeCol.setCellValueFactory(new PropertyValueFactory<Oggetto, String>("nome"));

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

    // --------------- triggherato quando si seleziona un' societa ---------------
    // //
    public void selectSocieta() {
        List<UnitaLocale> specificList = null;

        if (cercaSocieta.getValue() != null && !cercaSocieta.getValue().isEmpty()) {

            if (localSocieta != null) {
                if (!cercaSocieta.getValue().equals(localSocieta.getNome()))
                    modelCreazione.resetAllTmp();
            }

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
            tableOggetti.getItems().clear();
            if (localUnita != null) {
                if (!cercaUnita.getValue().equals(localUnita.getNome()))
                    modelCreazione.resetAllTmp();
            }
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
        if (tableOggetti.getSelectionModel().getSelectedItem() != null) {
            if (Alerts.deleteAlert(tableOggetti.getSelectionModel().getSelectedItem().getNome())) {
                Oggetto reparto = tableOggetti.getSelectionModel().getSelectedItem();
                Controller.eliminaRecord(reparto, reparto.getId());
                tableOggetti.getItems().remove(reparto);
            }
        }
    }

    @FXML
    public void modifica() {
        if (tableOggetti.getSelectionModel().getSelectedItem() != null) {

            Parent root = new Parent() {};
            modelModifica = new ModelModifica();

            if (modelCreazione.getUnitaLocaleTmp() != null)
                modelModifica.setUnitaLocale(modelCreazione.getUnitaLocaleTmp());
            else if (localUnita != null)
                modelModifica.setUnitaLocale(localUnita);

            try {
                root = modelPaths.switchToModificaOggetti(modelModifica);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Controller.changePane(modelPaths.getStackPaneHome(), root);
        }
    }

    @FXML
    public void aggiungi() {

        /*
         * if (modelCreazione.getSocietaTmp() != null &&
         * modelCreazione.getUnitaLocaleTmp() != null &&
         * modelCreazione.getRepartoTmp() != null &&
         * modelCreazione.getTitoloTmp() != null) {
         * 
         * FXMLLoader loader = new
         * FXMLLoader(getClass().getResource("/View/fxml/creaOggetto_dialogPane.fxml"));
         * DialogPane dialogPane;
         * try {
         * dialogPane = loader.load();
         * 
         * 
         * DialogPaneAddO dialogController = loader.getController();
         * 
         * dialogController.setModel(modelCreazione);
         * dialogController.fillTextBox(modelCreazione.getSocietaTmp().getNome(),
         * modelCreazione.getUnitaLocaleTmp().getNome(),
         * modelCreazione.getRepartoTmp().getNome(),
         * modelCreazione.getTitoloTmp().getDescrizione());
         * 
         * Dialog<ButtonType> dialog = new Dialog<>();
         * dialog.setDialogPane(dialogPane);
         * dialog.setTitle("Crea Oggetto");
         * 
         * Optional<ButtonType> clickedButton = dialog.showAndWait();
         * 
         * // ------------------- Se viene premuto il tasto "Applica"
         * ------------------- //
         * 
         * if (clickedButton.get() == ButtonType.APPLY) {
         * if (dialogController.getNome() != null
         * && !dialogController.getNome().equals("")) {
         * 
         * int id = Controller.getNewId(oggettoList);
         * Oggetto newOggetto = new Oggetto(id,
         * dialogController.getNome(),
         * modelCreazione.getTitoloTmp().getId());
         * 
         * modelCreazione.createOggettoTmp(newOggetto);
         * Controller.inserisciNuovoRecord(newOggetto);
         * 
         * tableOggetti.getItems().add(newOggetto);
         * 
         * tableOggetti.refresh();
         * 
         * } else {
         * Alerts.errorAllert("Errore", "Errore nell'inserimento",
         * "Qualcosa non è stato inserito correttamente");
         * }
         * }
         * } catch (IOException e) {
         * // TODO Auto-generated catch block
         * e.printStackTrace();
         * }
         * }else
         */ if (localSocieta != null &&
                localUnita != null &&
                localReparto != null &&
                localTitolo != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/creaOggetto_dialogPane.fxml"));
            DialogPane dialogPane;
            try {
                dialogPane = loader.load();

                DialogPaneAddO dialogController = loader.getController();

                dialogController.setModel(modelCreazione);
                dialogController.fillTextBox(localSocieta.getNome(),
                        localUnita.getNome(), localReparto.getNome(), localTitolo.getDescrizione());

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(dialogPane);
                dialog.setTitle("Crea Oggetto");

                Optional<ButtonType> clickedButton = dialog.showAndWait();

                // ------------------- Se viene premuto il tasto "Applica" -------------------
                // //

                if (clickedButton.get() == ButtonType.APPLY) {
                    if (dialogController.getNome() != null
                            && !dialogController.getNome().equals("")) {

                        int id = Controller.getNewId(oggettoList);
                        Oggetto newOggetto = new Oggetto(id,
                                dialogController.getNome(),
                                localTitolo.getId());

                        modelCreazione.createOggettoTmp(newOggetto);
                        Controller.inserisciNuovoRecord(newOggetto);

                        tableOggetti.getItems().add(newOggetto);

                        tableOggetti.refresh();

                    } else {
                        Alerts.errorAllert("Errore", "Errore nell'inserimento",
                                "Qualcosa non è stato inserito correttamente");
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void next() {
        if (tableOggetti.getSelectionModel().getSelectedItem() != null) {
            Oggetto oggetto = tableOggetti.getSelectionModel().getSelectedItem();

            modelCreazione.createSocietaTmp(localSocieta);
            modelCreazione.createUnitaLocaleTmp(localUnita);
            modelCreazione.createRepartoTmp(localReparto);
            modelCreazione.createTitoloTmp(localTitolo);
            modelCreazione.createOggettoTmp(oggetto);

            try {
                Parent root = modelPaths.switchToCreazioneProvvedimento(modelCreazione);

                Controller.changePane(modelPaths.getStackPaneCrea(), root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            Alerts.errorAllert("Errore", "Errore nella Selezione del Reparto",
                    "Non è stato selezionato nessun reparto");

    }

    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths, ModelModifica modelModifica) {
        this.modelCreazione = modelCreazione;
        this.modelPaths = modelPaths;
        this.modelModifica = modelModifica;

        if (modelCreazione.getSocietaTmp() != null) {
            cercaSocieta.setValue(localSocieta.getNome());
            selectUnita();
        }

        if (modelCreazione.getUnitaLocaleTmp() != null) {
            cercaUnita.setValue(localUnita.getNome());
            selectReparto();
        }

        if (modelCreazione.getRepartoTmp() != null) {
            cercaReparto.setValue(localReparto.getNome());
            selectTitolo();
        }

        if (modelCreazione.getTitoloTmp() != null) {
            cercaTitolo.setValue(localTitolo.getDescrizione());
            fillTableView();
        }

    }

}
