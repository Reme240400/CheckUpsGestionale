package View.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.Controller;
import Controllers.ControllerDb;
import Models.Model;
import Models.ModelCreazione;
import Models.ModelModifica;
import Models.ModelPaths;
import Models.ModelValutaRischi;

import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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

        try {
            switchToHome();

            modelPaths.setStackPaneHome(stackPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToHome() throws IOException {
        Parent root = modelPaths.switchToHome();
        if (root != null) {
            Controller.changePane(stackPane, root);
        }

    }

    public void switchToCreazione() throws IOException {
        Parent root = modelPaths.switchToCreazione(modelCreazione);

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
