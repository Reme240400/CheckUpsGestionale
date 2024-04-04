package View.Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.Controller;
import Controllers.ControllerDb;
import Helpers.ClassHelper;
import Models.Dialogs;
import Models.Model;
import Models.ModelCreazione;
import Models.ModelModifica;
import Models.ModelPaths;
import Models.ModelValutaRischi;
import Models.Tables.Provvedimento;
import View.Controllers.Creazione.dialogPane.DPScadenze;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;

public class ViewController implements Initializable {
    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnCreate;

    @FXML
    private StackPane stackPane;

    protected static ModelPaths modelPaths = new ModelPaths();
    protected static ModelCreazione modelCreazione = new ModelCreazione();
    protected static ModelModifica modelModifica = new ModelModifica();
    protected static ModelValutaRischi modelValutaRischi = new ModelValutaRischi();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ControllerDb.popolaListeDaDatabase();
        mostraScadenze();

        try {
            switchToHome();

            modelPaths.setStackPaneHome(stackPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostraScadenze() {
        var expired = new ArrayList<Provvedimento>();
        var provs = ClassHelper.getListProvvedimento();

        for (Provvedimento p : provs) {
            var scadenza = p.getDataScadenza();
            if (scadenza.isEmpty())
                continue;

            if (scadenza.get().isBefore(LocalDate.now()) || scadenza.get().isEqual(LocalDate.now())) {
                expired.add(p);
            }
        }

        if (expired.isEmpty())
            return;

        Dialogs.showDialog("verifica_scadenze.fxml", "Provvedimenti scaduti",
                (DPScadenze controller) -> controller.populate(expired));
    }

    public void switchToHome() throws IOException {
        Parent root = modelPaths.switchToHome();
        modelCreazione.resetAllTmp();
        if (root != null) {
            Controller.changePane(stackPane, root);
        }

    }

    public void switchToCreazione() throws IOException {
        Parent root = modelPaths.switchToCreazione(modelCreazione);
        modelCreazione.resetAllTmp();

        if (root != null) {
            Controller.changePane(stackPane, root);
        }

    }

    public void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle("Esci");
        alert.setHeaderText("Stai per uscire!");
        alert.setContentText("Vuoi salvare il lavoro prima di uscire?");
    }

    public static FilteredList<String> filterComboBox(JFXComboBox<String> cercaItem, ObservableList<String> units) {

        return Model.filterComboBox(cercaItem, units);
    }

    public static FilteredList<String> filterComboBoxById(JFXComboBox<String> cercaItem, int id,
            ObservableList<String> units) {

        return Model.filterComboBoxById(cercaItem, id, units);
    }

}
