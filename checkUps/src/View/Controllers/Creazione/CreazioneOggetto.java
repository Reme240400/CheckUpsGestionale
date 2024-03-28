package View.Controllers.Creazione;

import java.util.List;
import java.io.IOException;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import Controllers.Controller;
import Helpers.ClassHelper;
import Models.Alerts;
import Models.ModelCreazione;
import Models.ModelPaths;
import Models.TipoCreazionePagina;
import Models.Tables.Oggetto;
import Models.creazione.CreazioneBase;
import View.Controllers.Creazione.dialogPane.DialogPaneAddO;
import View.Controllers.Modifiche.DialogPaneModificaOggetto;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
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
            Alerts.errorAllert("Errore", "Selezione dell'Oggetto fallita", "L'oggetto selezionato non è valido");
            return;
        }

        this.showDialog("dialogPaneModifica/modifica_oggetto_dialogPane.fxml", "Modifica Oggetto",
                (DialogPaneModificaOggetto controller) -> controller.setModel(modelCreazione),
                (DialogPaneModificaOggetto controller) -> {
                    if (modelCreazione.getOggettoTmp() != null &&
                            modelCreazione.getOggettoTmp().getNome() != "") {

                        modelCreazione.getOggettoTmp().setNome(controller.getNome());

                        Controller.modificaCampo(modelCreazione.getOggettoTmp());
                        tableOggetti.refresh();
                    } else {
                        Alerts.errorAllert("Errore", "Selezione dell'Oggetto fallita",
                                "L'oggetto selezionato non è valido");
                    }
                });
    }

    @FXML
    public void onAggiungi() {
        this.showDialog("dialogPaneCreazione/creaOggetto_dialogPane.fxml",
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
                        Alerts.errorAllert("Errore", "Errore nell'inserimento",
                                "Qualcosa non è stato inserito correttamente");
                    }
                });
    }

    public void importa() {

    }

    public void onActionSave() {
        if (tableOggetti.getSelectionModel().getSelectedItem() == null) {
            Alerts.errorAllert("Errore", "Errore nella Selezione del Reparto",
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
