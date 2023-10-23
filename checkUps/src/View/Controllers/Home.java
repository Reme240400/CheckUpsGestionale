package View.Controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import Controllers.ClassHelper;
import Controllers.ControllerDb;
import Models.Tables.Societa;
import Models.Tables.UnitaLocale;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class Home implements Initializable {

    private int id = -1;
    List<UnitaLocale> listUnitaLocale = ClassHelper.getListUnitaLocale();
    List<Societa> listSocieta = ClassHelper.getListSocieta();

    @FXML
    private JFXComboBox<String> cercaSocieta;

    @FXML
    private JFXComboBox<String> cercaUnitaLocale;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // * *************** inizializza i campi *************** //
        ControllerDb.popolaListaSocietaDaDb();
        ControllerDb.popolaListaUnitaLocaleDaDb();

        List<Societa> listSocieta = ClassHelper.getListSocieta();

        ObservableList<String> societies = FXCollections.observableArrayList();

        // * *************** popola i combobox *************** //
        cercaSocieta.getItems().add("Nuovo");
        for (Societa societa : listSocieta) {
            cercaSocieta.getItems().add(societa.getNome());
            societies.add(societa.getNome());
        }

        // * **************************************** //

        // * filtra i Combobox
        FilteredList<String> filteredItems = ViewController.filterComboBox(cercaSocieta, societies);

        cercaSocieta.setItems(filteredItems);
        
        // * ************************************************ //
    }

    public void onSelectedSocieta() {

        // cercaUnitaLocale.getItems().removeAll();
        // cercaUnitaLocale.getSelectionModel().clearSelection();

        cercaSocieta.getSelectionModel()
                .selectedItemProperty()
                .addListener((options, oldValue, newValue) -> {

                    this.id = listSocieta.stream()
                            .filter(societa -> societa.getNome().equals(newValue)).findFirst()
                            .get().getIdSocieta();

                    System.out.println("Societa selezionata: " + id);
                    
                });
                
        if (this.id != -1) {
            System.out.println("Societa inculata: " + id);
            listUnitaLocale = listUnitaLocale.stream().filter(unitaLocale -> unitaLocale.getIdSocieta() == this.id)
                    .toList();

            ObservableList<String> units = FXCollections.observableArrayList();

            // * *************** popola i combobox *************** //

           // cercaUnitaLocale.getItems().add("Nuovo");

            for (UnitaLocale unitaLocale : listUnitaLocale) {
                System.out.println("aggiunte unita locali");
                cercaUnitaLocale.getItems().add(unitaLocale.getNome());
                units.add(unitaLocale.getNome());
            }

            // * **************************************** //

            // * filtra i Combobox
            FilteredList<String> filteredUnita = ViewController.filterComboBox(cercaUnitaLocale, units);

            cercaUnitaLocale.setItems(filteredUnita);

        }
    }

    

}
