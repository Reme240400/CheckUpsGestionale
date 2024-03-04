package View.Controllers.Creazione;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import Controllers.Controller;
import Helpers.ClassHelper;
import Models.Alerts;
import Models.ModelCreazione;
import Models.ModelPaths;
import Models.TipoCreazionePagina;
import Models.Tables.Titolo;
import Models.creazione.CreazioneBase;
import View.Controllers.Creazione.DialogPane.DialogPaneAddT;
import View.Controllers.Modifiche.DialogPaneModificaTitolo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
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

    public void modifica() {
        if (tableTitoli.getSelectionModel().getSelectedItem() != null) {
            Alerts.errorAllert("Errore", "Selezione del Titolo fallita", "Il titolo selezionato non è valido");
            return;
        }

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/View/fxml/dialogPaneModifica/modifica_titolo_dialogPane.fxml"));

        try {
            DialogPane dialogPane = loader.load();
            DialogPaneModificaTitolo dialogController = loader.getController();

            dialogController.setModel(modelCreazione);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Modifica Titolo");

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            if (clickedButton.get() == ButtonType.APPLY) {
                updateChanges(dialogController.getDescTitolo());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateChanges(String desc) {
        if (modelCreazione.getTitoloTmp() == null ||
                modelCreazione.getTitoloTmp().getDescrizione().equals("")) {
            Alerts.errorAllert("Errore", "Selezione del Titolo fallita", "Il reparto selezionato non è valido");
            return;
        }

        modelCreazione.getTitoloTmp().setDescrizione(desc);
        Controller.modificaCampo(modelCreazione.getTitoloTmp());
        tableTitoli.refresh();
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

    // CODICE "SISTEMATO"

    public void onActionSave() {
        if (tableTitoli.getSelectionModel().getSelectedItem() == null) {
            Alerts.errorAllert("Errore", "Errore nella Selezione del Titolo",
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
