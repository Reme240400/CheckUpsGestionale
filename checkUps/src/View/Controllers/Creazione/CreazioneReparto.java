package View.Controllers.Creazione;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.Controller;
import Helpers.ClassHelper;
import Interfaces.CreazioneInterface;
import Interfaces.CreazioneTInterface;
import Models.Alerts;
import Models.Model;
import Models.ModelCreazione;
import Models.ModelModifica;
import Models.ModelPaths;
import Models.Tables.Reparto;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import View.Controllers.ViewController;
import View.Controllers.Creazione.DialogPane.DialogPaneAddR;
import View.Controllers.Modifiche.DialogPane.DialogPaneModificaReparto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
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

public class CreazioneReparto implements Initializable, CreazioneTInterface {

    @FXML
    private TableView<Reparto> tableReparti;


    @FXML
    private TableColumn<Reparto, String> nomeCol;

    @FXML
    private TableColumn<Reparto, String> descCol;

    @FXML
    private JFXButton btnAggiungi;

    @FXML
    private JFXButton btnModifica;

    @FXML
    private JFXButton btnNext;

    private ModelCreazione modelCreazione;
    private ModelPaths modelPaths;
    private ModelModifica modelModifica;

    private List<Reparto> listaReparto;

    private Societa localSocieta = null;
    private UnitaLocale localUnita = null;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        listaReparto = ClassHelper.getListReparto();

        nomeCol.setCellValueFactory(new PropertyValueFactory<Reparto, String>("nome"));
        descCol.setCellValueFactory(new PropertyValueFactory<Reparto, String>("descrizione"));

    }

    // --------------- popola la tabella dei reparti --------------- //
    private void fillTableView() {
        List<Reparto> specificList = null;
        ObservableList<Reparto> observableList = null;

        if (localSocieta != null && localUnita != null) {
            specificList = modelCreazione.fillRepartiTable(listaReparto, localUnita);

            observableList = FXCollections.observableArrayList(specificList);
            tableReparti.setItems(observableList);
        } else
            Alerts.errorAllert("Errore2", "Societa non selezionata",
                    "Impossibile selezionare l'unita locale perchè non è stata selezionata una societa");
    }

    // --------------- va alla schermata di modifica --------------- //
    public void modifica() {
        if (tableReparti.getSelectionModel().getSelectedItem() != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/dialogPaneModifica/modifica_reparto_dialogPane.fxml"));
            DialogPane dialogPane;
            try {
                dialogPane = loader.load();
            
                DialogPaneModificaReparto dialogController = loader.getController();

                dialogController.setModel(modelModifica);

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(dialogPane);

                Optional<ButtonType> clickedButton = dialog.showAndWait();

                if (clickedButton.get() == ButtonType.APPLY){}
                    //updateChanges(dialogController);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alerts.errorAllert("Errore", "Selezione del Reparto fallita", "Il reparto selezionato non è valido");
        }
        
    }

    // --------------- elimina il reparto selezionato --------------- //
    public void delete() {
        if (tableReparti.getSelectionModel().getSelectedItem() != null) {
            Reparto reparto = tableReparti.getSelectionModel().getSelectedItem();
            Controller.eliminaRecord(reparto, reparto.getId());
            tableReparti.getItems().remove(reparto);
        }
    }

    // --------------- apre un dialog Pane per creare il Reparto --------------- //
    public void aggiungi() {

        if (localSocieta != null && localUnita != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/dialogPane/creaReparto_dialogPane.fxml"));
            DialogPane dialogPane;

            try {
                dialogPane = loader.load();

                DialogPaneAddR dialogController = loader.getController();

                dialogController.setModel(modelCreazione);
                dialogController.fillTextBox(localSocieta.getNome(),
                        localUnita.getNome());

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(dialogPane);

                dialog.setTitle("Crea Reparto");

                Optional<ButtonType> clickedButton = dialog.showAndWait();

                // ------------------- Se viene premuto il tasto "Applica" ------------------- //

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
                        modelCreazione.createRepartoTmp(newReparto);
                        Controller.inserisciNuovoRecord(newReparto);

                        tableReparti.getItems().add(newReparto);

                        tableReparti.refresh();

                    } else {
                        Alerts.errorAllert("Errore", "Errore nell'inserimento",
                                "Qualcosa non è stato inserito correttamente");
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else
            Alerts.errorAllert("Errore", "Errore nella Selezione dell'Unita Locale",
                    "Non è stato selezionato nessuna unita locale");

    }

    // --------------- va alla schermata di creazione Titolo --------------- //
    public void saveAndGoNext() {
        if (tableReparti.getSelectionModel().getSelectedItem() != null) {
            Reparto reparto = tableReparti.getSelectionModel().getSelectedItem();

            modelCreazione.createRepartoTmp(reparto);

            try {
                Parent root = modelPaths.switchToCreazioneTitolo(modelCreazione);

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

        this.btnNext.disableProperty().bind(modelCreazione.canGoNextProperty().not());
        this.btnModifica.disableProperty().bind(modelCreazione.canGoNextProperty().not());

        if (modelCreazione.getSocietaTmp() != null) {
            this.localSocieta = modelCreazione.getSocietaTmp();
        }

        if (modelCreazione.getUnitaLocaleTmp() != null) {
            this.localUnita = modelCreazione.getUnitaLocaleTmp();
        }

        tableReparti.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Handle the selection change, newValue contains the selected Reparto
            if (newValue != null) {
                modelCreazione.setCanGoNext(true);
            }
        });

        fillTableView();
    }

}
