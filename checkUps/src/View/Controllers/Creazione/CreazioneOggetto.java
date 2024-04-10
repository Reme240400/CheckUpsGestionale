package View.Controllers.Creazione;

import java.util.List;

import com.jfoenix.controls.JFXButton;

import Controllers.Controller;
import Helpers.ClassHelper;
import Models.Model;
import Models.ModelCreazione;
import Models.ModelDb;
import Models.ModelPaths;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.imports.ImportOggettoElement;
import Models.others.Alerts;
import Models.others.CreazioneBase;
import Models.others.Dialogs;
import Models.others.TipoCreazionePagina;
import View.Controllers.Creazione.dialogPane.DPImportOggetto;
import View.Controllers.Creazione.dialogPane.DialogPaneAddO;
import View.Controllers.Modifiche.DialogPaneModificaOggetto;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CreazioneOggetto extends CreazioneBase implements Initializable {

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

    private List<Oggetto> listaOggetti;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        listaOggetti = ClassHelper.getListOggetto();

        nomeCol.setCellValueFactory(new PropertyValueFactory<Oggetto, String>("nome"));
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
    public void onModifica() {
        if (tableOggetti.getSelectionModel().getSelectedItem() == null) {
            Alerts.errorAlert("Errore", "Selezione dell'Oggetto fallita", "L'oggetto selezionato non è valido");
            return;
        }

        Dialogs.showDialogWithResponse("dialogPaneModifica/modifica_oggetto_dialogPane.fxml", "Modifica Oggetto",
                (DialogPaneModificaOggetto controller) -> controller.setModel(modelCreazione),
                (DialogPaneModificaOggetto controller) -> {
                    if (modelCreazione.getOggettoTmp() != null &&
                            modelCreazione.getOggettoTmp().getNome() != "") {

                        modelCreazione.getOggettoTmp().setNome(controller.getNome());

                        Controller.modificaCampo(modelCreazione.getOggettoTmp());
                        tableOggetti.refresh();
                    } else {
                        Alerts.errorAlert("Errore", "Selezione dell'Oggetto fallita",
                                "L'oggetto selezionato non è valido");
                    }
                });
    }

    @FXML
    public void onAggiungi() {
        Dialogs.showDialogWithResponse("dialogPaneCreazione/creaOggetto_dialogPane.fxml",
                "Crea Oggetto",
                (DialogPaneAddO controller) -> controller.fillTextBox(localSocieta.getNome(),
                        localUnita.getNome(), localReparto.getNome(), localTitolo.getDescrizione()),
                (DialogPaneAddO controller) -> {
                    if (controller.getNome() != null && !controller.getNome().equals("")) {

                        int id = Controller.getNewId(listaOggetti);
                        Oggetto newOggetto = new Oggetto(id,
                                controller.getNome(),
                                localTitolo.getId());

                        Controller.inserisciNuovoRecord(newOggetto);
                        tableOggetti.getItems().add(newOggetto);
                        tableOggetti.refresh();
                    } else {
                        Alerts.errorAlert("Errore", "Errore nell'inserimento",
                                "Qualcosa non è stato inserito correttamente");
                    }
                });
    }

    public void onImporta() {
        Dialogs.showDialogWithResponse("dialogPaneImporta/importa_oggetto.fxml", "Importa Oggetto",
                (DPImportOggetto controller) -> {
                    controller.fillInfo(modelCreazione.getSocietaTmp().getNome(),
                            modelCreazione.getUnitaLocaleTmp().getNome(), modelCreazione.getRepartoTmp().getNome(),
                            modelCreazione.getTitoloTmp().getDescrizione());
                    controller.populateTable(modelCreazione.getRepartoTmp().getId());
                },
                (DPImportOggetto controller) -> {
                    if (controller.getSelectedData() == null) {
                        Alerts.errorAlert("Errore", "Errore nell'importazione",
                                "Non è stato selezionato nessun titolo");
                        return;
                    }

                    ImportOggettoElement selected = controller.getSelectedData();
                    Oggetto oggetto = selected.getOggetto();

                    List<Provvedimento> provvedimenti = ClassHelper.getListProvvedimento().stream()
                            .filter(provvedimento -> provvedimento.getIdOggetto() == oggetto.getId()).toList();

                    Oggetto nuovoOggetto = new Oggetto(Model.autoSetId(ClassHelper.getListOggetto()), oggetto.getNome(),
                            modelCreazione.getTitoloTmp().getId());

                    Controller.inserisciNuovoRecord(nuovoOggetto);
                    tableOggetti.getItems().add(nuovoOggetto);
                    tableOggetti.refresh();

                    ModelDb.bulkInsertOggetto(nuovoOggetto.getId(), provvedimenti);
                });
    }

    public void onActionSave() {
        if (tableOggetti.getSelectionModel().getSelectedItem() == null) {
            Alerts.errorAlert("Errore", "Errore nella Selezione del Reparto",
                    "Non è stato selezionato nessun reparto");
            return;
        }

        super.changePage(TipoCreazionePagina.PROVVEDIMENTO, true);
    }

    public void onActionBack() {
        modelCreazione.resetOggettoTmp();
        super.changePage(TipoCreazionePagina.TITOLO, false);
    }

    @Override
    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths) {
        super.setModel(modelCreazione, modelPaths);
        this.fillTable(tableOggetti, listaOggetti, this.localTitolo);

        this.btnNext.disableProperty().bind(modelCreazione.canGoNextProperty().not());
        this.btnModifica.disableProperty().bind(modelCreazione.canGoNextProperty().not());

        tableOggetti.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOggetto) -> {
            if (selectedOggetto != null) {
                modelCreazione.setCanGoNext(true);
                modelCreazione.createOggettoTmp(selectedOggetto);
            }
        });

        if (this.localOggetto != null) {
            tableOggetti.selectionModelProperty().get().select(this.localOggetto);
        }
    }

}
