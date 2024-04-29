package View.Controllers.Creazione;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import Controllers.Controller;
import Helpers.ClassHelper;
import Models.ModelCreazione;
import Models.ModelPaths;
import Models.Tables.Oggetto;
import Models.Tables.Provvedimento;
import Models.Tables.Reparto;
import Models.Tables.Titolo;
import Models.others.Alerts;
import Models.others.CreazioneBase;
import Models.others.TipoCreazionePagina;
import View.Controllers.Creazione.dialogPane.DialogPaneAddR;
import View.Controllers.Modifiche.DialogPaneModificaReparto;

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

public class CreazioneReparto extends CreazioneBase implements Initializable {

    @FXML
    private TableView<Reparto> tableReparti;

    @FXML
    private TableColumn<Reparto, String> nomeCol;

    @FXML
    private TableColumn<Reparto, String> descCol;

    @FXML
    private TableColumn<Reparto, String> revisioneCol;

    @FXML
    private TableColumn<Reparto, String> dataCol;

    @FXML
    private JFXButton btnAggiungi;

    @FXML
    private JFXButton btnModifica;

    @FXML
    private JFXButton btnNext;

    private List<Reparto> listaReparto;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        listaReparto = ClassHelper.getListReparto();

        nomeCol.setCellValueFactory(new PropertyValueFactory<Reparto, String>("nome"));
        revisioneCol.setCellValueFactory(new PropertyValueFactory<Reparto, String>("revisione"));
        dataCol.setCellValueFactory(data -> {
            var dataInizio = data.getValue().getData();
            return dataInizio.isPresent() ? new SimpleStringProperty(dataInizio.get().toString())
                    : new SimpleStringProperty("");
        });
        descCol.setCellValueFactory(new PropertyValueFactory<Reparto, String>("descrizione"));
    }

    // --------------- va alla schermata di modifica --------------- //
    public void modifica() {
        if (tableReparti.getSelectionModel().getSelectedItem() == null) {
            Alerts.errorAlert("Errore", "Selezione del Reparto fallita", "Il reparto selezionato non è valido");
            return;
        }

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/View/fxml/dialogPaneModifica/modifica_reparto_dialogPane.fxml"));
        try {
            DialogPane dialogPane = loader.load();
            DialogPaneModificaReparto dialogController = loader.getController();

            dialogController.setModel(modelCreazione);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Modifica Reparto");

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            if (clickedButton.get() == ButtonType.APPLY) {
                var checkResponse = dialogController.areFieldsValid();
                if (!checkResponse.isValid()) {
                    Alerts.errorAlert("Errore", "Errore nella modifica", checkResponse.getErrorMessage());
                    return;
                }

                updateChanges(dialogController.getNomeReparto(), dialogController.getDescReparto(),
                        dialogController.getRevisioneReparto(),
                        dialogController.getDataReparto());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateChanges(String nome, String desc, String rev, LocalDate data) {
        modelCreazione.getRepartoTmp().setNome(nome);
        modelCreazione.getRepartoTmp().setRevisione(rev);
        modelCreazione.getRepartoTmp().setData(Optional.of(data));
        modelCreazione.getRepartoTmp().setDescrizione(desc);

        Controller.modificaCampo(modelCreazione.getRepartoTmp());
        tableReparti.refresh();
    }

    @FXML
    public void onElimina() {
        if (tableReparti.getSelectionModel().getSelectedItem() == null)
            return;

        Reparto reparto = tableReparti.getSelectionModel().getSelectedItem();
        if (Alerts.deleteAlert(reparto.getNome()) == false)
            return;

        List<Provvedimento> provvedimenti = new ArrayList<>();
        List<Oggetto> oggetti = new ArrayList<>();
        List<Titolo> titoli = ClassHelper.getListTitolo().stream().filter(t -> t.getId() == reparto.getId()).toList();
        for (Titolo titolo : titoli) {
            oggetti.addAll(ClassHelper.getListOggetto().stream().filter(oggetto -> oggetto.getIdTitolo() == titolo.getId()).toList());

            for (Oggetto oggetto : oggetti) {
                List<Provvedimento> toDelete = ClassHelper.getListProvvedimento().stream().filter(prov -> prov.getIdOggetto() == oggetto.getId()).toList();
                provvedimenti.addAll(toDelete);
            }
        }

        provvedimenti.forEach(Provvedimento::selfRemoveFromList);
        oggetti.forEach(Oggetto::selfRemoveFromList);
        titoli.forEach(Titolo::selfRemoveFromList);
        Controller.eliminaRecord(reparto);
        tableReparti.getItems().remove(reparto);
        tableReparti.refresh();
    }

    // --------------- apre un dialog Pane per creare il Reparto --------------- //
    public void aggiungi() {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/View/fxml/dialogPaneCreazione/creaReparto_dialogPane.fxml"));
        DialogPane dialogPane;

        try {
            dialogPane = loader.load();

            DialogPaneAddR dialogController = loader.getController();

            dialogController.fillTextBox(localSocieta.getNome(),
                    localUnita.getNome());

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Crea Reparto");

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            if (clickedButton.get() == ButtonType.APPLY) {
                if (dialogController.getNome() != null
                        && !dialogController.getNome().equals("")
                        && dialogController.getData() != null) {

                    int id = Controller.getNewId(listaReparto);
                    Reparto newReparto = new Reparto(id,
                            localUnita.getId(),
                            dialogController.getNome(),
                            dialogController.getRevisione(),
                            dialogController.getDesc(),
                            Optional.of(dialogController.getData()));

                    Controller.inserisciNuovoRecord(newReparto);

                    tableReparti.getItems().add(newReparto);

                    tableReparti.refresh();

                } else {
                    Alerts.errorAlert("Errore", "Errore nell'inserimento",
                            "Qualcosa non è stato inserito correttamente");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Va alla schermata di creazione Titolo
    public void onActionSave() {
        if (tableReparti.getSelectionModel().getSelectedItem() == null) {
            Alerts.errorAlert("Errore", "Errore nella Selezione del Reparto",
                    "Non è stato selezionato nessun reparto");
            return;
        }

        super.changePage(TipoCreazionePagina.TITOLO, true);
    }

    public void onActionBack() {
        modelCreazione.resetRepartoTmp();
        super.changePage(TipoCreazionePagina.UNITA_LOCALE, false);
    }

    @Override
    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths) {
        super.setModel(modelCreazione, modelPaths);
        super.fillTable(tableReparti, listaReparto, localUnita);

        this.btnNext.disableProperty().bind(modelCreazione.canGoNextProperty().not());
        this.btnModifica.disableProperty().bind(modelCreazione.canGoNextProperty().not());

        tableReparti.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedReparto) -> {
            // Handle the selection change, newValue contains the selected Reparto
            if (selectedReparto != null) {
                modelCreazione.setCanGoNext(true);
                modelCreazione.createRepartoTmp(selectedReparto);
            }
        });

        if (this.localReparto != null) {
            tableReparti.selectionModelProperty().get().select(this.localReparto);
        }
    }

}
