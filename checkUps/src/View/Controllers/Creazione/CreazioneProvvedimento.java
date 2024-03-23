package View.Controllers.Creazione;

import java.net.URL;
import java.util.List;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;

import Controllers.Controller;
import Helpers.ClassHelper;
import Models.Alerts;
import Models.ModelCreazione;
import Models.ModelPaths;
import Models.TipoCreazionePagina;
import Models.Tables.Provvedimento;
import Models.creazione.CreazioneBase;
import View.Controllers.Creazione.dialogPane.DialogPaneAddP;
import View.Controllers.Modifiche.DialogPaneModificaProv;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
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
    private TableColumn<Provvedimento, String> emailCol;

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
        emailCol.setCellValueFactory(new PropertyValueFactory<Provvedimento, String>("email"));
        stimaRCol.setCellValueFactory(new PropertyValueFactory<Provvedimento, Integer>("stimaR"));
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
    public void aggiungi() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/View/fxml/dialogPaneCreazione/creaProv_dialogPane.fxml"));
            DialogPane dialogPane = loader.load();

            DialogPaneAddP dialogController = loader.getController();

            dialogController.fillTextBox(localSocieta.getNome(),
                    localUnita.getNome(),
                    localReparto.getNome(),
                    localTitolo.getDescrizione(),
                    localOggetto.getNome());

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Crea provvedimento");

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            if (clickedButton.get() == ButtonType.APPLY) {
                var checkResponse = dialogController.areFieldsValid();
                if (!checkResponse.isValid()) {
                    Alerts.errorAllert("Errore", "Errore nell'inserimento",
                            "Qualcosa non è stato inserito correttamente\nErrore: '" + checkResponse.getErrorMessage()
                                    + "'");
                    return;
                }

                int id = Controller.getNewId(listProv);
                Provvedimento newProvvedimento = new Provvedimento(id,
                        localOggetto.getId(),
                        dialogController.getNome(),
                        dialogController.getRischio(),
                        dialogController.getSoggettiEsposti(),
                        dialogController.getStimaR(),
                        dialogController.getStimaD(),
                        dialogController.getStimaP(),
                        dialogController.getEmail(),
                        Optional.of(LocalDate.now()),
                        Optional.of(dialogController.getDataFine()));

                Controller.inserisciNuovoRecord(newProvvedimento);
                tableProvvedimenti.getItems().add(newProvvedimento);
                tableProvvedimenti.refresh();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
    public void modifica() {
        if (tableProvvedimenti.getSelectionModel().getSelectedItem() == null) {
            Alerts.errorAllert("Errore", "Selezione del Provvedimento fallita",
                    "Il provvedimento selezionato non è valido");
            return;
        }

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/View/fxml/dialogPaneModifica/modifica_prov_dialogPane.fxml"));

        try {
            DialogPane dialogPane = loader.load();
            DialogPaneModificaProv dialogController = loader.getController();

            dialogController.setModel(modelCreazione);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Modifica Titolo");

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            if (clickedButton.get() == ButtonType.APPLY) {
                updateChanges();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateChanges() {
        if (modelCreazione.getProvvedimentoTmp() == null ||
                modelCreazione.getProvvedimentoTmp().getNome().equals("") ||
                modelCreazione.getProvvedimentoTmp().getRischio().equals("") ||
                modelCreazione.getProvvedimentoTmp().getSoggettiEsposti().equals("") ||
                modelCreazione.getProvvedimentoTmp().getEmail().equals("") ||
                modelCreazione.getProvvedimentoTmp().getStimaR() == 0 ||
                modelCreazione.getProvvedimentoTmp().getStimaD() == 0 ||
                modelCreazione.getProvvedimentoTmp().getStimaP() == 0) {
            Alerts.errorAllert("Errore", "Selezione del Provvedimento fallita",
                    "Il provvedimento selezionato non è valido");
            return;
        }

        Controller.modificaCampo(modelCreazione.getProvvedimentoTmp());
        tableProvvedimenti.refresh();
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
