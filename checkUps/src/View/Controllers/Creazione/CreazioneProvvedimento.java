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
    private TableColumn<Provvedimento, String> nomeColP;

    @FXML
    private TableColumn<Provvedimento, String> rischioCol;

    @FXML
    private TableColumn<Provvedimento, String> soggetiCol;

    @FXML
    private TableColumn<Provvedimento, Integer> stimaCol;

    @FXML
    private TableView<Provvedimento> tableProvvedimenti;

    private List<Provvedimento> listProv;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listProv = ClassHelper.getListProvvedimento();

        nomeColP.setCellValueFactory(new PropertyValueFactory<Provvedimento, String>("nome"));
        rischioCol.setCellValueFactory(new PropertyValueFactory<Provvedimento, String>("rischio"));
        soggetiCol.setCellValueFactory(new PropertyValueFactory<Provvedimento, String>("soggettiEsposti"));
        stimaCol.setCellValueFactory(new PropertyValueFactory<Provvedimento, Integer>("stimaR"));

    }

    @FXML
    public void aggiungi() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/creaProv_dialogPane.fxml"));
        DialogPane dialogPane = loader.load();

        DialogPaneAddP dialogController = loader.getController();

        dialogController.setModel(modelCreazione);
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
            if (dialogController.getNome() != null
                    && !dialogController.getNome().equals("")
                    && dialogController.getRischio() != null
                    && !dialogController.getRischio().equals("")
                    && dialogController.getSoggettiEsposti() != null
                    && !dialogController.getSoggettiEsposti().equals("")
                    && dialogController.getStimaR() != 0
                    && dialogController.getStimaD() != 0
                    && dialogController.getStimaP() != 0) {

                int id = Controller.getNewId(listProv);
                LocalDate data = LocalDate.now();
                Provvedimento newProvvedimento = new Provvedimento(id,
                        localOggetto.getId(),
                        dialogController.getNome(),
                        dialogController.getRischio(),
                        dialogController.getSoggettiEsposti(),
                        dialogController.getStimaR(),
                        dialogController.getStimaD(),
                        dialogController.getStimaP(),
                        dialogController.getEmail(),
                        Optional.of(data),
                        Optional.of(dialogController.getDataFine()));

                Controller.inserisciNuovoRecord(newProvvedimento);

                tableProvvedimenti.getItems().add(newProvvedimento);

                tableProvvedimenti.refresh();
            } else {
                Alerts.errorAllert("Errore", "Errore nell'inserimento",
                        "Qualcosa non è stato inserito correttamente");
            }
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
        // if (tableProvvedimenti.getSelectionModel().getSelectedItem() != null) {

        // Parent root = new Parent() {};
        // modelModifica = new ModelModifica();

        // if(modelCreazione.getOggettoTmp() != null)
        // modelModifica.setOggetto(modelCreazione.getOggettoTmp());
        // else if(localOggetto != null)
        // modelModifica.setOggetto(localOggetto);

        // try {
        // root = modelPaths.switchToModificaProvvedimenti(modelModifica);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        // Controller.changePane(modelPaths.getStackPaneHome(), root);
        // }
    }

    // private void updateChanges(String nome) throws IOException {
    // if (modelModifica.getProvTmp() != null &&
    // modelModifica.getProvTmp().getNome() != "") {

    // modelModifica.getOggettoTmp().setNome(nome);

    // Controller.modificaCampo(modelModifica.getOggettoTmp());
    // tableOggetti.refresh();
    // } else {
    // Alerts.errorAllert("Errore", "Selezione del Reparto fallita", "Il reparto
    // selezionato non è valido");
    // }
    // }

    public void onActionBack() {
        modelCreazione.resetProvvedimentoTmp();
        super.changePage(TipoCreazionePagina.OGGETTO, false);
    }

    @Override
    public void setModel(ModelCreazione modelCreazione, ModelPaths modelPaths) {
        super.setModel(modelCreazione, modelPaths);
        super.fillTable(tableProvvedimenti, listProv, this.localOggetto);

        this.btnModify.disableProperty().bind(modelCreazione.canGoNextProperty().not());
    }

}
