package View.Controllers.Creazione;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;

import Controllers.Controller;
import Helpers.ClassHelper;
import Models.Alerts;
import Models.Dialogs;
import Models.ModelCreazione;
import Models.ModelPaths;
import Models.TipoCreazionePagina;
import Models.Tables.Provvedimento;
import Models.creazione.CreazioneBase;
import View.Controllers.Creazione.dialogPane.DialogPaneAddP;
import View.Controllers.Modifiche.DialogPaneModificaProv;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CreazioneProvvedimento extends CreazioneBase implements Initializable {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDel;

    @FXML
    private JFXButton btnModify;

    @FXML
    private TableColumn<Provvedimento, String> rischioCol;

    @FXML
    private TableColumn<Provvedimento, String> nomeCol;

    @FXML
    private TableColumn<Provvedimento, String> soggettiCol;

    @FXML
    private TableColumn<Provvedimento, Integer> stimaRCol;

    @FXML
    private TableColumn<Provvedimento, String> datainizioCol;

    @FXML
    private TableColumn<Provvedimento, String> datascadenzaCol;

    @FXML
    private TableView<Provvedimento> tableProvvedimenti;

    private List<Provvedimento> listProv;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listProv = ClassHelper.getListProvvedimento();

        rischioCol.setCellValueFactory(new PropertyValueFactory<Provvedimento, String>("rischio"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<Provvedimento, String>("nome"));
        soggettiCol.setCellValueFactory(new PropertyValueFactory<Provvedimento, String>("soggettiEsposti"));
        stimaRCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStimaR()).asObject());
        datainizioCol.setCellValueFactory(data -> {
            var dataInizio = data.getValue().getDataInizio();
            return dataInizio.isPresent() ? new SimpleStringProperty(dataInizio.get().toString())
                    : new SimpleStringProperty("");
        });
        datascadenzaCol.setCellValueFactory(data -> {
            var dataScadenza = data.getValue().getDataInizio();
            return dataScadenza.isPresent() ? new SimpleStringProperty(dataScadenza.get().toString())
                    : new SimpleStringProperty("");
        });
    }

    @FXML
    public void onAggiungi() {
        Dialogs.showDialogWithResponse("dialogPaneCreazione/creaProv_dialogPane.fxml",
                "Crea Provvedimento",
                (DialogPaneAddP controller) -> controller.fillTextBox(localSocieta.getNome(),
                        localUnita.getNome(),
                        localReparto.getNome(),
                        localTitolo.getDescrizione(),
                        localOggetto.getNome()),
                (DialogPaneAddP controller) -> {
                    var checkResponse = controller.areFieldsValid();
                    if (!checkResponse.isValid()) {
                        Alerts.errorAlert("Errore", "Errore nell'inserimento",
                                "Qualcosa non è stato inserito correttamente\nErrore: '"
                                        + checkResponse.getErrorMessage()
                                        + "'");
                        return;
                    }

                    int id = Controller.getNewId(listProv);
                    Provvedimento newProvvedimento = new Provvedimento(id,
                            localOggetto.getId(),
                            controller.getNome(),
                            controller.getRischio(),
                            controller.getSoggettiEsposti(),
                            controller.getStimaD(),
                            controller.getStimaP(),
                            Optional.of(LocalDate.now()),
                            Optional.of(controller.getDataFine()));

                    Controller.inserisciNuovoRecord(newProvvedimento);
                    tableProvvedimenti.getItems().add(newProvvedimento);
                    tableProvvedimenti.refresh();
                });
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
    public void onModifica() {
        if (tableProvvedimenti.getSelectionModel().getSelectedItem() == null) {
            Alerts.errorAlert("Errore", "Selezione del Provvedimento fallita",
                    "Il provvedimento selezionato non è valido");
            return;
        }

        Dialogs.showDialogWithResponse("dialogPaneModifica/modifica_prov_dialogPane.fxml",
                "Modifica Provvedimento",
                (DialogPaneModificaProv controller) -> controller.setModel(modelCreazione),
                (DialogPaneModificaProv controller) -> {
                    var result = controller.areFieldsValid();
                    if (!result.isValid()) {
                        Alerts.errorAlert("Errore", "Errore durante la modifica",
                                result.getErrorMessage());
                        return;
                    }

                    Controller.modificaCampo(modelCreazione.getProvvedimentoTmp());
                    tableProvvedimenti.refresh();
                });
    }

    public void onActionBack() {
        modelCreazione.resetProvvedimentoTmp();
        super.changePage(TipoCreazionePagina.OGGETTO, false);
    }

    @Override
    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths) {
        super.setModel(modelCreazione, modelPaths);
        super.fillTable(tableProvvedimenti, listProv, this.localOggetto);

        tableProvvedimenti.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, selectedProvvedimento) -> {
                    // Handle the selection change, newValue contains the selected Reparto
                    if (selectedProvvedimento != null) {
                        modelCreazione.setCanGoNext(true);
                        modelCreazione.createProvvedimentoTmp(selectedProvvedimento);
                    }
                });

        this.btnModify.disableProperty().bind(modelCreazione.canGoNextProperty().not());
    }

}
