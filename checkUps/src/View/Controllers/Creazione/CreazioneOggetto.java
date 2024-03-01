package View.Controllers.Creazione;

import java.util.List;
import java.io.IOException;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import Controllers.Controller;
import Helpers.ClassHelper;
import Interfaces.CreazioneTInterface;
import Models.Alerts;
import Models.ModelCreazione;
import Models.ModelModifica;
import Models.ModelPaths;
import Models.Tables.Oggetto;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.Titolo;
import Models.Tables.UnitaLocale;
import View.Controllers.Creazione.DialogPane.DialogPaneAddO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CreazioneOggetto implements Initializable, CreazioneTInterface {

    @FXML
    private JFXButton btnAggiungi;

    @FXML
    private JFXButton btnModifica;

    @FXML
    private JFXButton btnNext;

    @FXML
    private TableView<Oggetto> tableOggetti;

    @FXML
    private TableColumn<Oggetto, String> nomeCol;

    @FXML
    private TableColumn<Oggetto, String> nomeColT;

    private ModelCreazione modelCreazione;
    private ModelPaths modelPaths;
    private ModelModifica modelModifica;

    private Societa localSocieta;
    private UnitaLocale localUnita;
    private Reparto localReparto;
    private Titolo localTitolo;
    private Oggetto localOggetto;

    private List<Oggetto> listaOggetti;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        listaOggetti = ClassHelper.getListOggetto();

    }

    private void fillTableView() {

        List<Oggetto> specificList = null;
        ObservableList<Oggetto> observableList = null;

        if (localTitolo != null) {
            specificList = modelCreazione.fillOggettiTable(listaOggetti, localTitolo);

            observableList = FXCollections.observableArrayList(specificList);
            tableOggetti.setItems(observableList);

        } else
            Alerts.errorAllert("Errore", "Titolo non selezionato",
                    "Impossibile riempire la tabella oggetti se non si è selezionato un Titolo");

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
        // if (tableOggetti.getSelectionModel().getSelectedItem() != null) {

        // Parent root = new Parent() {};
        // modelModifica = new ModelModifica();

        // if (modelCreazione.getUnitaLocaleTmp() != null)
        // modelModifica.setUnitaLocale(modelCreazione.getUnitaLocaleTmp());
        // else if (localUnita != null)
        // modelModifica.setUnitaLocale(localUnita);

        // try {
        // root = modelPaths.switchToModificaOggetti(modelModifica);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        // Controller.changePane(modelPaths.getStackPaneHome(), root);
        // }
    }

    @FXML
    public void aggiungi() {

        if (localSocieta != null &&
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

                // ------------------- Se viene premuto il tasto "Applica" ------------------- //

                if (clickedButton.get() == ButtonType.APPLY) {
                    if (dialogController.getNome() != null
                            && !dialogController.getNome().equals("")) {

                        int id = Controller.getNewId(listaOggetti);
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
                e.printStackTrace();
            }
        }
    }

    public void importa(){

    }

    @FXML
    public void saveAndGoNext() {
        if (tableOggetti.getSelectionModel().getSelectedItem() != null) {
            Oggetto oggetto = tableOggetti.getSelectionModel().getSelectedItem();

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
            this.localSocieta = modelCreazione.getSocietaTmp();
        }

        if (modelCreazione.getUnitaLocaleTmp() != null) {
            this.localUnita = modelCreazione.getUnitaLocaleTmp();
        }

        if (modelCreazione.getRepartoTmp() != null) {
            this.localReparto = modelCreazione.getRepartoTmp();
        }

        if (modelCreazione.getTitoloTmp() != null) {
            this.localTitolo = modelCreazione.getTitoloTmp();
            fillTableView();
        }

    }

}
