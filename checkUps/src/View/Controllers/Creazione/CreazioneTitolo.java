package View.Controllers.Creazione;

import java.io.IOException;
import java.util.List;
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
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;

import View.Controllers.ViewController;
import View.Controllers.Creazione.dialogPane.DialogPaneAddT;
import View.Controllers.Modifiche.DialogPane.DialogPaneModificaTitolo;
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

public class CreazioneTitolo implements Initializable, CreazioneTInterface {

    @FXML
    private TableView<Reparto> tableReparti;

    @FXML
    private TableView<Titolo> tableTitoli;

    @FXML
    private TableColumn<Titolo, String> descColT;

    @FXML
    private JFXButton btnNext;

    @FXML
    private JFXButton btnAggiungi;

    @FXML
    private JFXButton btnModifica;

    private ModelCreazione modelCreazione;
    private ModelPaths modelPaths;
    private ModelModifica modelModifica;

    private List<Titolo> listaTitolo;

    private Reparto localReparto;
    private UnitaLocale localUnita;
    private Societa localSocieta;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        listaTitolo = ClassHelper.getListTitolo();

        descColT.setCellValueFactory(new PropertyValueFactory<Titolo, String>("descrizione"));

    }

    // --------------- popola la tabella dei titoli --------------- //
    private void fillTableViewTitoli() {
        List<Titolo> specificList = null;
        ObservableList<Titolo> observableList = null;

        if (localReparto != null) {
            specificList = modelCreazione.fillTitoliTable(listaTitolo, localReparto);

            observableList = FXCollections.observableArrayList(specificList);
            tableTitoli.setItems(observableList);
        } else
            Alerts.errorAllert("Errore", "Reparto non selezionata",
                    "Impossibile riempire la tabella titoli se non si è selezionato un Reparto");
    }

    public void goBack() {

    }

    public void modifica() {

        if (tableTitoli.getSelectionModel().getSelectedItem() != null) {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/View/fxml/dialogPaneModifica/modifica_titolo_dialogPane.fxml"));
            DialogPane dialogPane;
            try {
                dialogPane = loader.load();

                DialogPaneModificaTitolo dialogController = loader.getController();

                dialogController.setModel(modelModifica);

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(dialogPane);
                dialog.setTitle("Modifica Titolo");

                Optional<ButtonType> clickedButton = dialog.showAndWait();

                if (clickedButton.get() == ButtonType.APPLY) {

                    // updateChanges(dialogController);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            Alerts.errorAllert("Errore", "Selezione del Titolo fallita", "Il titolo selezionato non è valido");
    }

    @FXML
    public void delete() {
        if (tableTitoli.getSelectionModel().getSelectedItem() != null) {
            Titolo titolo = tableTitoli.getSelectionModel().getSelectedItem();
            Controller.eliminaRecord(titolo, titolo.getId());
            tableTitoli.getItems().remove(titolo);
        }
    }

    @FXML
    public void aggiungi() {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/View/fxml/dialogPaneCreazione/creaTitolo_dialogPane.fxml"));
        DialogPane dialogPane;
        try {
            dialogPane = loader.load();

            DialogPaneAddT dialogController = loader.getController();

            dialogController.setModel(modelCreazione);
            dialogController.fillTextBox(modelCreazione.getSocietaTmp().getNome(),
                    modelCreazione.getUnitaLocaleTmp().getNome(), modelCreazione.getRepartoTmp().getNome());

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Crea Reparto");

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            // ------------------- Se viene premuto il tasto "Applica" -------------------
            // //

            if (clickedButton.get() == ButtonType.APPLY) {
                if (dialogController.getNome() != null && !dialogController.getNome().equals("")) {
                    int id = Controller.getNewId(listaTitolo);
                    Titolo newTitolo = new Titolo(id,
                            modelCreazione.getRepartoTmp().getId(),
                            dialogController.getNome());
                    modelCreazione.createTitoloTmp(newTitolo);
                    Controller.inserisciNuovoRecord(newTitolo);

                    tableTitoli.getItems().add(newTitolo);

                    tableTitoli.refresh();
                } else {
                    Alerts.errorAllert("Errore", "Errore nell'inserimento",
                            "Qualcosa non è stato inserito correttamente");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importa() {

    }

    @FXML
    public void saveAndGoNext() {
        if (tableTitoli.getSelectionModel().getSelectedItem() != null) {
            Titolo titolo = tableTitoli.getSelectionModel().getSelectedItem();

            modelCreazione.createTitoloTmp(titolo);

            modelCreazione.setSaved(false);
            modelCreazione.setCanGoNext(false);

            try {
                Parent root = modelPaths.switchToCreazioneOggetto(modelCreazione);

                Controller.changePane(modelPaths.getStackPaneCrea(), root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            Alerts.errorAllert("Errore", "Errore nella Selezione del Titolo",
                    "Non è stato selezionato nessun titolo");
    }

    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths, ModelModifica modelModifica) {

        this.modelCreazione = modelCreazione;
        this.modelPaths = modelPaths;
        this.modelModifica = modelModifica;

        if (modelCreazione.getSocietaTmp() != null) {
            this.localSocieta = modelCreazione.getSocietaTmp();
        }

        if (modelCreazione.getUnitaLocaleTmp() != null) {
            this.localUnita = modelCreazione.getUnitaLocaleTmp();
        }

        if (modelCreazione.getRepartoTmp() != null) {
            this.localReparto = modelCreazione.getRepartoTmp();
            fillTableViewTitoli();
        }

        tableTitoli.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                modelCreazione.setCanGoNext(true);
                System.out.println("Titolo selezionato: " + newValue.getDescrizione());
            }
        });

    }

}
