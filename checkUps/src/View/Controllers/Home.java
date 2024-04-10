package View.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Controllers.Controller;
import Helpers.ClassHelper;
import Models.ModelPaths;
import Models.ModelValutaRischi;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import Models.others.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class Home implements Initializable {

    private List<UnitaLocale> listUnitaLocale = ClassHelper.getListUnitaLocale();
    private List<Societa> listSocieta = ClassHelper.getListSocieta();

    @FXML
    private JFXComboBox<String> cercaSocieta;

    @FXML
    private JFXComboBox<String> cercaUnitaLocale;

    @FXML
    private JFXButton btnValutaRischi;

    private StackPane stackPane;

    private ModelPaths modelPaths;
    private ModelValutaRischi modelValutaRischi;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // * *************** inizializza i campi *************** //

        ObservableList<String> societies = FXCollections
                .observableArrayList(listSocieta.stream().map(s -> s.getNome()).toList());

        // * filtra i Combobox
        FilteredList<String> filteredItems = ViewController.filterComboBox(cercaSocieta, societies);

        cercaSocieta.setItems(filteredItems);

        // * ************************************************ //
    }

    public void onSelectedSocieta() {

        if (cercaSocieta.getValue() != null) {
            // Remove the previous listener, if any
            String selectedSocieta = cercaSocieta.getValue();

            int id = listSocieta.stream()
                    .filter(s -> s.getNome().equals(selectedSocieta))
                    .findFirst().get().getId();

            // Retrieve the list of UnitaLocale based on the selected Societa
            ObservableList<String> unitalocali = FXCollections.observableArrayList(listUnitaLocale.stream()
                    .filter(u -> u.getIdSocieta() == id)
                    .map(UnitaLocale::getNome)
                    .toList());

            FilteredList<String> filteredItems = ViewController.filterComboBox(cercaUnitaLocale, unitalocali);
            cercaUnitaLocale.setItems(filteredItems);
        }
    }

    public void showScadenze(){
        ViewController.mostraScadenze();
    }

    public void goToValutaRischi() {
        if (cercaSocieta.getValue() != null && cercaUnitaLocale.getValue() != null && !cercaSocieta.getValue().isEmpty()
                && !cercaUnitaLocale.getValue().isEmpty()) {
            try {
                Societa societa = listSocieta.stream()
                        .filter(s -> s.getNome().equals(cercaSocieta.getValue()))
                        .findFirst().get();

                UnitaLocale unitaLocale = listUnitaLocale.stream()
                        .filter(u -> u.getIdSocieta() == societa.getId())
                        .filter(u -> u.getNome().equals(cercaUnitaLocale.getValue()))
                        .findFirst().get();

                Parent root = modelPaths.switchToValutaRischi(modelValutaRischi, societa, unitaLocale);

                stackPane = modelPaths.getStackPaneHome();

                if (root != null) {
                    Controller.changePane(stackPane, root);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alerts.errorAlert("Errore", "Dati non validi", "Seleziona una società e un'unità locale per procedere");
        }

    }

    public void setModel(ModelValutaRischi modelValutaRischi, ModelPaths modelPaths) {

        this.modelPaths = modelPaths;
        this.modelValutaRischi = modelValutaRischi;

    }
}
