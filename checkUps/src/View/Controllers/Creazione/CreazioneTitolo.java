package View.Controllers.Creazione;

import java.util.List;

import com.jfoenix.controls.JFXButton;

import Controllers.Controller;
import Helpers.ClassHelper;
import Models.Alerts;
import Models.Dialogs;
import Models.Model;
import Models.ModelCreazione;
import Models.ModelDb;
import Models.ModelPaths;
import Models.TipoCreazionePagina;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Titolo;
import Models.creazione.CreazioneBase;
import Models.imports.ImportTitoloElement;
import View.Controllers.Creazione.dialogPane.DPImportTitolo;
import View.Controllers.Creazione.dialogPane.DialogPaneAddT;
import View.Controllers.Modifiche.DialogPaneModificaTitolo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CreazioneTitolo extends CreazioneBase implements Initializable {
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

    private List<Titolo> listaTitolo;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        listaTitolo = ClassHelper.getListTitolo();

        descColT.setCellValueFactory(new PropertyValueFactory<Titolo, String>("descrizione"));
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
        Dialogs.showDialogWithResponse("dialogPaneCreazione/creaTitolo_dialogPane.fxml", "Crea Reparto",
                (DialogPaneAddT controller) -> {
                    controller.fillTextBox(modelCreazione.getSocietaTmp().getNome(),
                            modelCreazione.getUnitaLocaleTmp().getNome(), modelCreazione.getRepartoTmp().getNome());
                },
                (DialogPaneAddT controller) -> {
                    if (controller.getNome() != null && !controller.getNome().equals("")) {
                        int id = Controller.getNewId(listaTitolo);
                        Titolo newTitolo = new Titolo(id,
                                modelCreazione.getRepartoTmp().getId(),
                                controller.getNome());
                        modelCreazione.createTitoloTmp(newTitolo);
                        Controller.inserisciNuovoRecord(newTitolo);
                        tableTitoli.getItems().add(newTitolo);
                        tableTitoli.refresh();
                    } else {
                        Alerts.errorAlert("Errore", "Errore nell'inserimento",
                                "Qualcosa non è stato inserito correttamente");
                    }
                });

    }

    @FXML
    public void onModifica() {
        if (tableTitoli.getSelectionModel().getSelectedItem() == null) {
            Alerts.errorAlert("Errore", "Selezione del Titolo fallita", "Il titolo selezionato non è valido");
            return;
        }

        Dialogs.showDialogWithResponse("dialogPaneModifica/modifica_titolo_dialogPane.fxml", "Modifica Titolo",
                (DialogPaneModificaTitolo controller) -> controller.setModel(modelCreazione),
                (DialogPaneModificaTitolo controller) -> {
                    var desc = controller.getDescTitolo();
                    if (modelCreazione.getTitoloTmp() == null ||
                            modelCreazione.getTitoloTmp().getDescrizione().equals("")) {
                        Alerts.errorAlert("Errore", "Selezione del Titolo fallita",
                                "Il reparto selezionato non è valido");
                        return;
                    }

                    modelCreazione.getTitoloTmp().setDescrizione(desc);
                    Controller.modificaCampo(modelCreazione.getTitoloTmp());
                    tableTitoli.refresh();
                });
    }

    @FXML
    public void onImporta() {
        Dialogs.showDialogWithResponse("dialogPaneImporta/importa_titolo.fxml", "Importa Titolo",
                (DPImportTitolo controller) -> {
                    controller.fillInfo(modelCreazione.getSocietaTmp().getNome(),
                            modelCreazione.getUnitaLocaleTmp().getNome(), modelCreazione.getRepartoTmp().getNome());
                    controller.populateTable(modelCreazione.getRepartoTmp().getId());
                },
                (DPImportTitolo controller) -> {
                    if (controller.getSelectedData() == null) {
                        Alerts.errorAlert("Errore", "Errore nell'importazione",
                                "Non è stato selezionato nessun titolo");
                        return;
                    }

                    ImportTitoloElement selected = controller.getSelectedData();
                    Titolo titolo = selected.getTitolo();

                    List<Oggetto> oggetti = ClassHelper.getListOggetto().stream()
                            .filter(oggetto -> oggetto.getIdTitolo() == titolo.getId()).toList();
                    List<Provvedimento> provvedimenti = ClassHelper.getListProvvedimento().stream()
                            .filter(provvedimento -> oggetti.stream()
                                    .anyMatch(oggetto -> oggetto.getId() == provvedimento.getIdOggetto()))
                            .toList();

                    Titolo nuovoTitolo = new Titolo(Model.autoSetId(ClassHelper.getListTitolo()),
                            modelCreazione.getRepartoTmp().getId(), titolo.getDescrizione());

                    Controller.inserisciNuovoRecord(nuovoTitolo);
                    tableTitoli.getItems().add(nuovoTitolo);
                    tableTitoli.refresh();

                    ModelDb.bulkInsertTitolo(nuovoTitolo.getId(), oggetti, provvedimenti);
                });
    }

    // CODICE "SISTEMATO"

    public void onActionSave() {
        if (tableTitoli.getSelectionModel().getSelectedItem() == null) {
            Alerts.errorAlert("Errore", "Errore nella Selezione del Titolo",
                    "Non è stato selezionato nessun titolo");
            return;
        }

        super.changePage(TipoCreazionePagina.OGGETTO, true);
    }

    public void onActionBack() {
        modelCreazione.resetTitoloTmp();
        super.changePage(TipoCreazionePagina.REPARTO, false);
    }

    @Override
    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths) {
        super.setModel(modelCreazione, modelPaths);
        super.fillTable(tableTitoli, listaTitolo, localReparto);

        this.btnNext.disableProperty().bind(modelCreazione.canGoNextProperty().not());
        this.btnModifica.disableProperty().bind(modelCreazione.canGoNextProperty().not());

        tableTitoli.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedTitolo) -> {
            if (selectedTitolo != null) {
                modelCreazione.setCanGoNext(true);
                modelCreazione.createTitoloTmp(selectedTitolo);
            }
        });

        if (this.localTitolo != null) {
            tableTitoli.selectionModelProperty().get().select(this.localTitolo);
        }
    }

}
