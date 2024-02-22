package View.Controllers.Modifiche;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Controllers.Controller;
import Helpers.ClassHelper;
import Models.Alerts;
import Models.ModelModifica;
import Models.ModelPaths;
import Models.Tables.Reparto;
import Models.Tables.Titolo;
import View.Controllers.Modifiche.DialogPane.DialogPaneModificaTitolo;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ModificaSezioneTitolo implements Initializable {

    @FXML
    private TableView<Titolo> tableViewTitoli;

    @FXML
    private TableColumn<Titolo, String> descCol;

    @FXML
    private TextField filterTable;

    @FXML
    private TableColumn<Titolo, Integer> idCol;

    @FXML
    private TableColumn<Titolo, String> nomeColR;

    @FXML
    private JFXButton btnRefresh;

    private ObservableList<Titolo> observableList = FXCollections.observableArrayList();

    private ModelModifica modelModifica;
    private ModelPaths modelPaths;

    private List<Titolo> listaTitoli = ClassHelper.getListTitolo();
    private List<Reparto> listaReparto = ClassHelper.getListReparto();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        idCol.setCellValueFactory(new PropertyValueFactory<Titolo, Integer>("id"));
        nomeColR.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(listaReparto.stream()
                    .filter(reparto -> reparto.getId() == cellData.getValue().getIdReparto())
                    .findFirst().get()
                    .getNome());
        });

        descCol.setCellValueFactory(new PropertyValueFactory<Titolo, String>("descrizione"));

        tableViewTitoli.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectTitolo(); // Chiama il metodo quando viene selezionato un elemento
            }
        });
    }

    private void updateChanges(DialogPaneModificaTitolo dialogController) throws IOException {

        if (modelModifica.getTitoloTmp() != null &&
                modelModifica.getTitoloTmp().getDescrizione() != "") {

            modelModifica.getTitoloTmp().setDescrizione(dialogController.getDescTitolo());

            Controller.modificaCampo(modelModifica.getTitoloTmp());

        } else {
            Alerts.errorAllert("Errore", "Selezione del Titolo fallita", "Il titolo selezionato non è valido");
        }
    }

    @FXML
    private void modify() throws IOException {

        if (modelModifica.getTitoloTmp() != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/modifica_titolo_dialogPane.fxml"));
            DialogPane dialogPane = loader.load();

            DialogPaneModificaTitolo dialogController = loader.getController();

            dialogController.setModel(modelModifica);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Modifica Titolo");

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            // ------------------- Se viene premuto il tasto "Applica" -------------------
            // //

            if (clickedButton.get() == ButtonType.APPLY) {

                updateChanges(dialogController);
            }
        } else {
            Alerts.errorAllert("Errore", "Selezione del Titolo fallita", "Il titolo selezionato non è valido");
        }
    }

    @FXML
    private void delete() {
        if (tableViewTitoli.getSelectionModel().getSelectedItem() != null) {
            Titolo titolo = tableViewTitoli.getSelectionModel().getSelectedItem();
            Controller.eliminaRecord(titolo, titolo.getId());
            tableViewTitoli.getItems().remove(titolo);
        }
    }

    @FXML
    private void refresh() {
        // try {
        // modelModifica.resetAllTmp();
        // ButtonType clickedButton =
        // modelPaths.showRepartiTitoliDialogPane(modelModifica);

        // if (clickedButton == ButtonType.APPLY) {
        // if(modelModifica.getUnitaLocaleTmp() != null){
        // modelModifica.setSelectedReparto(false);

        // // Parent root = modelPaths.switchToModificaReparto(modelModifica);
        // Controller.changePane(modelPaths.getStackPaneModificaR(), root);
        // } else {
        // Alerts.errorAllert("Errore", "Selezione errata", "Seleziona un'unità
        // locale");
        // }
        // }
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
    }

    public void filterTable() {

        modelModifica.filterTable(filterTable, tableViewTitoli, observableList);

    }

    private void selectTitolo() {

        Titolo titolo = tableViewTitoli.getSelectionModel().getSelectedItem();
        modelModifica.setTitolo(titolo);
    }

    public void setModel(ModelModifica modelModifica, ModelPaths modelPaths) {
        this.modelModifica = modelModifica;
        this.modelPaths = modelPaths;

        observableList = FXCollections.observableArrayList(modelModifica.fillTitoliTable(listaTitoli));
        tableViewTitoli.setItems(observableList);
    }

}
